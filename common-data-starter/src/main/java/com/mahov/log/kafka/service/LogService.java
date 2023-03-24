package com.mahov.log.kafka.service;

import com.mahov.enums.ModelName;

public interface LogService {

  void sendContinuousLogToOCService(Integer ModelCount, ModelName modelName);
  void sendLogByTimerToOCService(Integer ModelCount, ModelName modelName);
}
