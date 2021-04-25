package com.fastcampus.javaallinone.project3.mycontact.controller.dto;

import java.time.LocalDate;
//import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Martin
 * @since 06/09/2019
 */
@Data
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
public class PersonDto {
  //  @NotBlank(message = "이름은 필수값입니다")
    private String name;
    private String hobby;
    private String address;
    private LocalDate birthday;
    private String job;
    private String phoneNumber;
}