package org.yawlfoundation.admin.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.yawlfoundation.admin.data.repository.UserRepository;
import org.yawlfoundation.admin.data.Tenant;
import org.yawlfoundation.admin.data.User;

import javax.annotation.PostConstruct;
import javax.persistence.Transient;

/**
 * Created by root on 17-2-21.
 */
@Component
public class UserUtil extends BaseUtil<User> {

    @Autowired
    private UserRepository userRepository;

    @PostConstruct
    private void init(){
        this.repository=userRepository;
    }

    @Transient
    public User getUserByUserName(String userName){
        return this.userRepository.findByUserName(userName);
    }

    @Transient
    public String getAccounts(Tenant tenant){

    //    Set<User> users=tenant.getUsers();

        StringBuilder builder=new StringBuilder();
        for(User user:this.userRepository.findByTenant(tenant)){
            builder.append(user.toXML());
        }

        return builder.toString();

    }


    @Transient
    public String getClientPassword(Tenant tenant,String userID){

        for(User user:this.userRepository.findByTenant(tenant)){
            if(user.getUserName().equals(userID)){
                return user.getUserPassword();
            }
        }

        return null;

    }



}
