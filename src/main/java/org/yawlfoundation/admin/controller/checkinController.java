package org.yawlfoundation.admin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.yawlfoundation.admin.data.Engine;
import org.yawlfoundation.admin.util.CaseUtil;
import org.yawlfoundation.admin.util.RequestHelper;

import java.util.concurrent.Callable;

/**
 * Created by gary on 28/02/2017.
 */
@Controller
//@RequestMapping(name = "/checkin/{workitemID}")
public class checkinController {

    @Autowired
    private CaseUtil caseUtil;

    @Autowired
    private RequestHelper requestHelper;

    @RequestMapping(method = RequestMethod.POST,value = "/workitem/checkin")
    public Callable<String> checkIn(@PathVariable("workitemID") String workitemID,
                                    @RequestParam(value = "data") String data,
                                    @RequestParam(value = "logPredicate")String logPredicate){

        return new Callable<String>() {
            @Override
            public String call() throws Exception {

                String caseID=workitemID.split(":")[0];
                if(caseID.contains(".")){
                    caseID=caseID.split(".")[0];
                }
                Engine engine=caseUtil.getEngineByCaseID(caseID);
                return requestHelper.checkIn(workitemID,engine,data,logPredicate);
            }
        };
    }


}
