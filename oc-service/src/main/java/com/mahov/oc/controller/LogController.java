package com.mahov.oc.controller;

import com.mahov.dto.request.LogCreateRequestDto;
import com.mahov.dto.response.LogResponseDto;
import com.mahov.oc.model.entity.ContinuousLog;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

public interface LogController {

  ContinuousLog getCountByModelName(@RequestParam(name = "modelName") String modelName);

  LogResponseDto getCitizensCountFrom();

  List<ContinuousLog> getLogsInDateSpan(
      @RequestParam(name = "startDate") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm") LocalDateTime startDate,
      @RequestParam(name = "endDate") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm") LocalDateTime endDate);

  LogResponseDto create(@RequestBody LogCreateRequestDto requestDto);
}
