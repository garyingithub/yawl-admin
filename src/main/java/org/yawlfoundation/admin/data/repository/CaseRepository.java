package org.yawlfoundation.admin.data.repository;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.yawlfoundation.admin.data.YawlCase;
import org.yawlfoundation.admin.data.Specification;

import java.util.Set;

/**
 * Created by root on 17-2-9.
 */

@Repository
public interface CaseRepository extends CrudRepository<YawlCase,Long> {


    public Set<YawlCase> findBySpecification(Specification specification);

}
