package com.fastcampus.javaallinone.project3.mycontact.domain;


import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDate;
import java.time.LocalDateTime;


@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data           //side effect 있을수도...

//@RequiredArgsConstructor
//@EqualsAndHashCode
//@Getter
//@Setter
//@ToString
public class Person {

    @Id
    @GeneratedValue
    private Long id;

    //@ToString.Exclude           //ToString에 표시 시키지 않음.
    @NonNull
    private String name;

    @NonNull
    private int age;

    private String hobby;

    private String bloodType;

    private String address;

    private LocalDate birthday;

    private String job;

    @Override
    public String toString() {
        return id+" "+name +" "+ age +" "+ hobby +" "+ bloodType;
    }


//    public boolean equals(Object object){
//        if(object == null){
//            return false;
//        }
//
//        Person person = (Person) object;
//
//        if(!person.getName().equals(this.getName())){
//            return  false;
//        }
//        if(person.getAge() != this.getAge()){
//            return false;
//        }
//        return true;
//    }
//
//    public int hashCode(){
//        return (name+age).hashCode();
//    }

}
