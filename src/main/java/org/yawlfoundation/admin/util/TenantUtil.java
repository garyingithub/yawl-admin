package org.yawlfoundation.admin.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Component;
import org.yawlfoundation.admin.data.CustomService;
import org.yawlfoundation.admin.data.repository.TenantRepository;
import org.yawlfoundation.admin.data.Tenant;
import org.yawlfoundation.admin.data.User;

import javax.annotation.PostConstruct;

/**
 * Created by root on 17-2-14.
 */
@Component
public class TenantUtil extends BaseUtil<Tenant>{

    @Autowired
    private TenantRepository tenantRepository;

    @Autowired
    private UserUtil userUtil;

    @Autowired
    private CustomServiceUtil customServiceUtil;

    @PostConstruct
    private void init(){
        this.repository=tenantRepository;
        if(!repository.findAll().iterator().hasNext()) {
            Tenant tenant = new Tenant("test");
            CustomService resourceService;
            resourceService = new CustomService("http://localhost:8080/resourceService");
            resourceService.setUserName("DefaultWorklist:1");
            resourceService.setUserPassword("ehBHOJc1c7Y/E73HodgW4JtgFq0=");


           // this.storeObject(tenant);
            resourceService.setTenant(tenant);

            tenant.setDefaultWorkList(resourceService);

            User user=new User();
            user.setUserName("editor:1");
            user.setUserPassword("VfrZ/SW35S1ytFXq9Giw7+A05wA=");
            user.setTenant(tenant);

            User user2=new User();
            user2.setUserName("admin");
            user2.setUserPassword("Se4tMaQCi9gr0Q2usp7P56Sk5vM=");
            user2.setTenant(tenant);

            this.storeObject(tenant);
            customServiceUtil.storeObject(resourceService);
            userUtil.storeObject(user);
            userUtil.storeObject(user2);

            //    System.out.println(this.tenantRepository.findAll().iterator().next().getCustomServices().size());

        }
    }





}
