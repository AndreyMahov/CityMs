package com.mahov.cars.kafka.delete.citizen.rollback;

import com.mahov.dto.event.CitizenRollBackDeleteEventMessage;
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
public class CitizenRollbackDeleteEventProducer {
  KafkaTemplate<String, CitizenRollBackDeleteEventMessage> kafkaTemplate;

  public void sendMessage(CitizenRollBackDeleteEventMessage message) {
    log.info("CitizenRollbackDeleteEventProducer send message");
    kafkaTemplate.send("citizen_rollback_delete_event_from_car_service", message);
  }
}
