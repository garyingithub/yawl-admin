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
import javax.servlet.http.HttpServletRequest;
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

    @Autowired
    private ParametersFactory parametersFactory;



    class RequestForwader {

        public String forwardRequest(String uri, List<NameValuePair> parameterMap)
                throws IOException {

            CloseableHttpClient client= HttpClients.createDefault();
            String result;
            if(!uri.startsWith("http")){
                uri="http://"+uri;
            }
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

            return stripOuterElement(result);
        }

    }


    //@Autowired
    private RequestForwader client=new RequestForwader();







    public String sendRequest(Engine engine, HttpServletRequest request) throws IOException {
       // String sessionHandle=engine.getSessionHandle();

        List<NameValuePair> paramList;

        paramList=parametersFactory.getParameters(request);
        return sendRequest(engine,paramList, Constant.InterfaceType.INTERFACE_B);
    }

    public String connect(Engine engine) throws IOException {
        List<NameValuePair> list=parametersFactory.connectParams();
        return sendRequest(engine,list, Constant.InterfaceType.INTERFACE_B);
    }
    public String sendRequest(Engine engine, List<NameValuePair> paramList, Constant.InterfaceType type) throws IOException {
        String result="";
        String sessionHandle;
        paramList.add(new BasicNameValuePair(Constant.SESSIONHANDLE_NAME,engine.getSessionHandle()));
        for(int i=0;i<Constant.SESSION_RETRY_TIMES;i++){


            result=sendRequest(type==Constant.InterfaceType.INTERFACE_A?
                    engine.getIAURI():engine.getIBURI(),paramList);
            if(!result.contains("session")){
                return result;
            }
            sessionHandle=connect(engine);
            paramList.remove(paramList.size()-1);
            paramList.add(new BasicNameValuePair(Constant.SESSIONHANDLE_NAME,sessionHandle));
        }
        return YawlUtil.failureMessage(result);
    }

    private String sendRequest(String uri,List<NameValuePair> params) throws IOException {


        return stripOuterElement(client.forwardRequest(uri,params));

    }


    public String sendRequest(String uri,Map<String,String> params) throws IOException {

        List<NameValuePair> list=new ArrayList<>();
        for(String key:params.keySet()){

            BasicNameValuePair pair=new BasicNameValuePair(key,params.get(key));
            list.add(pair);
        }

        return stripOuterElement(client.forwardRequest(uri,list));

    }

    protected String stripOuterElement(String xml) {
        if (xml != null) {
            int start = xml.indexOf('>') + 1;
            int end = xml.lastIndexOf('<');
            if (end > start) {
                return xml.substring(start, end);
            }
        }
        return xml;
    }

}
