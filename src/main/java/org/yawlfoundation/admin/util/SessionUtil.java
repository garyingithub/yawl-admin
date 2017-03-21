package org.yawlfoundation.admin.util;

import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import org.yawlfoundation.admin.data.User;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Created by gary on 28/02/2017.
 */

@Component
public class SessionUtil {

    //private Map<String,User> sessionUserMap=new HashMap<>();

    @CachePut(key = "#result",value = "#a0",cacheManager = "sessionRedisManager")
    public String createSessionHandle(User user){


        return UUID.randomUUID().toString();
    }

    public boolean checkSessionHandle(String sessionHandle){

        return getUserBySession(sessionHandle)!=null;


    }

    @Cacheable(cacheManager = "sessionRedisManager")
    public User getUserBySession(String sessionHandle){

        return null;
    }



}
