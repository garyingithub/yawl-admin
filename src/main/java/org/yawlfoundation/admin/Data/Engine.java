package org.yawlfoundation.admin.Data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.yawlfoundation.admin.Utils.CaseUtil;
import org.yawlfoundation.admin.Utils.RequestHelper;
import org.yawlfoundation.admin.Utils.YawlUtil;
import org.yawlfoundation.yawl.engine.interfce.Interface_Client;

import javax.persistence.*;
import java.io.IOException;
import java.util.Set;

/**
 * Created by root on 17-2-7.
 */
@ComponentScan
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
    @Autowired
    private RequestHelper requestHelper;

    @Transient
    private String session;

    @Transient
    @Autowired
    private CaseUtil caseUtil;

    @OneToMany(targetEntity = Case.class,mappedBy = "engine",cascade = CascadeType.ALL)
    private Set<Case> cases;


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

    public String launchCase(Specification specification){

        String result;
        try {
            for(CustomService service:specification.getServices()){
                requestHelper.registerService(this.address,session,service);

            }
        } catch (IOException e) {
            result=YawlUtil.failureMessage(e.getMessage());
            return result;
        }

        try {
            requestHelper.loadSpecification(this.address,session,specification);
        } catch (IOException e) {
            result=YawlUtil.failureMessage(e.getMessage());
            return result;
        }

        Case c=new Case();
        c.setSpecification(specification);
        c.setEngine(this);
        caseUtil.storeObject(c);
        try {
            return requestHelper.launchCase(this.address,session,c);
        } catch (IOException e) {
            result=YawlUtil.failureMessage(e.getMessage());
            return result;
        }
    }


}
