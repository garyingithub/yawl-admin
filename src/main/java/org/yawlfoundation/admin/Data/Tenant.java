package org.yawlfoundation.admin.Data;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;
import org.yawlfoundation.admin.Utils.CustomServiceUtil;

import javax.annotation.PostConstruct;
import javax.persistence.*;
//import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by root on 17-2-7.
 */
@ComponentScan
@Entity
public class Tenant {



    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long tenantId;
    private String name;








    @OneToOne(targetEntity = CustomService.class,cascade = CascadeType.ALL)
    private CustomService defaultWorkList;

    protected Tenant(){}





    public void setDefaultWorkList(final CustomService service){
        this.defaultWorkList=service;
        //this.customServices.add(service);
    }









    public Tenant(String name) {
        this.name=name;
        User admin=new User();
        admin.setUserName("admin");
        admin.setUserPassword("Se4tMaQCi9gr0Q2usp7P56Sk5vM=");


        User editor=new User();
        editor.setUserName("editor");
        editor.setUserPassword("VfrZ/SW35S1ytFXq9Giw7+A05wA=");

    }





    @Id
    @GeneratedValue
    public Long getTenantId() {
        return tenantId;
    }

    public void setTenantId(Long tenantId) {
        this.tenantId = tenantId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }



}
