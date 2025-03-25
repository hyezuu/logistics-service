# Logistics Service
물류 배송을 위한 마이크로서비스 기반 백엔드 시스템으로, 효율적인 배송 경로 관리와 실시간 배송 상태 추적 기능을 제공합니다.

## 컨텍스트 맵
<img width="1071" alt="스크린샷 2025-03-25 오전 4 18 21" src="https://github.com/user-attachments/assets/00e7ec8b-b553-4424-a202-f7ea5d6a6913" />

## 아키텍쳐
<img width="846" alt="스크린샷 2025-03-24 오후 7 14 38" src="https://github.com/user-attachments/assets/0b9089f4-a0e3-4480-891c-72a8d0cd5770" />

## 프로젝트 목적/상세
이 프로젝트는 물류 배송 프로세스를 효율적으로 관리하기 위한 마이크로서비스 아키텍처 기반 백엔드 시스템입니다. 주요 기능은 다음과 같습니다:
- 주문 관리 및 처리
- 상품 카탈로그 관리
- 효율적인 배송 경로 계산 및 최적화
- 실시간 배송 상태 추적
- 사용자 계정 및 권한 관리
- 안전한 인증 및 인가 처리

## 기술 스택

### 백엔드
- **언어**: Java 17
- **프레임워크**: Spring Boot, Spring Cloud
- **API 통신**: OpenFeignClient, WebClient
- **API 게이트웨이**: Spring Cloud Gateway
- **설정 관리**: Spring Cloud Config
- **서비스 디스커버리**: Eureka
- **서킷 브레이커**: Resilience4j
- **인증/인가**: JWT, Spring Security
- **API 문서화**: OpenAPI 3 + Spring REST Docs

### 데이터 저장
- **관계형 데이터베이스**: PostgreSQL
- **인메모리 데이터베이스**: Redis

### 인프라 및 운영
- **컨테이너 기술**: Docker, Docker Compose
- **분산 추적**: Zipkin
- **모니터링**: Prometheus, Grafana

### 테스트
- **단위/통합 테스트**: JUnit5, Mockito
- **API 테스트**: WireMock

## 서비스 구성
이 프로젝트는 다음과 같은 마이크로서비스로 구성되어 있습니다:
- **Config Server**: 중앙 집중식 설정 관리
- **Eureka Server**: 서비스 디스커버리
- **Gateway Server**: API 게이트웨이 및 경로 라우팅
- **Auth Service**: 인증 및 권한 관리
- **User Service**: 사용자 정보 관리
- **Order Service**: 주문 처리
- **Product Service**: 상품 관리
- **Delivery Service**: 배송 관리 및 경로 최적화

## 실행 방법

### 사전 요구사항
- JDK 17
- Docker & Docker Compose
- PostgreSQL
- Redis

### 환경 설정
1. 저장소 클론
   ```bash
   git clone https://github.com/takeoff-26/logistics-service.git
   cd logistics-service
   ```

2. 환경 변수 설정
   `.env` 파일을 프로젝트 루트 디렉토리에 생성하고 필요한 환경 변수를 설정합니다:
   ```
   # 데이터베이스 연결 정보
   PRODUCT_DB_URL=
   SLACK_DB_URL=
   HUB_DB_URL=
   USER_DB_URL=
   ORDER_DB_URL=
   COMPANY_DB_URL=
   DELIVERY_DB_URL=
   
   DB_USERNAME=
   DB_PASSWORD=
   
   # Redis 설정
   REDIS_USERNAME=
   REDIS_PASSWORD=
   
   # JWT 설정
   JWT_SECRET_KEY=
   JWT_ACCESS_EXPIRATION=
   JWT_REFRESH_EXPIRATION=
   
   # 서비스 연결 정보
   EUREKA_SERVER_URL=
   ```

3. 서비스 실행
   ```bash
   # Docker Compose 사용
   docker-compose up -d
   
   # 또는 각 서비스 개별 실행
   ./gradlew :config-server:bootRun
   ./gradlew :eureka-server:bootRun
   ./gradlew :gateway-server:bootRun
   # 나머지 서비스도 동일하게 실행
   ```

## 팀원 역할분담

| 이름 | 역할 | 담당 서비스 | 주요 기여 |
|------|------|------------|---------|
| 한지훈 | 백엔드 개발 | Slack, Hub | 슬랙 연동 및 허브 관리 시스템 구현 |
| 강혜주 | 백엔드 개발 | Product, Common | 상품(재고) 관리 시스템 및 공통 모듈 개발 |
| 최해인 | 백엔드 개발 | Order, Delivery | 주문 처리 및 배송 시스템 구현 |
| 이성민 | 백엔드 개발 | User, Company | 사용자 관리 및 업체 관리 시스템 구현 |

## ERD
![ERD 다이어그램](path/to/erd-image.png)


## 트러블슈팅

- [Redis 직렬화 오류 해결](https://jh315.tistory.com/137)
- [Postgres Lock Timeout 적용하기](https://velog.io/@hyezuu/Postgres-Lock-Timeout-%EC%A0%81%EC%9A%A9%ED%95%98%EA%B8%B0)

## 기능 구현

- [DDD 역방향 의존성 제거](https://jh315.tistory.com/138)
- [외부 API 호출에 서킷브레이커 적용](https://jh315.tistory.com/136?category=1251728)

## API 문서
API 문서는 Swagger UI를 통해 접근할 수 있습니다:
- http://localhost:19000/docs
