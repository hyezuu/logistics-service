package takeoff.logistics_service.msa.product.stock.infrastructure.persistence.aspect;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.lang.reflect.Method;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class LockTimeoutAspectTest {

	@InjectMocks
	private LockTimeoutAspect aspect;
	@Mock
	private LockManager lockManager;

	@Test
	void testBeforeLockTimeout() throws NoSuchMethodException {
		//Given
		Method testMethod = TestClass.class.getMethod("annotatedMethod");
		LockTimeout lockTimeout = testMethod.getAnnotation(LockTimeout.class);

		//When
		aspect.beforeLockTimeout(lockTimeout);

		//Then
		verify(lockManager, times(1)).setLockTimeout(lockTimeout.timeout());
	}

	// 테스트 클래스
	private static class TestClass {
		@LockTimeout(timeout = 5000)
		public void annotatedMethod() {
		}
	}
}