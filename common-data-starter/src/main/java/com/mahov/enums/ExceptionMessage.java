package com.mahov.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;


@Getter
@RequiredArgsConstructor
public enum ExceptionMessage {
  NOT_FOUND("Информация по запросу не найден."),
  SERVER_ERROR("Ошибка сервера"),
  SELLER_BUSY("Нет свободных продавцов");

  private final String message;
}
