package org.yawlfoundation.admin.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.yawlfoundation.admin.Data.Tenant;
import org.yawlfoundation.admin.Utils.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

/**
 * Created by root on 17-2-8.
 */
@WebServlet(urlPatterns = "/yawl/ia/*")
public class InterfaceA extends BaseServlet{



    @Autowired
    SpecificationUtil specificationUtil;

    @Autowired
    private YawlUtil yawlUtil;
    @Autowired
    private CustomServiceUtil customServiceUtil;
    @Autowired
    private UserUtil userUtil;

    public InterfaceA(YawlUtil yawlUtil) {
        this.yawlUtil = yawlUtil;
    }

    @Override
    String processPostQuery(HttpServletRequest request,Tenant tenant) {

      //  Tenant tenant=tenantUtil.getObjectById(Long.parseLong(tenantId));
        String userID = request.getParameter("userID");
        StringBuilder msg=new StringBuilder();

        switch (request.getParameter("action")){

            case "connect": return UUID.randomUUID().toString();
            case "checkConnection": return YawlUtil.successMessage("");
            case "upload": String specXML=request.getParameter("specXML");
                msg.append(specificationUtil.loadSpecification(specXML,tenant));
                break;
            case "getYAWLServices": msg.append(customServiceUtil.getYAWLServices(tenant));
                break;
            case "getPassword":
                if(userID.equals("admin"))
                    msg.append("Se4tMaQCi9gr0Q2usp7P56Sk5vM=");
                else if(userID.equals("editor"))
                    msg.append("VfrZ/SW35S1ytFXq9Giw7+A05wA=");
                break;
            case "getBuildProperties":
                msg.append(YawlUtil.getBuildProperties());
                break;
            case "getExternalDBGateways":
                msg.append("<ExternalDBGateways/>");
                break;
            case "getList":
                msg.append(specificationUtil.getSpecificationList(tenant));
                break;
            case "newYAWLService":
                String serviceStr=request.getParameter("service");
                msg.append(customServiceUtil.addYawlService(tenant,serviceStr));
                break;
            case "getAccounts":
                msg.append(userUtil.getAccounts(tenant));
                break;


            default:
                break;

        }
        return msg.toString();


    }


}
