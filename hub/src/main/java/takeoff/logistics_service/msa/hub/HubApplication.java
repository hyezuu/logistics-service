package takeoff.logistics_service.msa.hub;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication(
	scanBasePackages = {"takeoff.logistics_service.msa.common","takeoff.logistics_service.msa.hub"}
)
@EnableFeignClients
public class HubApplication {

	public static void main(String[] args) {
		SpringApplication.run(HubApplication.class, args);
	}

}
