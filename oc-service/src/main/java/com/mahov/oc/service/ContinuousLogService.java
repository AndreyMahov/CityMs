package com.mahov.oc.service;

import com.mahov.oc.model.entity.ContinuousLog;
import com.mahov.oc.model.entity.TimerLog;
import java.time.LocalDateTime;
import java.util.List;

public interface ContinuousLogService {

  ContinuousLog getCountByModelName(String modelName);

  void saveContinuousLog(ContinuousLog continuousLogEventMessage);

  List<ContinuousLog> getLogsInDateSpan(LocalDateTime startDate, LocalDateTime endDate);

  ContinuousLog create(ContinuousLog ContinuousLog);
}
