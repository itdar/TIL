# Java 의 로그 패키지 (Slf4j, Log4j2, Logback)

---

* slf4j, log4j, logback 설명
* slf4j, log4j, logback 성능

---

## slf4j, log4j, logback 설명

- Slf4j (Simple Logging Facade For Java): logger 구현체 프레임워크를 연결 할 수 있는 추상체
- Log4j: Logger 구현체, 1.x 버전이 일찍 나왔고 현재 2.x 버전이 나옴. 최근 제로데이 이슈는 2.x 문제.
- Logback: Logger 구현체, Log4j 1.x 과 2.x 버전 사이에 나옴. 자동 리로드 등의 기능이 추가로 들어감.

---

## slf4j, log4j, logback 성능 순위
1. Log4j 2
2. Logback
3. Log4j 1

- Log4j 공식 문서 참조 (https://logging.apache.org/log4j/2.x/performance.html)
