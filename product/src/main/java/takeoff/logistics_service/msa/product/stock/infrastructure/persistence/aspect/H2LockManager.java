package takeoff.logistics_service.msa.product.stock.infrastructure.persistence.aspect;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Slf4j
@Profile("test")
@Component
@RequiredArgsConstructor
public class H2LockManager implements LockManager {

	@Override
	public void setLockTimeout(int timeout) {
		log.info("테스트 환경에서는 락 타임아웃 설정이 무시됩니다: {}ms", timeout);
	}
}
