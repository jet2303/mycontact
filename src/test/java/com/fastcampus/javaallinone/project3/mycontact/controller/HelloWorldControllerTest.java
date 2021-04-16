package com.fastcampus.javaallinone.project3.mycontact.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class HelloWorldControllerTest {

    @Autowired
    private HelloWorldController helloWorldController;

    //request , response를 생성하여 테스트 할수있게함
    private MockMvc mockMvc;

    @Test
    void helloWorld(){
        System.out.println(helloWorldController.helloWorld());

        Assertions.assertEquals(helloWorldController.helloWorld(), "Hello World");
    }

    @Test
    void mockMvcTest() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(helloWorldController).build();
        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/helloWorld")      //http Get 으로 호출
        ).andDo(MockMvcResultHandlers.print())     //조금더 구체적인 정보 표시
        .andExpect(MockMvcResultMatchers.status().isOk())      //예상 ( http status : 200)
        .andExpect(MockMvcResultMatchers.content().string("Hello World"));  //문자열 예상
    }
}