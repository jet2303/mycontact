package com.fastcampus.javaallinone.project3.mycontact.domain;


import com.fastcampus.javaallinone.project3.mycontact.domain.dto.Birthday;
import lombok.*;

import javax.persistence.*;


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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //@ToString.Exclude           //ToString에 표시 시키지 않음.
    @NonNull
    @Column(nullable = false)
    private String name;

    @NonNull
    //@Min(1)
    private int age;


    private String hobby;
    @NonNull
    //@NotEmpty
    private String bloodType;

    private String address;

    //private LocalDate birthday;
    //@Valid
    @Embedded
    private Birthday birthday;

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
