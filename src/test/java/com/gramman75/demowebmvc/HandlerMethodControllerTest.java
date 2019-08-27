package com.gramman75.demowebmvc;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.web.servlet.ModelAndView;

import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest
public class HandlerMethodControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    public void getEventTest() throws Exception {
        mockMvc.perform(post("/events")
                .param("name", "gramman75")
                .param("limit", "20"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("name").value("gramman75"))
                .andExpect(jsonPath("limit").value("20"))
                .andExpect(request().sessionAttribute("event", notNullValue()));
    }

    @Test
    public void  getEventModelAttrTest() throws Exception {
        mockMvc.perform(post("/eventsModelAttr")
                    .param("limit", "-10"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("name").value("gramman75"));
    }

    @Test
    public void getEventModelViewTest() throws Exception {
        mockMvc.perform(post("/eventsModelView")
                    .param("limit", "-10"))
                .andDo(print())
                .andExpect(status().isOk());


    }

}