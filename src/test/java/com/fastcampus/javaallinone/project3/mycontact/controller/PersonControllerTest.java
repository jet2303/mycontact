package com.fastcampus.javaallinone.project3.mycontact.controller;

import com.fastcampus.javaallinone.project3.mycontact.controller.dto.PersonDto;
import com.fastcampus.javaallinone.project3.mycontact.domain.Person;
import com.fastcampus.javaallinone.project3.mycontact.domain.dto.Birthday;
import com.fastcampus.javaallinone.project3.mycontact.repository.PersonRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.JsonPath;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.util.NestedServletException;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest

class PersonControllerTest {
    @Autowired
    private PersonController personController;
    @Autowired
    private PersonRepository personRepository;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private MappingJackson2HttpMessageConverter messageConverter;

    private MockMvc mockMvc;



    @BeforeEach     //해당 메소드는 매 테스트 전에 실행됨
    void beforeEach(){
        mockMvc = MockMvcBuilders.standaloneSetup(personController).setMessageConverters(messageConverter).build();
    }


    @Test
    void getPerson() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.get("/api/person/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("martin"))      //$ -> 객체
                .andExpect(jsonPath("$.hobby").isEmpty())
                .andExpect(jsonPath("$.address").isEmpty())
//                .andExpect(jsonPath("$.birthday.yearOfBirthday").value(1991))
//                .andExpect(jsonPath("$.birthday.monthOfBirthday").value(8))
//                .andExpect(jsonPath("$.birthday.dayOfBirthday").value(15))
                .andExpect(jsonPath("$.birthday").value("1991-08-15"))
                .andExpect(jsonPath("$.job").isEmpty())
                .andExpect(jsonPath("$.phoneNumber").isEmpty())
                .andExpect(jsonPath("$.deleted").value(false))
                .andExpect(jsonPath("$.age").isNumber())      //key값이 숫자 인지
                .andExpect(jsonPath("$.birthdayToday").isBoolean())
        ;
    }

    @Test
    void postPerson() throws Exception{
        PersonDto dto = PersonDto.of("martin","programming","판교",LocalDate.now(),"programmer","010-1111-2222");
        mockMvc.perform(
                MockMvcRequestBuilders.post("/api/person")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
               .content(toJsonString(dto)))
                .andDo(print())
                .andExpect(status().isCreated());

        Person result = personRepository.findAll(Sort.by(Sort.Direction.DESC, "id")).get(0);

        assertAll(
                ()->assertEquals(result.getName(), "martin"),
                ()->assertEquals(result.getHobby(), "programming"),
                ()->assertEquals(result.getAddress(), "판교"),
                ()->assertEquals(result.getBirthday(), Birthday.of( LocalDate.now())),
                ()->assertEquals(result.getJob(), "programmer"),
                ()->assertEquals(result.getPhoneNumber(), "010-1111-2222")
        );
    }

    @Test
    void modifyPerson() throws Exception{
        PersonDto dto = PersonDto.of("martin","programming","판교",LocalDate.now(), "programmer","010-1111-2222");
        mockMvc.perform(
                MockMvcRequestBuilders.put("/api/person/1")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(toJsonString(dto)))
                .andDo(print())
                .andExpect(status().isOk());

        Person result = personRepository.findById(1L).get();
        //한번에 검증 가능.
        assertAll(
                ()-> Assertions.assertEquals(result.getName(),"martin"),
                ()->Assertions.assertEquals(result.getHobby(),"programming"),
                ()-> Assertions.assertEquals(result.getAddress(),"판교"),
                ()->Assertions.assertEquals(result.getBirthday(), Birthday.of(LocalDate.now())),
                ()->Assertions.assertEquals(result.getJob(),"programmer"),
                ()->Assertions.assertEquals(result.getPhoneNumber(),"010-1111-2222")
        );
    }

    @Test
    void modifyPersonIfNameIsDifferent() throws Exception{
        PersonDto dto = PersonDto.of("james","programming","판교",LocalDate.now(), "programmer","010-1111-2222");
        Assertions.assertThrows(NestedServletException.class, ()->mockMvc.perform(
                MockMvcRequestBuilders.put("/api/person/1")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(toJsonString(dto)))
                .andDo(print())
                .andExpect(status().isOk())
        );
    }

    @Test
    void modifyName() throws Exception{


        mockMvc.perform(
                MockMvcRequestBuilders.patch("/api/person/1")
                .param("name","martin2"))
                .andDo(print())
                .andExpect(status().isOk());

        Assertions.assertEquals(personRepository.findById(1L).get().getName(), "martin2");
    }


    @Test
    void deletePerson() throws  Exception{
        mockMvc.perform(
                MockMvcRequestBuilders.delete("/api/person/1"))
                .andDo(print())
                .andExpect(status().isOk());
        //테스트에서 직접 repository를 호출하여 확인
        Assertions.assertTrue(personRepository.findPeopleDeleted().stream().anyMatch(person -> person.getId().equals(1L)));
    }

    //personDto 를 json 형식으로 직렬화 해줌
    private String toJsonString(PersonDto personDto) throws JsonProcessingException {
        return objectMapper.writeValueAsString(personDto);
    }
}