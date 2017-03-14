package org.yawlfoundation.admin.util;

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

    private Map<String,User> sessionUserMap=new HashMap<>();

    public String createSessionHandle(User user){

        String sessionHandle= UUID.randomUUID().toString();

        sessionUserMap.put(sessionHandle,user);
        return sessionHandle;
    }

    public boolean checkSessionHandle(String sessionHandle){

        if(sessionUserMap.get(sessionHandle)==null){
            return false;
        }else {
            return true;
        }

       // return sessionUserMap.get(sessionHandle).equals(user);

    }

    public User getUserBySession(String sessionHandle){
        return this.sessionUserMap.get(sessionHandle);
    }



}
