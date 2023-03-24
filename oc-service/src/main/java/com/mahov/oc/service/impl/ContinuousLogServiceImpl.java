package com.mahov.oc.service.impl;

import com.mahov.oc.model.entity.ContinuousLog;
import com.mahov.oc.repository.ContinuousLogRepository;
import com.mahov.oc.service.ContinuousLogService;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ContinuousLogServiceImpl implements ContinuousLogService {
  ContinuousLogRepository continuousLogRepository;

  @Override
  public ContinuousLog getCountByModelName(String modelName) {
    return continuousLogRepository.findLastLogByModelName(modelName).orElseGet(()-> ContinuousLog.builder().count(0).build());
  }

  @Override
  public void saveContinuousLog(ContinuousLog continuousLog) {
    continuousLogRepository.save(continuousLog);
  }

  @Override
  public List<ContinuousLog> getLogsInDateSpan(LocalDateTime startDate, LocalDateTime endDate) {
    return continuousLogRepository.findLogsInTimeSpan(startDate,endDate);
  }

  @Override
  public ContinuousLog create(ContinuousLog continuousLog) {
    continuousLog.setTimestamp(LocalDateTime.now());
    return continuousLogRepository.save(continuousLog);
  }
}
