package com.fastcampus.javaallinone.project3.mycontact.controller;

import com.fastcampus.javaallinone.project3.mycontact.controller.dto.PersonDto;
import com.fastcampus.javaallinone.project3.mycontact.domain.Person;
import com.fastcampus.javaallinone.project3.mycontact.domain.dto.Birthday;
import com.fastcampus.javaallinone.project3.mycontact.exception.PersonNotFoundException;
import com.fastcampus.javaallinone.project3.mycontact.exception.RenameNotPermittedException;
import com.fastcampus.javaallinone.project3.mycontact.exception.dto.ErrorResponse;
import com.fastcampus.javaallinone.project3.mycontact.repository.PersonRepository;
import com.fastcampus.javaallinone.project3.mycontact.service.PersonService;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;


@RequestMapping(value = "/api/person")      //
@RestController
@Slf4j              //log 따기위해
public class PersonController {
    @Autowired
    private PersonService personService;

    @Autowired
    private PersonRepository personRepository;

    @GetMapping(value = "/api/person/birthday-friends")
    public List<Person> birthday_friends(){
        return personService.getBirthdayList();
    }


    @GetMapping
    public Page<Person> getAll(@PageableDefault Pageable pageable){
        return personService.getAll(null);
    }

    @RequestMapping(value = "/{id}")
    @GetMapping
    public Person getPerson(@PathVariable Long id){

        return personService.getPerson(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)                 //조금더 명확하게 create 되었다는것 200 OK 의 세부항목쯤이라고 생각.
    public void postPerson(@RequestBody PersonDto persondto){     //POST 방식으로 전송된 HTTP 요청 데이터를 Person 타입의 person파라미터로 수신됨.
        personService.put(persondto);

    }

    @PutMapping(value = "/{id}")
    public void modifyPerson(@PathVariable Long id, @RequestBody PersonDto person){

            personService.modify(id, person);
    }

    @PatchMapping("/{id}")      //일부 resource 업데이트시 사용
    public void modifyPerson(@PathVariable Long id, String name){

            personService.modify(id,name);
    }

    //
    @DeleteMapping("/{id}")
    public void deletePerson(@PathVariable Long id){
        personService.delete(id);


    }


}
