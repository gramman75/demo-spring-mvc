package com.gramman75.demowebmvc;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest
public class SampleControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    public void helloTest() throws Exception {
        mockMvc.perform(get("/hello/gramman"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("hello gramman"));

    }

    @Test
    public void helloJsonTest() throws Exception {
        mockMvc.perform(get("/helloJson")
                    .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                    .accept(MediaType.TEXT_PLAIN_VALUE))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void helloHeaderTest() throws Exception {
        mockMvc.perform(get("/helloHeader")
                    .header(HttpHeaders.FROM, "localhost"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void helloParamTest() throws Exception {
        mockMvc.perform(get("/helloParam")
                    .param("name", "gramman75" )
                ).andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void helloAnnoTest() throws Exception {
        mockMvc.perform(get("/helloAnno"))
                .andDo(print())
                .andExpect(status().isOk());
    }
}