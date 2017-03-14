package org.yawlfoundation.admin.data.Repositories;

import org.springframework.data.repository.CrudRepository;
import org.yawlfoundation.admin.data.Tenant;

/**
 * Created by root on 17-2-9.
 */
public interface TenantRepository  extends CrudRepository<Tenant,Long>{
}
