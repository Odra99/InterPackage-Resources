package com.interpackage.resources.controller;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.junit.jupiter.Testcontainers;

import com.interpackage.resources.AbstractIntegrationTest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.Matchers.containsString;

@AutoConfigureMockMvc
@SpringBootTest
@Testcontainers
public class PathControllerTest extends AbstractIntegrationTest{

    @Autowired
    private MockMvc mockMvc;

    private String path="paths";


    @Test
    public void createPathResource() throws Exception{
        this.mockMvc.perform(post(path+"/")).andDo(print()).andExpect(status().isOk())
        .andExpect(content().string(containsString("Hello, World")));
    }
    
}
