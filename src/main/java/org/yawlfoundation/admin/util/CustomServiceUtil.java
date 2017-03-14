package org.yawlfoundation.admin.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.yawlfoundation.admin.data.CustomService;
import org.yawlfoundation.admin.data.Repositories.CustomServiceRepository;
import org.yawlfoundation.admin.data.Tenant;
import org.yawlfoundation.yawl.elements.YAWLServiceReference;
import org.yawlfoundation.yawl.util.HttpURLValidator;

import javax.annotation.PostConstruct;
import java.util.TreeMap;

/**
 * Created by root on 17-2-7.
 */
@Component
public class CustomServiceUtil extends BaseUtil<CustomService> {

   @Autowired
   private CustomServiceRepository serviceRepository;

   @PostConstruct
   private void init(){
      this.repository=serviceRepository;
   }

   public String getYAWLServices(Tenant tenant){
      StringBuilder result=new StringBuilder();
      for(Object service:this.serviceRepository.findByTenant(tenant)){
         CustomService customService=(CustomService)service;
         result.append(customService.toXMLComplete());
      }
      return result.toString();
   }

   public String addYawlService(Tenant tenant,String serviceStr){
      YAWLServiceReference service = YAWLServiceReference.unmarshal(serviceStr);
      if (null != service) {
         if (0 == this.serviceRepository.findByUri(service.getURI()).size()) {
            if (HttpURLValidator.validate(service.getURI()).startsWith("<success")) {

                  CustomService customService=new CustomService(service.getURI());
                  customService.setAssignable(service.get_assignable());
                  customService.setUserName(service.getServiceName());
                  customService.setUserPassword(service.getServicePassword());

                  customService.setTenant(tenant);

                  this.storeObject(customService);
                  return YawlUtil.successMessage("");

            }
            else {
               return YawlUtil.failureMessage("Service unresponsive: " + service.getURI());
            }
         } else {
            return YawlUtil.failureMessage("Engine has already registered a service with " +
                    "the same URI [" + service.getURI() + "]");
         }
      } else {
         return YawlUtil.failureMessage("Failed to parse yawl service from [" +
                 serviceStr + "]");
      }

   }





}
