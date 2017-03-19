package org.yawlfoundation.admin.data.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.yawlfoundation.admin.data.Tenant;

/**
 * Created by root on 17-2-9.
 */
@Repository
public interface TenantRepository  extends CrudRepository<Tenant,Long>{
}
