# Oracle 에서 MySQL 로 변환..

-   일반적인 변환 이유
-   고려할 사항들
-   RDBMS 변환 시 전체적인 계획

## 일반적인 변환 이유

-   비용문제
-   노후 시스템 개선 등

## 고려사항

- 데이터타입
- Subquery alias (mysql 에서는 subquery에 alias 없으면 쿼리에러 발생)
- Mysql 은 대소문자를 구분함 (설치 시 구분여부 설정 가능)
- 함수 명칭 (IFNULL, NOW, CASE, CAST, DATE\_ADD 등)
- Oracle 에서만 지원하는 구문/함수 들 (sequence, rownum, join, pivot 등)
- 계층형 쿼리
- || (pipe\_as\_concat -> cancat\_ws)
- 공백문자를 null로 취급하는 것의 차이
- Dynamic where 차이
- Byte 입력 및 byte 연산 차이 (함수 만들어서 해결)
- 대용량 처리 시 성능 저하 (oracle -> mysql)
    -   쿼리개선
    -   파티셔닝
- Isolation level

## RDBMS 변환 시 계획

-   사전준비 필수
-   충분한 시간
-   복구 가능한 형태로 데이터 보관
-   QA 지속적으로.

#### Reference

[https://www.youtube.com/watch?v=jMHOJQczrnI](https://www.youtube.com/watch?v=jMHOJQczrnI)