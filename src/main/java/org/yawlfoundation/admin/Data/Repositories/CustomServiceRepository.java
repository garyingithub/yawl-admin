package org.yawlfoundation.admin.Data.Repositories;

import org.springframework.data.repository.CrudRepository;
import org.yawlfoundation.admin.Data.CustomService;
import org.yawlfoundation.admin.Data.Tenant;

import java.util.List;
import java.util.Set;

/**
 * Created by root on 17-2-9.
 */
public interface CustomServiceRepository extends CrudRepository<CustomService,Long> {

    public List<CustomService> findByUri(String uri);

    public List<CustomService> findByTenant(Tenant tenant);

}
