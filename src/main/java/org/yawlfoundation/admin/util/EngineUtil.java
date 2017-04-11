package org.yawlfoundation.admin.util;

import org.apache.http.HttpHost;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestHeader;
import org.yawlfoundation.admin.Constant;
import org.yawlfoundation.admin.data.Engine;
import org.yawlfoundation.admin.data.repository.EngineRepository;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by root on 17-2-14.
 */
@Component
public class EngineUtil{


     private List<Engine> engineCluster= new ArrayList<>();
     private Map<String,Engine> engineMap=new HashMap<>();
     private AtomicInteger state=new AtomicInteger(0);


     @Value("${zookeeper.address}")
     private String zkAddress;

     @Value("${zookeeper.timeout}")
     private String zkTimeout;

     private ZooKeeper zooKeeper;

     private Map<String, CloseableHttpClient> engineHttpClientHashMap=
            new HashMap<String, CloseableHttpClient>();

    private PoolingHttpClientConnectionManager cm=new PoolingHttpClientConnectionManager();

    @PostConstruct
     private void init() {
         try {
             //zkAddress="192.168.0.12:1212";
             zooKeeper=new ZooKeeper(zkAddress, Integer.valueOf(zkTimeout), new EngineClusterWatcher());

             reset();
         } catch (IOException e) {
             throw new RuntimeException(e);
         }
        cm.setMaxTotal(200);
        cm.setDefaultMaxPerRoute(20);

    }


     class EngineClusterWatcher implements Watcher {


        @Override
        public void process(WatchedEvent watchedEvent) {


            reset();

        }
    }

    public Engine getEngineById(String engineId){
        return engineMap.get(engineId);
    }





    private void reset(){

        while (!state.compareAndSet(0,1));


        List<String> engines= null;

        try {
            if(zooKeeper.getState()!= ZooKeeper.States.CONNECTED){
                zooKeeper=new ZooKeeper(zkAddress, Integer.valueOf(zkTimeout), new EngineClusterWatcher());

            }
            engines = zooKeeper.getChildren(Constant.ZK_ENGINE_PATH,true);
        } catch (KeeperException e) {
            if(engineCluster!=null)
                engineCluster.clear();
            state.compareAndSet(1,0);
            return;
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } catch (IOException e) {
            e.printStackTrace();
        }
        List<Engine> engineList=new ArrayList<>();
        final String prefix=Constant.ZK_ENGINE_PATH+"/";
        for(String engine : engines){

            try {
                //Stat stat=new Stat();
                String address= parseData(zooKeeper.getData(Constant.ZK_ENGINE_PATH+"/1", false, new Stat()));
                Engine temp=new Engine(Long.valueOf(engine),address);
                engineList.add(temp);

            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            } catch (KeeperException e) {
                if(engineCluster!=null)
                    engineCluster.clear();
                state.compareAndSet(1,0);
                return;
            }

        }

        engineCluster=engineList;

        engineMap=new HashMap<>();

        Iterator<Engine> engineIterator=this.engineCluster.iterator();
        while (engineIterator.hasNext()){
            Engine engine=engineIterator.next();
            engineMap.put(String.valueOf(engine.getEngineId()),engine);
            HttpHost engineHost=new HttpHost(engine.getHostAddress(),Integer.parseInt(engine.getPort()));
            cm.setMaxPerRoute(new HttpRoute(engineHost),50);
            CloseableHttpClient client= HttpClients.custom().
                    setConnectionManager(cm).
                    build();
            engineHttpClientHashMap.put(engine.getAddress(),client);
        }
        state.compareAndSet(1,0);
    }

    public CloseableHttpClient getEngineClient(Engine engine){
        while (state.compareAndSet(0,0));
        return engineHttpClientHashMap.get(engine.getAddress());
    }


    private Random random=new Random();
    public Engine getRandomEngine(){
        while (!state.compareAndSet(0,0));
        if(engineCluster.size()>0)
            return this.engineCluster.get(random.nextInt()%engineCluster.size());
        return null;
    }

    private String parseData(byte[] bytes){
        StringBuffer buffer=new StringBuffer();
        for(byte b:bytes){
            buffer.append((char)b);
        }
        return buffer.toString();
    }


    public static void main(String[] args){

        ZooKeeper zooKeeper= null;
        try {
            zooKeeper = new ZooKeeper("192.168.239.128:2181", Integer.valueOf(3000), null);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            List<String> engines = zooKeeper.getChildren(Constant.ZK_ENGINE_PATH,true);
            //System.out.println(engines);
            byte[] bytes=zooKeeper.getData(Constant.ZK_ENGINE_PATH+"/1",null,new Stat());
            StringBuffer buffer=new StringBuffer();
            for (byte c:bytes){
                buffer.append((char) c);
            }
            System.out.println(String.valueOf(bytes));
        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }


}
