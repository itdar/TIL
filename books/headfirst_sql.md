# Head First SQL (헤드퍼스트 SQL)

1. [데이터와 테이블](#1.-데이터와-테이블)

2. [SELECT 문](#2.-SELECT-문)

3. [DELETE 와 UPDATE](#3.-DELETE-와-UPDATE)
4. [좋은 테이블 설계: 정규화는 왜 하죠?](#4.-좋은-테이블-설계:-정규화는-왜-하죠?)
5. [ALTER: 과거 다시 쓰기](#5.-ALTER:-과거-다시-쓰기)
6. [고급 SELECT](#6.-고급-SELECT)
7. [테이블이 여러개인 데이터베이스 설계](#7.-테이블이-여러개인-데이터베이스-설계)
8. [조인과 다중 테이블 연산](#8.-조인과-다중-테이블-연산)
9. [서브 쿼리: 쿼리 안의 쿼리](#9.-서브-쿼리:-쿼리-안의-쿼리)
10. [외부 조인, 셀프 조인, 유니온](#10.-외부-조인,-셀프-조인,-유니온)
11. [제약사항, 뷰, 트랜잭션](#11.-제약사항,-뷰,-트랜잭션)
12. [보안](#12.-보안)


## 1. 데이터와 테이블
~~~mysql
CREATE DATABASE database_1 

USE database_1;

CREATE TABLE table_1 {
    value1 CHAR(1) NOT NULL,
    value2 INT NOT NULL
};

DROP TABLE table_1;

-- 테이블 구조 확인 (Describe)
DESC table_1;

INSERT INTO table_1 (value1, value2) VALUES ('1', 2);

SELECT * FROM table_1;
~~~
- 세로 (열, 필드), 가로 (행, 레코드)
- 테이블 구조 확인은 DESC 사용
- DROP TABLE 은 주의해서 사용 (테이블 삭제)
- 테이블에 데이터 넣는 것은 INSERT
- INSERT 문에서 값이 할당되지 않은 열은 디폴트로 NULL 이 된다.
- 테이블을 만들 때 NOT NULL 키워드를 사용해서 NULL 값이 올 수 없도록 할 수 있다.
- 테이블을 만들 때 열에 대해서 DEFAULT 값을 사용하면 레코드 삽입 시 그 열에 대해 값을 주지 않을 경우 디폴트 값으로 열을 채운다.

## 2. SELECT 문

## 3. DELETE 와 UPDATE
## 4. 좋은 테이블 설계: 정규화는 왜 하죠?
## 5. ALTER: 과거 다시 쓰기
## 6. 고급 SELECT
## 7. 테이블이 여러개인 데이터베이스 설계
## 8. 조인과 다중 테이블 연산
## 9. 서브 쿼리: 쿼리 안의 쿼리
## 10. 외부 조인, 셀프 조인, 유니온
## 11. 제약사항, 뷰, 트랜잭션
## 12. 보안