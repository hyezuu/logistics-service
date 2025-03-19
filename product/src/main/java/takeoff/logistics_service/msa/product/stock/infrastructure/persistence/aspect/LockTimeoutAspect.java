package takeoff.logistics_service.msa.product.stock.infrastructure.persistence.aspect;

import lombok.RequiredArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

@Aspect
@Component
@RequiredArgsConstructor
public class LockTimeoutAspect {

	private final LockManager lockManager;

	@Before("@annotation(takeoff.logistics_service.msa.product.stock.infrastructure.persistence"
		+ ".aspect.LockTimeout)")
	public void beforeLockTimeout(JoinPoint joinPoint) {
		MethodSignature signature = (MethodSignature) joinPoint.getSignature();
		LockTimeout annotation = signature.getMethod().getAnnotation(LockTimeout.class);
		lockManager.setLockTimeout(annotation.timeout());
	}
}
