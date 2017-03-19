package org.yawlfoundation.admin.util;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;
import org.yawlfoundation.admin.data.*;
import org.yawlfoundation.admin.data.repository.CaseRepository;
import org.yawlfoundation.yawl.util.XNode;

import javax.annotation.PostConstruct;
import java.io.IOException;

/**
 * Created by root on 17-2-14.
 */
@Component
public class CaseUtil extends BaseUtil<YawlCase> {

    @Autowired
    private CaseRepository caseRepository;

    @Autowired
    private EngineUtil engineUtil;

    @Autowired
    private RequestUtil util;

    @Autowired
    private SpecificationUtil specificationUtil;

    @Autowired
    private JpaTransactionManager transactionManager;


    public Engine getEngineByCaseID(String caseID){

        YawlCase yawlCase=this.getObjectById(Long.parseLong(caseID));

        return yawlCase.getEngine();

    }

    public CustomService getResourceServiceByCaseID(String caseID){

        YawlCase yawlCase=this.getObjectById(Long.getLong(caseID));

        return yawlCase.getSpecification().getTenant().getDefaultWorkList();

    }


    public String  launchCase(Engine engine,Specification specification) throws IOException {

        String result;

        result=util.loadSpecification(engine,specification);

       // System.out.println(result);
        if(YawlUtil.isFailure(result)&&!result.contains("already")&&!result.contains("warning")){
            throw new IOException(result);
        }

        TransactionTemplate template=new TransactionTemplate();
        template.setTransactionManager(transactionManager);


        return template.execute(new TransactionCallback<String>() {
            @Override
            public String doInTransaction(TransactionStatus transactionStatus) {
                String result2="";
                YawlCase c=new YawlCase();
                c.setEngine(engine);
                c.setSpecification(specification);

                storeObject(c);


                try {
                    result2=util.launchCase(engine,c);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }


                if(YawlUtil.isFailure(result2)){
                    throw new RuntimeException(result2);
                    //throw new IOException(result2);
                }

                return String.valueOf(c.getCaseId());
            }
        });







    }

    public String getAllRunningCases(Tenant tenant){

        XNode node=new XNode("AllRunningCases");
        for(Specification specification:specificationUtil.getSpecificationsbyTenant(tenant)){
            if(this.caseRepository.findBySpecification(specification).size()>0) {
                XNode idNode = node.addChild("specificationID");
                idNode.addAttribute("identifier", specification.getUniqueID());
                idNode.addAttribute("version", specification.getVersion());
                idNode.addAttribute("uri", specification.getUri());

                for (YawlCase c : this.caseRepository.findBySpecification(specification)) {
                    idNode.addChild("caseID", c.getCaseId());
                }
            }
        }
        return node.toString();
    }

    @PostConstruct
    private void init(){
        this.repository=caseRepository;
    }









}
