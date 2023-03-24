package com.mahov.log.kafka;

import com.mahov.dto.event.LogEventMessage;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class LogEventProducer {
  KafkaTemplate<String, LogEventMessage> kafkaTemplate;

  public void sendMessage(LogEventMessage message) {
      kafkaTemplate.send("log_events", message);
  }
}
