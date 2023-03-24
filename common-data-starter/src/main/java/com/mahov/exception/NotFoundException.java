package com.mahov.exception;

import com.mahov.enums.ExceptionMessage;

public class NotFoundException extends RuntimeException {

  public NotFoundException(ExceptionMessage exceptionMessage) {
    super(exceptionMessage.getMessage());
  }
}
