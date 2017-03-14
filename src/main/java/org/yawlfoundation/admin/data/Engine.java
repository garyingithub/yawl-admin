package org.yawlfoundation.admin.data;

import org.yawlfoundation.admin.util.RequestHelper;

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



    protected Engine(){

    }

    public Engine(String hostAddress, String port) {
        this.hostAddress = hostAddress;
        this.port = port;
    }


//@Transient
    //private Interface_Client client=new Interface_Client();


    private String hostAddress;
    private String port;

    public String getHostAddress() {
        return hostAddress;
    }

    public void setHostAddress(String hostAddress) {
        this.hostAddress = hostAddress;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    @Transient
    private String sessionHandle;


    @OneToMany(targetEntity = YawlCase.class,mappedBy = "engine",cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private Set<YawlCase> yawlCases;


    public Long getEngineId() {
        return engineId;
    }
    public void setEngineId(Long engineId) {
        this.engineId = engineId;
    }

    private String getAddress() {
        return this.hostAddress+":"+port;
    }

    public String getIAURI(){
        return this.getAddress()+"/yawl/ia";
    }

    public String getIBURI(){
        return this.getAddress()+"/yawl/ib";
    }


    public String refreshSession(RequestHelper requestHelper) throws IOException {
        sessionHandle =requestHelper.getSessionHandle(this);
        return sessionHandle;
    }

    public String getSessionHandle(){
        return sessionHandle;
    }




}
