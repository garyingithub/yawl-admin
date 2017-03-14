package org.yawlfoundation.admin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.yawlfoundation.admin.AdminException;
import org.yawlfoundation.admin.Constant;
import org.yawlfoundation.admin.data.Specification;
import org.yawlfoundation.admin.data.Tenant;
import org.yawlfoundation.admin.util.CaseUtil;
import org.yawlfoundation.admin.util.EngineUtil;
import org.yawlfoundation.admin.util.SessionUtil;
import org.yawlfoundation.admin.util.SpecificationUtil;

import java.util.concurrent.Callable;

/**
 * Created by gary on 1/03/2017.
 */
@Controller
public class CaseController {

    private final String RESULT="result";

    @Autowired
    private CaseUtil caseUtil;

    @Autowired
    private SessionUtil sessionUtil;

    @Autowired
    private EngineUtil engineUtil;

    @Autowired
    private SpecificationUtil specificationUtil;

    @RequestMapping(method = RequestMethod.POST,path = "/case/launchCase")
    public Callable<String> launchCase(@RequestParam(name = "sessionHandle")String sessionHandle,
                               @RequestParam(name = "specidentifier")String specidentifier,
                                       Model model) throws AdminException {
        if(!sessionUtil.checkSessionHandle(sessionHandle)){
            throw new AdminException("getSpecificationPrototypesList","Invalid Session");
        }else {
            //Tenant tenant=sessionUtil.getUserBySession(sessionHandle).getTenant();
            Specification specification=specificationUtil.getSpecificationByIdentification(specidentifier);

            return new Callable<String>() {
                @Override
                public String call() throws Exception {
                    model.addAttribute(Constant.RESULT,caseUtil.launchCase(engineUtil.getRandomEngine(),specification));
                    return Constant.PLAIN;
                }
            };
        }
    }

    @RequestMapping(method = RequestMethod.POST,path = "/case/getAllRunningCases")
    public String getAllRunningCases(@RequestParam(name="sessionHandle")String sessionHandle,
                                     Model model) throws AdminException {
        if (!sessionUtil.checkSessionHandle(sessionHandle)) {
            throw new AdminException("getAllRunningCases", "Invalid Session");
        }else {
            Tenant tenant=sessionUtil.getUserBySession(sessionHandle).getTenant();
            model.addAttribute(Constant.RESULT,caseUtil.getAllRunningCases(tenant));
            return Constant.PLAIN;
        }
    }



}
