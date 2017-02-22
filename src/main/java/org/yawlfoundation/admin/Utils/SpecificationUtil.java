package org.yawlfoundation.admin.Utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;
import org.yawlfoundation.admin.Data.Repositories.SpecificationRepository;
import org.yawlfoundation.admin.Data.Specification;
import org.yawlfoundation.admin.Data.Tenant;
import org.yawlfoundation.yawl.elements.*;
import org.yawlfoundation.yawl.elements.data.YParameter;
import org.yawlfoundation.yawl.exceptions.YAWLException;
import org.yawlfoundation.yawl.exceptions.YSyntaxException;
import org.yawlfoundation.yawl.unmarshal.YMarshal;

import org.yawlfoundation.admin.Data.Specification;
import org.yawlfoundation.yawl.unmarshal.YMetaData;
import org.yawlfoundation.yawl.util.StringUtil;
import org.yawlfoundation.yawl.util.YVerificationHandler;

import javax.annotation.PostConstruct;

import java.util.Iterator;
import java.util.List;


/**
 * Created by root on 17-2-7.
 */
@Component
public class SpecificationUtil extends BaseUtil<Specification> {

    @Autowired
    private YawlUtil yawlUtil;

    @Autowired
    private SpecificationRepository specificationRepository;

    Logger logger= LoggerFactory.getLogger(this.getClass());

    @PostConstruct
    private void init() {
        this.repository = specificationRepository;
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
            //specs.append(StringUtil.wrap(_engine.getLoadStatus(spec.getSpecificationID()),
              //      "status"));
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
