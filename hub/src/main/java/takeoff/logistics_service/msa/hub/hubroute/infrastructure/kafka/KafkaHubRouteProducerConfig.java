package takeoff.logistics_service.msa.hub.hubroute.infrastructure.kafka;

import java.util.HashMap;
import java.util.Map;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import takeoff.logistics_service.msa.hub.hub.infrastructure.kafka.serializer.DtoSerializer;

/**
 * @author : hanjihoon
 * @Date : 2025. 03. 30.
 */
@Configuration
@ComponentScan("takeoff.logistics_service.msa.hub.hubroute")
public class KafkaHubRouteProducerConfig {

    @Bean
    public <T> ProducerFactory<String, T> producerFactory() {
        Map<String, Object> props = new HashMap<>();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, DtoSerializer.class);
        return new DefaultKafkaProducerFactory<>(props);
    }

    @Bean
    public <T> KafkaTemplate<String, T> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }
}
