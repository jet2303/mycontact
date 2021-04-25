package com.fastcampus.javaallinone.project3.mycontact.controller;

import com.fastcampus.javaallinone.project3.mycontact.exception.dto.ErrorResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class HelloWorldControllerTest {

    @Autowired
    private HelloWorldController helloWorldController;

    @Autowired
    private WebApplicationContext wac;

    //request , response를 생성하여 테스트 할수있게함
    private MockMvc mockMvc;

    @BeforeEach()
    void beforeEach(){
        mockMvc = MockMvcBuilders.webAppContextSetup(wac)
                        .alwaysDo(print())
                        .build();
    }

    @Test
    void helloWord() throws Exception {

        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/helloWorld")      //http Get 으로 호출
                )
                .andDo(print())     //조금더 구체적인 정보 표시
                .andExpect(status().isOk())      //예상 ( http status : 200)
                .andExpect(MockMvcResultMatchers.content().string("Hello World"));  //문자열 예상
    }

    @Test
    void helloException() throws Exception{
        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/helloException"))
                    .andExpect(status().isInternalServerError())
                    .andExpect(jsonPath("$.code").value(500))
                    .andExpect(jsonPath("$.message").value("알 수 없는 서버 오류가 발생하였습니다."));

    }

    @ExceptionHandler(value = RuntimeException.class)
    public ResponseEntity<ErrorResponse> handleRuntimeException(RuntimeException ex){
        return new ResponseEntity<>(ErrorResponse.of(HttpStatus.INTERNAL_SERVER_ERROR, "알 수 없는 서버 오류가 발생하였습니다"), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}