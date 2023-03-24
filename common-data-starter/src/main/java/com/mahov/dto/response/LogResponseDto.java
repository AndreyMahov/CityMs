package com.mahov.dto.response;

import com.mahov.enums.ModelName;
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
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = false)
@NoArgsConstructor
@AllArgsConstructor
public class LogResponseDto {
  ModelName modelName;
  Integer count;
}
