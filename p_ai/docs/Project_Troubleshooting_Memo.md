# Project Troubleshooting Memo

> 본 문서는 프로젝트 회고 중심의 정리 자료이며,
> 구현 중심의 핵심 내용은 포트폴리오 PDF에서 확인하실 수 있습니다.

---

## 1.1 JPA 다대다 매핑 문제 및 해결

**문제**  
JPA에서는 AI_ANSWER와 AI_QUESTION을 직접적으로 1:N 또는 N:1 관계로 매핑하여 사용했으나,  
실사용 중 하나의 답변이 여러 질문에 연결되는 경우가 발생하였고, 이는 다대다(N:N) 구조로 설계되어야 하는 상황임을 인지하게 됨.

하지만 JPA에서는 다대다 직접 매핑(@ManyToMany) 방식이 실무에서 비추천되며,  
직렬화 과정 중 순환 참조에 따른 무한 루프가 발생하고,  
중간 테이블에 추가 정보(등록일, 정렬 우선순위 등)를 넣을 수 없는 구조적 제약이 있었음.

**해결**  
AI_QandA라는 중간 테이블(엔티티)을 도입하여 AI_QUESTION과 AI_ANSWER 간의 다대다 관계를  
명시적인 1:N + N:1 구조로 매핑함. 이를 통해 JSON 직렬화 시 무한 루프 문제를 방지하고,  
데이터 조회 및 삭제 시의 유연성을 확보함. 또한, 중간 테이블에 별도 컬럼 추가도 가능하게 됨.

**얻은 점**  
이 과정을 통해 JPA에서의 엔티티 중심 설계 철학과 MyBatis의 쿼리 기반 접근 방식의 차이를 실감하였으며,  
실무에서 흔히 발생할 수 있는 다대다 매핑, 순환 참조, 직렬화 이슈에 대한 대응 역량을 기를 수 있었음.

## 1.2 Spring Security 필터 구조와 JWT 인증 누락과 예외 경로 처리 누락

---

### 문제

Spring Security 필터 체인을 구성할 때, `csrf().disable()` 설정이나 CORS 관련 정책 같은 기초 보안 개념에 대한 이해 부족 때문에 인증 관련 에러가 발생함.  
JWT 기반의 Stateless 인증 구조에 익숙하지 않은 상태에서, Spring Security의 기본 세션 기반 구조와 충돌이 발생함.

또한, JWT 인증 시 모든 요청을 인증 대상으로 처리하여, 로그인/회원가입 등 인증이 필요 없는 경로에서도 403 Forbidden 오류가 발생함.  
이는 `doFilterInternal()` 내부에서 인증 예외 경로를 고려하지 않은 채 모든 요청에 인증을 강제한 설계 때문이었음.

### 해결

- CSRF 검증은 JWT 환경에선 불필요하므로, `csrf().disable()` 적용
- CORS 정책에서 Authorization 헤더와 Origin 관련 설정을 명확히 지정하여, 프론트와 통신이 끊기지 않도록 조정
- `sessionCreationPolicy(STATELESS)`로 설정하여 세션 없이도 인증 동작하도록 구조를 재정비
- JWT 필터에서 인증이 필요 없는 경로를 별도 분기 처리하여, 해당 요청은 `SecurityContextHolder` 설정 없이 통과하도록 수정

### 얻은 점

- Spring Security는 기본적으로 세션 기반이며, JWT 방식과 함께 쓰려면 필터 구조와 보안 정책을 전환해야 함을 실감
- 예외 경로 처리가 누락되면 전체 API 사용 흐름이 막힐 수 있으며, 코드 레벨에서의 세밀한 분기 처리가 필요함을 체득
- 보안 설정 또한 아키텍처 설계와 함께 고려해야 할 중요한 요소임을 명확히 인식하게 됨

## 1.3 WebSocket 인증 및 SecurityContext 연동

---

### 문제

- WebSocket을 통한 실시간 질문-답변 시스템 구현 과정에서, 클라이언트의 구독(Subscribe) 및 메시지 전송(Send) 시점에 JWT 인증 처리가 누락되어 있었음.

- 이로 인해 비인증 사용자의 메시지 접근이 가능하거나, 혹은 인증 정보가 없는 경우 WebSocket 연결 또는 송수신 중 예외 발생 문제가 나타났음.

---

### 해결

- `BeforeHandshake` 및 `preSend` 인터셉터에서 JWT를 검증하고, 유효한 토큰을 가진 사용자만 구독 및 송수신이 가능하도록 인증 흐름을 구성함.

- `SecurityContext`에 인증 정보를 세팅한 후, `Principal`로 참조하여 인증된 사용자 기반의 메시지 송수신이 가능하도록 연동함.

---

### 얻은 점

- WebSocket의 핸드셰이크(Handshake) 및 메시지 채널의 흐름을 이해하고, 각 단계에 인터셉터를 활용한 인증 구조 설계 경험을 쌓음.

- JWT 기반 인증을 WebSocket 흐름에 안전하게 결합하면서, 실시간 통신에서의 보안 고려사항과 Spring Security 확장법을 실전에서 체득함.

## 1.4 JWT 토큰 인증 실패 및 SecurityContext 미연동

---

### 문제

- 사용자 이름을 기반으로 JWT를 생성하여, 보안이 강화된 인증 방식을 적용. Stateless 환경에서 최적화된 인증을 구성했으나,

- JWT 검증 후 인증 객체(Authentication)를 생성했음에도, `SecurityContextHolder`에 등록하지 않아 Spring Security 전반에서 인증된 사용자로 인식되지 않는 문제가 발생함.

---

### 해결

- 토큰 검증 후, `UsernamePasswordAuthenticationToken`을 생성한 뒤, `SecurityContextHolder.getContext().setAuthentication(...)`을 통해 인증 객체를 명시적으로 설정하여 문제를 해결함.

---

### 얻은 점

- JWT는 세션이 없기 때문에, 인증 객체를 명시적으로 등록하지 않으면 사용자 인증이 무효 처리됨.

- Spring Security는 기본적으로 `SecurityContextHolder`에 저장된 객체를 기준으로 인증 여부를 판단하므로, 필터 내 인증 객체 등록은 필수임을 확실히 이해하게 됨.

## 2.1 useEffect 타이밍 문제

---

### 문제

WebSocket + 인증 구조 설계 초기에, 클라이언트에서 JWT 유효성 검사를 수행하는 과정에서 다음과 같은 문제를 겪음:

- `validateToken()` 함수가 여러 번 실행되어 중복 인증 시도 발생
- 인증 시도 중 WebSocket 연결이 지연되거나 403 오류 발생
- `isAuthenticated` 상태가 불안정하게 반영되어 WebSocket 연결 흐름과 인증 흐름 간의 타이밍 불일치 발생
- 이로 인해 인증 여부에 따라 메시지를 수신해야 하는 로직이 정상 동작하지 않음

---

### 해결

- `useCallback()`으로 `validateToken` 함수를 메모이제이션하여, 불필요한 재생성을 방지하고 `useEffect()` 재실행을 제어함
- `isAuthenticating` 이라는 플래그 변수를 도입해 중복 인증 요청을 방지하고, 비동기 흐름의 안정성을 확보함
- `validateToken()` 내부에서 정상 응답 여부에 따라 `authenticate()` 실행. 인증 실패 시만 새로운 인증 시도하도록 하여 인증 흐름 안정화함

---

### 얻은 점

- React에서의 인증 흐름은 상태와 타이밍 관리가 중요하다는 것을 체감함
- 특히 WebSocket처럼 실시간 연결이 필요한 구조에서는 인증 흐름이 조금만 어긋나도 전체 시스템의 동작이 불안정해질 수 있음을 경험함
- `useEffect()`만으로는 비동기 인증 흐름을 안정적으로 제어하기 어렵고, `useCallback()` 및 제어 플래그 등을 통한 흐름 제어가 필요함을 깨달음
- 이 경험을 통해, 인증은 성공 여부가 아니라 시스템 전체 흐름과의 정합성이 더 중요하다는 것을 체감함
- 결과적으로, 실시간 시스템에서는 상태 관리가 핵심이라는 설계적 감각을 얻었고, 이는 향후 React 기반 상태 관리나 API 연동에 있어서 큰 자산이 될 수 있음을 느꼈음

## 2.2 JWT 토큰 상태 전달 및 localStorage 의존 제거

---

### 문제

- API 요청 시 인증 토큰을 `localStorage.getItem("jwt")`로 직접 꺼내 사용했으나, React 렌더링 및 JWT 갱신 타이밍에 따라 비어 있는 값이 전달되는 문제 발생
- JWT가 초기화되지 않은 시점에 API 요청이 먼저 실행됨
- 서버에서는 `Bearer undefined`로 인식하여 인증 실패
- 즉, 인증 상태를 컴포넌트에서 관리하지 않고, 브라우저 저장소에 직접 접근하면서 React 상태 관리 흐름과 분리된 것이 문제의 핵심

---

### 해결

- `jwt`를 `useState` 또는 `useRef`를 통해 직접 관리하고, 초기 렌더링 이전에 `validateToken`으로 토큰을 갱신하거나 확보한 뒤  
  해당 값을 `props`를 통해 직접 API 요청에 주입하도록 수정함

---

### 얻은 점

- 인증 토큰과 같이 민감하고 상태 의존적인 값은 브라우저 저장소 직접 접근보다 상태로 관리하는 것이 안정적임
- React의 비동기 렌더링 흐름에 맞추어, 토큰 상태가 확보된 이후에만 API 요청이 실행되도록 제어하는 방식이 더 안전함
- 실제 배포 환경에서도 `localStorage`는 CSRF 우려가 있으므로, 토큰을 HttpOnly 쿠키나 상태 기반으로 관리할 수 있도록 설계 여지를 확보해야 함

## 2.3 WebSocket 자동 재연결 문제

---

### 문제

- WebSocket 연결 실패 시 `setTimeout`을 활용한 **재연결 로직(`handleReconnect`)**을 구현했으나, 다음과 같은 문제 발생:
  - 불필요한 재연결 시도 또는 중복 연결 가능성 존재
  - 재연결 타이밍을 조절하더라도 JWT 토큰 만료, 서버 측 인증 오류 등의 근본 원인은 해결되지 않음

---

### 해결

- 실패 원인이 주로 네트워크 단절보다는 JWT 토큰 유효성 또는 handshake 조건 미충족에서 발생했기 때문에,  
  단순 재연결보다 초기 연결 조건 보장이 더 중요하다고 판단함
- 이에 따라 재연결 로직은 삭제하고, `connectWebSocket()` 호출 조건과 `token` 상태 검증 로직을 통해  
  초기 연결의 신뢰성을 높이는 방향으로 구조 개선

## 2.4 reduce 병합 중복 발생 및 상태 정합성

---

### 문제

- API에서 받은 데이터를 가공 없이 그대로 화면에 반영하였더니,  
  같은 질문이 여러 번 나타나거나, 질문과 답변이 제대로 묶이지 않는 문제가 발생함
- `push`로 단순 배열 처리한 것이 원인으로, `questionId` 기준 중복 제거 처리가 미흡했음

---

### 해결

- `reduce`를 통해 데이터를 처리하면서, `acc.find()`로 이미 동일한 `questionId`가 있는지를 확인하고,  
  기존 항목이 존재할 경우 해당 질문의 `answers` 배열에 답변을 추가
- 없으면 새롭게 항목을 만들어 넣는 방식으로 중복 없이 일관된 질문-답변 구조를 구성함

---

### 얻은 점

- 기존에는 Thymeleaf 기반 SSR 방식처럼, 질문과 답변 데이터를 서버에서 묶어 순차적으로 출력하면 된다고 생각했지만,  
  React 환경에서는 질문과 답변이 비동기적으로 분리된 형태로 수신되기 때문에,  
  클라이언트 측에서 데이터를 직접 병합하고 정렬하는 로직이 필요하다는 점을 경험함
- 특히 `questionId`와 같은 고유 식별자를 기준으로 병합 처리하지 않으면,  
  동일한 질문이 중복 렌더링되거나 일부 답변이 누락되는 등의 문제가 발생할 수 있음을 확인함
- 또한 상태를 업데이트할 때 단순히 배열에 새 데이터를 덧붙이는 방식(`setState([...prev, new])`)은  
  중복 발생이나 상태 불일치의 원인이 될 수 있음을 깨달았고,  
  ID 기반 병합 처리와 같은 로직이 함께 설계돼야 안정적인 상태 관리가 가능하다는 점을 학습함

## 3.1 실시간 학습 구조의 데이터 오염 문제

---

### 문제

초기에는 `AI_QUESTION`과 `AI_ANSWER`에 저장된 실시간 사용자 대화 내용을  
그대로 학습 데이터로 사용하는 방식을 시도했음.  
하지만 이 방식에서는 질문과 무관한 답변이 그대로 학습되는 등 데이터 오염 문제가 자주 발생했고,  
수동으로 학습 데이터를 가공하더라도 정합성 확보에 많은 추가 작업이 필요했음.

---

### 해결

운영 데이터와 분리된 **학습 전용 테이블셋**을 별도로 구성함.  
관리자가 직접 검수하거나 조건에 따라 자동 추출하여 사용할 수 있도록 설계하였고,  
AI 모델 학습 시에는 이 테이블만 참조하도록 구조를 분리함.

---

### 얻은 점

- 학습 데이터 관리 및 정합성 확보가 훨씬 쉬워졌고,
- 실제 운영 데이터는 그대로 유지하면서, 학습에 적합한 품질을 따로 관리할 수 있는 구조를 갖추게 되었음
- AI 학습 구조도 초기에 별도 설계하는 것이 훨씬 효율적이라는 걸 실감함

## 3.2 Flask 구조 개선 (모델 응답/학습 분리)

---

### 문제

Flask API 내부에서는  
`Fetch Data → Train Model → Get Answers`가 하나의 흐름으로 묶여 있었고,  
이로 인해 학습과 응답이 동시에 이루어지며 데이터 오염 뿐 아니라 서버 부하 우려 및
구조상의 불안정함 또한 존재하였음.

---

### 해결

- Flask API 내부 구조를 다음과 같이 리팩토링:

  - 기존:  
    `Flask → Fetch Data → Train Model → Get Answers`

  - 개선:  
    `Flask API → GetAnswer`,  
    `Fetch Data → Train Model` 로 분리

이를 통해 학습과 응답 로직을 명확히 분리하고,  
학습 데이터와 응답 흐름이 서로 영향을 주지 않도록 구조화함.

- 최초 API 흐름을 다음과 같이 명확하게 역할 분리하였기에 원인 파악 및 흐름 개선이 쉬웠음 :

  - Client = 실시간 데이터 반영
  - Spring Boot = 데이터베이스 처리 및 JWT 토큰 발급, 비즈니스 로직 처리, 각 API 중계
  - Flask API = 머신러닝 모델 학습 및 서빙

---

### 얻은 점

- 단일 클래스 책임뿐만 아니라 **API 단위에서도 단일 책임 원칙(SRP)** 을 적용하는 것이  
  유지보수성과 확장성에 큰 도움이 됨을 체감함
- 추후 모델 서빙 경로와 학습 로직의 교체, 마이그레이션, 배포 자동화 등을 고려할 때  
  초기 설계에서의 책임 분리가 구조적 유연성을 제공함을 실감함

## 3.3 Pickle 기반 모델 구조와 정합성 확보

---

### 문제

- 배포된 포트폴리오 서비스에서, AI 학습 데이터의 제한으로 인해 사용자가 기대하지 않은 부정확한 답변을 받는 문제가 발생할 수 있었음.
- 이는 서비스에 대한 신뢰도 저하 및 사용자 혼란을 초래할 수 있는 UX 측면의 리스크였음.

---

### 해결

- 실제 학습된 질문/답변 쌍을 사용자에게 보여주는 **"학습 질문 리스트" UI** 를 React 화면에 추가함
- 또한, AI의 한계와 사용 방법을 안내하는 **사용자 가이드**를 함께 표시하여,  
  사용자의 기대치를 명확히 조정하고 편의성을 개선함

---

### 얻은 점

- **UX 설계의 중요성**을 실감함. 단순히 기능 구현을 넘어서, 사용자가 예측 가능한 결과를 받도록 유도하는 것이 중요함
- 기술적인 완성도뿐 아니라, **사용자 신뢰도와 서비스 목적 전달** 측면에서도 초기 설계가 중요함을 학습함
- 작은 UX 개선이 전체 사용자 경험에 큰 영향을 줄 수 있음을 체감함

## 4.1 WebSocket + NGINX 연결 실패 원인

---

### 문제

- WebSocket 연결을 위한 `/chat-ws/` 경로에서 연결이 지속적으로 실패함
- 원인은 NGINX 설정에서 `proxy_http_version 1.1`, `Upgrade`, `Connection` 헤더가 누락되어 있었기 때문
- 이로 인해 WebSocket Handshake가 정상적으로 이루어지지 않음

---

### 해결

- NGINX 설정 파일에 다음 항목을 명시적으로 추가하여 문제 해결
  - `proxy_http_version 1.1`
  - `proxy_set_header Upgrade $http_upgrade;`
  - `proxy_set_header Connection "upgrade";`
- 클라이언트 측에서도 WebSocket 연결 시 정확한 경로(`/chat-ws`)와 함께 JWT 포함 방식으로 구성

---

### 얻은 점

- WebSocket은 일반 HTTP 요청과 달리, **지속적인 연결을 위한 별도 설정**이 필요함
- 특히 **NGINX와 같은 프록시 서버**가 중간에 있을 경우, `Upgrade` 헤더가 전달되지 않으면 연결 자체가 실패할 수 있음
- 실시간 기능 구현 시, 서버 프록시 설정까지 함께 고려해야 한다는 점을 실전에서 체감함

## 4.2 CloudFront 라우팅 충돌 (S3 vs API)

---

### 문제

- 배포된 포트폴리오 서비스에서 React는 S3에서 정적 파일을 로딩하고, Spring Boot 및 Flask API는 EC2에서 동작함
- 하지만 CloudFront의 원본(origin) 설정이 하나로 통합되어 있어, 모든 요청이 S3로 전달되는 구조였음
- 그 결과, `/api/*`, `/chat-ts/*` 경로의 요청도 정적 파일 서버로 전달되며 CORS 정책 및 인증 오류 발생

---

### 해결

- CloudFront의 원본을 다음과 같이 분리함:
  - `/` 경로는 S3를 기본 원본으로 설정하여 정적 리소스를 제공
  - `/api/*`, `/chat-ts/*` 경로는 EC2 원본으로 지정하여 동적 API 요청을 별도 처리
- 각 원본에 대해 HTTPS 설정 및 헤더 처리를 독립적으로 구성하여 라우팅 충돌을 방지

---

### 얻은 점

- SPA 구조에서는 정적 리소스와 동적 API 요청을 물리적으로 분리하고, CloudFront의 원본 매핑을 명확히 해야 안정적인 배포가 가능함을 체감
- API 호출 시 CORS 및 인증 오류는 단순한 백엔드 문제만이 아니라, CDN이나 라우팅 구조에서도 발생할 수 있음을 학습
- 실제 경험을 통해, 클라이언트와 서버의 요청 흐름을 분리 설계하고 이를 인프라 설정으로 정확히 연결하는 능력을 강화함

## 4.3 CloudFront 경로 우선순위 문제

---

### 문제

- CloudFront의 기본값(_) 경로가 상위 우선순위를 가지는 /api/_, /chat-ws/\*보다 먼저 매칭되면서, 모든 요청이 S3 정적 리소스로 전달되는 문제가 발생함.
- 이로 인해 REST API와 WebSocket 요청이 EC2가 아닌 S3로 라우팅되며 403, 404 오류가 빈번히 발생

---

### 해결

- CloudFront의 경로 설정에서 /api/_, /chat-ws/_, /.well-known/acme-challenge/ 등 동적 요청 경로에 대해 우선순위를 명시적으로 상위로 지정
- CloudFront 기본값(\*) 경로는 정적 리소스 용도로 최하위 우선순위로 설정하여 충돌 방지
- 각 경로에 맞는 원본(origin) 지정(S3 또는 EC2)을 통해 요청이 정확히 전달되도록 구성

---

### 얻은 점

- CloudFront 경로 패턴은 시각적 정렬이 아닌, 실제 라우팅 우선순위로 작동하며 이는 API 통신 안정성에 직접적인 영향을 준다는 점을 체감

## 4.4 EC2 보안그룹 인바운드 설정 누락

---

### 문제

- EC2에 배포된 Flask 및 Spring Boot 서버에 대해 CloudFront에서 요청 시 응답이 도달하지 않음
- EC2 인바운드 규칙에서 해당 포트가 개방되어 있지 않아서 외부 접근이 차단된 문제
- 특히 React와 정적 리소스는 정상적으로 로드되었으나, API 및 WebSocket 요청만 실패하는 현상이 발생

---

### 해결

- EC2 인바운드 규칙에 대해 필요한 포트(HTTP: 80, HTTPS: 443, WebSocket: 8080 등)를 명시적으로 개방
- 관리자 IP 및 직접 사용하는 포트에 한해서만 제한된 범위 내에서만 허용
- 포트 별 역할 (예: Spring Boot 8080, Flask 5000 등)을 주석 처리하여 구분하고 불필요한 포트는 차단함

---

### 얻은 점

- AWS EC2에서의 보안 그룹 설정은 방화벽 역할을 하며, 서버가 동작하더라도 포트가 닫혀 있으면 외부 요청은 무조건 차단됨을 실무적으로 체감
- 보안을 위해 모든 포트를 열기보다는 필요한 포트만 최소한으로 열고, IP 범위도 제한하는 것이 운영 안정성과 보안 모두에 유리함을 경험함
- 인프라/보안 설정이 애플리케이션 동작과 직결된다는 점을 실전에서 학습함

## 4.5 HTTPS 인증 실패 – 인증 경로 누락

---

### 문제

Let's Encrypt로 HTTPS 인증서를 발급받는 과정에서, 인증 요청 경로인 `.well-known/acme-challenge/*`가  
CloudFront 설정에 빠져 있어서 문제가 생김.  
요청이 EC2 서버로 가야 하는데, 정적 리소스를 호스팅하고 있는 S3로 전달되면서 404 오류가 났고, 인증서 발급이 실패.

---

### 해결

CloudFront 경로 설정에 `.well-known/acme-challenge/*`를 별도로 등록하고,  
이 경로의 원본을 EC2로 지정하여 인증 요청이 제대로 서버까지 도달, 인증 발급이 정상적으로 처리됨.

---

### 얻은 점

- 처음엔 인증은 단순히 서버에서만 처리되는 문제라고 생각했는데,  
  CloudFront나 CDN 경로 설정도 영향을 준다는 걸 알게 됨.
- 클라우드 환경에서는 인증 요청이 어떤 경로로 오고 가는지도 잘 설정해줘야  
  자동 발급이나 갱신이 문제 없이 돌아간다는 걸 직접 겪으며 배움.

## 4.6 CloudFront 정적/동적 경로 분리

---

### 문제

- CloudFront를 통해 프론트엔드(React)와 백엔드(Spring Boot 및 Flask API) 배포 후,  
  React에서 다른 API 호출 시 인증 오류 발생.

- 이는 CloudFront의 원본 대상이 명확히 분리되지 않아,  
  정적 리소스 요청과 API 요청이 동일한 원본으로 라우팅되는 구조에서 발생.

- 특히 정적 리소스는 S3에서 제공하고, API는 EC2에서 서비스되지만,  
  원본 설정이 하나로 통합되어 있어 CORS 정책 및 인증 헤더 전달이 정상적으로 처리되지 않음.

---

### 해결

- CloudFront의 원본 설정을 2개로 분리하여 구성함.

  - `/` 경로의 기본 요청은 S3 정적 리소스를 참조하도록 설정.
  - `/api/*`, `/chat-ts/*` 경로는 EC2 원본을 참조하도록 명시적으로 매핑.

- 원본마다 HTTPS, 헤더 처리, 엑세스 권한을 적절히 분리하여  
  동작 신뢰성을 높임.

---

### 얻은 점

- 클라이언트와 서버가 분리된 SPA 구조에서는 정적 파일과 동적 API를 각각 다른 원본으로 명확히 분리해야  
  CloudFront의 라우팅이 안정적으로 작동함을 체감.

- API 호출 시 CORS 및 인증 오류는 단순한 백엔드 문제가 아닌,  
  CloudFront 원본 설정 및 경로 매핑 구조에 따라서도 발생할 수 있음을 알게 되었음.

- 이 경험을 통해 단순히 배포를 넘어서,  
  정적 파일과 API 요청을 분리하여 각기 다른 서버로 효율적으로 라우팅하는 구조를 설계하고 적용한 경험을 얻게 되었음.  
  이를 통해 CDN과 API Gateway 개념을 실무에 직접 적용하는 감각을 체득함.

## 4.7 CloudFront 경로 우선순위 문제

---

### 문제

- CloudFront의 기본값(_) 경로가 상위 우선순위를 가지는 `/api/_`, `/chat-ws/\*`보다 먼저 매칭되면서,  
  모든 요청이 S3 정적 리소스로 전달되는 문제가 발생함.

- 이로 인해 REST API와 WebSocket 요청이 EC2가 아닌 S3로 라우팅되며 403, 404 오류가 빈번히 발생.

---

### 해결

- CloudFront의 경로 설정에서 `/api/*`, `/chat-ws/*`, `/.well-known/acme-challenge/` 등  
  동적 요청 경로에 대해 우선순위를 상위로 지정.

- CloudFront 기본값(\*) 경로는 정적 리소스 용도로 최하위 우선순위로 설정하여 충돌 방지.

- 각 경로에 맞는 원본(origin) 지정(S3 또는 EC2)을 통해 요청이 정확히 전달되도록 구성.

---

### 얻은 점

- CloudFront 경로 패턴은 시각적 정렬이 아닌, 실제 라우팅 우선순위로 작동하며  
  이는 API 통신 안정성에 직접적인 영향을 준다는 점을 체감.

## 4.8 NGINX 헤더/라우팅 누락

---

### 문제

- WebSocket과 REST API 요청이 모두 실패하거나 잘못된 응답을 반환하는 문제가 발생함.
- 이는 엔드포인트 라우팅에 대한 개념이 부족한 상태에서 NGINX를 프록시 서버로 구성하면서,  
  각 요청 경로(`/chat-ws`, `/api`)에 대한 개별 `location` 설정을 하지 않았고,  
  NGINX가 요청을 올바른 백엔드로 전달하지 못하는 상황이었음.

---

### 해결

- CloudFront와 통신하는 주요 엔드포인트(`/chat-ws`, `/api`)에 대해 각각 `location` 블록을 정의하고,  
  내부 포트로 정확히 프록시 전달되도록 `proxy_pass`, 헤더 전달 등의 설정을 보완함.

---

### 얻은 점

- 배포 환경에서는 단순히 NGINX를 프록시 서버로서 두는 것만으로는 충분하지 않으며,  
  요청이 정확한 엔드포인트로 전달되도록 라우팅을 명시적으로 설정해야 함을 실전에서 체감.

- 특히 프론트와 백엔드가 도메인 또는 경로 기준으로 분리된 구조에서는,  
  각 경로별 세분화된 프록시 설정이 API 동작의 핵심이 될 수 있음을 학습함.

## 4.9 EC2 보안그룹 인바운드 설정 누락

---

### 문제

- EC2에 배포된 Flask 및 Spring Boot 서버에 대해 CloudFront에서 요청 시 응답이 도달하지 않음.
- EC2 인바운드 규칙에서 해당 포트가 개방되어 있지 않아서 외부 접근이 차단된 문제.
- 특히 React와 정적 리소스는 정상적으로 로드되었으나, API 및 WebSocket 요청만 실패하는 현상이 발생.

---

### 해결

- EC2 인바운드 규칙에 대해 필요한 포트(HTTP: 80, HTTPS: 443, WebSocket: 8080 등)를 명시적으로 개방.
- 관리자 IP 및 직접 사용하는 포트에 한해서만 제한된 범위 내에서만 허용.
- 포트 별 역할 (예 : Spring Boot 8080, Flask 5000 등)을 주석 처리하여 구분하고 불필요한 포트는 차단함.

---

### 얻은 점

- AWS EC2에서의 보안 그룹 설정은 방화벽 역할을 하며, 서버가 동작하더라도 포트가 닫혀 있으면 외부 요청은 무조건 차단됨을 실무적으로 체감.
- 보안을 위해 모든 포트를 열기보다는 필요한 포트만 최소한으로 열고, IP 범위도 제한하는 것이 운영 안정성과 보안 모두에 유리함을 경험함.
- 인프라/보안 설정이 애플리케이션 동작과 직결된다는 점을 실전에서 학습함.

## 5.1 Jenkins 디스크 공간 부족 및 자동 삭제 정책

---

### 문제

- Jenkins에서 AWS 연동 전, Nexus 업로드까지 반복 테스트를 수행하던 중,
  시스템 로그에서 `Only 0.034 Gb free on (controller)` 메시지와 함께 `Execution aborted` 경고가 발생함.
- 당시 빌드 버전은 0.0.7까지로 많지 않았으나, 빌드 캐시, 로그, 워크스페이스의 누적 등으로 인해
  예상보다 빠르게 디스크 사용량이 증가한 것으로 판단됨.

---

### 해결

- Build Discarder 설정을 적용하여, 오래된 빌드는 일정 개수 이상 자동 삭제되도록 구성함.
- 필요 시 수동 정리를 병행하여 디스크 여유 공간 확보함.
- 추후 AWS 연동 및 EC2 배포 시, EBS 볼륨 크기 및 디스크 모니터링 방안을 함께 고려할 계획임.

---

### 얻은 점

- 빌드 횟수와는 별개로 Jenkins는 각 빌드에 대한 로그, 캐시, 워크스페이스 등을 자동으로 누적하므로,
  장기적인 운영 시 디스크 사용량 관리가 필수임을 실감함.
- CI 시스템의 운영 안정성을 위해, **디스크 공간 부족은 예방보다 감시와 주기적 정리가 더 중요함**을 체득함.

## 5.2 동일 버전 재배포 실패 → redeploy 허용

---

### 문제

- 테스트 반복 중 같은 버전(예: `0.0.7`)으로 Nexus에 다시 배포하려 했으나
- Nexus 정책상 중복 버전 배포가 차단되어 있었음

---

### 해결

- Nexus Repository 설정에서 `Allow redeploy` 옵션을 활성화

---

### 얻은 점

- 테스트 및 반복 배포 흐름에서 발생할 수 있는 실 배포 실패를 예방
- 빌드 자동화 과정에서 버전 관리 유연성을 확보하여
  CI/CD 파이프라인의 테스트 효율성을 높임

## 5.3 자동 빌드 후 변경사항 미반영 → clean build publish 적용

---

### 문제 상황

- Jenkins에서 자동 빌드 후 Nexus에 업로드는 되었으나
- 반영된 결과물에는 변경 사항이 적용되지 않아 디버깅이 필요했음

---

### 해결 방법

- Jenkins Build Steps에 `clean build publish` 명령어 추가
- 항상 새로운 아티팩트를 생성 및 업로드하도록 강제함

---

### 얻은 점

- Gradle의 캐시 및 증분 빌드로 인해 발생할 수 있는 의도치 않은 재사용을 차단하여
  안정적인 최신 빌드 결과를 보장함

## 5.4 운영 리스크 인지 및 사전 대응 사례: Git Credential 누락 방지

---

### 문제 예방 배경

- SSH 방식으로 Git 저장소를 연동할 때, 인증 키 설정이 누락되면 `Permission denied (publickey)` 오류가 발생할 가능성이 있음
- Jenkins Job을 생성하거나 GitHub에서 소스코드를 클론하는 단계에서 Credential 누락 시 자동 빌드가 실패할 수 있음

---

### 적용한 조치

- Jenkins Job 생성 시 SSH Key 기반 Credential을 처음부터 명시적으로 등록하여 누락을 방지함
- Git 연동을 요구하는 모든 Job에 대해 인증 정보를 사전에 지정하고, 공유 저장소가 아닌 개인 저장소와 연동할 경우에도 별도 Credential 등록을 기준으로 설정함

---

### 얻은 점

- 배포 실패를 사전에 방지하고, CI 환경에서 Git 인증 누락으로 인한 오류 발생 가능성을 사전에 차단함
- Jenkins에서의 Credential 관리는 단순 편의 설정이 아닌, 시스템 안정성에 직접 영향을 미치는 핵심 요소임을 인식하게 됨

## 5.5 운영 리스크 인지 및 사전 대응 사례: SMTP 실패 알림 설정

---

### 문제 예방 배경

SMTP 설정 오류로 인해 빌드 실패 알림이 전송되지 않으면, 장애 상황을 실시간으로 인지하지 못할 위험이 존재함.

---

### 적용한 조치

- SMTP 서버 설정 시, 인증 및 SSL 사용을 명시적으로 지정하고  
  Gmail SMTP(465) 포트를 활용하여 보안성과 안정성을 확보함.
- 빌드 실패 시, 관리자에게 자동 이메일 알림이 전송되도록 구성함.

---

### 얻은 점

- CI/CD는 단순히 자동화된 빌드와 배포의 흐름만 설계하는 것이 아니라,  
  운영 이후 발생할 수 있는 장애와 리스크까지 고려한 설정이 중요하다는 점을 상기.

## 5.6 운영 리스크 인지 및 사전 대응 사례: Jenkins 권한 미흡 및 SSH 보안

---

### 문제 예방 배경

- Jenkins에서 SSH를 활용한 원격 배포 Job을 구성했지만, Anonymous 및 Authenticated Users에게 일부 권한(read 등)들이 열려 있는 상태였음.
- 이로 인해 외부에서 로그인만 가능한 사용자가 Job의 설정 내용을 열람하거나, 설정 내용을 기반으로 유사 Job을 수동 복제하는 시도 가능성이 존재.
- Jenkins의 기본 Credential에 등록된 SSH 인증 정보가 스크립트 내에 포함되어 노출 위험이 있음.

---

### 적용한 조치

- Anonymous 및 Authenticated Users 그룹의 권한을 모두 제한함.
- **기본 Credential 사용 대신 Job 별 Credential을 분리하여 관리함.**

---

### 얻은 점

- Jenkins와 같은 CI 도구는 기본 보안 설정이 허술할 수 있으므로, 초기 세팅 단계부터 사용자별 권한을 명확히 지정하는 것이 중요함을 상기.
- 민감한 Job이나 배포 로직이 포함된 환경에서는 **최소 권한 원칙**을 적용하는 것이 보안을 지키는 기본임을 학습함.

## 6.1 SSR → CSR 마이그레이션: Thymeleaf에서 React로의 전환

---

### 문제

- 초기에는 Thymeleaf 기반의 SSR(Server-Side Rendering)을 사용하여 프론트엔드를 구성했음.
- 하지만 배포 환경에서의 자원 관리, 프론트와 백엔드 간의 책임 분리, UX 향상 및 확장성을 고려할 필요가 있었음.

---

### 해결

- React 기반의 CSR(Client-Side Rendering)로 프론트엔드를 마이그레이션함.
- WebSocket 기반 실시간 데이터 갱신 기능과 REST API 호출 구조도 함께 반영하여 구조를 개선함.

---

### 얻은 점

- 프론트엔드 마이그레이션을 직접 경험하며, SSR과 CSR의 차이점과 각 구조의 장단점을 실감함.
- 특히 CSR 기반 구조는 유지보수성과 사용자 경험(UX) 측면에서 큰 이점을 제공함을 이해함.
- 또한, 백엔드와 프론트엔드를 독립적으로 배포 및 관리할 수 있는 구조로 전환됨에 따라 운영 유연성과 확장성이 크게 향상됨.

## 6.2 배포 구조 개선 및 보안 강화

---

### 문제

- 초기에는 모든 요소(React, Spring Boot, Flask)를 단일 EC2에 배포하려고 했으나, 다음과 같은 문제가 발생함:
  - 서버 부하 집중 및 운영 관리의 복잡성 증가
  - RDS 퍼블릭 IP 직접 접근으로 인한 보안 및 비용 이슈
  - HTTP 배포로 인한 보안 취약점

---

### 해결

- 배포 구조를 다음과 같이 개선함:
  - React 정적 파일은 S3에 업로드하고 CloudFront를 통해 분리 배포
  - Spring Boot와 Flask는 EC2에 배포하여 API 서버로 구성
  - RDS는 퍼블릭 IP 대신 EC2 경유 터널링 방식으로 접속
  - HTTP에서 HTTPS로 전환하여 보안 및 브라우저 호환성 확보

---

### 얻은 점

- S3 + CloudFront를 통한 정적 파일 분리는 성능 최적화와 운영 편의성 측면에서 효과적임을 확인함
- EC2와 RDS 간 터널링 구조를 통해 보안 강화 및 VPC 요금 절감을 동시에 달성
- HTTPS 적용을 통해 브라우저 보안 정책을 만족시키고 사용자 신뢰도 향상에 기여함
- 배포 구조 설계가 단순한 기술 선택이 아닌, 운영 효율성과 보안 정책을 반영한 전략적 선택임을 실감함
