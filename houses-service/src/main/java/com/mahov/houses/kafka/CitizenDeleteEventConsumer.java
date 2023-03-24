package com.mahov.houses.kafka;


import com.mahov.dto.event.CitizenDeleteEventMessage;
import com.mahov.houses.service.HouseService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@KafkaListener(topics = "house_delete_event",containerFactory = "kafkaListenerContainerFactoryHouseDelete")
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class CitizenDeleteEventConsumer {
  HouseService houseService;

  @KafkaHandler
  public void deleteOwner(CitizenDeleteEventMessage citizenDeleteEventMessage){
  houseService.deleteOwnerById(citizenDeleteEventMessage.getCitizenId());
}
}
