package takeoff.logistics_service.msa.product.stock.infrastructure.persistence.aspect;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Profile("!test")
@Component
@RequiredArgsConstructor
public class PostgresLockManager implements LockManager {

	private final EntityManager em;

	public void setLockTimeout(int timeout) {
		Query query = em.createNativeQuery("SET LOCAL lock_timeout = '" + timeout + "ms'");
		query.executeUpdate();
	}

}
