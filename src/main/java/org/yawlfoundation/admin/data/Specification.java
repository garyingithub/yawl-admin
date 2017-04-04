package org.yawlfoundation.admin.data;

import org.hibernate.annotations.NaturalId;
import org.yawlfoundation.yawl.elements.YSpecification;
import org.yawlfoundation.yawl.unmarshal.YMarshal;

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

    @Column(columnDefinition = "TEXT",length = 5000)
    private String specificationXML;

    private String name;


    private String version;

    private String documentation;



    private String uniqueID;



    private String uri;

    public String getUniqueID() {
        return uniqueID;
    }

    public void setUniqueID(String uniqueID) {
        this.uniqueID = uniqueID;
    }

    public String getUri() {
        return uri;

    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getDataSchema() {
        return dataSchema;
    }

    public void setDataSchema(String dataSchema) {
        this.dataSchema = dataSchema;
    }

    @Column(columnDefinition = "TEXT")
    private String dataSchema;

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    @ManyToMany(targetEntity = Specification.class,fetch = FetchType.EAGER)
    private Set<CustomService> services;


    @ManyToOne(cascade = {CascadeType.MERGE,CascadeType.REFRESH})
    @JoinColumn
    private Tenant tenant;

    public Specification(YSpecification ySpecification){

        this.setSpecificationXML(YMarshal.marshal(ySpecification));
     //   this.setTenant(tenant);
        this.setDocumentation(ySpecification.getDocumentation());
        this.setName(ySpecification.getName());
        this.setVersion(ySpecification.getSpecVersion());
        //this.setMetaData(ySpecification.getMetaData());
        this.setDataSchema(ySpecification.getDataSchema());
        this.setUri(ySpecification.getURI());
        this.setUniqueID(ySpecification.getID());
    }




    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }




    public String getDocumentation() {
        return documentation;
    }

    public void setDocumentation(String documentation) {
        this.documentation = documentation;
    }


    public void setSpecificationId(Long specificationId) {
        this.specificationId = specificationId;
    }





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
        //tenant.addSpecification(this);
    }

    public Set<CustomService> getServices() {
        return services;
    }

    public Long getSpecificationId() {
        return specificationId;
    }

    public Specification(){

    }
    public Specification(String specificationXML) {
        this.specificationXML = specificationXML;
    }


    @Override
    public boolean equals(Object obj) {
        boolean result=false;
        if(obj instanceof Specification){
            Specification specification=(Specification)obj;
            result=this.getUniqueID().equals(specification.getUniqueID());
        }
        return result;
    }

    @Override
    public int hashCode() {
        return this.getUniqueID().hashCode();
    }
}
