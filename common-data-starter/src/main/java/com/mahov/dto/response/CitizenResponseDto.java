package com.mahov.dto.response;

import com.mahov.enums.Gender;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CitizenResponseDto {

  private Long id;
  private String name;
  private String surname;
  private Gender gender;
}
