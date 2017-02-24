package org.yawlfoundation.admin.Utils;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.yawlfoundation.admin.Data.*;
import org.yawlfoundation.admin.Data.Repositories.CaseRepository;
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

    public String launchCase(Engine engine,Specification specification) throws IOException {

        helper.loadSpecification(engine,specification);
        for(CustomService service:specification.getServices()){
            helper.registerService(engine,service);
        }

        YawlCase c=new YawlCase();
        c.setEngine(engine);
        c.setSpecification(specification);

        this.storeObject(c);

        String result=helper.launchCase(engine,c);

        System.out.println(result);

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
