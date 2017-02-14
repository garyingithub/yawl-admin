package org.yawlfoundation.admin.Utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;
import org.yawlfoundation.admin.Data.Repositories.SpecificationRepository;
import org.yawlfoundation.admin.Data.Specification;
import org.yawlfoundation.admin.Data.Tenant;
import org.yawlfoundation.yawl.elements.*;
import org.yawlfoundation.yawl.exceptions.YAWLException;
import org.yawlfoundation.yawl.unmarshal.YMarshal;

import org.yawlfoundation.admin.Data.Specification;
import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by root on 17-2-7.
 */
@Component
public class SpecificationUtil extends BaseUtil<Specification> {

    @Autowired
    private YawlUtil yawlUtil;

    @Autowired
    private SpecificationRepository specificationRepository;

    @PostConstruct
    public void init(){
        this.repository=specificationRepository;
    }


}
