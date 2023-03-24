package com.mahov.dto.response;


import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HouseResponseDto {

  private Long id;
  private String street;
  private Integer number;
  private List<Long> ownerIds;
}
