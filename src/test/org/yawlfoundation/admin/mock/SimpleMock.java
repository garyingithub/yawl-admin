package org.yawlfoundation.admin.mock;

import org.junit.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.yawlfoundation.admin.controller.Dispatcher;
import org.yawlfoundation.admin.data.Engine;

import java.util.HashMap;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Created by gary on 1/03/2017.
 */
public class SimpleMock {

    @Test
    public void testDispatcher() throws Exception {

        HashMap<Engine,String> testMap=new HashMap<Engine, String>();
        Engine engine=new Engine("qwe","1");

        Engine engine1=new Engine("qwe","1");
        testMap.put(engine,"1");


        System.out.println(testMap.get(engine1).hashCode());


    }
}
