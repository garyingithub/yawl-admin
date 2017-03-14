package org.yawlfoundation.admin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.yawlfoundation.admin.AdminException;
import org.yawlfoundation.admin.Constant;
import org.yawlfoundation.admin.data.Tenant;
import org.yawlfoundation.admin.util.CustomServiceUtil;
import org.yawlfoundation.admin.util.SessionUtil;

/**
 * Created by gary on 1/03/2017.
 */
@Controller
public class CustomServiceController {

    @Autowired
    private SessionUtil sessionUtil;

    @Autowired
    private CustomServiceUtil customServiceUtil;


    @RequestMapping(method = RequestMethod.POST,path = "/customService/newYAWLService")
    public String newYAWLService(@RequestParam(name = "sessionHandle")String sessionHandle,
                         @RequestParam(name = "service")String serviceStr,
                         Model model) throws AdminException {

        if(!sessionUtil.checkSessionHandle(sessionHandle)){
            throw new AdminException("newYAWLService","Invalid Session");
        }else {
            Tenant tenant=sessionUtil.getUserBySession(sessionHandle).getTenant();
            customServiceUtil.addYawlService(tenant,serviceStr);
            model.addAttribute(Constant.RESULT,Constant.SUCCESS);
            return Constant.PLAIN;
        }
    }

    @RequestMapping(method = RequestMethod.POST,path = "/customService/getYAWLServices")
    public String getYAWLServices(@RequestParam(name = "sessionHandle")String sessionHandle,
                                  Model model) throws AdminException {

        if(!sessionUtil.checkSessionHandle(sessionHandle)){
            throw new AdminException("getYAWLServices","Invalid Session");
        }else {
            Tenant tenant=sessionUtil.getUserBySession(sessionHandle).getTenant();
            model.addAttribute(Constant.RESULT,customServiceUtil.getYAWLServices(tenant));
            return Constant.PLAIN;
        }
    }

}
