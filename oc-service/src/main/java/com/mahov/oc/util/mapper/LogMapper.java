package com.mahov.oc.util.mapper;

import com.mahov.dto.event.LogEventMessage;
import com.mahov.dto.request.LogCreateRequestDto;
import com.mahov.dto.response.LogResponseDto;
import com.mahov.oc.model.entity.ContinuousLog;
import com.mahov.oc.model.entity.TimerLog;
import java.time.LocalDateTime;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface LogMapper {

  @Mapping(target = "timestamp", qualifiedByName = "setTimestampNow")
  ContinuousLog toContinuousLog(LogEventMessage logEventMessage);

  @Mapping(target = "timestamp", qualifiedByName = "setTimestampNow")
  TimerLog toLogByTimer(LogEventMessage logEventMessage);

  ContinuousLog toContinuousLog(LogCreateRequestDto createRequestDto);

  @Named("setTimestampNow")
  default LocalDateTime setTimestamp(LocalDateTime localDateTime){
    return LocalDateTime.now();
  }

  LogResponseDto toLogResponseDto(ContinuousLog countByModelName);
}
