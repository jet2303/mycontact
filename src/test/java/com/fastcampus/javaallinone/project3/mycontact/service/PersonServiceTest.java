package com.fastcampus.javaallinone.project3.mycontact.service;

import com.fastcampus.javaallinone.project3.mycontact.controller.dto.PersonDto;
import com.fastcampus.javaallinone.project3.mycontact.domain.Person;
import com.fastcampus.javaallinone.project3.mycontact.domain.dto.Birthday;
import com.fastcampus.javaallinone.project3.mycontact.exception.PersonNotFoundException;
import com.fastcampus.javaallinone.project3.mycontact.exception.RenameNotPermittedException;
import com.fastcampus.javaallinone.project3.mycontact.repository.PersonRepository;
import net.bytebuddy.asm.Advice;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatcher;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import javax.transaction.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

//@SpringBootTest
@ExtendWith(MockitoExtension.class)     //모키토를 사용해서 테스트를 진행 할것임
class PersonServiceTest {
//    @Autowired
    @InjectMocks    //테스트 대상이 되는 클래스
    private PersonService personService;

//    @Autowired
    @Mock       //해당 클래스의 autowired되고 있는 것들. @mock을 @injectMocks에 주입
    private PersonRepository personRepository;

    @Test
    void getAll(){
        when(personRepository.findAll(any(Pageable.class)))
                .thenReturn(new PageImpl<>(Lists.newArrayList(new Person("martin"),new Person("dennis"),new Person("tony"))));

        Page<Person> result = personService.getAll(PageRequest.of(0,3));

        Assertions.assertEquals(result.getTotalElements(), 3);
        Assertions.assertEquals(result.getContent().get(0).getName(), "martin");
        Assertions.assertEquals(result.getContent().get(1).getName(), "dennis");
        Assertions.assertEquals(result.getContent().get(2).getName(), "tony");
    }

    @Test
    void getPeopleByName(){
        when(personRepository.findByName("martin"))
                .thenReturn(Lists.newArrayList(new Person("martin")));

        List<Person> result = personService.getPeopleByName("martin");

        Assertions.assertEquals(result.size(), 1);
        Assertions.assertEquals(result.get(0).getName(), "martin");

    }

    @Test
    void getPerson(){
        when(personRepository.findById(1L))
                .thenReturn(Optional.of(new Person("martin")));

        Person person = personService.getPerson(1L);

        Assertions.assertEquals(person.getName(), "martin");
    }

    @Test
    void getPersonIfNotFound(){
        when(personRepository.findById(1L))
                .thenReturn(Optional.empty());
        Person person = personService.getPerson(1L);
        Assertions.assertEquals(person, null);
    }

    @Test
    void put(){
        personService.put(mockPersonDto());

        verify(personRepository, times(1)).save(argThat(new IsPersonWillBeUpdate.IsPersonWillBeInserted()));     //검증
    }

    @Test
    void modifyIfPersonNotFound(){
        when(personRepository.findById(1L))
                .thenReturn(Optional.empty());

        Assertions.assertThrows(PersonNotFoundException.class, ()->personService.modify(1L, mockPersonDto()));
    }
    @Test
    void modifyIfNameIsDifferent(){
        when(personRepository.findById(1L))
                .thenReturn(Optional.of(new Person("tony")));

        Assertions.assertThrows(RenameNotPermittedException.class, ()->personService.modify(1L, mockPersonDto()));
    }
    @Test
    void modify(){
        when(personRepository.findById(1L))
                .thenReturn(Optional.of(new Person("martin")));
        personService.modify(1L, mockPersonDto());

        //verify(personRepository, times(1)).save(any(Person.class));
        verify(personRepository, times(1)).save(argThat(new IsPersonWillBeUpdate()));
    }

    @Test
    void modifyByNameIfPersonNotFound(){
        when(personRepository.findById(1L))
            .thenReturn(Optional.empty());

        Assertions.assertThrows(PersonNotFoundException.class, ()->personService.modify(1L, "martin"));
    }

    @Test
    void modifyByName(){
        when(personRepository.findById(1L))
                .thenReturn(Optional.of(new Person("martin")));

        personService.modify(1L, "denial");

        verify(personRepository, times(1)).save(argThat(new IsPersonWillBeUpdate.IsNameWillBeUpdated()));

    }

    @Test
    void deleteIfPersonNotFound(){
        when(personRepository.findById(1L))
            .thenReturn(Optional.empty());

        Assertions.assertThrows(PersonNotFoundException.class, ()->personService.delete((1L)));

    }
    @Test
    void delete(){
        when(personRepository.findById(1L))
                    .thenReturn(Optional.of(new Person("martin")));
        personService.delete(1L);
        verify(personRepository, times(1)).save(argThat(new IsPersonWillBeUpdate.IsPersonWillBeDeleted()));     //save하는 이유는 soft delete이기 때문에 flag값 변경임
    }

    private PersonDto mockPersonDto(){
        return PersonDto.of("martin", "programming","판교", LocalDate.now(),"programmer", "010-1111-2222");
    }

    private static class IsPersonWillBeUpdate implements ArgumentMatcher<Person> {

        @Override
        public boolean matches(Person person) {
            return equals(person.getName(), "martin")
                    && equals(person.getHobby(),"programming")
                    && equals(person.getAddress(),"판교")
                    && equals(person.getBirthday(),Birthday.of(LocalDate.now()))
                    && equals(person.getJob(),"programmer")
                    && equals(person.getPhoneNumber(),"010-1111-2222");
        }
        private boolean equals(Object actual, Object expected){
            return expected.equals(actual);
        }

        private static class IsPersonWillBeInserted implements ArgumentMatcher<Person>{

            @Override
            public boolean matches(Person person) {
                return equals(person.getName(), "martin")
                        && equals(person.getHobby(),"programming")
                        && equals(person.getAddress(),"판교")
                        && equals(person.getBirthday(),Birthday.of(LocalDate.now()))
                        && equals(person.getJob(),"programmer")
                        && equals(person.getPhoneNumber(),"010-1111-2222");
            }
            private boolean equals(Object actual, Object expected){
                return expected.equals(actual);
            }
        }
        private static class IsNameWillBeUpdated implements ArgumentMatcher<Person>{

            @Override
            public boolean matches(Person person) {

                return person.getName().equals("daniel");
            }
        }

        private static class IsPersonWillBeDeleted implements ArgumentMatcher<Person>{

            @Override
            public boolean matches(Person person) {
                return person.isDeleted();
            }
        }
    }
}