package org.yawlfoundation.admin.Data;

import org.yawlfoundation.yawl.elements.YAWLServiceReference;

import javax.persistence.*;

/**
 * Created by root on 17-2-7.
 */
@Entity
public class CustomService {

    @Id
    @GeneratedValue
    private Long serviceId;


    private String uri;

    @ManyToOne
    @JoinColumn
    private Tenant tenant;

    public String getUri() {
        return uri;
    }

    public CustomService(String uri) {
        this.uri = uri;
    }

    public Long getServiceId() {
        return serviceId;
    }

    public void setServiceId(Long serviceId) {
        this.serviceId = serviceId;
    }

    public Tenant getTenant() {
        return tenant;
    }

    public void setTenant(Tenant tenant) {
        this.tenant = tenant;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }
}
