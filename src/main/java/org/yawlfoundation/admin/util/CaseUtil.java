package org.yawlfoundation.admin.util;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.yawlfoundation.admin.data.*;
import org.yawlfoundation.admin.data.Repositories.CaseRepository;
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
    private RequestHelper helper;

    @Autowired
    private SpecificationUtil specificationUtil;

    public Engine getEngineByCaseID(String caseID){

        YawlCase yawlCase=this.getObjectById(Long.parseLong(caseID));

        return yawlCase.getEngine();

    }

    public CustomService getResourceServiceByCaseID(String caseID){

        YawlCase yawlCase=this.getObjectById(Long.getLong(caseID));

        return yawlCase.getSpecification().getTenant().getDefaultWorkList();

    }

    public String   launchCase(Engine engine,Specification specification) throws IOException {

        String result;


        for(CustomService service:specification.getServices()){
            result=helper.registerService(engine,service);
            if(YawlUtil.isFailure(result)){
                return result;
            }
        }

        result=helper.loadSpecification(engine,specification);

        if(YawlUtil.isFailure(result)&&!result.contains("already")&&!result.contains("warning")){

            return result;
        }
        YawlCase c=new YawlCase();
        c.setEngine(engine);
        c.setSpecification(specification);

        this.storeObject(c);

        result=helper.launchCase(engine,c);

        if(YawlUtil.isFailure(result)){
            return result;
        }


        return String.valueOf(c.getCaseId());
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
