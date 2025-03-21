package takeoff.logistics_service.msa.common.domain;

import java.util.Optional;
import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Component
public class AuditorAwareImpl implements AuditorAware<Long> {

	private static final String USER_ID_HEADER = "X-User-Id";

	@Override
	public Optional<Long> getCurrentAuditor() {

		return Optional.ofNullable(RequestContextHolder.getRequestAttributes())
			.filter(ServletRequestAttributes.class::isInstance)
			.map(ServletRequestAttributes.class::cast)
			.map(ServletRequestAttributes::getRequest)
			.map(request -> request.getHeader(USER_ID_HEADER))
			.filter(userId -> !userId.isEmpty())
			.flatMap(this::parseUserId);
	}

	private Optional<Long> parseUserId(String userId) {
		try {
			return Optional.of(Long.parseLong(userId));
		} catch (NumberFormatException e) {
			return Optional.empty();
		}
	}
}

