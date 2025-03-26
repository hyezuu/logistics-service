# **take_off**

## 🔗 목차
[![프로젝트 소개](https://img.shields.io/badge/📌-프로젝트_소개-1F3A53?style=flat-square&logoColor=white)](#프로젝트-개요)
[![기술 스택](https://img.shields.io/badge/🛠️-기술_스택-24435C?style=flat-square&logoColor=white)](#기술) 
[![실행 방법](https://img.shields.io/badge/💻-실행_방법-294C65?style=flat-square&logoColor=white)](#실행-방법) 
[![설계 산출물](https://img.shields.io/badge/📐-설계_산출물-2E556E?style=flat-square&logoColor=white)](#설계산출물)
[![컨벤션](https://img.shields.io/badge/📋-컨벤션-335E77?style=flat-square&logoColor=white)](#컨벤션)
[![공통 관심사](https://img.shields.io/badge/🌐-공통_관심사-335E77?style=flat-square&logoColor=white)](#공통-관심사)
[![트러블슈팅](https://img.shields.io/badge/🔍-트러블슈팅-2E556E?style=flat-square&logoColor=white)](#트러블슈팅)
[![리팩토링](https://img.shields.io/badge/♻️-리팩토링-294C65?style=flat-square&logoColor=white)](#리팩토링)
[![API 산출](https://img.shields.io/badge/📜-API_산출-24435C?style=flat-square&logoColor=white)](#api-산출)
[![요구사항](https://img.shields.io/badge/📝-요구사항-1F3A53?style=flat-square&logoColor=white)](#요구사항)
[![팀원 소개](https://img.shields.io/badge/👥-팀원_소개-1A314A?style=flat-square&logoColor=white)](#팀원-소개)

---

## 📌 프로젝트 개요  
**MSA로 설계된 B2B 물류 허브 관리 시스템**  
- 허브에 소속된 업체 간 B2B 배송을 관리하는 프로젝트  
- MSA 기반으로 서비스 간 유기적인 연계를 통해 운영됨  

---

## 💻 실행 방법  

### 1️⃣ **프로젝트 클론**  
```bash
git clone https://github.com/takeoff-26/logistics-service.git
```

### 2️⃣ **DB 세팅**  
1. Docker 설치  
2. 프로젝트 루트 디렉터리에서 CLI를 실행하여 아래 명령어 입력  
   ```bash
   docker compose up
   ```
3. `docker ps` 명령어로 컨테이너가 정상적으로 실행되었는지 확인 (`root` 실행 여부 확인)  

### 3️⃣ **플러그인 설치**  
- HTTP Client TEST 실행을 위해 HTTP Client 플러그인을 설치  

### 4️⃣ **애플리케이션 실행**  

---

## 🛠️ 기술 스택  

| **Category**         | **Technology**                                                  |
|----------------------|----------------------------------------------------------------|
| **IDE**             | IntelliJ IDEA                                                  |
| **Language**        | Java 17                                                        |
| **Framework**       | Spring Boot 3.4.2                                              |
| **Database**        | PostgreSQL, H2, Redis                                          |
| **ORM**             | JPA (Jakarta Persistence API)                                  |
| **Query Builder**   | QueryDSL 5.0.0                                                 |
| **Test**            | JUnit, Spring Boot Starter Test, Spring Security Test, WireMock |
| **Containerization**| Docker, Docker Compose                                        |
| **Service Discovery** | Spring Cloud Eureka                                         |
| **Circuit Breaker** | Resilience4j                                                  |
| **Spring Cloud**    | Spring Cloud                                                  |
| **Metric**         | Prometheus                                                     |
| **Monitoring**     | Grafana                                                        |

---

## 📐 설계 산출물  

- **API 문서**: [API 명세](https://teamsparta.notion.site/API-1b42dc3ef51480d5a411d1c15c6ccdc7)  
- **ERD 설계**: [ERD 문서](https://teamsparta.notion.site/ERD-1b52dc3ef51480638c01f87086c52bd8)  
- **아키텍처 문서**: [아키텍처 개요](https://github.com/takeoff-26/logistics-service/wiki/aggregate-map)  
- **애그리게이트 맵**: [Aggregate Map](https://github.com/takeoff-26/logistics-service/wiki/aggregate-map)  

---

## 📋 컨벤션  

- [커밋 메시지 컨벤션](https://github.com/takeoff-26/logistics-service/wiki/commit-message-convent)  
- [Git Flow](https://github.com/takeoff-26/logistics-service/wiki/git%E2%80%90flow)  
- [패키지 구조](https://github.com/takeoff-26/logistics-service/wiki/package-structure)  

---

## 🌐 공통 관심사  

- [MSA 서비스 권한 체크](https://github.com/takeoff-26/logistics-service/wiki/common:-Auditing)  
- [Auditing](https://github.com/takeoff-26/logistics-service/wiki/common:-Auditing)  
- [예외 처리 정책](https://github.com/takeoff-26/logistics-service/wiki/common:-exception-handling-policy)  

---

## 🔍 트러블슈팅  

- [Postgres Lock Timeout](https://github.com/takeoff-26/logistics-service/wiki/Troubleshooting:Lock-Time-Out)  
- [RedisCache 직렬화/역직렬화 문제](https://github.com/takeoff-26/logistics-service/wiki/Troubleshooting:-RedisCache-%EC%A7%81%EB%A0%AC%ED%99%94-%EC%97%AD%EC%A7%81%EB%A0%AC%ED%99%94-%EB%AC%B8%EC%A0%9C)  
- [외부 API 호출 시 보상 트랜잭션 적용](https://github.com/takeoff-26/logistics-service/wiki/Troubleshooting:-%EC%99%B8%EB%B6%80-api-%ED%98%B8%EC%B6%9C%EC%8B%9C-%EC%82%AC%EC%9A%A9%ED%95%98%EB%8A%94-%EB%B3%B4%EC%83%81%ED%8A%B8%EB%9E%9C%EC%9E%AD%EC%85%98)  
- [비관적 LOCK & 낙관적 LOCK](https://github.com/takeoff-26/logistics-service/wiki/Troubleshooting:-%EB%82%99%EA%B4%80%EC%A0%81-LOCK,-%EB%B9%84%EA%B4%80%EC%A0%81-LOCK)  

---

## ♻️ 리팩토링  

- [의존 관계 역방향 제거](https://github.com/takeoff-26/logistics-service/wiki/Refactoring:-%EC%9D%98%EC%A1%B4%EA%B4%80%EA%B3%84-%EC%97%AD%EB%B0%A9%ED%96%A5-%EC%A0%9C%EA%B1%B0)  
- [Spring Circuit Breaker 적용](https://github.com/takeoff-26/logistics-service/wiki/Refactoring:-Spring-Circuit-Breaker-%EC%A0%81%EC%9A%A9)  

---

## 📜 API 산출  

- [API 문서](https://github.com/takeoff-26/logistics-service/wiki/API)  

---

## 📝 요구사항  

### 🧨 도메인 요구사항  
<details>
<summary>공통</summary>
<ul>
  <li>✅ 각 도메인의 CRUD, 검색 기능 적용</li>
  <li>✅ 도메인 주도 설계(DDD) 적용</li>
</ul>
</details>

<details>
<summary>허브 관리</summary>
<ul>
  <li>✅ 허브 정보 캐싱</li>
  <li>✅ 17개 허브 고정 데이터 생성</li>
</ul>
</details>
