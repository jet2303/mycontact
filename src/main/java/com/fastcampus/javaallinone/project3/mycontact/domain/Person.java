package com.fastcampus.javaallinone.project3.mycontact.domain;


import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;




//@RequiredArgsConstructor
//@EqualsAndHashCode
//@Getter
//@Setter
//@ToString
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data           //side effect 있을수도...
@RequiredArgsConstructor
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
    @NonNull
    private String bloodType;

    private String address;

    private LocalDate birthday;

    private String job;

    //영속성을 함께 관리하겠다는 의미
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    private Block block;



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
