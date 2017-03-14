package org.yawlfoundation.admin.data.Repositories;

import org.springframework.data.repository.CrudRepository;
import org.yawlfoundation.admin.data.Specification;
import org.yawlfoundation.admin.data.Tenant;

import java.util.List;

/**
 * Created by root on 17-2-9.
 */
public interface SpecificationRepository extends CrudRepository<Specification,Long>{
    public List<Specification> findByTenant(Tenant tenant);
    public List<Specification> findByUniqueID(String uniqueID);


}
