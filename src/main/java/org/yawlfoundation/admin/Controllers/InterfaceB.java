package org.yawlfoundation.admin.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.yawlfoundation.admin.Data.Tenant;
import org.yawlfoundation.admin.Utils.SpecificationUtil;
import org.yawlfoundation.admin.Utils.TenantUtil;
import org.yawlfoundation.admin.Utils.YawlUtil;

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
@WebServlet(urlPatterns = "/yawl/ib/*")
public class InterfaceB extends BaseServlet{


    @Autowired
    private TenantUtil tenantUtil;

    @Autowired
    SpecificationUtil specificationUtil;

    @Autowired
    private YawlUtil yawlUtil;

    public InterfaceB(YawlUtil yawlUtil) {
        this.yawlUtil = yawlUtil;
    }

    @Override
    String processPostQuery(HttpServletRequest request,Tenant tenant) {

        //Tenant tenant=tenantUtil.getObjectById(Long.parseLong(tenantId));

        StringBuilder msg=new StringBuilder();

        switch (request.getParameter("action")){

            case "connect": return UUID.randomUUID().toString();
            case "checkConnection": return YawlUtil.successMessage("");
            case "upload": String specXML=request.getParameter("specXML");
                msg.append(specificationUtil.loadSpecification(specXML,tenant));
                break;
            case "getSpecificationPrototypesList":
                msg.append(specificationUtil.getSpecificationList(tenant));
                break;
            default:
                break;

        }
        return msg.toString();


    }


}
