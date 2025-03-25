# take_off

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
- 목표: 광화문 근처에서 운영될 음식점들의 배달 및 포장 주문 관리, 결제, 그리고 주문 내역 관리 기능을 제공하는 플랫폼 개발

### 프로젝트 목표
1. **안정적인 유지보수**
    - 문서화된 유지보수 프로세스를 통해 팀원 간의 지식 공유<br>
    - 개발자가 쉽게 시스템을 이해하고 관리
2. **AI 기반 맞춤형 추천 시스템**
    - 사용자 친화적인 상품 설명 자동 생성
3. **비즈니스 확장성**
    - 다양한 지역과 음식점으로의 확장을 고려하여 시스템 아키텍처 설계

## 🛠️ Design Artifacts:설계산출물
- [api](https://github.com/toyland/toyland-project/wiki/API-Endpoints)
- [erd](https://github.com/toyland/toyland-project/wiki/erd)
- [architecture](https://github.com/toyland/toyland-project/wiki/architecture)
### convention
- [commit message convent](https://github.com/toyland/toyland-project/wiki/commit-message-convent)
- [git flow](https://github.com/toyland/toyland-project/wiki/git%E2%80%90flow)
- [package structure](https://github.com/toyland/toyland-project/wiki/package-structure)
### global concerns
- [consistent common response object:일관된 반환 타입을 위한 객체 정의](https://github.com/toyland/toyland-project/wiki/%EA%B3%B5%ED%86%B5-%EA%B4%80%EC%8B%AC%EC%82%AC#consistent-common-response-object%EC%9D%BC%EA%B4%80%EB%90%9C-%EB%B0%98%ED%99%98-%ED%83%80%EC%9E%85%EC%9D%84-%EC%9C%84%ED%95%9C-%EA%B0%9D%EC%B2%B4-%EC%A0%95%EC%9D%98)
- [test code:서비스 통합 테스트](https://github.com/toyland/toyland-project/wiki/%EA%B3%B5%ED%86%B5-%EA%B4%80%EC%8B%AC%EC%82%AC#test-code%EC%84%9C%EB%B9%84%EC%8A%A4-%ED%86%B5%ED%95%A9-%ED%85%8C%EC%8A%A4%ED%8A%B8)
- [data 추적/감사를 위한 auditing 정책](https://github.com/toyland/toyland-project/wiki/%EA%B3%B5%ED%86%B5-%EA%B4%80%EC%8B%AC%EC%82%AC#data-%EC%B6%94%EC%A0%81%EA%B0%90%EC%82%AC%EB%A5%BC-%EC%9C%84%ED%95%9C-auditing-%EC%A0%95%EC%B1%85)
- [soft-delete 정책](https://github.com/toyland/toyland-project/wiki/%EA%B3%B5%ED%86%B5-%EA%B4%80%EC%8B%AC%EC%82%AC#soft-delete-%EC%A0%95%EC%B1%85)
- [security: 인증 방식(jwt), filter(인증, 인가)](https://github.com/toyland/toyland-project/wiki/%EA%B3%B5%ED%86%B5-%EA%B4%80%EC%8B%AC%EC%82%AC#security-%EC%9D%B8%EC%A6%9D-%EB%B0%A9%EC%8B%9Djwt-filter%EC%9D%B8%EC%A6%9D-%EC%9D%B8%EA%B0%80)
- [common exception handling policy](https://github.com/toyland/toyland-project/wiki/%EA%B3%B5%ED%86%B5-%EA%B4%80%EC%8B%AC%EC%82%AC#common-exception-handling-policy)
- [swagger api document:API 문서화](https://github.com/toyland/toyland-project/wiki/%EA%B3%B5%ED%86%B5-%EA%B4%80%EC%8B%AC%EC%82%AC#swagger-api-documentapi-%EB%AC%B8%EC%84%9C%ED%99%94)

## 🐥 Troubleshooting:트러블슈팅
- [package structure 고민](https://github.com/toyland/toyland-project/wiki/%ED%8C%A8%ED%82%A4%EC%A7%80-%EA%B5%AC%EC%A1%B0-%EC%84%A0%ED%83%9D-%ED%8A%B8%EB%9F%AC%EB%B8%94-%EC%8A%88%ED%8C%85)
- [access denied exception 처리](https://github.com/toyland/toyland-project/wiki/access-denied-exception-%EC%B2%98%EB%A6%AC)
- [no property:Repository 인터페이스와 구현 클래스 패키지 위치 문제](https://github.com/toyland/toyland-project/wiki/no-property:Repository-%EC%9D%B8%ED%84%B0%ED%8E%98%EC%9D%B4%EC%8A%A4%EC%99%80-%EA%B5%AC%ED%98%84-%ED%81%B4%EB%9E%98%EC%8A%A4-%ED%8C%A8%ED%82%A4%EC%A7%80-%EC%9C%84%EC%B9%98-%EB%AC%B8%EC%A0%9C)
- [github actions에서 CI/CD 실패](https://github.com/toyland/toyland-project/wiki/github-actions%EC%97%90%EC%84%9C-CI-CD-%EC%8B%A4%ED%8C%A8)
- [취소된 주문 목록 조회 실패](https://github.com/toyland/toyland-project/wiki/%EC%B7%A8%EC%86%8C%EB%90%9C-%EC%A3%BC%EB%AC%B8-%EB%AA%A9%EB%A1%9D-%EC%A1%B0%ED%9A%8C-%EC%8B%A4%ED%8C%A8)
- [lazy loading으로 인한 오류](https://github.com/toyland/toyland-project/wiki/lazy-loading%EC%9C%BC%EB%A1%9C-%EC%9D%B8%ED%95%9C-%EC%98%A4%EB%A5%98)

## 요구사항

### 🧨 도메인 요구사항
<details>
 <summary>공통</summary>
 <ul>
   <li>✅ 각 도메인의 CRUD, search 작업 필요</li>
 </ul>
</details>

<details>
 <summary>운영 지역</summary>
 <ul>
   <li>✅ 확장을 고려한 지역 분류 시스템 설계 필요</li>
   <li>✅ 지역정보 수정 및 추가 등이 가능 하도록 고려</li>
   <li>✅ 지역별 필터링</li>
 </ul>
</details>

<details>
 <summary>음식점 카테고리 분류</summary>
 <ul>
   <li>✅ 음식점 카테고리로 분류(한식, 중식, 분식, 치킨, 피자 등)</li>
   <li>✅ 음식점 카테고리를 추가하거나 수정할 수 있도록 유연한 데이터 구조 설계 필요</li>
 </ul>
</details>

<details>
 <summary>주문 관리</summary>
 <ul>
   <li>✅ 주문 취소: 주문 생성 후 5분 이내에만 취소 가능하도록 제한</li>
   <li>✅ 주문 유형: 온라인 주문과 대면 주문(가게에서 직접 주문) 모두 지원</li>
   <li>✅ 대면 주문 처리: 가게 사장님이 직접 대면 주문을 접수</li>
 </ul>
</details>

<details>
 <summary>고객 배송지 정보</summary>
 <ul>
   <li>✅ 필수 입력 사항: 주소지, 요청 사항</li>
   <li>✅ ‘주문’ 과 ‘배달’ 에 모두 관련된 정보</li>
 </ul>
</details>

<details>
 <summary>AI API 연동</summary>
 <ul>
   <li>✅ 상품 설명 자동 생성: AI API를 연동하여 가게 사장님이 상품 설명을 쉽게 작성할 수 있도록 REST API 호출 로직 구현</li>
   <li>✅ AI 요청 기록: AI API 호출 시 요청 및 응답 데이터를 DB에 저장</li>
 </ul>
</details>

<details>
 <summary>결제 시스템</summary>
 <ul>
   <li>❌ 결제 방식: 카드 결제만 가능</li>
   <li>❌ PG사 연동: PG사와의 결제 연동은 외주 개발로 진행하며, 결제 관련 내역만 플랫폼의 데이터베이스에 저장</li>
   <li>✅ 결제 테이블: 결제 내역을 저장하기 위한 전용 테이블 설계</li>
 </ul>
</details>


### 🧨 글로벌 요구사항

 <details>
   <summary>프로젝트 구조</summary>
   <ul>
     <li>✅ Monolithic Application</li>
     <li>✅ Entity 및 DTO: 각 기능별로 Entity와 DTO(Data Transfer Object)를 분리하여 관리</li>
     <li>✅ API 설계: RESTful API 원칙에 따라 설계</li>
     <li>✅ Exception Handling: 글로벌 예외 처리 (ExceptionHandler 사용)</li>
   </ul>
 </details>

 <details>
   <summary>데이터 보존 및 삭제 처리</summary>
   <ul>
     <li>✅ 데이터 보존: 모든 데이터는 완전 삭제되지 않고 숨김 처리로 관리</li>
     <li>✅ 상품 숨김: 개별 상품도 숨김 처리 가능하도록 구현(숨김과 삭제는 다른 필드에서 동작해야함)</li>
     <li>✅ 데이터 감사 로그: 모든 정보에 생성일, 생성 아이디, 수정일, 수정 아이디, 삭제일, 삭제 아이디를 포함</li>
   </ul>
 </details>

 <details>
   <summary>데이터베이스 설계</summary>
   <ul>
     <li>✅ 테이블 명명 규칙: 모든 테이블에 p_ 접두사 사용</li>
     <li>✅ UUID 사용: 모든 주요 엔티티의 식별자는 UUID를 사용 (유저는 예외)</li>
     <li>✅ Audit 필드: 모든 테이블에 created_at, created_by, updated_at, updated_by, deleted_at, deleted_by 필드를 추가하여 데이터 감사 로그 기록</li>
     <li>✅ ERD 설계: 엔티티 간의 관계를 명확히 하는 ERD(Entity-Relationship Diagram) 작성</li>
   </ul>
 </details>

 <details>
   <summary>접근 권한 관리</summary>
   <ul>
     <li>✅ 고객: 자신의 주문 내역만 조회 가능</li>
     <li>✅ 가게 주인: 자신의 가게 주문 내역, 가게 정보, 주문 처리 및 메뉴 수정 가능</li>
     <li>✅ 관리자: 모든 가게 및 주문에 대한 전체 권한 보유</li>
   </ul>
 </details>

 <details>
   <summary>보안</summary>
   <ul>
     <li>✅ JWT 인증: Spring Security와 JWT(Json Web Token)를 이용한 인증 및 권한 관리</li>
     <li>✅ 권한 확인 : CUSTOMER 이상의 권한은 요청마다 저장되어 있는 권한 값과 동일한지 체크필요</li>
     <li>✅ 비밀번호 암호화: BCrypt 해시 알고리즘을 사용한 비밀번호 암호화</li>
     <li>✅ 데이터 유효성 검사: 서버 측 데이터 유효성 검사를 위해 Spring Validator 사용</li>
   </ul>
 </details>

 <details>
   <summary>테스트</summary>
   <ul>
     <li>✅ 테스트: Spring Boot Test를 사용한 테스트</li>
     <li>✅ service 통합 테스트 진행</li>
   </ul>
 </details>

## 🙆🏻‍♀️🙆🏻 Team Member Introduction&Retrospective:팀원소개&회고
| 멤버                                           | 프로필                                                |                     역할               | 소감                                                                                                                                                                                                                                                                                                                                           |
|----------------------------------------------|----------------------------------------------------------|------------------------------|----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| [한지훈](https://github.com/hanjihoon03) | <img src="https://github.com/user-attachments/assets/06930309-fe87-4cdd-9d34-ea735b74f7c4" width=100px alt="_"/> | Leader. 주소, 지역, 전체적인 예외처리, 횡단 관심사 | 협업에 대해 더 알게 되고 다른 분에게 무언가를 설명 한다는게 정말 어렵지만 더 잘 알게 되고 설명하는 측에서 배우는 것도 많고 다른 분들 코드를 보며 많이 배웠습니다. 소통이 많고, 의견이 틀리거나 맞더라도 의견은 많은게 정말 좋다고 생각했고 사전에 가지고 있는 템플릿이나 공부한 것들이 얼마나 중요한지 알게 됐습니다. 최종적으로는 개인의 부족함을 많이 깨달았습니다. 협업을 처음 해보았는데 자신의 부족함을 더 알게 되고 팀원과 의견을 나누거나 코드를 리뷰하며 성장할 수 있는 계기가 되었고 더 많은 것을 해보고 싶다고 느껴졌으며 힘드셨을텐데 잘 따라와주신 팀원 분들께 감사합니다.🐈‍⬛🐈 |
| [박지은](https://github.com/je-pa)           | <img src="https://github.com/user-attachments/assets/a6759d44-3bd8-434c-a9d9-63efb29f3523" width=100px alt="_"/>  | SubLeader. 카테고리, 음식점, 상품| 협업은 역시 힘들지만, 함께 같이 노력하고 나아가는 모습에 힘이 난다는 것이 묘미인 것 같습니다. 이번 프로젝트로 부터 다들 발전한게 보여서 뿌듯합니다. 이번에는 맡은 도메인의 CRUD와 search 작업을 다하여 기본에 충실한 프로젝트였던 것 같습니다. 항상 클린한 코드를 작성하려고 하지만 어렵습니다! 깔끔한 설계를 위해선 관련 개념에 대한 이해가 정말 중요한 것 같습니다. 이번에 queryDSL도 좀 더 깔끔하게 작성하려고 노력해봤는데 나름 재미있었던 것 같습니다. 카테고리 부분은 아쉬운 설계가 좀 있는데 다음에 적용할 때는 좀 더 빠르게 나아가보도록 해보겠습니다.🦄          |
| [김민경](https://github.com/khloe08)      | <img src="https://github.com/user-attachments/assets/3ebc1a0e-0358-4a40-ba92-d329c4ca511b" width=130px alt="_"/> | AI QnA, 리뷰, AI 외부 API 연결 | 개인적으로는 처음 0에서부터 10까지 개인이 아닌 팀으로 진행해보았어서 부족한 부분을 많이 깨달을 수 있었고, 또 팀원들과 튜터님께 배운 부분이 많았다. 부족한 부분이 많았는데 잘 리드해주는 팀원들과 끝까지 맡은 바 다하는 팀원들 덕분에 많이 배웠습니다.프로젝트의 근본을 배운 것 같습니다. 감사합니다🧚🏻‍♀️                                                                                                                                                              |
| [남정길](https://github.com/letsgilit)      | <img src="https://github.com/user-attachments/assets/d0958a1a-0ead-42a6-b10f-e251251f5922" width=130px alt="_"/> | 회원, 시큐리티 설정 | 이전에는 구현에 집중을 했더라면 협업을 통해 확장성, 가독성을 고려한 Cleancode를 배웠다.개인적으로 어렵거나 몰랐던 부분을 명쾌하게 답변해줄 수 있는 팀원들이 있어 든든했고 고마웠다.이번 협업을 통해 다른 사람의 장점을 흡수하고 자신의 단점을 알아가는 과정이었던 것 같다.늦은 시간까지 프로젝트에 집중하는 팀원들을 보면서 같이 남아 그 열정을 함께 할 수 있어 영광이었습니다. 지훈님 사랑합니다❤️                                                                                                           |
| [서진영](https://github.com/jin2304)         | <img src="https://github.com/user-attachments/assets/74a9b142-5b5b-4d0d-baf1-d3f8aeee0c17" width=130px alt="_"/> | 결제, 주문, 주문 상품 | 이번 프로젝트는 처음으로 구체적인 요구사항이 있고, 체계적인 개발 프로세스를 경험한 프로젝트였다. 기존에는 내가 구상한 대로 설계하고 개발했지만, 요구사항을 기반으로 개발하다 보니 처음엔 다소 서툴렀다.또한, 이번 프로젝트를 통해 많은 고민들을 팀원들과 공유하고 소통하면서 협업하는 능력도 키울 수 있었다. 이를 통해 한층 더 성장하고, 많은 것을 배울 수 있었기에 매우 만족스러운 경험이었다🐥                                                                                                                |