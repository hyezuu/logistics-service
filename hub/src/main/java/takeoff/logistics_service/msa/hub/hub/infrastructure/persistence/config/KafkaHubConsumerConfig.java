package takeoff.logistics_service.msa.hub.hub.infrastructure.persistence.config;

import java.util.HashMap;
import java.util.Map;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import takeoff.logistics_service.msa.hub.hub.application.dto.HubIdsDto;
import takeoff.logistics_service.msa.hub.hub.infrastructure.kafka.serializer.DtoDeserializer;

/**
 * @author : hanjihoon
 * @Date : 2025. 03. 30.
 */
@EnableKafka
@Configuration
@ComponentScan("takeoff.logistics_service.msa.hub.hub")
public class KafkaHubConsumerConfig {

    @Bean
    public <T> ConsumerFactory<String, T> consumerFactory(Class<T> targetType) {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, DtoDeserializer.class);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "saga-takeoff-group");
        return new DefaultKafkaConsumerFactory<>(
            props,
            new StringDeserializer(),
            new DtoDeserializer<>(targetType)
        );
    }

    // HubIdsDto를 위한 타입 안전한 컨슈머 팩토리
    @Bean
    public ConsumerFactory<String, HubIdsDto> hubIdsDtoConsumerFactory() {
        return consumerFactory(HubIdsDto.class);
    }

    // HubIdsDto 전용 리스너 컨테이너 팩토리
    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, HubIdsDto> hubIdsDtoKafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, HubIdsDto> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(hubIdsDtoConsumerFactory());
        return factory;
    }
}
