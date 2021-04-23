package com.fastcampus.javaallinone.project3.mycontact.domain;


import com.fastcampus.javaallinone.project3.mycontact.controller.dto.PersonDto;
import com.fastcampus.javaallinone.project3.mycontact.domain.dto.Birthday;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Where;
import org.springframework.util.StringUtils;

import javax.persistence.*;
import java.time.LocalDate;


//@RequiredArgsConstructor
//@EqualsAndHashCode
//@Getter
//@Setter
//@ToString
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data           //side effect 있을수도...
//@RequiredArgsConstructor
@Where(clause = "deleted = false")          //Query의 무조건으로 기본 조건으로 들어감
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //@ToString.Exclude           //ToString에 표시 시키지 않음.

    @Column(nullable = false)
    private String name;

    private String hobby;

    private String address;

    private String phoneNumber;

    //private LocalDate birthday;
    //@Valid
    @Embedded
    private Birthday birthday;

    private String job;

    @ColumnDefault("0")
    private boolean deleted;

    //영속성을 함께 관리하겠다는 의미
//    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
//    private Block block;

    public void set(PersonDto personDto){


        if(!StringUtils.isEmpty(personDto.getHobby())){
            this.setHobby(personDto.getHobby());
        }

        if(!StringUtils.isEmpty(personDto.getAddress())){
            this.setAddress(personDto.getAddress());
        }
        if(!StringUtils.isEmpty(personDto.getJob())){
            this.setJob(personDto.getJob());
        }
        if(!StringUtils.isEmpty(personDto.getPhoneNumber())){
            this.setPhoneNumber(personDto.getPhoneNumber());
        }

        if(personDto.getBirthday() != null){
            this.setBirthday(Birthday.of(personDto.getBirthday()));
        }
    }

    public Integer getAge(){
        if(this.birthday != null){
            return LocalDate.now().getYear() - this.birthday.getYearOfBirthday()+1;
        }
        else{
            return null;
        }

    }

    public boolean isBirthdayToday(){
        return LocalDate.now().equals(LocalDate.of(this.birthday.getYearOfBirthday(), this.birthday.getMonthOfBirthday(), this.birthday.getDayOfBirthday()));
    }
}
