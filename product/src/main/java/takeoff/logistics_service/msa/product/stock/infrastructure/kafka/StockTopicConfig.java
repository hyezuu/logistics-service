package takeoff.logistics_service.msa.product.stock.infrastructure.kafka;

import java.util.Map;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
@ComponentScan("takeoff.logistics_service.msa.product.stock")
public class StockTopicConfig {

	private static final String STOCK_TOPIC_NAME = "stock-events";

	@Bean
	public NewTopic stockTopic() {
		return TopicBuilder.name(STOCK_TOPIC_NAME)
			.partitions(3)
			.replicas(1)
			.configs(Map.of(
				// 해당 토픽의 보존 기간을 지정
				"retention.ms", "604800000",
				"cleanup.policy", "delete"
			))
			.build();
	}
	@Bean
	public String getStockTopic() {
		return STOCK_TOPIC_NAME;
	}
}

