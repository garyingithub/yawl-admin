package org.yawlfoundation.admin.Controllers;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.yawlfoundation.admin.Data.Tenant;
import org.yawlfoundation.admin.Utils.TenantUtil;
import org.yawlfoundation.admin.Utils.YawlUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStreamWriter;

/**
 * Created by gary on 16-8-4.
 */
@Component
public abstract class BaseServlet extends HttpServlet {

    protected org.slf4j.Logger logger= LoggerFactory.getLogger(this.getClass());

    private OutputStreamWriter prepareResponse(HttpServletResponse response) throws IOException {
        response.setContentType("text/xml; charset=UTF-8");
        return new OutputStreamWriter(response.getOutputStream(), "UTF-8");
    }

    @Autowired
    TenantUtil tenantUtil;
    //private Tenant tenant;


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.info(req.getRequestURI());
        logger.info(req.getMethod());
        logger.info(req.getQueryString());


        doPost(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.info(req.getRequestURI());
        logger.info(req.getMethod());


        if(req.getParameter("action")!=null){
            logger.info(req.getParameter("action"));
        }

        OutputStreamWriter outputWriter = this.prepareResponse(resp);
        StringBuilder output = new StringBuilder();
        output.append("<response>");
        String[] folders = req.getRequestURI().split("/");

        if (folders.length<3)
        {
            output.append(YawlUtil.failureMessage("can't process without tenantId"));
        }
        else {

            try {
                output.append(processPostQuery(req,tenantUtil.getObjectById(Long.parseLong(folders[3]))));

            }catch (Exception e){
                output.append(YawlUtil.failureMessage(e.getMessage()));
            }
                 }
        output.append("</response>");


        outputWriter.write(output.toString());
        outputWriter.flush();
        outputWriter.close();



    }

    abstract String processPostQuery(HttpServletRequest request,Tenant tenant);
}
