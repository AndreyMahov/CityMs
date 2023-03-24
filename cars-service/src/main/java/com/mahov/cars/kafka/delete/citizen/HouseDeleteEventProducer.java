package com.mahov.cars.kafka.delete.citizen;


import com.mahov.cars.kafka.delete.citizen.rollback.CitizenRollbackDeleteEventProducer;
import com.mahov.dto.event.CitizenDeleteEventMessage;
import com.mahov.dto.event.CitizenRollBackDeleteEventMessage;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class HouseDeleteEventProducer {
  CitizenRollbackDeleteEventProducer citizenRollbackDeleteEventProducer;
  KafkaTemplate<String, CitizenDeleteEventMessage> kafkaTemplate;

  public void sendMessage(CitizenDeleteEventMessage message) {
    log.info("HouseDeleteEventProducer send message");
    try {
      kafkaTemplate.send("house_delete_event", message);
    } catch (Exception e) {
      citizenRollbackDeleteEventProducer.sendMessage(
          new CitizenRollBackDeleteEventMessage(message.getCitizenId()));
    }
  }
}

