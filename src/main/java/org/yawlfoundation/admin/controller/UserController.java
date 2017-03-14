package org.yawlfoundation.admin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.yawlfoundation.admin.AdminException;
import org.yawlfoundation.admin.Constant;
import org.yawlfoundation.admin.data.Tenant;
import org.yawlfoundation.admin.data.User;
import org.yawlfoundation.admin.util.SessionUtil;
import org.yawlfoundation.admin.util.UserUtil;
import org.yawlfoundation.admin.view.Plain;

/**
 * Created by gary on 1/03/2017.
 */
@Controller
@ComponentScan(basePackageClasses = Plain.class)
//@RequestMapping("/user/*")
public class UserController {

    @Autowired
    private SessionUtil sessionUtil;

    @Autowired
    private UserUtil userUtil;

    @RequestMapping(path = "/user/getAccounts")
    public String getAccounts(@RequestParam(name = "sessionHandle")String sessionHandle,
                              Model model) throws AdminException {
        if(!sessionUtil.checkSessionHandle(sessionHandle)){
            throw new AdminException("getAccounts","Invalid Session");
        }else {
            Tenant tenant=sessionUtil.getUserBySession(sessionHandle).getTenant();
            model.addAttribute(Constant.RESULT,userUtil.getAccounts(tenant));
            return Constant.PLAIN;
        }
    }

    @RequestMapping(path = "/user/getPassword")
    public String getPassord(@RequestParam(name = "sessionHandle")String sessionHandle,
                              @RequestParam(name = "userID")String userID,
                              Model model) throws AdminException {
        if(!sessionUtil.checkSessionHandle(sessionHandle)){
            throw new AdminException("getPassword","Invalid Session");
        }else {
            Tenant tenant=sessionUtil.getUserBySession(sessionHandle).getTenant();
            model.addAttribute(Constant.RESULT,userUtil.getClientPassword(tenant,userID));
            return Constant.PLAIN;
        }
    }

    @RequestMapping(path = "/user/checkConnection")
    public String checkConnection(@RequestParam(name = "sessionHandle")String sessionHandle,
                                  Model model){
        model.addAttribute(Constant.RESULT,Constant.SUCCESS);
        return Constant.PLAIN;
    }

    @RequestMapping(path = "/user/connect")
    public String connect(@RequestParam(value = "userid")String userID,
                          @RequestParam(value = "password")String password,
                          Model model){

        if(!userID.contains(":")){
            throw new RuntimeException("connect: Wrong UserID");
        }else{
            User user=userUtil.getUserByUserName(userID);
            if(!user.getUserPassword().equals(password)){
                throw new RuntimeException("connect: Wrong Password");
            }
            model.addAttribute(Constant.RESULT,sessionUtil.createSessionHandle(user));
            return Constant.PLAIN;
        }
    }







}
