package org.yawlfoundation.admin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.Bean;
import org.yawlfoundation.admin.Controllers.InterfaceA;
import org.yawlfoundation.admin.Data.Repositories.TenantRepository;
import org.yawlfoundation.admin.Data.Tenant;
import org.yawlfoundation.admin.Utils.YawlUtil;

/**
 * Created by root on 17-2-7.
 */

@SpringBootApplication
@ServletComponentScan
public class YawlAdminApplication {

    public static void main(String[] args) {
        SpringApplication.run(YawlAdminApplication.class, args);

    }



    @Bean
    public YawlUtil yawlUtil(){
        return new YawlUtil();
    }



}
