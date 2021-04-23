package com.fastcampus.javaallinone.project3.mycontact.repository;

import com.fastcampus.javaallinone.project3.mycontact.domain.Person;
import com.fastcampus.javaallinone.project3.mycontact.domain.dto.Birthday;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

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
    void findByName(){
        List<Person> people = personRepository.findByName("tony");
        Assertions.assertEquals(people.size(),1);

        Person person = people.get(0);

        Assertions.assertAll(
                ()-> Assertions.assertEquals(person.getName(), "tony"),
                ()-> Assertions.assertEquals(person.getHobby(), "reading"),
                ()->Assertions.assertEquals(person.getAddress(), "서울"),
                ()->Assertions.assertEquals(person.getBirthday(), Birthday.of(LocalDate.of(1991,7,10))),
                ()->Assertions.assertEquals(person.getJob(), "officer"),
                ()->Assertions.assertEquals(person.getPhoneNumber(), "010-2222-5555"),
                ()->Assertions.assertEquals(person.isDeleted(), false)
        );
    }

    @Test
    void findByNameIfDeleted(){
        List<Person> people = personRepository.findByName("andrew");
        Assertions.assertEquals(people.size(), 0);
    }

    @Test
    void findByMonthOfBirthday(){
        List<Person> people = personRepository.findByMonthOfBirthday(7);

        Assertions.assertEquals(people.size(), 2);
        Assertions.assertAll(
                ()-> Assertions.assertEquals(people.get(0).getName(), "david"),
                ()-> Assertions.assertEquals(people.get(1).getName(), "tony")
        );
    }

    @Test
    void findPeopleDeleted(){
        List<Person> people = personRepository.findPeopleDeleted();

        Assertions.assertEquals(people.size(), 1);          //List를 반환할경우 size 체크는 필수.
        Assertions.assertEquals(people.get(0).getName(), "andrew");
    }
}