package com.mahov.oc.kafka;

import com.mahov.dto.event.LogEventMessage;
import com.mahov.enums.LogType;
import com.mahov.oc.service.TimerLogService;
import com.mahov.oc.util.mapper.LogMapper;
import com.mahov.oc.service.ContinuousLogService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
@KafkaListener(topics = "log_events", containerFactory = "kafkaListenerContainerLogEvent")
public class LogEventConsumer {
  ContinuousLogService continuousLogService;
  TimerLogService timerLogService;
  LogMapper logMapper;

  @KafkaHandler
  public void logEvent(LogEventMessage logEventMessage) {
    boolean itIsContinuousLog = logEventMessage.getLogType().equals(LogType.CONTINUOUS);
    if (itIsContinuousLog) {
      continuousLogService.saveContinuousLog(logMapper.toContinuousLog(logEventMessage));
    } else {
      timerLogService.saveTimerLog(logMapper.toLogByTimer(logEventMessage));
    }
  }
}
