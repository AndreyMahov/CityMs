package com.mahov.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CarResponseDto {

  private String federalNumber;

  public String getFederalNumber() {
    return federalNumber;
  }
}
