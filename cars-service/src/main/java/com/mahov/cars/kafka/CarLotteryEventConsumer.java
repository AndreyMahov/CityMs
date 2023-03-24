package com.mahov.cars.kafka;

import com.mahov.cars.service.LotteryService;
import com.mahov.dto.event.LotteryCarEvent;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@KafkaListener(topics = "lottery_car_event", groupId = "lottery_event",containerFactory = "kafkaListenerContainerFactoryLotteryCarEvent")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class CarLotteryEventConsumer {
  LotteryService lotteryService;

  @KafkaHandler
  public void lotteryEvent(LotteryCarEvent lotteryCarEvent) {
    lotteryService.giveOutPrize(lotteryCarEvent);
  }
}
