package takeoff.logistics_service.msa.user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan(basePackages = "takeoff.logistics_service.msa.user.domain.entity")
@EnableJpaRepositories(basePackages = "takeoff.logistics_service.msa.user.infrastructure.persistence")
public class UserApplication {

	public static void main(String[] args) {
		SpringApplication.run(UserApplication.class, args);
	}

}
