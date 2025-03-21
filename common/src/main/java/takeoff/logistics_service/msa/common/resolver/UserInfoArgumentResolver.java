package takeoff.logistics_service.msa.common.resolver;

import jakarta.servlet.http.HttpServletRequest;
import java.util.Optional;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import takeoff.logistics_service.msa.common.domain.UserInfo;
import takeoff.logistics_service.msa.common.domain.UserInfoDto;

@Component
public class UserInfoArgumentResolver implements HandlerMethodArgumentResolver {

	private static final String USER_ID_HEADER = "X-User-Id";
	private static final String USER_ROLE_HEADER = "X-User-Role";

	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		return parameter.hasParameterAnnotation(UserInfo.class) &&
			parameter.getParameterType().equals(UserInfoDto.class);
	}

	@Override
	public Object resolveArgument(MethodParameter parameter,
		ModelAndViewContainer mavContainer,
		NativeWebRequest webRequest,
		WebDataBinderFactory binderFactory) {

		return Optional.of(webRequest.getNativeRequest())
			.filter(HttpServletRequest.class::isInstance)
			.map(HttpServletRequest.class::cast)
			.flatMap(this::extractUserInfo)
			.orElse(null);
	}

	private Optional<UserInfoDto> extractUserInfo(HttpServletRequest request) {
		String userId = request.getHeader(USER_ID_HEADER);
		String role = request.getHeader(USER_ROLE_HEADER);

		return (userId != null && role != null) ? UserInfoDto.of(userId, role) : Optional.empty();
	}
}