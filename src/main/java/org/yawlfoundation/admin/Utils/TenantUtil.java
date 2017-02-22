package org.yawlfoundation.admin.Utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.yawlfoundation.admin.Data.CustomService;
import org.yawlfoundation.admin.Data.Repositories.CustomServiceRepository;
import org.yawlfoundation.admin.Data.Repositories.TenantRepository;
import org.yawlfoundation.admin.Data.Tenant;

import javax.annotation.PostConstruct;

/**
 * Created by root on 17-2-14.
 */
@Component
public class TenantUtil extends BaseUtil<Tenant>{

    @Autowired
    private TenantRepository tenantRepository;

    @Autowired
    private CustomServiceRepository serviceRepository;

    @PostConstruct
    private void init(){
        this.repository=tenantRepository;
        if(!repository.findAll().iterator().hasNext()) {
            Tenant tenant = new Tenant("test");
            CustomService resourceService;
            resourceService = new CustomService("http://localhost:8080/resourceService");
            resourceService.setUserName("DefaultWorklist");
            resourceService.setUserPassword("resource");


           // this.storeObject(tenant);
            resourceService.setTenant(tenant);

            tenant.setDefaultWorkList(resourceService);
            serviceRepository.save(resourceService);


            this.storeObject(tenant);
        //    System.out.println(this.tenantRepository.findAll().iterator().next().getCustomServices().size());

        }
    }





}
