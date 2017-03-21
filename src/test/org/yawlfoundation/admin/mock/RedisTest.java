package org.yawlfoundation.admin.mock;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.yawlfoundation.admin.data.CustomService;
import org.yawlfoundation.admin.data.Specification;
import org.yawlfoundation.admin.data.Tenant;
import org.yawlfoundation.admin.data.YawlCase;
import org.yawlfoundation.admin.util.CaseUtil;

import static org.junit.Assert.assertEquals;

/**
 * Created by gary on 21/03/2017.
 */


public class RedisTest {



    @Test
    public void addCase(){

        YawlCase c=new YawlCase();
        Specification specification=new Specification();
        specification.setName("test");
        Tenant tenant=new Tenant("test");
        CustomService defaultWorklist=new CustomService("http://test.com");
        tenant.setDefaultWorkList(defaultWorklist);
        specification.setTenant(tenant);


        CaseUtil caseUtil=new CaseUtil();

        caseUtil.bindCaseSpecification(c,specification);


       // caseUtil.storeObject(c);
        c.setCaseId("1");
        assertEquals(caseUtil.getDefaultWorklistByCaseID(String.valueOf(c.getCaseId())),
                String.valueOf(c.getSpecification().getTenant().getDefaultWorkList()));

    }
}
