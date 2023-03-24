package com.mahov.citizens.config.kafka;

import com.mahov.dto.event.LotteryCarEvent;
import java.util.Map;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

@Configuration
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class KafkaLotteryCarEventConfig {
  KafkaProperties kafkaProperties;

  public ProducerFactory<String, LotteryCarEvent> producerFactory() {
    Map<String, Object> configProps = kafkaProperties.getProducer().buildProperties();
    configProps.put(JsonSerializer.ADD_TYPE_INFO_HEADERS,
        "com.mahov.dto.event.LotteryCarEvent");
    return new DefaultKafkaProducerFactory<>(configProps);
  }

  @Bean
  public KafkaTemplate<String, LotteryCarEvent> kafkaTemplateLottery() {
    return new KafkaTemplate<>(producerFactory());
  }

  @Bean
  public NewTopic LotteryEvent() {
    return new NewTopic("lottery_car_event", 1, (short) 1);
  }

}
