package com.mahov.passports.kafka;


import com.mahov.dto.event.CitizenDeleteEventMessage;
import com.mahov.passports.service.PassportService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@KafkaListener(topics = "passport_delete_event", containerFactory = "kafkaListenerContainerFactoryPassportDelete")
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class PassportDeleteEventConsumer {
  PassportService passportService;

  @KafkaHandler
  public void deleteOwner(CitizenDeleteEventMessage citizenDeleteEventMessage) {
    passportService.deleteByOwnerId(citizenDeleteEventMessage.getCitizenId());
  }
}
