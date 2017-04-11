package org.yawlfoundation.admin.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
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
//@CacheConfig(cacheNames = "session",cacheManager = "sessionRedisManager")
public class SessionUtil {

    private Map<String,User> sessionUserMap=new HashMap<>();

    @Autowired
    private UserUtil userUtil;

  //  @CachePut(key = "#a0")
    public String putSessionCache(String sessionHandle,User user){
        //return String.valueOf(user.getUserId());
        this.sessionUserMap.put(sessionHandle,user);
        return String.valueOf(user.getUserId());

    }


    public String createSessionHandle(User user){

        String sessionHandle=UUID.randomUUID().toString();
        putSessionCache(sessionHandle,user);

        return sessionHandle;
    }

    public boolean checkSessionHandle(String sessionHandle){

        return getUserIdBySession(sessionHandle)!=null;


    }

    public User getUserBySession(String sessionHandle){

        //String userId=getUserIdBySession(sessionHandle);
        //if(userId!=null)
        //    return userUtil.getUser(userId);
        //else
          //  return null;

        return this.sessionUserMap.get(sessionHandle);
    }

    //@Cacheable
    public String getUserIdBySession(String sessionHandle){

        return String.valueOf(this.sessionUserMap.get(sessionHandle).getUserId());
    }



}
