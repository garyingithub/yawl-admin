package org.yawlfoundation.admin.Data.Repositories;

import org.springframework.data.repository.CrudRepository;
import org.yawlfoundation.admin.Data.Specification;
import org.yawlfoundation.admin.Data.Tenant;
import org.yawlfoundation.admin.Data.User;
import org.yawlfoundation.admin.Utils.BaseUtil;

import java.util.List;

/**
 * Created by root on 17-2-21.
 */
public interface UserRepository extends CrudRepository<User,Long> {

    public List<User> findByTenant(Tenant tenant);

}
