package org.yawlfoundation.admin.util;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.yawlfoundation.admin.Constant;
import org.yawlfoundation.admin.data.YawlCase;
import org.yawlfoundation.admin.data.CustomService;
import org.yawlfoundation.admin.data.Engine;
import org.yawlfoundation.admin.data.Specification;
import org.yawlfoundation.yawl.logging.YLogDataItem;
import org.yawlfoundation.yawl.logging.YLogDataItemList;


import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.*;
import java.util.Map;

/**
 * Created by root on 17-2-16.
 */
@Component
public class RequestHelper {

    private String[] interfaceAList={
            "loadSpecification",
            "registerService"
    };
    private String[] interfaceBList={
            "checkin",
            "checkout",
            "launchCase"
    };
    private Set<String> interfaceASet,interfaceBSet;

    @PostConstruct
    private void init(){

        interfaceASet=new HashSet<>();
        interfaceBSet=new HashSet<>();

        interfaceASet.addAll(Arrays.asList(interfaceAList));
        interfaceBSet.addAll(Arrays.asList(interfaceBList));
    }

    @Autowired
    private EngineUtil engineUtil;

    class RequestForwader {

        public String forwardRequest(String uri, List<NameValuePair> parameterMap)
                throws IOException {

            CloseableHttpClient client= HttpClients.createDefault();
            String result;
            HttpPost httpPost=new HttpPost(uri);
            UrlEncodedFormEntity entity=new UrlEncodedFormEntity(parameterMap,"UTF-8");
            httpPost.setEntity(entity);
            HttpContext httpContext=new BasicHttpContext();
            CloseableHttpResponse response;
            response=client.execute(httpPost,httpContext);
            result= EntityUtils.toString(response.getEntity());

            return result;
        }

        public String forwardRequest(Engine engine, List<NameValuePair> parameterMap,
                                     Constant.InterfaceType type)
                throws IOException {

            CloseableHttpClient client=engineUtil.getEngineClient(engine);
            String result;
            HttpPost httpPost=new HttpPost(type== Constant.InterfaceType.INTERFACE_A?engine.getIAURI():engine.getIBURI());
            UrlEncodedFormEntity entity=new UrlEncodedFormEntity(parameterMap,"UTF-8");
            httpPost.setEntity(entity);
            HttpContext httpContext=new BasicHttpContext();
            CloseableHttpResponse response;
            response=client.execute(httpPost,httpContext);
            result= EntityUtils.toString(response.getEntity());

            return result;
        }

    }


    //@Autowired
    private RequestForwader client=new RequestForwader();

    private Map<String, String> getParams(String action){

        Map<String,String> params=new HashMap<>();
        params.put("action",action);
       
        return params;


    }


    private List<NameValuePair> checkConnectionParams(String sessionHandle){

        Map<String,String> params=getParams("checkConnection");
        params.put("sessionHandle",sessionHandle);

        List<NameValuePair> list=new ArrayList<>();
        for(String key:params.keySet()){

            BasicNameValuePair pair=new BasicNameValuePair(key,params.get(key));
            list.add(pair);
        }
        return list;
    }

    private List<NameValuePair> connectParams(){
        Map<String,String> params=getParams("connect");
        params.put("password","Se4tMaQCi9gr0Q2usp7P56Sk5vM=");
        params.put("userid","admin");

        List<NameValuePair> list=new ArrayList<>();
        for(String key:params.keySet()){

            BasicNameValuePair pair=new BasicNameValuePair(key,params.get(key));
            list.add(pair);
        }
        return list;

    }

    private List<NameValuePair> loadSpecificationParams(String sessionHandle,
                                                             String xml){
        Map<String,String> params=getParams("upload");
        params.put("sessionHandle",sessionHandle);
        params.put("specXML",xml);
        List<NameValuePair> list=new ArrayList<>();
        for(String key:params.keySet()){

            BasicNameValuePair pair=new BasicNameValuePair(key,params.get(key));
            list.add(pair);
        }
        return list;
    }

    private List<NameValuePair> registerServiceParams(String sessionHandle,
                                                           CustomService service){
        Map<String,String> params=getParams("newYAWLService");
        params.put("sessionHandle",sessionHandle);
        params.put("service",service.toXMLComplete());
        List<NameValuePair> list=new ArrayList<>();
        for(String key:params.keySet()){

            BasicNameValuePair pair=new BasicNameValuePair(key,params.get(key));
            list.add(pair);
        }
        return list;
    }

    private boolean hasFailed(String result){
        return result.contains("failure");
    }



    private List<NameValuePair> launchCaseParams(YawlCase c, String sessionHandle, Specification specification){

        Map<String,String> params=getParams("launchCase");

        params.put("sessionHandle",sessionHandle);
        params.put("specidentifier", specification.getUniqueID());
        String logData= new YLogDataItemList(
                new YLogDataItem("launched", "name", "resourceService", "string")).toXML();
        params.put("logData", logData);

        params.put("specuri", specification.getUri());
        params.put("specversion", specification.getVersion());
        params.put("caseID",c.getCaseId().toString());

        List<NameValuePair> list=new ArrayList<>();
        for(String key:params.keySet()){

            BasicNameValuePair pair=new BasicNameValuePair(key,params.get(key));
            list.add(pair);
        }
        return list;
    }

    private List<NameValuePair> checkInParams(String workItemID, String sessionHandle, String data, String logPredicate){

        Map<String,String> params=getParams("checkin");

        params.put("workItemID",workItemID);
        params.put("sessionHandle",sessionHandle);
        params.put("data",data);
        params.put("logPredicate",logPredicate);

        List<NameValuePair> list=new ArrayList<>();
        for(String key:params.keySet()){

            BasicNameValuePair pair=new BasicNameValuePair(key,params.get(key));
            list.add(pair);
        }
        return list;

    }

    private List<NameValuePair> checkOutParams(String workItemID, String sessionHandle){
        Map<String,String> params=getParams("checkout");

        params.put("workItemID",workItemID);
        params.put("sessionHandle",sessionHandle);
        List<NameValuePair> list=new ArrayList<>();
        for(String key:params.keySet()){

            BasicNameValuePair pair=new BasicNameValuePair(key,params.get(key));
            list.add(pair);
        }
        return list;
    }

    public String checkIn(String workItemID,Engine engine,String data,String logPredicate) throws IOException {

        String result=client.forwardRequest(engine,checkInParams(workItemID,engine.getSessionHandle(),data,logPredicate),
                Constant.InterfaceType.INTERFACE_B);
        if(result.contains("session")){
            result=client.forwardRequest(engine,checkInParams(workItemID,engine.refreshSession(this),data,logPredicate),
                    Constant.InterfaceType.INTERFACE_B);
        }
        return result;


    }

    public String checkOut(String workItemID,Engine engine) throws IOException {

        String result=client.forwardRequest(engine,checkOutParams(workItemID,engine.getSessionHandle()), 
                Constant.InterfaceType.INTERFACE_B);
        if(result.contains("session")){
            result=client.forwardRequest(engine,checkOutParams(workItemID,engine.refreshSession(this)),
                    Constant.InterfaceType.INTERFACE_B);
        }
        return result;


    }

    public String loadSpecification(Engine engine, Specification specification) throws IOException {
        String result=client.forwardRequest(engine,loadSpecificationParams(engine.getSessionHandle(),specification.getSpecificationXML()),
                Constant.InterfaceType.INTERFACE_A);

        if(result.contains("session")){
           result= client.forwardRequest(engine,loadSpecificationParams(engine.refreshSession(this),specification.getSpecificationXML()),
                   Constant.InterfaceType.INTERFACE_A);
        }

        return  result;
    }

    public String registerService(Engine engine,CustomService service) throws IOException {
        String result=client.forwardRequest(engine,registerServiceParams(engine.getSessionHandle(),service),
                Constant.InterfaceType.INTERFACE_A);

        if(result.contains("session")){
            result= client.forwardRequest(engine,registerServiceParams(engine.refreshSession(this),service),
                    Constant.InterfaceType.INTERFACE_A);
        }

        return  result;

    }

    public String launchCase(Engine engine,YawlCase c) throws IOException {

        String result=client.forwardRequest(engine,launchCaseParams(c,engine.refreshSession(this),c.getSpecification()),
                Constant.InterfaceType.INTERFACE_B);

        if(result.contains("session")){
            result= client.forwardRequest(engine,launchCaseParams(c,engine.refreshSession(this),c.getSpecification()),
                    Constant.InterfaceType.INTERFACE_B);
        }

        return  result;

    }

    public String getSessionHandle(Engine engine) throws IOException {
        return client.forwardRequest(engine,connectParams(),
                Constant.InterfaceType.INTERFACE_A);
    }

    public boolean checkConnection(Engine engine,String sessionHandle) throws IOException {

        return client.forwardRequest(engine,checkConnectionParams(sessionHandle),
                Constant.InterfaceType.INTERFACE_A).contains("success");
    }




    public String sendRequest(String uri,Map<String,String> params) throws IOException {

        List<NameValuePair> list=new ArrayList<>();
        for(String key:params.keySet()){

            BasicNameValuePair pair=new BasicNameValuePair(key,params.get(key));
            list.add(pair);
        }

        return client.forwardRequest(uri,list);

    }



}
