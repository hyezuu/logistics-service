package takeoff.logistics_service.msa.common.config;

import feign.RequestInterceptor;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Configuration
public class FeignClientConfig {
	@Bean
	public RequestInterceptor requestInterceptor() {
		return requestTemplate -> {
			ServletRequestAttributes attrs = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
			if (attrs != null) {
				HttpServletRequest request = attrs.getRequest();
				String userId = request.getHeader("X-User-Id");
				String role = request.getHeader("X-User-Role");

				if (userId != null) requestTemplate.header("X-User-Id", userId);
				if (role != null) requestTemplate.header("X-User-Role", role);
			}
		};
	}
}