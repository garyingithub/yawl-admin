package org.yawlfoundation.admin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.yawlfoundation.admin.Constant;
import org.yawlfoundation.admin.data.Engine;
import org.yawlfoundation.admin.util.CaseUtil;
import org.yawlfoundation.admin.util.RequestHelper;

import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.Callable;

/**
 * Created by gary on 28/02/2017.
 */
@Controller
public class WorkItemController {

    @Autowired
    private CaseUtil caseUtil;

    @Autowired
    private RequestHelper requestHelper;

    @RequestMapping(method = RequestMethod.POST,path = "/workitem")
    public Callable<String> processWorkItem(@RequestParam(value = "workItemID",required = false)String workitemID,
                                            @RequestParam(value = "taskID",required = false)String taskID,
                                            final HttpServletRequest request,
                                            final Model model){


        String caseID=workitemID.split(":")[0];
        if(caseID.contains(".")){
            caseID=caseID.split(".")[0];
        }
        Engine engine=caseUtil.getEngineByCaseID(caseID);

        return () -> {
            model.addAttribute(Constant.RESULT,requestHelper.sendRequest(engine,
                    request));

            return Constant.PLAIN;
        };

    }

}
