package org.yawlfoundation.admin.data.repository;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.yawlfoundation.admin.data.CustomService;
import org.yawlfoundation.admin.data.Tenant;

import java.util.List;

/**
 * Created by root on 17-2-9.
 */
@Repository
public interface CustomServiceRepository extends CrudRepository<CustomService,Long> {


    public List<CustomService> findByUri(String uri);

    public List<CustomService> findByTenant(Tenant tenant);

}
