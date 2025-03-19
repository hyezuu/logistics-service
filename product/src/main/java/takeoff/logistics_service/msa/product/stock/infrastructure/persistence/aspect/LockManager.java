package takeoff.logistics_service.msa.product.stock.infrastructure.persistence.aspect;

public interface LockManager {

	void setLockTimeout(int timeout);
}
