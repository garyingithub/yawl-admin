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



    @OneToMany(targetEntity = User.class,mappedBy = "tenant",cascade = {CascadeType.ALL})
    private Set<User> users;

    @OneToMany(targetEntity = Specification.class,mappedBy = "tenant",cascade = {CascadeType.ALL})
    private Set<Specification> specifications;

    @OneToMany(targetEntity = CustomService.class,mappedBy = "tenant",cascade = {CascadeType.ALL})
    private Set<CustomService> customServices;




    @OneToOne(targetEntity = CustomService.class,cascade = CascadeType.ALL)
    private CustomService defaultWorkList;

    protected Tenant(){}



    @Transient
    public Set<User> getUsers() {
        return users;
    }

    public void setDefaultWorkList(final CustomService service){
        this.defaultWorkList=service;
        //this.customServices.add(service);
    }

    public void addSpecification(final Specification specification){
        if(this.specifications==null){
            this.specifications=new HashSet<>();
        }
        this.specifications.add(specification);
        if(!this.equals(specification.getTenant())){
            specification.setTenant(this);
        }
    }

    public void addCustomService(final CustomService service){
        if(this.customServices==null){
            this.customServices=new HashSet<>();
        }
        this.customServices.add(service);
        if(!this.equals(service.getTenant())){
            service.setTenant(this);
        }
    }

    public void addUser(final User user){
        if(this.users==null){
            this.users=new HashSet<>();
        }
        this.users.add(user);
        if(!this.equals(user.getTenant())){
            user.setTenant(this);
        }
    }



    public Tenant(String name) {
        this.name=name;
        User admin=new User();
        admin.setUserName("admin");
        admin.setUserPassword("Se4tMaQCi9gr0Q2usp7P56Sk5vM=");
        this.addUser(admin);

        User editor=new User();
        editor.setUserName("editor");
        editor.setUserPassword("VfrZ/SW35S1ytFXq9Giw7+A05wA=");
        this.addUser(editor);
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
