package org.yawlfoundation.admin.Data;



import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.List;
import java.util.Set;

/**
 * Created by root on 17-2-7.
 */
@Entity
public class Tenant {


    private Long tenantId;
    private String name;
    private Set<Specification> specifications;


    protected Tenant(){}

    public Tenant(String name){
        this.name=name;
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
