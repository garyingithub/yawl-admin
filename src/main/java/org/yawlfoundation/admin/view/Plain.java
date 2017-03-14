package org.yawlfoundation.admin.view;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.AbstractUrlBasedView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStreamWriter;
import java.lang.*;
import java.lang.Exception;
import java.util.Map;

/**
 * Created by gary on 1/03/2017.
 */
@Component
public class Plain implements View {

    @Override
    public String getContentType() {
        return "text/html; charset=UTF-8";
    }

    @Override
    public void render(Map<String, ?> map, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
        OutputStreamWriter outputWriter = new OutputStreamWriter(httpServletResponse.getOutputStream(),"UTF-8");
        StringBuilder output = new StringBuilder();
        output.append("<response>");
        if(map.containsKey("result"))
            output.append(map.get("result"));
        output.append("</response>");
        outputWriter.write(output.toString());
        outputWriter.flush();
        outputWriter.close();
    }
}
