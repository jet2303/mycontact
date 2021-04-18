package com.fastcampus.javaallinone.project3.mycontact.repository;

import com.fastcampus.javaallinone.project3.mycontact.domain.Person;
import com.fastcampus.javaallinone.project3.mycontact.domain.dto.Birthday;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootTest
class PersonRepositoryTest {
    @Autowired
    private PersonRepository personRepository;

    @Test
    void crud(){
        Person person = new Person();

        person.setAge(10);
        person.setName("martin");
        personRepository.save(person);

        System.out.println(personRepository.findAll());     //전체 데이터를 가져옴
        List<Person> people = personRepository.findAll();

        Assertions.assertEquals(people.size(), 1);
        Assertions.assertEquals(people.get(0).getName(), "martin");
        Assertions.assertEquals(people.get(0).getAge(), 10);
    }



    @Test
    void hashCodeAndEquals(){
        Person person1 = new Person();
        Person person2 = new Person();

        System.out.println(person1.equals(person2));
        System.out.println(person1.hashCode());
        System.out.println(person2.hashCode());

        Map<Person, Integer> map = new HashMap<>();
        map.put(person1, person1.getAge());

        System.out.println(map);
        System.out.println(map.get(person2));


    }

    @Test
    void finByBloodType(){
        givenPerson("martin",10,"A");
        givenPerson("david",9,"B");
        givenPerson("denis",8,"O");
        givenPerson("sophia",7,"AB");
        givenPerson("benny",6,"A");
        List<Person> result = personRepository.findByBloodType("A");

        result.forEach(System.out::println);
    }

    private void givenPerson(String name, int age, String bloodType, LocalDate birthday){
        Person person = new Person(name, age, bloodType);
        person.setBirthday(new Birthday(birthday));

        personRepository.save(person);
        //personRepository.save(new Person(name, age, bloodType));

    }

    private void givenPerson(String name, int age, String bloodType){
        givenPerson(name, age, bloodType, null);
    }

    @Test
    void findByBirthdayBetween(){
        givenPerson("martin",10,"A",LocalDate.of(1991,8,30));
        givenPerson("david",9,"B",LocalDate.of(1992,7,10));
        givenPerson("denis",8,"O",LocalDate.of(1993,1,5));
        givenPerson("sophia",7,"AB",LocalDate.of(1994,6,30));
        givenPerson("benny",6,"A",LocalDate.of(1995,8,30));

        List<Person> result = personRepository.findByMonthOfBirthday(13);

        result.forEach(System.out::println);

    }
}