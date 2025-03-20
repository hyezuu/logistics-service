package takeoff.logistics_service.msa.product.stock.infrastructure.persistence.aspect;

import lombok.RequiredArgsConstructor;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
@RequiredArgsConstructor
public class LockTimeoutAspect {

	private final LockManager lockManager;

	@Before("@annotation(lockTimeout)")
	public void beforeLockTimeout(LockTimeout lockTimeout) {
		if (lockTimeout != null) {
			lockManager.setLockTimeout(lockTimeout.timeout());
		}
	}
}
