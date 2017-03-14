package org.yawlfoundation.admin.data.Repositories;

import org.springframework.data.repository.CrudRepository;
import org.yawlfoundation.admin.data.Tenant;
import org.yawlfoundation.admin.data.User;

import java.util.List;

/**
 * Created by root on 17-2-21.
 */
public interface UserRepository extends CrudRepository<User,Long> {

    public List<User> findByTenant(Tenant tenant);

    public User findByUserName(String userName);


}
