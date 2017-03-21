package org.yawlfoundation.admin.data;

import org.yawlfoundation.yawl.util.XNode;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by root on 17-2-21.
 */
@Entity
public class User implements Serializable {

    public User(){

    }


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long userId;

    private String userName;

    private String userPassword;

    private String documentation;

    @ManyToOne
    @JoinColumn
    private Tenant tenant;

    public Tenant getTenant() {
        return tenant;
    }

    public void setTenant(Tenant tenant) {
        this.tenant = tenant;
    }

    public String getUserName() {
        return userName;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getDocumentation() {
        return documentation;
    }

    public void setDocumentation(String documentation) {
        this.documentation = documentation;
    }

    public String toXML(){
        XNode root = new XNode("client");
        root.addChild("username", this.getUserName());
        root.addChild("password", this.getUserPassword());
        root.addChild("documentation", this.getDocumentation());
        return root.toString();
    }

    @Override
    public boolean equals(Object obj) {
        boolean result=false;
        if(obj instanceof User){
            User user=(User) obj;
            result=user.getUserName().equals(this.getUserName());
        }
        return result;
    }

    @Override
    public int hashCode() {
        return this.getUserName().hashCode();
    }
}
