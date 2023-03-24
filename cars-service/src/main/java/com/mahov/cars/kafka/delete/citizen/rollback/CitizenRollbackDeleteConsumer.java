package com.mahov.cars.kafka.delete.citizen.rollback;

import com.mahov.cars.service.CarService;
import com.mahov.dto.event.CitizenRollBackDeleteEventMessage;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;

@RequiredArgsConstructor
@KafkaListener(topics = "citizen_rollback_from_house_service", containerFactory = "kafkaListenerContainerFactoryRollbackCarDelete")
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class CitizenRollbackDeleteConsumer {
  CarService carService;

  @KafkaHandler
  public void rollbackDeleteOwner(
      CitizenRollBackDeleteEventMessage citizenRollBackDeleteEventMessage) {
    carService.rollbackCitizenDeletedEvenByOwnerId(
        citizenRollBackDeleteEventMessage.getCitizenId());
  }
}
