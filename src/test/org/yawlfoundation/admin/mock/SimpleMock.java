package org.yawlfoundation.admin.mock;

import org.junit.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.yawlfoundation.admin.controller.Dispatcher;

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

        Dispatcher dispatcher=new Dispatcher();
        MockMvc mockMvc=standaloneSetup(dispatcher).build();

        mockMvc.perform(post("/yawl/ib").param("userid","admin:1").
                param("password","Se4tMaQCi9gr0Q2usp7P56Sk5vM=").
                param("action","connect"))
                .andExpect(status().is2xxSuccessful()).andDo(print());
    }
}
