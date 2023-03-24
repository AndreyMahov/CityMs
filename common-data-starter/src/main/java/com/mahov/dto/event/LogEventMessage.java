package com.mahov.dto.event;

import com.mahov.enums.LogType;
import com.mahov.enums.ModelName;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LogEventMessage {
  ModelName modelName;
  LocalDateTime timestamp;
  Integer count;
  LogType logType;
}
