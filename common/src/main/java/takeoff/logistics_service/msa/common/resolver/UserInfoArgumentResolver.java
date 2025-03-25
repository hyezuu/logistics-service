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

		return parameter.hasParameterAnnotation(UserInfo.class)
			&& parameter.getParameterType().equals(UserInfoDto.class);
	}

	@Override
	public Object resolveArgument(MethodParameter parameter,
		ModelAndViewContainer mavContainer,
		NativeWebRequest webRequest,
		WebDataBinderFactory binderFactory) {

		return Optional.of(webRequest.getNativeRequest())
			.filter(HttpServletRequest.class::isInstance)
			.map(HttpServletRequest.class::cast)
			.map(this::extractUserInfo)
			.orElse(UserInfoDto.empty());
	}

	private UserInfoDto extractUserInfo(HttpServletRequest request) {

		return UserInfoDto.of(
			request.getHeader(USER_ID_HEADER),
			request.getHeader(USER_ROLE_HEADER));
	}
}