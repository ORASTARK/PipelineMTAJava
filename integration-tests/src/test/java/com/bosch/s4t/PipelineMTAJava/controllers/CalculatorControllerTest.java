package com.bosch.s4t.PipelineMTAJava.controllers;

import com.bosch.s4t.PipelineMTAJava.Application;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import com.sap.cloud.sdk.cloudplatform.thread.ThreadContextExecutor;
import com.sap.cloud.sdk.testutil.MockUtil;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith( SpringRunner.class )
//@ComponentScan({"com.sap.cloud.sdk", "com.bosch.s4t.PipelineMTAJava"})
//@SpringBootTest(classes= Application.class)
@WebMvcTest
public class CalculatorControllerTest
{
    private static final MockUtil mockUtil = new MockUtil();

    @Autowired
    private MockMvc mvc;

    @BeforeClass
    public static void beforeClass()
    {
        mockUtil.mockDefaults();
    }

    @Test
    public void testAdd() throws Exception
    {
        new ThreadContextExecutor().execute(() -> {
            mvc.perform(MockMvcRequestBuilders.get("/add?p1=1&p2=2"))
                    .andExpect(status().isOk())
                    .andExpect(MockMvcResultMatchers.jsonPath(".value").value(3.0));
        });
    }
}