package org.yawlfoundation.admin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.yawlfoundation.admin.AdminException;
import org.yawlfoundation.admin.Constant;
import org.yawlfoundation.admin.data.Tenant;
import org.yawlfoundation.admin.util.SessionUtil;
import org.yawlfoundation.admin.util.SpecificationUtil;

/**
 * Created by gary on 1/03/2017.
 */
@Controller
@RequestMapping("/specification")
public class SpecificationController {





    @Autowired
    private SessionUtil sessionUtil;

    @Autowired
    private SpecificationUtil specificationUtil;


    @RequestMapping(method = RequestMethod.POST,path = "/specification/upload")
    public String upload(@RequestParam(name = "sessionHandle")String sessionHandle,
                         @RequestParam(name = "specXML")String specXML,
                         Model model) throws AdminException {

        if(!sessionUtil.checkSessionHandle(sessionHandle)){
            throw new AdminException("/specification/upload","Invalid Session");
        }else {
            Tenant tenant=sessionUtil.getUserBySession(sessionHandle).getTenant();
            specificationUtil.loadSpecification(specXML,tenant);
            model.addAttribute(Constant.RESULT,Constant.SUCCESS);
            return Constant.PLAIN;
        }
    }

    @PostMapping({"/getSpecificationPrototypesList","/specification/getList"})
    public String getSpecificationPrototypesList(@RequestParam(name = "sessionHandle")String sessionHandle,
                                                 Model model) throws AdminException {
        if(!sessionUtil.checkSessionHandle(sessionHandle)){
            throw new AdminException("getSpecificationPrototypesList","Invalid Session");
        }else {
            Tenant tenant=sessionUtil.getUserBySession(sessionHandle).getTenant();
            model.addAttribute(Constant.RESULT,specificationUtil.getSpecificationList(tenant));
            return Constant.PLAIN;
        }
    }



}
