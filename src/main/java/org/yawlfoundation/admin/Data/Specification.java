package org.yawlfoundation.admin.Data;

import org.yawlfoundation.yawl.elements.YSpecification;

import javax.persistence.*;
import java.util.Set;

/**
 * Created by root on 17-2-7.
 */
@Entity
public class Specification {

    @Id
    @GeneratedValue
    private Long specificationId;
    private String specificationXML;

    public void setSpecificationId(Long specificationId) {
        this.specificationId = specificationId;
    }

    @ManyToOne

    @JoinColumn
    private Tenant tenant;



    public String getSpecificationXML() {
        return specificationXML;
    }

    public void setSpecificationXML(String specificationXML) {
        this.specificationXML = specificationXML;
    }


    public Tenant getTenant() {
        return tenant;
    }

    public void setTenant(Tenant tenant) {
        this.tenant = tenant;
    }


    public Long getSpecificationId() {
        return specificationId;
    }

    public Specification(){

    }
    public Specification(String specificationXML) {
        this.specificationXML = specificationXML;
    }
}
