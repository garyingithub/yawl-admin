package org.yawlfoundation.admin.data.Repositories;

import org.springframework.data.repository.CrudRepository;
import org.yawlfoundation.admin.data.YawlCase;
import org.yawlfoundation.admin.data.Specification;

import java.util.Set;

/**
 * Created by root on 17-2-9.
 */
public interface CaseRepository extends CrudRepository<YawlCase,Long> {

    public Set<YawlCase> findBySpecification(Specification specification);

}
