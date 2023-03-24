package com.mahov.houses.kafka.rollback;


import com.mahov.dto.event.CitizenRollBackDeleteEventMessage;
import com.mahov.houses.service.HouseService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@KafkaListener(topics = "citizen_rollback_from_passports_service", containerFactory = "kafkaListenerContainerFactoryCitizenRollbackDelete")
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class CitizenRollbackDeleteConsumer {
  HouseService houseService;

  @KafkaHandler
  public void rollbackDeleteOwner(
      CitizenRollBackDeleteEventMessage citizenRollBackDeleteEventMessage) {
    houseService.rollbackDeletedUserById(citizenRollBackDeleteEventMessage.getCitizenId());
  }
}
