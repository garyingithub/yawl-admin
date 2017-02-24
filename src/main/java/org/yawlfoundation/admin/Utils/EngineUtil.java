package org.yawlfoundation.admin.Utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.yawlfoundation.admin.Data.Engine;
import org.yawlfoundation.admin.Data.Repositories.EngineRepository;

import javax.annotation.PostConstruct;

/**
 * Created by root on 17-2-14.
 */
@Component
public class EngineUtil extends BaseUtil<Engine> {

    @Autowired
    EngineRepository engineRepository;

    @PostConstruct
    private void init(){
        this.repository=engineRepository;

        Engine engine=new Engine("http://localhost:8080");
        this.storeObject(engine);
    }


    public Engine getRandomEngine(){
        return (Engine) this.getAllObjects().iterator().next();
    }



}
