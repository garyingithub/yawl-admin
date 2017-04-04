package org.yawlfoundation.admin.mock;

import org.apache.http.HttpHost;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.yawlfoundation.admin.data.CustomService;
import org.yawlfoundation.admin.data.Specification;
import org.yawlfoundation.admin.data.Tenant;
import org.yawlfoundation.admin.data.YawlCase;
import org.yawlfoundation.admin.util.CaseUtil;
import sun.net.www.http.HttpClient;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;

/**
 * Created by gary on 21/03/2017.
 */


public class RedisTest {



    @Test
    public void addCase(){


        Executor executor= Executors.newFixedThreadPool(4);
        for(int j=0;j<4;j++){

            executor.execute(new Runnable() {
                @Override
                public void run() {
                    PoolingHttpClientConnectionManager cm=new PoolingHttpClientConnectionManager();

                    HttpHost engineHost=new HttpHost("127.0.0.1",8088);
                    cm.setMaxPerRoute(new HttpRoute(engineHost),50);
                    CloseableHttpClient client= HttpClients.custom().
                            setConnectionManager(cm).
                            build();


                    HttpPost httpPost=new HttpPost("http://localhost:8088/test");
                    HttpContext httpContext=new BasicHttpContext();

                    UrlEncodedFormEntity entity= null;

                    for(int i=0;i<1000;i++){
                        List<NameValuePair> params=new ArrayList<>();
                        params.add(new BasicNameValuePair("tenantId",String.valueOf(i%4+1)));
                        try {
                            entity = new UrlEncodedFormEntity(params,"UTF-8");
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }
                        httpPost.setEntity(entity);

                        CloseableHttpResponse response;
                        try {
                            response=client.execute(httpPost,httpContext);
                            String result= EntityUtils.toString(response.getEntity());
                            System.out.println(result);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });

            try {
                TimeUnit.MILLISECONDS.sleep(8000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }



    }
}
