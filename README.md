# take_off

[![프로젝트 소개](https://img.shields.io/badge/📌-프로젝트_소개-1F3A53?style=flat-square&logoColor=white)](#-프로젝트-소개) 
[![기술 스택](https://img.shields.io/badge/🛠️-기술_스택-24435C?style=flat-square&logoColor=white)](#-기술-스택) 
[![실행 방법](https://img.shields.io/badge/💻-실행_방법-294C65?style=flat-square&logoColor=white)](#-실행-방법) 
[![설계 산출물](https://img.shields.io/badge/📐-설계_산출물-2E556E?style=flat-square&logoColor=white)](#-설계-산출물) 
[![컨벤션](https://img.shields.io/badge/📋-컨벤션-335E77?style=flat-square&logoColor=white)](#-컨벤션) 
[![공통 관심사](https://img.shields.io/badge/🌐-공통_관심사-335E77?style=flat-square&logoColor=white)](#-공통-관심사) 
[![트러블슈팅](https://img.shields.io/badge/🔍-트러블슈팅-2E556E?style=flat-square&logoColor=white)](#-트러블슈팅) 
[![리팩토링](https://img.shields.io/badge/♻️-리팩토링-294C65?style=flat-square&logoColor=white)](#-리팩토링) 
[![API 산출](https://img.shields.io/badge/📜-API_산출-24435C?style=flat-square&logoColor=white)](#-api-산출) 
[![요구사항](https://img.shields.io/badge/📝-요구사항-1F3A53?style=flat-square&logoColor=white)](#-요구사항) 
[![팀원 소개](https://img.shields.io/badge/👥-팀원_소개-1A314A?style=flat-square&logoColor=white)](#-팀원-소개회고)
> MSA로 설계된 B2B로 각 업체간 상품을 제공하고 허브를 관리하는 프로젝트 입니다.
> ### 👩🏻‍💻 How to Execute the Project:실행 방법
> 1️⃣ **git clone https://github.com/takeoff-26/logistics-service.git**
>
> 2️⃣ **DB 세팅**
>    1. Docker 설치
>    2. 프로젝트 루트 디렉터리에서 CLI를 통해 'docker compose up' 명령을 실행한다.
>    3. 'docker ps' 명령으로 'root'가 제대로 실행되었는지 확인한다.
>
> 3️⃣ **플러그인 설치**
>     Http Client TEST를 실행하기 위해 HTTP Client 플러그인을 설치한다.
>
> 4️⃣ **Run 실행**

## 💻 Skiils:기술

| Category              | Technology                                                      |
|-----------------------|-----------------------------------------------------------------|
| **IDE**               | IntelliJ IDEA                                                   |
| **Language**          | Java 17                                                         |
| **Framework**         | Spring Boot 3.4.2                                               |
| **Database**          | PostgreSQL, H2, Redis                                           |
| **ORM**               | JPA (Jakarta Persistence API)                                   |
| **Query Builder**     | QueryDSL 5.0.0                                                  |
| **Test**              | JUnit, Spring Boot Starter Test, Spring Security Test, WireMock |
| **Containerization**  | Docker, Docker Compose                                          |
| **Service Discovery** | Spring Cloud Eureka                                             |
| **Circuit Breaker**   | Resilience4j                                                    |
| **Spring Cloud**      | Spring Cloud                                                    |
| **Metric**            | Prometheus                                                      |
| **Monitoring**        | Grafana                                                         |


## 📌 프로젝트 개요
- 주제: 허브에 소속된 업체간 B2B 배송 시스템


## 🛠️ Design Artifacts:설계산출물
- [api](https://teamsparta.notion.site/API-1b42dc3ef51480d5a411d1c15c6ccdc7)
- [erd](https://teamsparta.notion.site/ERD-1b52dc3ef51480638c01f87086c52bd8)
- [architecture](https://github.com/takeoff-26/logistics-service/wiki/aggregate-map)
- [aggregate-map](https://github.com/takeoff-26/logistics-service/wiki/aggregate-map)
### convention
- [commit message convent](https://github.com/takeoff-26/logistics-service/wiki/commit-message-convent)
- [git flow](https://github.com/takeoff-26/logistics-service/wiki/git%E2%80%90flow)
- [package structure](https://github.com/takeoff-26/logistics-service/wiki/package-structure)
### global concerns
- [common: MSA 서비스 권한 체크](https://github.com/takeoff-26/logistics-service/wiki/common:-Auditing)
- [common: Auditing](https://github.com/takeoff-26/logistics-service/wiki/common:-Auditing)
- [common: exception handling policy](https://github.com/takeoff-26/logistics-service/wiki/common:-exception-handling-policy)

## 🐥 Troubleshooting:트러블슈팅
- [Postgres Lock Timeout](https://github.com/takeoff-26/logistics-service/wiki/Troubleshooting:Lock-Time-Out)
- [RedisCache 직렬화 역직렬화 문제](https://github.com/takeoff-26/logistics-service/wiki/Troubleshooting:-RedisCache-%EC%A7%81%EB%A0%AC%ED%99%94-%EC%97%AD%EC%A7%81%EB%A0%AC%ED%99%94-%EB%AC%B8%EC%A0%9C)
- [외부 api 호출시 사용하는 보상트랜잭션](https://github.com/takeoff-26/logistics-service/wiki/Troubleshooting:-%EC%99%B8%EB%B6%80-api-%ED%98%B8%EC%B6%9C%EC%8B%9C-%EC%82%AC%EC%9A%A9%ED%95%98%EB%8A%94-%EB%B3%B4%EC%83%81%ED%8A%B8%EB%9E%9C%EC%9E%AD%EC%85%98)
- [비관적 LOCK, 낙관적 LOCK](https://github.com/takeoff-26/logistics-service/wiki/Troubleshooting:-%EB%82%99%EA%B4%80%EC%A0%81-LOCK,-%EB%B9%84%EA%B4%80%EC%A0%81-LOCK)

## 💡 Refactoring:리팩토링
- [의존관계 역방향 제거](https://github.com/takeoff-26/logistics-service/wiki/Refactoring:-%EC%9D%98%EC%A1%B4%EA%B4%80%EA%B3%84-%EC%97%AD%EB%B0%A9%ED%96%A5-%EC%A0%9C%EA%B1%B0)
- [Spring Circuit Breaker 적용](https://github.com/takeoff-26/logistics-service/wiki/Refactoring:-Spring-Circuit-Breaker-%EC%A0%81%EC%9A%A9)

## API 산출
- [API](https://github.com/takeoff-26/logistics-service/wiki/API)

## 요구사항

### 🧨 도메인 요구사항
<details>
 <details>
 <summary>공통</summary>
 <ul>
   <li>✅ 각 도메인의 CRUD, search 작업 필요 (몇몇 도메인 제외)</li>
   <li>✅ 도메인 주도 설계(DDD) 적용</li>
 </ul>
</details>

<details>
 <summary>허브 관리</summary>
 <ul>
   <li>✅ 허브 정보 캐싱</li>
   <li>✅ 17곳의 허브 고정 데이터 생성</li>
 </ul>
</details>

<details>
 <summary>허브 간 이동 정보 관리</summary>
 <ul>
   <li>✅ 허브 경로 위도, 경도 계산</li>
   <li>✅ 이동 경로 알고리즘 설정 및 구현 (난이도: 중, P2P + Hub to Hub Relay 방식 적용)</li>
   <li>✅ 허브 간 이동 경로 캐싱</li>
 </ul>
</details>

<details>
 <summary>배송 담당자 관리</summary>
 <ul>
   <li>✅ 배송 담당자 배송 순번 순차적 배정</li>
   <li>✅ 배송 담당자 타입에 따라 업체 배송, 허브 배송 분리</li>
 </ul>
</details>

<details>
 <summary>업체 관리</summary>
 <ul>
   <li>✅ 업체 추가 시 관리 허브 ID가 존재하는지 확인</li>
 </ul>
</details>

<details>
 <summary>상품 관리</summary>
 <ul>
   <li>✅ 상품 생성 시 업체가 존재하는지 확인</li>
   <li>✅ 상품 관리 허브 ID가 존재하는지 확인</li>
 </ul>
</details>

<details>
 <summary>주문 관리</summary>
 <ul>
   <li>✅ 주문 생성 시 배송도 같이 생성</li>
 </ul>
</details>

<details>
 <summary>배송 관리</summary>
 <ul>
   <li>✅ 배송과 배송 경로 기록 엔티티 분리 생성</li>
   <li>✅ 배송 과정에서 발생한 경로를 추적</li>
   <li>✅ 주문 생성 시 배송과 배송 경로 기록 데이터 같이 생성</li>
 </ul>
</details>

<details>
 <summary>슬랙 메시지 관리</summary>
 <ul>
   <li>✅ AI를 통해 메시지 발송 기능 구현</li>
 </ul>
</details>

<details>
 <summary>사용자 관리</summary>
 <ul>
   <li>✅ 권한 분리</li>
   <li>✅ 로그인 기능</li>
 </ul>
</details>

<details>
 <summary>AI 연동 기능</summary>
 <ul>
   <li>✅ 발송 허브 담당자에게 배송 예상 시간 알림 처리</li>
 </ul>
</details>

---

## 🧨 글로벌 요구사항

<details>
 <summary>프로젝트 구조</summary>
 <ul>
   <li>✅ Layered Architecture 적용</li>
   <li>✅ Entity 및 DTO: 각 기능별로 분리하여 관리</li>
   <li>✅ API 설계: RESTful API 원칙에 따라 설계</li>
   <li>✅ Exception Handling: 글로벌 예외 처리 (`@ExceptionHandler` 사용)</li>
 </ul>
</details>

<details>
 <summary>데이터 보존 및 삭제 처리</summary>
 <ul>
   <li>✅ 모든 데이터는 완전 삭제되지 않고 숨김 처리로 관리</li>
   <li>✅ 데이터 감사 로그: 생성일, 생성 ID, 수정일, 수정 ID, 삭제일, 삭제 ID 포함</li>
 </ul>
</details>

<details>
 <summary>MSA 애플리케이션 구성</summary>
 <ul>
   <li>✅ 유레카 서버 생성 및 애플리케이션 관리</li>
   <li>✅ 게이트웨이 생성 및 라우팅</li>
   <li>✅ 게이트웨이에서 인가 처리</li>
   <li>✅ 서킷 브레이커 적용</li>
   <li>✅ 모든 애플리케이션을 Docker 컨테이너로 실행</li>
 </ul>
</details>

<details>
 <summary>데이터베이스 설계</summary>
 <ul>
   <li>✅ 테이블 명명 규칙: 모든 테이블에 `p_` 접두사 사용</li>
   <li>✅ UUID 사용: 주요 엔티티의 식별자는 UUID 사용 (유저는 예외)</li>
   <li>✅ Audit 필드: `created_at`, `created_by`, `updated_at`, `updated_by`, `deleted_at`, `deleted_by` 필드 추가</li>
 </ul>
</details>

<details>
 <summary>접근 권한 관리</summary>
 <ul>
   <li>✅ 각 바운더리 컨텍스트별 권한 검사</li>
 </ul>
</details>

<details>
 <summary>보안</summary>
 <ul>
   <li>✅ JWT 인증: Spring Security와 JWT(Json Web Token)를 이용한 인증 및 권한 관리</li>
   <li>✅ 권한 확인: `CUSTOMER` 이상의 권한은 요청마다 저장된 권한 값과 동일한지 체크</li>
   <li>✅ 비밀번호 암호화: BCrypt 해시 알고리즘 사용</li>
   <li>✅ 데이터 유효성 검사: Spring Validator 사용</li>
 </ul>
</details>

<details>
 <summary>테스트</summary>
 <ul>
   <li>✅ 테스트: Spring Boot Test 사용</li>
   <li>✅ Service 통합 테스트 진행</li>
 </ul>
</details>
 </details>

## 🙆🏻‍♀️🙆🏻 Team Member Introduction&Retrospective:팀원소개&회고
|     멤&nbsp;&nbsp;&nbsp;&nbsp;버                                   |                     역할               | 소감                                                                                                                                                                                                                           |
|----------------------------------------------|----------------------------------------------------------|------------------------------|
| [한지훈](https://github.com/hanjihoon03)  | Leader. 허브, 허브 경로 생성, 슬랙 | MSA 프로젝트는 처음이여서 고려할 점도 많고 독립적으로 동작 하고 각 서비스간 통신을 해 데이터를 주고 받는 과정이 어려웠고 좀 더 많은 것을 적용하지 못한 아쉬움과 일정 관리의 부족함으로 시간이 부족했던게 많은 여러모로 아쉬운 프로젝트였습니다.                                                                                                              |
| [강혜주](https://github.com/hyezuu)    | 공통 관심사, config서버, 상품, 재고| msa 환경에서 팀원간의 소통이 얼마나 중요한지 깨달았습니다.짧은 기간에 난이도도 높아서 어려움이 많았지만 그만큼 배운점도 많았던 프로젝트 였습니다! 다들 수고 많으셨습니다~~!                            |
| [최해인](https://github.com/Sodychoe)    | 주문, 배송, 배송 경로 기록 | MSA 와 DDD 를 같이 병행하는 개발은 처음이어서 무척 어려웠고 도전해야할 부분들이 많았습니다. DDD 를 위한 설계와 MSA 를 위한 설계를 자주 헷갈리기도 했고,  기술적으로도 복잡한 부분이 많아 목표한만큼 성과가 없어서  아쉬움이 많이 남는 프로젝트였습니다. 이번 기회에 아쉬웠던 부분을 복습해서 다음 프로젝트에서 좋은 마무리를 할 수 있었으면 좋겠습니다.                                                                                      |
| [이성민](https://github.com/2sminn)  | 보안, 사용자, 업체 | MSA 프로젝트를 진행하면서 짧은 기간에 기존과는 다른 설계 패턴으로 설계하는 것부터 시작해 각 MS간 내부 통신까지, 정말 복잡하고 난이도도 높은 개발 작업이었습니다. 요구사항에 업데이트가 생기거나 서로 필요한 데이터를 넘겨받고 다시 넘겨주는 과정에서 일찍 테스트를 진행하지 못한점이 너무 아쉬운 프로젝트였고, 팀원과의 소통도 정말 중요하다는 것을 다시금 느끼게 되었습니다.                                                        |
