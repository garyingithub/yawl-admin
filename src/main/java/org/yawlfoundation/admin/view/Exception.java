package org.yawlfoundation.admin.view;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.View;
import org.yawlfoundation.admin.util.YawlUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStreamWriter;
import java.util.Map;

/**
 * Created by gary on 1/03/2017.
 */
@Component
public class Exception implements View {

    @Override
    public String getContentType() {
        return "text/html; charset=UTF-8";
    }

    @Override
    public void render(Map<String, ?> map, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws java.lang.Exception {
        OutputStreamWriter outputWriter = new OutputStreamWriter(httpServletResponse.getOutputStream(),"UTF-8");
        StringBuilder output = new StringBuilder();
        output.append("<response>");
        output.append(YawlUtil.failureMessage(""));
        output.append("</response>");
        outputWriter.write(output.toString());
        outputWriter.flush();
        outputWriter.close();
    }


}
