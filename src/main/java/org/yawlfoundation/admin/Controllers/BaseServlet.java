package org.yawlfoundation.admin.Controllers;

import org.slf4j.LoggerFactory;
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
public abstract class BaseServlet extends HttpServlet {

    protected org.slf4j.Logger logger= LoggerFactory.getLogger(this.getClass());

    private OutputStreamWriter prepareResponse(HttpServletResponse response) throws IOException {
        response.setContentType("text/xml; charset=UTF-8");
        return new OutputStreamWriter(response.getOutputStream(), "UTF-8");
    }



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

            output.append(processPostQuery(req,folders[2]));
        }
        output.append("</response>");


        outputWriter.write(output.toString());
        outputWriter.flush();
        outputWriter.close();



    }

    abstract String processPostQuery(HttpServletRequest request,String tenantId);
}
