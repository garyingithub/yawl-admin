package org.yawlfoundation.admin.data;

import org.hibernate.annotations.NaturalId;
import org.yawlfoundation.yawl.util.XNode;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by root on 17-2-7.
 */
@Entity
public class CustomService extends User implements Serializable{


    public CustomService(){

    }


    private String uri;

    private boolean assignable;


    public boolean isAssignable() {
        return assignable;
    }

    public void setAssignable(boolean assignable) {
        this.assignable = assignable;
    }


    public String getUri() {
        return uri;
    }

    public CustomService(String uri) {
        this.uri = uri;
    }



    public void setUri(String uri) {
        this.uri = uri;
    }

    public String toXMLComplete() {
        XNode root = toBasicXNode();
        root.addChild("servicename", this.getUserName());
        root.addChild("servicepassword", this.getUserPassword());
        root.addChild("assignable", true);
        return root.toString();
    }

    private XNode toBasicXNode() {
        XNode root = new XNode("yawlService");
        root.addAttribute("id", this.getUri());
        if (this.getDocumentation() != null) {
            root.addChild("documentation", this.getDocumentation());
        }
        return root;
    }


    @Override
    public boolean equals(Object obj) {
        boolean result=false;
        if(obj instanceof CustomService){
            CustomService service=(CustomService) obj;
            result=this.getUri().equals(service.getUri());
        }
        return result;
    }

    @Override
    public int hashCode() {
        return this.getUri().hashCode();
    }
}
