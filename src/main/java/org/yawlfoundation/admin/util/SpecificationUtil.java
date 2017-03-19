package org.yawlfoundation.admin.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.yawlfoundation.admin.data.repository.SpecificationRepository;
import org.yawlfoundation.admin.data.Specification;
import org.yawlfoundation.admin.data.Tenant;
import org.yawlfoundation.yawl.elements.*;
import org.yawlfoundation.yawl.elements.data.YParameter;
import org.yawlfoundation.yawl.exceptions.YSyntaxException;
import org.yawlfoundation.yawl.unmarshal.YMarshal;

import org.yawlfoundation.yawl.unmarshal.YMetaData;
import org.yawlfoundation.yawl.util.StringUtil;
import org.yawlfoundation.yawl.util.YVerificationHandler;

import javax.annotation.PostConstruct;

import java.util.Iterator;
import java.util.List;
import java.util.Set;


/**
 * Created by root on 17-2-7.
 */
@Component
public class SpecificationUtil extends BaseUtil<Specification> {

    @Autowired
    private YawlUtil yawlUtil;

    @Autowired
    private SpecificationRepository specificationRepository;

    @Autowired
            private TenantUtil tenantUtil;

    Logger logger= LoggerFactory.getLogger(this.getClass());

    @PostConstruct
    private void init() {
        this.repository = specificationRepository;
    }


    public Specification getSpecificationByIdentification(String id){
        Iterator iterator=this.specificationRepository.findByUniqueID(id).iterator();
        if(!iterator.hasNext()){
            return null;
        }else {
            return (Specification) iterator.next();
        }
    }

    public List<Specification> getSpecificationsbyTenant(Tenant t){

        return this.specificationRepository.findByTenant(t);
    }

    public String taskInformation(String id,String taskID){

        Specification specification=getSpecificationByIdentification(id);

        YSpecification spec;
        YTask task = null;

        try {
            spec = YMarshal.unmarshalSpecifications(specification.getSpecificationXML()).get(0);
        } catch (YSyntaxException e) {
            throw new RuntimeException(e);
        }
        if (spec != null) {
            Set<YDecomposition> decompositions = spec.getDecompositions();
            for (YDecomposition decomposition : decompositions) {
                if (decomposition instanceof YNet) {
                    YNet net = (YNet) decomposition;
                    YExternalNetElement element = net.getNetElement(taskID);
                    if ((element != null) && (element instanceof YTask)) {
                        task = (YTask) element;
                        break;                                               // found it
                    }
                }
            }
        }


        if (task != null) {
            return task.getInformation();
        } else {
            return YawlUtil.failureMessage("The was no task found with ID " + taskID);
        }
    }
    public String getSpecificationList(Tenant t)  {

        List<Specification> specifications=this.specificationRepository.findByTenant(t);

        StringBuilder specs = new StringBuilder();
        for (Specification s : specifications) {
            YSpecification spec= null;
            try {
                spec = YMarshal.unmarshalSpecifications(s.getSpecificationXML()).get(0);
            } catch (YSyntaxException e) {
                e.printStackTrace();
            }
            specs.append("<specificationData>");
            specs.append(StringUtil.wrap(spec.getURI(), "uri"));

            if (spec.getID() != null) {
                specs.append(StringUtil.wrap(spec.getID(), "id"));
            }
            if (spec.getName() != null) {
                specs.append(StringUtil.wrap(spec.getName(), "name"));
            }
            if (spec.getDocumentation() != null) {
                specs.append(StringUtil.wrap(spec.getDocumentation(), "documentation"));
            }

            Iterator inputParams = spec.getRootNet().getInputParameters().values().iterator();
            if (inputParams.hasNext()) {
                specs.append("<params>");
                while (inputParams.hasNext()) {
                    YParameter inputParam = (YParameter) inputParams.next();
                    specs.append(inputParam.toSummaryXML());
                }
                specs.append("</params>");
            }
            specs.append(StringUtil.wrap(spec.getRootNet().getID(), "rootNetID"));
            specs.append(StringUtil.wrap(spec.getSchemaVersion().toString(), "version"));
            specs.append(StringUtil.wrap(spec.getSpecVersion(), "specversion"));
            specs.append(StringUtil.wrap("loaded",
                    "status"));
            YMetaData metadata = spec.getMetaData();
            if (metadata != null) {
                specs.append(StringUtil.wrap(metadata.getTitle(), "metaTitle"));
                List<String> creators = metadata.getCreators();
                if (creators != null) {
                    specs.append("<authors>");
                    for (String author : creators) {
                        specs.append(StringUtil.wrap(author, "author"));
                    }
                    specs.append("</authors>");
                }
            }
            String gateway = spec.getRootNet().getExternalDataGateway();
            if (gateway != null) {
                specs.append(StringUtil.wrap(gateway, "externalDataGateway"));
            }
            specs.append("</specificationData>");
        }
        return specs.toString();


    }
    public String loadSpecification(String xml,Tenant t){

        List<YSpecification> specifications;

        try {
            specifications=YMarshal.unmarshalSpecifications(xml);
        } catch (YSyntaxException e) {
            return e.getMessage();
        }

        YVerificationHandler handler=new YVerificationHandler();
        for(YSpecification ySpecification: specifications) {
            ySpecification.verify(handler);

            if (!handler.hasErrors()) {
                if (this.specificationRepository.findByUniqueID(ySpecification.getID()).size() == 0) {
                    Specification s=new Specification(ySpecification);
                    s.setTenant(t);
                    tenantUtil.storeObject(t);
                    this.storeObject(s);

                } else {
                    String errDetail = ySpecification.getSchemaVersion().isBetaVersion() ?
                            "URI: " + ySpecification.getURI() : "UID: " + ySpecification.getID();
                    errDetail += "- Version: " + ySpecification.getSpecVersion();
                    return ("There is a specification with an identical id to ["
                            + errDetail + "] already loaded into the engine.");

                }

            } else {
                return YawlUtil.failureMessage(handler.getErrors().toString());
            }
        }

        return YawlUtil.successMessage("");


    }



}
