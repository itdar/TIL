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
~~~mysql
-- table_1 이라는 테이블에서 모든 데이터를 가져온다.
SELECT * FROM table_1;
-- table_1 이라는 테이블에서 first_name 컬럼이 Anne 인 데이터를 가져온다. 
SELECT * FROM table_1 WHERE first_name = 'Anne';
~~~
- 문자열 'words' 와 "words" 는 쿼리에서 동작하지만, 작은따옴표가 관습적
- 숫자를 따옴표 안에 넣은 쿼리도 동작함

|작은따옴표 필요|따옴표 필요없음|
|---|---|
|CHAR|DEC|
|VARCHAR|INT|
|DATE| |
|DATETIME, TIME, TIMESTAMP| |
|BLOB| |

- 문자열 내에 작은 따옴표가 있으면, (문자열의 일부임을 표기한다)
  - 앞에 백슬래시 ( \\' ) 를 붙이거나 -> 더 관습적임
  - 작은따옴표를 하나 더 붙인다 ( '' )
- SELECT * 보다는 특정 열들을 SELECT 해서 결과를 제한한다.
  - 가독성을 올리기 위함도 있고,
  - 데이터 조회 속도도 더 빠르다. (테이블이 클 수록)
- 쿼리를 외부에서 복붙하여 사용할 때, 문법이 맞는 것 같은데 에러가 나면 편집기를 거쳐서 온다.
- 비교연산자 ( <>  와 != 는 같다.)
- 비교연산자로 문자열 처리도 가능하다.
  - 제일 앞글자가 a 보다 뒤의 문자인 문자열 등..
~~~mysql
-- 첫번째 문자가 'L' 이상인 문자를 찾는다.
SELECT drink_name FROM drink_info WHERE drink_name >= 'L';
~~~
- NULL 을 찾기 위해서는 IS NULL 을 사용한다.
~~~mysql
SELECT drink_name FROM drink_info WHERE calories IS NULL;
~~~
- LIKE 는 와일드카드 문자와 사용 가능하다
  - % : 다수의 불특정 문자를 대신하는 문자
  - _ : 하나의 불특정 문자를 대신하는 문자
~~~mysql
SELECT first_name FROM my_contacts WHERE first_name LIKE '%im';
SELECT first_name FROM my_contacts WHERE first_name LIKE '_im';
~~~
- BETWEEN 키워드로 범위를 사용한다. (>=, <=)
~~~mysql
-- 30 <= x <= 60
-- 항상 작은값이 앞에 와야한다.
SELECT drink_name FROM drink_info WHERE calories BETWEEN 30 AND 60;
~~~
- IN 키워드로 OR 여러 개를 짧게 할 수 있다.
~~~mysql
-- 동일한 쿼리. 개수가 많을수록 더 보기 좋다.
SELECT date_name FROM black_book WHERE rating = 'innovative' OR rating = 'fabulous';
SELECT data_name FROM black_book WHERE rating IN ('innovative', 'fabulous');

-- NOT 을 붙여서 포함하지 않는 것을 찾는다.
SELECT data_name FROM black_book WHERE rating NOT IN ('innovative', 'fabulous');
~~~
- NOT 은 WHERE / AND / OR 등 바로 뒤에도 쓰인다.

|연산자| 의미 |
|---|---|
|SELECT| 조회 |
|IS NULL| null check|
|BETWEEN| 이상/이하 사이값 |
|%| LIKE와 쓰는 와일드카드 문자 (다수)|
| _| LIKE와 쓰는 와일드카드 문자 (단일)|
|<>|!=|

## 3. DELETE 와 UPDATE
~~~mysql
-- 테이블에서 해당 조건에 맞는 모든 레코드(행)를 삭제한다.
DELETE FROM table_1 WHERE column_1 = 'value1';
~~~
~~~mysql
INSERT INTO table_1 VALUES (column1, column2);
~~~

**테이블에서 행의 순서가 시간 순이라고 할 수 없음**
* 테이블에 저장되는 순서는 다양한 요소에 의해 정해짐
  * 어느 회사 데이터베이스 인지, 열에 인덱스가 있는지 등

**DELETE**
* 한 열이나 여러 열의 값을 지우는데는 사용 할 수 없다.
  * WHERE 절에 따라서 한 행이나 여러 행들을 지울 수 있다.
* WHERE 절은 SELECT 문에서 썼던 WHERE 절과 같아서 LIKE, IN, BETWEEN 등 조건 사용 가능하다.
* 조건을 안넣어서 전체 테이블 날리는 것 주의

**WHERE 절을 사용하는 쿼리문은 SELECT 를 먼저 해보고 조건이 맞는지 확인해 볼 수 있다.**

**UPDATE 문은: 조건 SELECT(확인) -> 새로운 내용 INSERT(삽입) -> 기존 내용 DELETE(삭제)**
~~~mysql
UPDATE table_1 SET column_name = 'newValue'
    WHERE column_name = 'oldValue';
~~~
- WHERE 절을 뺴고 업데이트 하면, 모든 행에 대해 SET 절에 명시된 각 열의 값이 새 값으로 업데이트 된다..
- 내가 원하는 행만 변경하는지 어떻게 확인? -> SELECT 문을 먼저 해본다.

**UPDATE 문은: 아래처럼 기본적인 수학 연산도 수행 가능**
~~~mysql
UPDATE drink_info
SET cost = cost + 1
WHERE drink_name = 'Blue Moon'
OR drink_name = 'Oh My Gosh';
~~~
- 수학연산 뿐 아니라 문자 변수도 연산이 있음
  - UPPER(), LOWER() -> 대문자 / 소문자 변경 등

## 4. 좋은 테이블 설계: 정규화는 왜 하죠?
## 5. ALTER: 과거 다시 쓰기
## 6. 고급 SELECT
## 7. 테이블이 여러개인 데이터베이스 설계
## 8. 조인과 다중 테이블 연산
## 9. 서브 쿼리: 쿼리 안의 쿼리
## 10. 외부 조인, 셀프 조인, 유니온
## 11. 제약사항, 뷰, 트랜잭션
## 12. 보안