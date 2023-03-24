package com.mahov.houses.domain.dto.request;


import com.mahov.houses.domain.model.Owner;
import java.util.Set;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class HouseCreateDto {
  String street;
  Integer number;
  Set<Owner> owners;
}
