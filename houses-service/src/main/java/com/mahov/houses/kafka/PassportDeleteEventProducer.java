package com.mahov.houses.kafka;


import com.mahov.dto.event.CitizenDeleteEventMessage;
import com.mahov.dto.event.CitizenRollBackDeleteEventMessage;
import com.mahov.houses.kafka.rollback.CarRollbackDeleteEventProducer;
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
public class PassportDeleteEventProducer {
  CarRollbackDeleteEventProducer carRollbackDeleteEventProducer;
  KafkaTemplate<String, CitizenDeleteEventMessage> kafkaTemplate;

  public void sendMessage(CitizenDeleteEventMessage message) {

    try {
      kafkaTemplate.send("passport_delete_event", message);
    } catch (Exception e) {
      carRollbackDeleteEventProducer.sendMessage(new CitizenRollBackDeleteEventMessage(message.getCitizenId()));
    }
  }
}
