# HTTP 메시지에 관하여.. (HTTP/1.1 기준)

- 기본적인 HTTP 메시지의 구조는  
  - `메시지 헤더`, `개행문자`, `메시지 바디` 로 만들어진다.
  

- 크게 Request, Response 메시지로 나눌 수 있다.
- 요청과 응답 시 헤더의 차이가 나는데,
  - 요청 시: 리퀘스트 라인, 리퀘스트 헤더필드, 일반 헤더필드, 엔티티 헤더필드
  - 응답 시: 상태 라인, 리스폰스 헤더필드, 일반 헤더필드, 엔티티 헤더필드

---

#### 메시지 바디와 엔티티 바디의 차이점은?
 - 기본적으로 HTTP 메시지 바디의 역할은 리퀘스트와 리스폰스에 관한 엔티티 바디를 운반하는 일이다.
 - 따라서 기본적으로 메시지 바디와 엔티티 바디는 같지만, 전송 코딩이 적용되는 경우에 엔티티 바디의 내용이 변화하기 때문에 메시지 바디와 달라지게 된다.
   - 메시지 바디 안에 엔티티 바디가 있다고 이해하면 될 듯 하다.
   - 인코딩된 엔티티는 클라 쪽에서 디코딩해서 사용함

---

#### 메시지 바디의 엔티티를 콘텐츠 코딩하는 콘텐츠 압축의 종류는?
- gzip(GNU zip)
- compress(UNIX의 표준 압축)
- deflate(zlib)
- identity(인코딩 없음)

---

#### 최적의 콘텐츠를 내려주기 위한 콘텐츠 네고시에이션이란? (Content Negotiation)
- 같은 콘텐츠 이지만 여러 개의 페이지를 지닌 웹페이지 등의 경우, 예를 들면 같은 내용에 영어판, 한국어판 처럼 같이 표시되는 언어가 다른 경우
  - 주로 사용하는 브라우저가 같은 URI 에 액세스 할 때 적절하게 웹페이지를 표시한다.
- 제공 기준으로는, 제공하는 리소스를 언어와 문자셋, 인코딩 방식 등 으로 한다.
  - Accept
  - Accept-Charset
  - Accept-Encoding
  - Accept-Language
  - Content-Language
  
- Server-driven Negotiation
  - 서버 측에서 요청 헤더필드를 참고해서 처리 (브라우저가 보낸 정보 기반이라서 유저에게 정말 적절한지 모름) 
- Agent-driven Negotiation
  - 클라이언트에서 브라우저에 표시된 선택지 중에서 유저가 수동으로 선택
- Transparent Negotiation
  - 서버와 에이전트 구동형을 각각 혼합

---

#### 상태코드 클래스
| | 클래스 | 설명 | | 상세 | 
|---|---|---|---|---|
| 1xx | Informational | 요청을 받아들여 처리 중 | 
| 2xx | Success | 요청을 정상적으로 처리했음 | 
|  |  | | 200 | OK | 
|  |  | | 204 | No Content | 
|  |  | | 206 | Partial Content | 
| 3xx | Redirection | 요청을 완료하기 위해 추가 동작이 필요함 | 
|  |  | | 301 | Moved Permanently | 
|  |  | | 302 | Found | 
|  |  | | 303 | See Other | 
|  |  | | 304 | Not Modified | 
|  |  | | 307 | Temporary Redirect | 
| 4xx | Client Error | 서버는 리퀘스트를 이해 불가능함 | 
|  |  |  | 400 | Client Error | 
|  |  |  | 401 | Unauthorized | 
|  |  |  | 403 | Forbidden | 
|  |  |  | 404 | Not Found | 
| 5xx | Server Error | 서버는 리퀘스트 처리 실패함 | 
|  |  |  | 500 | Internal Server Error |
|  |  |  | 503 | Service Unavailable |

- 에러가 발생한 경우에도 에러메시지를 포함한 200 OK 가 오는 경우도 많음...  

---

#### 웹서버에 관하여? 
- HTTP 를 이용 할 때 도메인명이 달라도 DNS 에 의해서 IP 로 변환되어 액세스 하기 때문에 서버에 도착한 시점에 서버에 여러 도메인 서버가 있을 경우 어떤 도메인에 대한 액세스인지 알 수 없음
  - 같은 IP 주소에 다른 호스트명과 도메인 명을 가진 여러 웹서버가 실행되는 가상 호스트 시스템이 있음
  - HTTP 리퀘스트 보낼 경우 호스트명과 도메인 명을 완전하게 포함한 URI 를 지정하거나, 반드시 Host 헤더필드에 지정을 해야함
  

- 통신을 중계하는 것으로는
  - 프록시, 게이트웨이, 터널 이 있음

---

#### 일반 헤더 필드 
| 헤더 필드 | 설명 | 상세 |
| --- | --- | --- |
| Cache-Control | 캐싱 동작 지정 |
| Connection | 커넥션 관리, Hop-by-hop 헤더 |  |
| Date | 메시지 생성 날짜 |  |
| Pragma | 메시지 제어 |  |
| Trailer | 메시지 끝에 있는 헤더의 일람 |  |
| Transfer-Encoding | 메시지 바디의 전송 코딩 형식 지정 |  |
| Upgrade | 다른 프로토콜에 업그레이드 |  |
| Via | 프록시 서버에 관한 정보 |  |
| Warning | 에러 통지 |  |
<br>

#### 리퀘스트 헤더 필드
| 헤더 필드 | 설명 | 상세 |
| --- | --- | --- |
| Accept | 유저 에이전트가 처리 가능한 미디어 타입 |  |
| Accept-Charset | 문자셋 우선순위|  |
| Accept-Encoding | 콘텐츠 인코딩 우선순위|  |
| Accept-Language | 언어(자연어) 우선순위|  |
| Authorization | 웹 인증을 위한 정보 |  |
| Expect | 서버에 대한 특정 동작의 기대 |  |
| From | 유저의 메일 주소|  |
| Host | 요구된 리소스 호스트 |  |
| If-Match | 리소스 태그의 비교 |  |
| If-Modified-Since | 리소스 갱신 시간 비교 |  |
| If-None-Match | 엔티티 태그의 비교 (If-Match 의 반대)  |  |
| If-Range | 리소스가 갱신되지 않은 경우에 엔티티의 바이트 범위의 요구를 송신 |  |
| If-Unmodified-Since | 리소스의 갱신 시간 비교 (If-Modified-Since 의 반대) |  |
| Max-Forwards | 최대 전송 홉 수 |  |
| Proxy-Authorization | 프록시 서버의 클라이언트 인증을 위한 정보 |  |
| Range | 엔티티 바이트 범위 요구 (청크 전송 등) |  |
| Referer | 리퀘스트 중의 URI 를 취득하는 곳|  |
| TE | 전송 인코딩의 우선순위|  |
| User-Agent | HTTP 클라이언트 정보 |  |

<br>

#### 리스폰스 헤더 필드
| 헤더 필드 | 설명 | 상세 |
| --- | --- | --- |
| Accept-Ranges | 바이트 단위의 요구를 수신할 수 있는지 없는지 여부 |  |
| Age | 리소스의 지정 경과 시간 |  |
| Etag | 리소스 특정하기 위한 정보 |  |
| Location | 클라이언트를 지정한 URI 에 리다이렉트 |  |
| Proxy-Authenticate | 프록시 서버의 클아이언트 인증을 위한 정보 |  |
| Retry-After | 리퀘스트 재시행의 타이밍 요구 |  |
| Server | HTTP 서버 정보 |  |
| Vary | 프록시 서버에 대한 캐시 관리 정보 |  |
| WWW-Authenticate | 서버의 클라이언트 인증을 위한 정보 |  |

<br>

#### 엔티티 헤더 필드
| 헤더 필드 | 설명 | 상세 |
| --- | --- | --- |
| Allow | 리소스가 제공하는 HTTP 메소드 |  |
| Content-Encoding | 엔티티 바디에 적용되는 콘텐츠 인코딩 |  |
| Content-Language | 엔티티의 자연어 |  |
| Content-Length | 엔티티 바디의 사이즈(단위:바이트) |  |
| Content-Location | 리소스에 대응하는 대체 URI |  |
| Content-MD5 | 엔티티 바디의 메시지 다이제스트 |  |
| Content-Range | 엔티티 바디의 범위 위치(청크 전송 시) |  |
| Content-Type | 엔티티 바디의 미디어 타입 |  |
| Expires | 엔티티 바디의 유효기한 날짜 |  |
| Last-Modified | 리소스의 최종 갱신 날짜 |  |


---

### HTTPS 란?
 IP 위에 TCP 위에 HTTP 를 사용하는데 SSL(TLS) 로 암호화를 해서 보안성을 높였다.  
대칭키를 사용하는데, 이 대칭키를 교환하기 위해서 인증기관과 서버의 비대칭키를 사용한다.



---

#### Reference
- 그림으로 배우는 Http Network Basic
- https://developer.mozilla.org/ko/docs/Web/HTTP/Messages
