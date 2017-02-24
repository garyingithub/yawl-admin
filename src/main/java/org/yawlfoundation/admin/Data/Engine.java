package org.yawlfoundation.admin.Data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;
import org.yawlfoundation.admin.Utils.RequestHelper;
import org.yawlfoundation.yawl.engine.interfce.Interface_Client;

import javax.persistence.*;
import java.io.IOException;
import java.util.Set;

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

    @Transient
    private Interface_Client client=new Interface_Client();



    @Transient
    private String sessionHandle;


    @OneToMany(targetEntity = YawlCase.class,mappedBy = "engine",cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private Set<YawlCase> yawlCases;


    public Engine(String address) {
        this.address = address;
    }

    public Long getEngineId() {
        return engineId;
    }
    public void setEngineId(Long engineId) {
        this.engineId = engineId;
    }

    private String getAddress() {
        return address;
    }

    public String getIAURI(){
        return address+"/yawl/ia";
    }

    public String getIBURI(){
        return address+"/yawl/ib";
    }
    public void setAddress(String address) {
        this.address = address;
    }

    public String refreshSession(RequestHelper requestHelper) throws IOException {
        sessionHandle =requestHelper.getSessionHandle(this.getIAURI());
        return sessionHandle;
    }

    public String getSessionHandle(){
        return sessionHandle;
    }




}
