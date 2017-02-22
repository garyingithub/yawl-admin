package org.yawlfoundation.admin.Data;

import javax.persistence.*;

/**
 * Created by root on 17-2-7.
 */
@Entity
public class Case {

    @Id
    @GeneratedValue
    private Long caseId;



    @ManyToOne
    @JoinColumn
    private Specification specification;

    @ManyToOne(targetEntity = Engine.class,cascade = CascadeType.ALL)
    @JoinColumn
    private Engine engine;

    public Long getCaseId() {
        return caseId;
    }

    public Specification getSpecification() {
        return specification;
    }

    public void setSpecification(Specification specification) {
        this.specification = specification;
    }

    public Engine getEngine() {
        return engine;
    }

    public void setEngine(Engine engine) {
        this.engine = engine;
    }


    @Override
    public int hashCode() {
        return caseId.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        boolean result=false;
        if(obj instanceof Case){
            Case c=(Case) obj;
            result=c.caseId.equals(c.getCaseId());
        }
        return result;
    }
}
