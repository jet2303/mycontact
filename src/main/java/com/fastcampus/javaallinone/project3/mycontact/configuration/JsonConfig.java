package com.fastcampus.javaallinone.project3.mycontact.configuration;

import com.fastcampus.javaallinone.project3.mycontact.configuration.serializer.BirthdaySerializer;
import com.fastcampus.javaallinone.project3.mycontact.domain.dto.Birthday;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

@Configuration      //spring의 configuration 표시
public class JsonConfig {

    //직접 만든 serializer를(BirthdaySerializer) BirthdayModule에 주입, BirthdayModule은 objectMapper에 넣어짐, objectMapper는 MessageConverter에 넣어줌

    @Bean
    //주입하는 용도
    public MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter(ObjectMapper objectMapper){
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        converter.setObjectMapper(objectMapper);

        return converter;
    }

    @Bean
    public ObjectMapper objectMapper(){
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new BirthdayModule());
        objectMapper.registerModule(new JavaTimeModule());      //Localdate 형식은 복잡해서 ,간단한 형식으로 제공하는 module / [1991-12-25]

        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);      //"1991-12-25"
        return objectMapper;
    }


    static class BirthdayModule extends SimpleModule{
        BirthdayModule(){
            super();
            addSerializer(Birthday.class, new BirthdaySerializer());
        }
    }

}
