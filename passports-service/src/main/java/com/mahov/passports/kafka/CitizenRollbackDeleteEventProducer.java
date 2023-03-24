package com.mahov.passports.kafka;


import com.mahov.dto.event.CitizenRollBackDeleteEventMessage;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;


@Component
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class CitizenRollbackDeleteEventProducer {
  KafkaTemplate<String, CitizenRollBackDeleteEventMessage> kafkaTemplate;

  public void sendMessage(CitizenRollBackDeleteEventMessage message) {
    kafkaTemplate.send("citizen_rollback_from_passports_service", message);
  }
}
