package org.yawlfoundation.admin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.yawlfoundation.admin.Constant;
import org.yawlfoundation.admin.data.CustomService;
import org.yawlfoundation.admin.util.CaseUtil;
import org.yawlfoundation.admin.util.RequestHelper;
import org.yawlfoundation.yawl.engine.interfce.Marshaller;
import org.yawlfoundation.yawl.engine.interfce.WorkItemRecord;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;

/**
 * Created by gary on 10/03/2017.
 */
@Controller
public class DefaultWorklistController {



    @Autowired
    private CaseUtil caseUtil;

    @Autowired
    private RequestHelper helper;

    @RequestMapping(value = "/resourceService")
    public Callable<String> dispatch(@RequestParam(name = "caseID")final String ID,
                                     @RequestParam(name = "workItem")final String workItemXML,
                                     final HttpServletRequest request,final Model model){

        return () -> {

            String caseID=ID;
            if(caseID==null){
                WorkItemRecord workItemRecord= Marshaller.unmarshalWorkItem(workItemXML);
                caseID=workItemRecord.getCaseID();
            }

            CustomService defaultWorklist=caseUtil.getDefaultWorklistByCaseID(caseID);

            Map<String,String> params=new HashMap<>();
            for(String key:request.getParameterMap().keySet()){
                params.put(key,request.getParameter(key));
            }


            String result;
            try {
                result=helper.sendRequest(defaultWorklist.getUri(),params);
                model.addAttribute(Constant.RESULT,result);
                return Constant.PLAIN;

            } catch (IOException e) {
                result=e.getMessage();
                model.addAttribute(Constant.RESULT,result);
                return Constant.EXCEPTION;

            }

        } ;


    }


}
