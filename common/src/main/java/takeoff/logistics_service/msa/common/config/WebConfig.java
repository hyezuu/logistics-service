package takeoff.logistics_service.msa.common.config;

import java.util.List;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import takeoff.logistics_service.msa.common.resolver.UserInfoArgumentResolver;

@Configuration
public class WebConfig implements WebMvcConfigurer {
	private final UserInfoArgumentResolver userInfoArgumentResolver;

	public WebConfig(UserInfoArgumentResolver userInfoArgumentResolver) {
		this.userInfoArgumentResolver = userInfoArgumentResolver;
	}

	@Override
	public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
		resolvers.add(userInfoArgumentResolver);
	}
}