# Spring Security 5 OAuth 정리 (클라에서 서버까지)

- SpringSecurity5 OAuth
- SpringSecurity5 이전의 AOuth
- OAuth Client 최소설정 및 주의할 점
- OAuth Server 최소설정 및 주의할 점

--- 

## SpringSecurity 란
- 스프링프레임워크 기반 인증(Authn), 인가(Authz) 프레임워크
- 스프링 기반 애플리케이션에서는 사실상 표준 (de-facto standard)

#### Spring Security 5
- 스프링부터 2.0부터
- 스프링프레임워크 5.0 기반
- 새로운 기능: OAuth 2.0 Login 등

Spring Security 5 OAuth
- http.oauth2.Login() (대표)
    - 한방 사용

--- 

## Spring Security 5 이전의 OAuth
- Spring Security 4 까지 - Spring Security OAuth 별도 모듈 존재
- OAuth 1.0, OAuth 2.0
- OAuth Client, Resource Source, Authorization Server 지원

#### OAuth 2.0 Roles
    - Resource Owner
    - Resource Server
    - Client
    - Authorization Server

#### Spring Security OAuth 불편한점
- 기존 form login 이나 지금의 oath login 과는 코딩스타일이 다르고 확장포인트가 불명확함
- 명시적으로 OAuth 기능을 제공하지 않음

--- 

## Spring Boot OAuth Client 최소설정
- Common OAuth2 Provider: google, GitHub, Facebook, okta
    - Application.properties 에서 id 와 secret 설정으로 구현 가능
- Uncommon provider
    - Client registration 코드 작성 등록
- 최소설정 사용 시, OAuth2AuthorizedClientService bean 기본 구현이 in memory.
    - 실제 환경은 별도 구현 필요함 (서버 여러 대 이거나 하면 문제)
    - Jdbc 등으로
- OAuth Client 주요 확장 포인트
    - authorizationEndpoint, redirectionEndpoint, userInfoEndpoint

---

## Spring Boot OAuth Server 최소설정
- SpringSecurity5 + Spring-authorization-server 라이브러리 추가 사용
- 아직 userEndpoint 구현 안됨. (곧 릴리즈 될 예정임)

---

#### Reference
https://youtu.be/-YbqW-pqt3w