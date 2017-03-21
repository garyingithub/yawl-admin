package org.yawlfoundation.admin.data;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.yawlfoundation.admin.util.RequestHelper;
import org.yawlfoundation.admin.util.RequestUtil;

import javax.persistence.*;
import java.io.IOException;
import java.io.Serializable;
import java.util.Set;

/**
 * Created by root on 17-2-7.
 */

@Entity
public class Engine implements Serializable{

    

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

    public String getAddress() {
        return this.hostAddress+":"+port;
    }

    public String getIAURI(){
        return this.getAddress()+"/yawl/ia";
    }

    public String getIBURI(){
        return this.getAddress()+"/yawl/ib";
    }


    public String getSessionHandle(){
        return sessionHandle;
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this,"hostAddress","port");
    }

    @Override
    public boolean equals(Object obj) {
        return EqualsBuilder.reflectionEquals(this,obj,"hostAddress","port");
    }

}
