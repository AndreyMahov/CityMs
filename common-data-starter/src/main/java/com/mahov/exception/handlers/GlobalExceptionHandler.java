package com.mahov.exception.handlers;


import com.mahov.enums.ExceptionMessage;
import com.mahov.enums.ExceptionType;
import com.mahov.exception.CarCreateException;
import com.mahov.exception.NotFoundException;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(NotFoundException.class)
  public ResponseEntity<ErrorResponse> handlerCitizenNotFoundException() {
    return buildResponse(ExceptionMessage.NOT_FOUND, ExceptionType.NO_DATA,
        HttpStatus.NOT_FOUND.value());
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ErrorResponse> handlerException() {
    return buildResponse(ExceptionMessage.SERVER_ERROR, ExceptionType.ERROR,
        HttpStatus.INTERNAL_SERVER_ERROR.value());
  }

  @ExceptionHandler(CarCreateException.class)
  public ResponseEntity<ErrorResponse> handlerCarCreateException() {
    return buildResponse(ExceptionMessage.SELLER_BUSY, ExceptionType.FAILED_DEPENDENCY,
        HttpStatus.FAILED_DEPENDENCY.value());
  }

  private ResponseEntity<ErrorResponse> buildResponse(
      ExceptionMessage exceptionMessage, ExceptionType exceptionType, int code) {
    ErrorResponse errorResponse = new ErrorResponse(exceptionMessage.getMessage(),
        exceptionType.getType(), code);
    return ResponseEntity.badRequest().body(errorResponse);
  }

  @Getter
  @AllArgsConstructor
  @FieldDefaults(level = AccessLevel.PRIVATE)
  public static class ErrorResponse {
     String message;
     String type;
     int code;
  }
}
