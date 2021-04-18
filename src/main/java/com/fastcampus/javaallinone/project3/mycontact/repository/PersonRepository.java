package com.fastcampus.javaallinone.project3.mycontact.repository;

import com.fastcampus.javaallinone.project3.mycontact.domain.Person;
import jdk.vm.ci.meta.Local;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface PersonRepository extends JpaRepository<Person, Long> {
    List<Person> findByName(String name);       //Query Method

    List<Person> findByBlockIsNull();   //차단이 되지않은 person을 가져옴

    List<Person> findByBloodType(String bloodType);

    //List<Person> findByBirthdayBetween(LocalDate startDate, LocalDate endDate);
    //@Query(value = "select person from Person person where person.birthday.monthOfBirthday= ?1 and person.birthday.dayOfBirthday=?2")     //JPQL  / ?1 첫번째 인
    //@Query(value = "select * from person where month_of_birthday= :monthOfBirthday and day_of_birthday= :dayOfBirthday", nativeQuery = true)     //위와 동일
    @Query(value = "select person from Person person where person.birthday.monthOfBirthday= ?1 ")     //JPQL  / ?1 첫번째 인자
    List<Person> findByMonthOfBirthday(@Param("monthOfBirthday") int monthOfBirthday);
}
