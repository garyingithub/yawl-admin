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
import org.yawlfoundation.admin.util.*;

import java.util.concurrent.Callable;

/**
 * Created by gary on 1/03/2017.
 */
@Controller
public class YawlController {

    @RequestMapping(method = RequestMethod.POST,path = "/yawl/getBuildProterties")
    public String launchCase(Model model) throws AdminException {

        model.addAttribute(Constant.RESULT, YawlUtil.getBuildProperties());
        return Constant.PLAIN;

    }






}
