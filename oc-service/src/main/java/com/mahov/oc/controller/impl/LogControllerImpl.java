package com.mahov.oc.controller.impl;

import com.mahov.dto.request.LogCreateRequestDto;
import com.mahov.dto.response.LogResponseDto;
import com.mahov.oc.controller.LogController;
import com.mahov.oc.model.entity.ContinuousLog;
import com.mahov.oc.util.enums.ModelName;
import com.mahov.oc.util.mapper.LogMapper;
import com.mahov.oc.service.ContinuousLogService;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/oc-service/logs")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class LogControllerImpl implements LogController {
  ContinuousLogService continuousLogService;
  LogMapper logMapper;

  @Override
  @GetMapping("/models/counts")
  public ContinuousLog getCountByModelName(@RequestParam(name = "modelName") String modelName){
    return continuousLogService.getCountByModelName(modelName);
  }

  @Override
  @GetMapping("/continuous/citizens")
  public LogResponseDto getCitizensCountFrom() {
    return logMapper.toLogResponseDto(continuousLogService.getCountByModelName(ModelName.CITIZENS.name()));
  }
  @Override
  @GetMapping()
  public List<ContinuousLog> getLogsInDateSpan(
      @RequestParam(name = "startDate") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm") LocalDateTime startDate,
      @RequestParam(name = "endDate") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm") LocalDateTime endDate){
    return continuousLogService.getLogsInDateSpan(startDate, endDate);
  }

  @Override
  @PostMapping()
  public LogResponseDto create(@RequestBody LogCreateRequestDto requestDto){
    return logMapper.toLogResponseDto(continuousLogService.create(logMapper.toContinuousLog(requestDto)));
  }
}
