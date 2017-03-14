package org.yawlfoundation.admin.data.Repositories;

import org.springframework.data.repository.CrudRepository;
import org.yawlfoundation.admin.data.CustomService;
import org.yawlfoundation.admin.data.Tenant;

import java.util.List;

/**
 * Created by root on 17-2-9.
 */
public interface CustomServiceRepository extends CrudRepository<CustomService,Long> {

    public List<CustomService> findByUri(String uri);

    public List<CustomService> findByTenant(Tenant tenant);

}
