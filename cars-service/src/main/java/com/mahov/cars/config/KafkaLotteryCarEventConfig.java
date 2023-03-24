package com.mahov.cars.config;

import com.mahov.dto.event.LotteryCarEvent;
import java.util.Map;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

@Configuration
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class KafkaLotteryCarEventConfig {
  KafkaProperties kafkaProperties;

  public ConsumerFactory<String, LotteryCarEvent> consumerFactoryLotteryCarEvent() {
    Map<String, Object> configProps = kafkaProperties.getConsumer().buildProperties();
    configProps.put(JsonDeserializer.TRUSTED_PACKAGES, "com.mahov.dto.event.LotteryCarEvent");
    configProps.put(JsonDeserializer.VALUE_DEFAULT_TYPE, "com.mahov.dto.event.LotteryCarEvent");
    return new DefaultKafkaConsumerFactory<>(configProps);
  }

  @Bean
  public ConcurrentKafkaListenerContainerFactory<String, LotteryCarEvent> kafkaListenerContainerFactoryLotteryCarEvent() {
    ConcurrentKafkaListenerContainerFactory<String, LotteryCarEvent> factory = new ConcurrentKafkaListenerContainerFactory<>();
    factory.setConsumerFactory(consumerFactoryLotteryCarEvent());
    return factory;
  }
}
