package takeoff.logistics_service.msa.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import takeoff.logistics_service.msa.common.domain.UserRole;

/**
 * @author : hanjihoon
 * @Date : 2025. 03. 22.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface RoleCheck {
    UserRole[] roles() default {UserRole.MASTER_ADMIN, UserRole.COMPANY_MANAGER, UserRole.HUB_MANAGER,
    UserRole.COMPANY_DELIVERY_MANAGER, UserRole.HUB_DELIVERY_MANAGER};
}
