package takeoff.logistics_service.msa.common.annotation;

import jakarta.servlet.http.HttpServletRequest;
import java.util.Set;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import takeoff.logistics_service.msa.common.domain.UserRole;
import takeoff.logistics_service.msa.common.exception.BusinessException;
import takeoff.logistics_service.msa.common.exception.code.CommonErrorCode;

/**
 * @author : hanjihoon
 * @Date : 2025. 03. 22.
 */
@Aspect
@Component
public class RoleCheckAspect {
    private static final String USER_ROLE_HEADER = "X-User-Role";

    @Before("@annotation(roleCheck)")
    public void roleCheck(RoleCheck roleCheck) {

        Set<UserRole> roles = Set.of(roleCheck.roles());
        UserRole userRole = getUserRole();

        if (!roles.contains(userRole)) {
            throw BusinessException.from(CommonErrorCode.FORBIDDEN);
        }
    }

    private UserRole getUserRole() {
        ServletRequestAttributes attributes =
            (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();

        if (attributes == null) {
            throw BusinessException.from(CommonErrorCode.UNAUTHORIZED);
        }

        HttpServletRequest request = attributes.getRequest();
        String roleHeader = request.getHeader(USER_ROLE_HEADER);

        if (roleHeader == null || roleHeader.isEmpty()) {
            throw BusinessException.from(CommonErrorCode.UNAUTHORIZED);
        }

        return UserRole.valueOf(roleHeader);
    }
}
