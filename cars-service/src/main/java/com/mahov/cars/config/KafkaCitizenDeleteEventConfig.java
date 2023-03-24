package com.mahov.cars.config;


import com.mahov.dto.event.CitizenDeleteEventMessage;
import com.mahov.dto.event.CitizenRollBackDeleteEventMessage;
import java.util.Map;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

@Configuration
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class KafkaCitizenDeleteEventConfig {
  KafkaProperties kafkaProperties;

  public ConsumerFactory<String, CitizenDeleteEventMessage> consumerFactoryCarDelete() {
    Map<String, Object> configProps = kafkaProperties.getConsumer().buildProperties();
    return new DefaultKafkaConsumerFactory<>(configProps);
  }

  @Bean
  public ConcurrentKafkaListenerContainerFactory<String, CitizenDeleteEventMessage> kafkaListenerContainerFactoryCarDelete() {
    ConcurrentKafkaListenerContainerFactory<String, CitizenDeleteEventMessage> factory = new ConcurrentKafkaListenerContainerFactory<>();
    factory.setConsumerFactory(consumerFactoryCarDelete());
    return factory;
  }

  public ConsumerFactory<String, CitizenRollBackDeleteEventMessage> consumerFactoryRollbackCarDelete() {
    Map<String, Object> configProps = kafkaProperties.getConsumer().buildProperties();
    return new DefaultKafkaConsumerFactory<>(configProps);
  }

  @Bean
  public ConcurrentKafkaListenerContainerFactory<String, CitizenRollBackDeleteEventMessage> kafkaListenerContainerFactoryRollbackCarDelete() {
    ConcurrentKafkaListenerContainerFactory<String, CitizenRollBackDeleteEventMessage> factory = new ConcurrentKafkaListenerContainerFactory<>();
    factory.setConsumerFactory(consumerFactoryRollbackCarDelete());
    return factory;
  }

  @Bean
  public ProducerFactory<String, CitizenDeleteEventMessage> producerFactoryDeleteHouse() {
    Map<String, Object> configProps = kafkaProperties.getProducer().buildProperties();
    configProps.put(JsonSerializer.ADD_TYPE_INFO_HEADERS,
        "com.mahov.dto.event.CitizenDeleteEventMessage");
    return new DefaultKafkaProducerFactory<>(configProps);
  }

  @Bean
  public KafkaTemplate<String, CitizenDeleteEventMessage> kafkaTemplateDeleteHouse() {
    return new KafkaTemplate<>(producerFactoryDeleteHouse());
  }

  @Bean
  public ProducerFactory<String, CitizenRollBackDeleteEventMessage> producerFactoryRollbackDeleteHouse() {
    Map<String, Object> configProps = kafkaProperties.getProducer().buildProperties();
    configProps.put(JsonSerializer.ADD_TYPE_INFO_HEADERS,
        "com.mahov.dto.event.CitizenRollBackDeleteEventMessage");
    return new DefaultKafkaProducerFactory<>(configProps);
  }

  @Bean
  public KafkaTemplate<String, CitizenRollBackDeleteEventMessage> kafkaTemplateRollbackDeleteHouse() {
    return new KafkaTemplate<>(producerFactoryRollbackDeleteHouse());
  }

  @Bean
  public NewTopic deleteEvent() {
    return new NewTopic("house_delete_event", 1, (short) 1);
  }

  @Bean
  public NewTopic rollbackDeleteEvent() {
    return new NewTopic("citizen_rollback_delete_event_from_car_service", 1, (short) 1);
  }
}
