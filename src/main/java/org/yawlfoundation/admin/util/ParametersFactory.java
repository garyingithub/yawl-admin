package org.yawlfoundation.admin.util;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Component;
import org.yawlfoundation.admin.Constant;
import org.yawlfoundation.admin.data.CustomService;
import org.yawlfoundation.admin.data.Specification;
import org.yawlfoundation.admin.data.YawlCase;
import org.yawlfoundation.yawl.logging.YLogDataItem;
import org.yawlfoundation.yawl.logging.YLogDataItemList;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by gary on 17/03/2017.
 */
@Component
public class ParametersFactory {


    public List<NameValuePair> getParameters(final HttpServletRequest request
                                             ){

        List<NameValuePair> result=new ArrayList<>();
        for(String key:request.getParameterMap().keySet()){

            if(!key.equals(Constant.SESSIONHANDLE_NAME))
                result.add(new BasicNameValuePair(key,request.getParameter(key)));
        }

        //.add(new BasicNameValuePair(Constant.SESSIONHANDLE_NAME,sessionHandle));
        return result;

    }


    public List<NameValuePair> getParamsBasedOnAction(String action){
        List<NameValuePair> result=new ArrayList<>();
        result.add(new BasicNameValuePair("action",action));
        return result;
    }



    public List<NameValuePair> connectParams(){

        List<NameValuePair> result=getParamsBasedOnAction("connect");
        result.add(new BasicNameValuePair("userID","admin"));
        result.add(new BasicNameValuePair("password","Se4tMaQCi9gr0Q2usp7P56Sk5vM="));

        return result;

    }

    public List<NameValuePair> loadSpecificationParams(
                                                        String xml){

        List<NameValuePair> list=getParamsBasedOnAction("upload");
        list.add(new BasicNameValuePair("specXML",xml));

        return list;
    }

    public List<NameValuePair> registerServiceParams(
                                                      CustomService service){

        List<NameValuePair> list=getParamsBasedOnAction("newYAWLService");
        list.add(new BasicNameValuePair("service",service.toXMLComplete()));


        return list;
    }

    public boolean hasFailed(String result){
        return result.contains("failure");
    }



    public List<NameValuePair> launchCaseParams(YawlCase c, Specification specification){


        List<NameValuePair> list=getParamsBasedOnAction("launchCase");
        list.add(new BasicNameValuePair("specidentifier", specification.getUniqueID()));
        String logData= new YLogDataItemList(
                new YLogDataItem("launched", "name", "resourceService", "string")).toXML();

        list.add(new BasicNameValuePair("logData", logData));

        list.add(new BasicNameValuePair("specuri", specification.getUri()));
        list.add(new BasicNameValuePair("specversion", specification.getVersion()));
        list.add(new BasicNameValuePair("caseID",c.getCaseId().toString()));

        return list;
    }

    public List<NameValuePair> checkInParams(String workItemID, String data, String logPredicate){


        List<NameValuePair> list=getParamsBasedOnAction("checkin");


        list.add(new BasicNameValuePair("workItemID",workItemID));
       // list.add(new BasicNameValuePair("sessionHandle",sessionHandle));
        list.add(new BasicNameValuePair("data",data));
        list.add(new BasicNameValuePair("logPredicate",logPredicate));


        return list;

    }

    public List<NameValuePair> checkOutParams(String workItemID){

        List<NameValuePair> list=getParamsBasedOnAction("checkout");
        list.add(new BasicNameValuePair("workItemID",workItemID));
       
        return list;
    }

    public List<NameValuePair> getWorkItemParams(String workItemID){
        List<NameValuePair> result=new ArrayList<>();

        result.add(new BasicNameValuePair("action","getWorkItem"));
        
        result.add(new BasicNameValuePair("workItemID",workItemID));

        return result;
    }
}
