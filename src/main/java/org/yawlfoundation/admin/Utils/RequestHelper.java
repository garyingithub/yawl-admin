package org.yawlfoundation.admin.Utils;
import org.springframework.stereotype.Component;
import org.yawlfoundation.admin.Data.YawlCase;
import org.yawlfoundation.admin.Data.CustomService;
import org.yawlfoundation.admin.Data.Engine;
import org.yawlfoundation.admin.Data.Specification;
import org.yawlfoundation.yawl.engine.interfce.Interface_Client;
import org.yawlfoundation.yawl.logging.YLogDataItem;
import org.yawlfoundation.yawl.logging.YLogDataItemList;


import java.io.IOException;
import java.util.*;
import java.util.Map;

/**
 * Created by root on 17-2-16.
 */
@Component
public class RequestHelper {


    class RequestForwader extends Interface_Client{


        public String forwardRequest(String urlDestination, Map<String, String> parameterMap)
                throws IOException
        {
            String result;
            result = executePost(urlDestination, parameterMap);
            return result;
        }
    }


    //@Autowired
    private RequestForwader client=new RequestForwader();

    private Map<String,String> getParams(String action){

        Map<String,String> params=new HashMap<>();
        params.put("action",action);
       // params.put("sessionHandle",sessionUtil.connectToEngineAsAdmin(engine));
        return params;


    }


    private Map<String, String> checkConnectionParams(String sessionHandle){

        Map<String,String> params=getParams("checkConnection");
        params.put("sessionHandle",sessionHandle);

        return params;
    }

    private Map<String,String> connectParams(){
        Map<String,String> params=getParams("connect");
        params.put("password","Se4tMaQCi9gr0Q2usp7P56Sk5vM=");
        params.put("userID","admin");

        return params;

    }

    private Map<String,String> loadSpecificationParams(String sessionHandle,
                                                       String xml){
        Map<String,String> result=getParams("upload");
        result.put("sessionHandle",sessionHandle);
        result.put("specXML",xml);
        return result;
    }

    private Map<String,String> registerServiceParams( String sessionHandle,
                                                     CustomService service){
        Map<String,String> result=getParams("newYAWLService");
        result.put("sessionHandle",sessionHandle);
        result.put("service",service.toXMLComplete());
        return result;
    }


    private Map<String,String> launchCaseParams(YawlCase c, String sessionHandle, Specification specification){

        Map<String,String> params=getParams("launchCase");

        params.put("specidentifier", specification.getUniqueID());
        String logData= new YLogDataItemList(
                new YLogDataItem("launched", "name", "resourceService", "string")).toXML();
        params.put("logData", logData);

        params.put("specuri", specification.getUri());
        params.put("specversion", specification.getVersion());
        params.put("caseID",c.getCaseId().toString());

        return params;
    }

    public String loadSpecification(Engine engine, Specification specification) throws IOException {
        String result=client.forwardRequest(engine.getIAURI(),loadSpecificationParams(engine.getSessionHandle(),specification.getSpecificationXML()));

        if(result.contains("session")){
           result= client.forwardRequest(engine.getIAURI(),loadSpecificationParams(engine.refreshSession(this),specification.getSpecificationXML()));
        }

        return  result;
    }

    public String registerService(Engine engine,CustomService service) throws IOException {
        String result=client.forwardRequest(engine.getIAURI(),registerServiceParams(engine.getSessionHandle(),service));

        if(result.contains("session")){
            result= client.forwardRequest(engine.getIAURI(),registerServiceParams(engine.refreshSession(this),service));
        }

        return  result;

    }

    public String launchCase(Engine engine,YawlCase c) throws IOException {

        String result=client.forwardRequest(engine.getIBURI(),launchCaseParams(c,engine.refreshSession(this),c.getSpecification()));

        if(result.contains("session")){
            result= client.forwardRequest(engine.getIBURI(),launchCaseParams(c,engine.refreshSession(this),c.getSpecification()));
        }

        return  result;

    }

    public String getSessionHandle(String uri) throws IOException {
        return client.forwardRequest(uri,connectParams());
    }

    public boolean checkConnection(String uri,String sessionHandle) throws IOException {

        return client.forwardRequest(uri,checkConnectionParams(sessionHandle)).contains("success");
    }






}
