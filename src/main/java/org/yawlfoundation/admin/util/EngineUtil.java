package org.yawlfoundation.admin.util;

import org.apache.http.HttpHost;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.yawlfoundation.admin.data.Engine;
import org.yawlfoundation.admin.data.repository.EngineRepository;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by root on 17-2-14.
 */
@Component
public class EngineUtil extends BaseUtil<Engine> {

    @Autowired
    EngineRepository engineRepository;

    private PoolingHttpClientConnectionManager cm=new PoolingHttpClientConnectionManager();


    @PostConstruct
    private void init(){
        this.repository=engineRepository;

        Engine engine=new Engine("http://localhost","8089");
        this.storeObject(engine);

        cm.setMaxTotal(200);
        cm.setDefaultMaxPerRoute(20);

        Iterator<Engine> engineIterator=this.engineRepository.findAll().iterator();
        while (engineIterator.hasNext()){
            Engine engine1=engineIterator.next();

            HttpHost engineHost=new HttpHost(engine.getHostAddress(),Integer.parseInt(engine.getPort()));
            cm.setMaxPerRoute(new HttpRoute(engineHost),50);
            CloseableHttpClient client= HttpClients.custom().
                    setConnectionManager(cm).
                    build();
            engineHttpClientHashMap.put(engine.getAddress(),client);
        }
    }

    public CloseableHttpClient getEngineClient(Engine engine){
        //System.out.println(engine.hashCode());
        //System.out.println(engineHttpClientHashMap.keySet().iterator().next().hashCode());
        return engineHttpClientHashMap.get(engine.getAddress());
    }


    public Engine getRandomEngine(){
        return (Engine) this.getAllObjects().iterator().next();
    }


    Map<String, CloseableHttpClient> engineHttpClientHashMap=
            new HashMap<String, CloseableHttpClient>();




}
