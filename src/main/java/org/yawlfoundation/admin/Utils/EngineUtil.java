package org.yawlfoundation.admin.Utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.yawlfoundation.admin.Data.Engine;
import org.yawlfoundation.admin.Data.Repositories.EngineRepository;

import javax.annotation.PostConstruct;

/**
 * Created by root on 17-2-14.
 */
public class EngineUtil extends BaseUtil<Engine> {

    @Autowired
    EngineRepository engineRepository;

    @PostConstruct
    private void init(){
        this.repository=engineRepository;
    }


    public Engine getRandomEngine(){

        return (Engine) this.getAllObjects().iterator().next();


    }

}
