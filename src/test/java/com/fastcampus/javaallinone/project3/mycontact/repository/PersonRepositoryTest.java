package com.fastcampus.javaallinone.project3.mycontact.repository;

import com.fastcampus.javaallinone.project3.mycontact.domain.Person;
import com.fastcampus.javaallinone.project3.mycontact.domain.dto.Birthday;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Transactional
@SpringBootTest
class PersonRepositoryTest {
    @Autowired
    private PersonRepository personRepository;

    @Test
    void crud(){
        Person person = new Person();

        person.setName("john");
        personRepository.save(person);


        List<Person> result = personRepository.findByName("john");

//        Assertions.assertEquals(result.size(), 1);
//        Assertions.assertEquals(result.get(0).getName(), "john");
//        Assertions.assertEquals(result.get(0).getAge(), 10);


    }


//    private void givenPerson(String name, int age, String bloodType, LocalDate birthday){
//        Person person = new Person(name, age, bloodType);
//        person.setBirthday(new Birthday(birthday));
//
//        personRepository.save(person);
//        //personRepository.save(new Person(name, age, bloodType));
//
//    }

    @Test
    void findByBirthdayBetween(){


        List<Person> result = personRepository.findByMonthOfBirthday(13);
//        Assertions.assertEquals(result.size(), 2);
//        Assertions.assertEquals(result.get(0).getName(), "martin");
//        Assertions.assertEquals(result.get(1).getName(), "sophia");

    }
}