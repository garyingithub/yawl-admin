package org.yawlfoundation.admin.Data.Repositories;

import org.springframework.data.repository.CrudRepository;
import org.yawlfoundation.admin.Data.Tenant;

/**
 * Created by root on 17-2-9.
 */
public interface TenantRepository  extends CrudRepository<Tenant,Long>{
}
