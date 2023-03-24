package com.mahov.exception;

import com.mahov.enums.ExceptionMessage;

public class CarCreateException extends RuntimeException {

  public CarCreateException(ExceptionMessage exceptionMessage) {
    super(exceptionMessage.getMessage());
  }
}
