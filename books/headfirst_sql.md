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
- 데이터가 많아지면 원하는 데이터를 찾기 위한 WHERE 절이 점점 복잡해짐 -> 테이블을 정규화하여 문제 해결 가능
- 테이블은 사용자가 원하는 데이터에 따라서 형태가 달라진다.
  - 사용용도와 테이블의 데이터 형태가 맞지 않는 경우, LIKE 등을 사용하고 쿼리가 길어지게 된다.
    - LIKE 키워드를 사용할 경우, 쓰는 것이 문제는 아니지만 사용이 쉽지 않고 열의 정보가 복잡하면 원하는 특정 데이터를 뽑기 쉽지 않다.
    - 쿼리는 간단할수록(짧을수록) 좋은데, 데이터베이스가 증가하고 테이블이 추가됨에 따라 쿼리는 점점 더 복잡해진다. 처음부터 최대한 간단하게 만들면 도움이 된다.
    - 무조건 잘게 쪼개는 것은 아니고, 데이터를 어떻게 사용하는 지에 따라서 정해진다.
#### RDBMS 에서 테이블은 관계에 관한 것: 테이블을 잘 설계하려면 정보를 나타낼 때 여러 테이블 사이의 열들이 서로 어떤 관계인지 고려해야 한다는 것 
**데이터를 어떻게 사용할 것인가에 대한 결정이 테이블을 어떻게 만들지에 영향을 준다**
- 테이블을 생성하는 과정
  1. 테이블로 표현하려는 것을 선택
  2. 테이블을 사용하여 얻어야 하는 정보들의 리스트를 작성
  3. 리스트를 이용하여 테이블을 만들 정보들의 조각으로 나눔 (가장 쉽게 쿼리를 보낼 방법)
~~~
예를들어, 테이블로 음식점의 평점과 간판메뉴를 알고 싶다면
테이블로 표현하려는 것: 식당들의 평점과 간판메뉴 정보
테이블을 사용하여 얻어야 하는 정보들의 리스트: 식당이름, 주소, 전화번호, 평점, 간판메뉴(들)
테이블을 만들 정보들의 조각: 식당이름, 지역, 상세주소, 전화번호, 평점, 간판메뉴(들)

여기서 주소는 지역/상세주소 로 원자화 했지만
간판메뉴는 여러개이지만 목록으로 가져오면 된다고 생각해서 쪼개지 않음 (별도 테이블로?)
 -> 충분히 원자적(atomic) 이다.
~~~
- 데이터가 원자적이라는 의미는, 쪼갤 수 없는 가장 작은 조각으로 쪼개졌다는 의미
  - 하지만 무조건 쪼개는 것이 아니라, 위 예시처럼 필요에 따라서 사용할 최소의 단위로 쪼개두면 됨
**원자적 데이터를 사용하면 쿼리 작성이 더 쉽고, 수행 시간이 더 빨라진다. (저장 데이터가 대용량일 경우 더 효율적)**

- 테이블에 무엇을 넣을지 도움되는 질문
  1. 테이블이 표현하는 것은?
  2. 그것을 얻기 위해 테이블을 어떻게 사용? (쿼리가 쉽도록 테이블을 디자인)
  3. 열들은 쿼리를 짧게 명료하게 하기 위해 원자적 데이터를 갖는가?

**원자적 데이터 규칙**
1. 원자적 데이터로 구성된 열은 그 열에 같은 타입의 데이터를 여러 개 가질 수 없다.

|food_name|ingredients
|---|---|
|bread|flour, milk, egg, yeast, oil|
|salad|lettuce, tomato, cucumber|
2. 원자적 데이터로 구성된 테이블은 같은 타입의 데이터를 여러 열에 가질 수 없다.

|teacher|student1|student2|studnet3|
|---|---|---|---|
|Ms. Martini|Joe|Ron|Kelly|
|Mr. Howard|Sanjaya|Tim|Julie|

#### **정규화의 이유**
  * 테이블이 표준 규칙을 따르게 한다 -> 여러 사람이 테이블을 이해하기 더 쉽다.
  * 중복 데이터가 없어서 데이터베이스 크기를 줄여준다.
  * 찾아야 할 테이터가 적어서 쿼리가 더 빨라진다.
  * 테이블이 작을 때에도 커지는 경우를 고려해서 정규화한다.

#### 제 1정규화 (1NF)
1. 각 행의 데이터들은 원자적 값을 가져야 한다.
2. 각 행은 유일무이한 식별자인 기본키(primary key)를 가지고 있어야 한다.

- 기본키 규칙
  1. NULL 불가
  2. 레코드가 삽입될 때 값이 있어야 함
  3. 간결해야함
  4. 변경 불가
- 기본키를 주민번호 등 기존 존재하는 열 데이터로 만드는 것 (natural key)
  - 개인정보 유출 등을 고려해서 조심
- 새로 만들어 내는 기본키 사용 (synthetic key)
  
~~~mysql
DESCRIBE table_name; 
-- 위 경우는 테이블 필드 정보

SHOW CREATE TABLE table_name; 
-- 위 경우는 테이블 작성문 반환
~~~
- SHOW 를 통해서 테이블 작성문을 반환해보면 백틱문자(`) 가 있음 (복붙에는 상관 없음)
  
- SHOW 키워드
```mysql
-- 테이블 모든 열을 표시하고 열의 데이터 타입과 열의 세부사항 표시
SHOW COLUMNS FROM table_name;

-- 테이블을 생성하는 명령을 얻음
SHOW CREATE TABLE table_name;

-- 데이터베이스를 생성하는 명령을 얻음
SHOW CREATE DATABASE database_name;

-- 인덱싱이 되어 있는 열과 무슨 타입의 인덱스를 갖는지 표시
SHOW INDEX FROM table_name;

-- 수행한 SQL문이 경고 메시지를 표시하면 이것으로 실제 경고 메시지 확인 가능
SHOW WARNINGS;
```
- 키워드를 열 이름으로 백틱문자를 사용해서 쓸 수 있지만 사용하지 말라.

* **AUTO_INCREMENT** 키워드를 사용해서 자동으로 최대값 이 후 값을 넣어주는 기능을 넣을 수 있다.
* 기본키 값을 포함해서 INSERT 가능하지만, 중복은 안되고 없는 값은 넣을 수 있다.
  * NULL 의 경우는 무시되고 알아서 AUTO_INCREMENT 된 값이 들어간다.

```mysql
-- 기존 테이블에 기본키 추가하는 방법 (ALTER)
ALTER TABLE table_name
ADD COLUMN id INT NOT NULL AUTO_INCREMENT FIRST,
ADD PRIMARY KEY (id);

-- 위 코드의 경우 기존에 있던 레코드들에 id 값을 자동으로 넣어준다.
```
  

### 마무리 정리
- 원자적데이터: 사용자가 사용 할 데이터 형태의 최소 단위 (쿼리가 쉽도록) 
- AUTO_INCREMENT: primary key 또는 unique 키 에서 사용 가능함. 해당 열의 최대값보다 큰 값으로 더해서 겹치지 않게 넣어줌.
- SHOW CREATE TABLE: 테이블 생성 명령어를 보여줌
- 제 1정규형(1NF): 
  1. 데이터의 원자화가 되어있다.
  2. 기본키가 설정되어 있다.
- 기본키(Primary Key): 중복되지 않고 null 이 될 수 없는 식별자 역할의 키. 레코드 삽입 시 필수!
- 원자적데이터 규칙
  1. 하나의 열에 같은 타입의 데이터가 여러개 있으면 안됨
  2. 같은 타입의 데이터가 여러 열로 나뉘어 중복되면 안됨
  

## 5. ALTER: 과거 다시 쓰기
## 6. 고급 SELECT
## 7. 테이블이 여러개인 데이터베이스 설계
## 8. 조인과 다중 테이블 연산
## 9. 서브 쿼리: 쿼리 안의 쿼리
## 10. 외부 조인, 셀프 조인, 유니온
## 11. 제약사항, 뷰, 트랜잭션
## 12. 보안