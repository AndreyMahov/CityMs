package com.mahov.citizens.kafka.event.delete;

import com.mahov.citizens.service.CitizenService;
import com.mahov.dto.event.CitizenRollBackDeleteEventMessage;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@KafkaListener(topics = "citizen_rollback_from_car_service", containerFactory = "kafkaListenerContainerFactoryRollbackDelete")
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class RollbackDeleteEventConsumer {
  CitizenService citizenService;

  @KafkaHandler
  public void deleteCar(CitizenRollBackDeleteEventMessage citizenRollBackDeleteEventMessage) {
    citizenService.rollbackDeletedById(citizenRollBackDeleteEventMessage.getCitizenId());
  }
}
