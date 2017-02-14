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

    @Enumerated(EnumType.STRING)
    private CaseStatus status;

    @ManyToOne
    @JoinColumn
    private Specification specification;

    @ManyToOne
    @JoinColumn
    private Engine engine;



    public CaseStatus getStatus() {
        return status;
    }

    public void setStatus(CaseStatus status) {
        this.status = status;
    }


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
}
