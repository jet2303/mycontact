package com.fastcampus.javaallinone.project3.mycontact.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.jupiter.api.Assertions.*;

class PersonControllerTest {
    @Autowired
    private PersonController personController;

    private MockMvc mockMvc;

    @Test
    void getPerson() throws Exception{
        mockMvc = MockMvcBuilders.standaloneSetup(personController).build();

        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/person1"));
//                .andDo(print())
//                .andExpect(status().isOk());
    }

    @Test
    void postPerson() throws Exception{
        mockMvc = MockMvcBuilders.standaloneSetup(personController).build();

        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/person?name=martin2&age=20&bloodType=A")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
               .content("{\n" +
                       "    name : \"martin2\", age : 20, bloodType : \"A\"\n" +
                        "}"));

//                .andDo(print())
//                .andExpect(status().isOk());

    }
}