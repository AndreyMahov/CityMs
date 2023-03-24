package com.mahov.citizens.kafka.event.delete;

import com.mahov.citizens.service.CitizenService;
import com.mahov.dto.event.CitizenDeleteEventMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class DeleteEventProducer {
  private final KafkaTemplate<String, CitizenDeleteEventMessage> kafkaTemplate;
  private CitizenService citizenService;

  public void sendMessage(CitizenDeleteEventMessage message) {
    try {
      kafkaTemplate.send("car_delete_event", message);
    } catch (Exception e) {
      citizenService.rollbackDeletedById(message.getCitizenId());
    }
  }

  public void setCitizenService(CitizenService citizenService) {
    this.citizenService = citizenService;
  }
}
