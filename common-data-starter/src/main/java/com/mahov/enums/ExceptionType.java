package com.mahov.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ExceptionType {
  NO_DATA("No data"),
  ERROR("error"),
  FAILED_DEPENDENCY("Failed Dependency");

  private final String type;
}
