package org.yawlfoundation.admin.Data.Repositories;

import org.springframework.data.repository.CrudRepository;
import org.yawlfoundation.admin.Data.Specification;
import org.yawlfoundation.admin.Data.Tenant;

import java.util.List;

/**
 * Created by root on 17-2-9.
 */
public interface SpecificationRepository extends CrudRepository<Specification,Long>{
    public List<Specification> findByTenant(Tenant tenant);
    public List<Specification> findByUniqueID(String uniqueID);


}
