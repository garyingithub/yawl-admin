package org.yawlfoundation.admin.data;



import org.springframework.context.annotation.ComponentScan;

import javax.persistence.*;
import java.io.Serializable;
//import javax.persistence.*;


/**
 * Created by root on 17-2-7.
 */

@Entity
public class Tenant implements Serializable{



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

    @Transient
    public CustomService getDefaultWorkList() {
        return defaultWorkList;
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
