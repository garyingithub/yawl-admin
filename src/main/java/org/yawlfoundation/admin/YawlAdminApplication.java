package org.yawlfoundation.admin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.BeanNameViewResolver;
import org.yawlfoundation.admin.view.Plain;

/**
 * Created by root on 17-2-7.
 */

@SpringBootApplication
@Import(Plain.class)
public class YawlAdminApplication {

    public static void main(String[] args) {
        SpringApplication.run(YawlAdminApplication.class, args);
    }


    @Bean
    BeanNameViewResolver beanNameViewResolver(){
        BeanNameViewResolver resolver=new BeanNameViewResolver();
        resolver.setOrder(1);
        return resolver;
    }

    @Bean
    View plain(){
        return new Plain();
    }
}


