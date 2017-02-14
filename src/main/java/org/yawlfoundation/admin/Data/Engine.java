package org.yawlfoundation.admin.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Created by root on 17-2-7.
 */
@Entity
public class Engine {

    @Id
    @GeneratedValue
    private Long engineId;

    private String address;

    protected Engine(){

    }

    public Engine(String address) {
        this.address = address;
    }

    public Long getEngineId() {
        return engineId;
    }
    public void setEngineId(Long engineId) {
        this.engineId = engineId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
