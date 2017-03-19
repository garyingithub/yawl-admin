package org.yawlfoundation.admin.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.yawlfoundation.admin.Constant;
import org.yawlfoundation.admin.data.CustomService;
import org.yawlfoundation.admin.data.Engine;
import org.yawlfoundation.admin.data.Specification;
import org.yawlfoundation.admin.data.YawlCase;

import java.io.IOException;

/**
 * Created by gary on 18/03/2017.
 */

@Component
public class RequestUtil {


    @Autowired
    private RequestHelper helper;

    @Autowired
    private ParametersFactory factory;


    public String getWorkItem(String woriItemID,Engine engine) throws IOException {
        return helper.sendRequest(engine,factory.getWorkItemParams(woriItemID),
                Constant.InterfaceType.INTERFACE_B);

    }
    public String checkIn(String workItemID,Engine engine,String data,String logPredicate) throws IOException {
        return helper.sendRequest(engine,factory.checkInParams(workItemID,data,logPredicate),
                Constant.InterfaceType.INTERFACE_B);

    }

    public String checkOut(String workItemID,Engine engine) throws IOException {

        return helper.sendRequest(engine,factory.checkOutParams(workItemID),
                Constant.InterfaceType.INTERFACE_B);


    }

    public String connect(Engine engine) throws IOException {
        return helper.sendRequest(engine,factory.connectParams(), Constant.InterfaceType.INTERFACE_B);
    }

    public String loadSpecification(Engine engine, Specification specification) throws IOException {


        return helper.sendRequest(engine,factory.loadSpecificationParams(specification.getSpecificationXML()),
                Constant.InterfaceType.INTERFACE_A);
    }

    public String registerService(Engine engine,CustomService service) throws IOException {


        return helper.sendRequest(engine,factory.registerServiceParams(service),
                Constant.InterfaceType.INTERFACE_A);

    }

    public String launchCase(Engine engine,YawlCase c) throws IOException {

       return helper.sendRequest(engine,factory.launchCaseParams(c,c.getSpecification()),
               Constant.InterfaceType.INTERFACE_B);

    }



}
