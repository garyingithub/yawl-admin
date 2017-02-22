package org.yawlfoundation.admin.Utils;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.AccessType;
import org.yawlfoundation.admin.Data.Case;
import org.yawlfoundation.admin.Data.Engine;
import org.yawlfoundation.admin.Data.Repositories.CaseRepository;
import org.yawlfoundation.admin.Data.Specification;
import org.yawlfoundation.admin.Data.Tenant;

import javax.annotation.PostConstruct;

/**
 * Created by root on 17-2-14.
 */
public class CaseUtil extends BaseUtil<Case> {

    @Autowired
    private CaseRepository caseRepository;

    @Autowired EngineUtil engineUtil;

    @PostConstruct
    private void init(){
        this.repository=caseRepository;

    }









}
