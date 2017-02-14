package org.yawlfoundation.admin.Utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.yawlfoundation.admin.Data.Repositories.CustomServiceRepository;

/**
 * Created by root on 17-2-7.
 */
@ComponentScan
public class ServiceUtil {

   @Autowired
   private CustomServiceRepository serviceRepository;






}
