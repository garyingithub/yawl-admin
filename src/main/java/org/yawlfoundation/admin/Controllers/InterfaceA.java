package org.yawlfoundation.admin.Controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
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
@WebServlet(urlPatterns = "/yawl/ia/*")
public class InterfaceA extends BaseServlet{


    private YawlUtil yawlUtil;

    public InterfaceA(YawlUtil yawlUtil) {
        this.yawlUtil = yawlUtil;
    }

    @Override
    String processPostQuery(HttpServletRequest request,String tenantId) {

        switch (request.getParameter("action")){

            case "connect": return UUID.randomUUID().toString();
            case "checkConnection": return YawlUtil.successMessage("");
            case "upload":

        }


        return null;
    }


}
