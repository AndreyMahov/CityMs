package com.mahov.citizens.kafka.event.lottery;

import com.mahov.dto.event.LotteryCarEvent;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class LotteryEventProducer {
  KafkaTemplate<String, LotteryCarEvent> kafkaTemplate;

  public void sendMessage(LotteryCarEvent message) {
    kafkaTemplate.send("lottery_car_event", message);
  }
}
