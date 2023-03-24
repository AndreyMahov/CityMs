package com.mahov.dto.request;


import com.mahov.dto.PassportDto;
import com.mahov.enums.Gender;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CitizenCreateDto {
   String name;
   String surname;
   Gender gender;
   PassportDto passportDto;
}
