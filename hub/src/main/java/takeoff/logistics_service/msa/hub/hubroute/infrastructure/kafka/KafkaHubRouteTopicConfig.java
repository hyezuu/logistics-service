package takeoff.logistics_service.msa.hub.hubroute.infrastructure.kafka;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author : hanjihoon
 * @Date : 2025. 03. 30.
 */
@Configuration
@ComponentScan("takeoff.logistics_service.msa.hub.hubroute")
public class KafkaHubRouteTopicConfig {

    private static final String HUB_ROUTE_TOPIC_NAME = "hubRoute-events";
    private static final String HUB_TOPIC_NAME = "hubRoute-events";
    @Bean
    public NewTopic hubTopic() {
        return new NewTopic("hub-events", 3, (short) 1);
    }
    @Bean
    public String hubTopicName() {
        return HUB_TOPIC_NAME;
    }
    @Bean
    public NewTopic hubRouteTopic() {
        return new NewTopic("hubRoute-events", 3, (short) 1);
    }

    @Bean
    public String hubRouteTopicName() {
        return HUB_ROUTE_TOPIC_NAME;
    }

}
