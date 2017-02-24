package org.yawlfoundation.admin.Utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.yawlfoundation.admin.Data.Repositories.UserRepository;
import org.yawlfoundation.admin.Data.Tenant;
import org.yawlfoundation.admin.Data.User;

import javax.annotation.PostConstruct;
import java.util.Set;

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

    public String getAccounts(Tenant tenant){

    //    Set<User> users=tenant.getUsers();

        StringBuilder builder=new StringBuilder();
        for(User user:this.userRepository.findByTenant(tenant)){
            builder.append(user.toXML());
        }

        return builder.toString();

    }

    public String getClientPassword(Tenant tenant,String userID){

        for(User user:this.userRepository.findByTenant(tenant)){
            if(user.getUserName().equals(userID)){
                return user.getUserPassword();
            }
        }

        return null;

    }

}
