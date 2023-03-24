package com.mahov.cars.kafka.delete.citizen;

import com.mahov.cars.service.CarService;
import com.mahov.dto.event.CitizenDeleteEventMessage;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@KafkaListener(topics = "car_delete_event", groupId = "lottery_event", containerFactory = "kafkaListenerContainerFactoryCarDelete")
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class CitizenDeleteEventConsumer {
  CarService carService;

  @KafkaHandler
  public void deleteCar(CitizenDeleteEventMessage citizenDeleteEventMessage) {
    carService.citizenDeleteEvent(citizenDeleteEventMessage.getCitizenId());
  }
}
