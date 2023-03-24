package com.mahov.log.kafka.service;

import com.mahov.dto.event.LogEventMessage;
import com.mahov.enums.LogType;
import com.mahov.enums.ModelName;
import com.mahov.log.kafka.LogEventProducer;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class LogServiceImpl implements LogService {
  LogEventProducer logEventProducer;

  @Override
  public void sendContinuousLogToOCService(Integer modelCount, ModelName modelName) {
    logEventProducer.sendMessage(buildLogEventMessage(modelCount, modelName, LogType.CONTINUOUS ));
  }

  private LogEventMessage buildLogEventMessage(Integer modelCount, ModelName modelName, LogType logType){
    return LogEventMessage
        .builder()
        .count(modelCount)
        .modelName(modelName)
        .timestamp(LocalDateTime.now())
        .logType(logType)
        .build();
  }
  @Override
  public void sendLogByTimerToOCService(Integer modelCount, ModelName modelName) {
    logEventProducer.sendMessage(buildLogEventMessage(modelCount, modelName, LogType.BY_TIMER));
  }
}
