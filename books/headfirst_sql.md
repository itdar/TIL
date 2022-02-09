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

CREATE TABLE table_1
(
  value1 CHAR(1) NOT NULL,
  value2 INT     NOT NULL
);

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
```mysql
-- 예시
-- 테이블에 전화번호 컬럼을 추가하는데, 위치는 이름 컬럼 바로 뒤에 넣는다.
-- FIRST, SECOND, THIRD, FOURTH... , AFTER, LAST, FIRST 위치 지정도 가능하다.
ALTER TABLE table_name
ADD COLUMN phone VARCHAR(20) AFTER name;
```
```mysql
-- 예시
-- 연락처테이블에 아이디컬럼을 추가하는데, 자동증가 키값으로 가장 앞 열에 추가한다.
ALTER TABLE contacts
ADD COLUMN contact_id INT NOT NULL AUTO_INCREMENT FIRST,
ADD PRIMARY KEY (contact_id);
```

#### 데이터 변경 (데이터 손실에 유의한다.)
- CHANGE: 기존 열의 `이름`과 `데이터타입`을 바꾼다.
- MODIFY: 기존 열의 `데이터타입`이나 `위치`를 바꾼다.
- ADD: 테이블에 열을 추가한다. (데이터타입 설정 가능)
- DROP: 열을 제거한다.
```mysql
-- 예시
ALTER TABLE projekts
RENAME TO project_list;

ALTER TABLE project_list
CHANGE COLUMN number proj_id INT NOT NULL AUTO_INCREMENT,
ADD PRIMARY KEY (proj_id);

ALTER TABLE project_list
CHANGE COLUMN descriptionofproj proj_desc VARCHAR(100),
CHANGE COLUMN contractoronjob con_name VARCHAR(30);
```
> 데이터 타입을 새롭게 바꾸면 데이터를 잃을 수 있다.
  
- 테이블 설계 시점에 데이터 열 순서에 대해 생각해보는 것이 좋다. (하지만 조회를 자유롭게 할 수 있어서 크게 상관없음)
  

- **테이블 당 1개의 필드만 AUTO INCREMENT 가 있을 수 있고, 정수 타입이고 NULL 이 아니어야 한다.**
  
<br>

**문자 함수들: 실제 값을 변화시키지 않고, 쿼리 결과로 반환된 문자열을 변환**
  - SUBSTRING: SUBSTRING(your_string, start_position, length)
  - SUBSTRING_INDEX: 찾는 문자의 해당 숫자번째로 찾아진 문자까지 출력한다. 
```mysql
SUBSTRING_INDEX('Hello, world, bye,', ',', 2)
-- 'Hello, world'
```
  - RIGHT: RIGHT('Hello, 2) -> 'lo'
  - LEFT: LEFT('Hello', 4) -> 'Hell'
  - UPPER: 문자열 대문자로 변환
  - LOWER: 문자열 소문자로 변환
  - REVERSE: 문자열 역순으로 만듦
  - LTRIM: 왼쪽 공백 제거
  - RTRIM: 오른쪽 공백 제거
  - LENGTH: 문자열 길이 반환

## 6. 고급 SELECT
  **<>** -> **!=**
#### CASE
```mysql
-- 예시
-- 무비테이블에서 카테고리를 업데이트하는데,
-- 드라마가 트루이면 'drama' 로,
-- 코메디가 트루이면 'comedy' 로,
-- 조건에 없으면 'misc' 로 업데이트 한다.
UPDATE movie_table
SET category =
    CASE
        WHEN drama = 'T' THEN 'drama'
        WHEN comedy = 'T' THEN 'comedy'
        -- ...
        ELSE 'misc'
    END;
```
- CASE 문이 동작 할 때, 윗줄의 조건에 해당하는 것이 있으면 아랫줄로 내려오지 않는다.
- ELSE 문은 뺄 수 있지만, 조건이 없는 경우 NULL 외에 다른 값이 좋다. (ELSE 도 없고 조건에도 없으면 CRUD 동작하지 않는다.)
- END 뒤에 WHERE 절을 붙여서 해당 조건의 행에서만 CASE 문을 실행하도록 할 수 있다.
- CASE 문은 SELECT, INSERT, DELETE, UPDATE 모두 사용 가능하다.
  
#### ORDER BY
```mysql
-- 예시
-- 타이틀, 카테고리, 구매날짜를 무비테이블에서 조회하는데
-- 카테고리가 Family 인 것들을 조회하고,
-- 카테고리는 오름차순 정렬하고, 카테고리 내부에서는 구매날짜의 내림차순으로 정렬한다.
SELECT title, category, purchased
FROM movie_table
WHERE category = 'Family'
ORDER BY category ASC, purchased DESC;
```
- 결과를 정해둔 기준으로 정렬한다. (오름차순(ASC), 내림차순(DESC))
- SQL 순서 규칙
  - 알파벳이 아닌 문자들은 숫자의 앞과 뒤에 온다.
  - 숫자는 알파벳 앞에 온다.
  - NULL 은 숫자 앞에 온다.
  - NULL 은 알파벳 앞에 온다.
  - 대문자는 소문자 앞에 온다.
  - "A 1" 은 "A1" 앞에 온다.
- SQL 순서 규칙 (기호)
  - ! " & ' * + = ? @ ~
#### SUM, AVG, MIN, MAX, COUNT (함수) + GROUP BY, DISTINCT (키워드)
```mysql
-- 예시
-- sales 를 조회해서 합산한 값을 보여주는데,
-- 쿠키세일 테이블에서 조회,
-- 이름으로 그룹핑한 것에서 조회하여,
-- sales 합산 값의 내림차순으로 보여주는데,
-- 0부터 2개 (0,1) 까지만 보여준다. (또는 그냥 LIMIT 2)
SELECT SUM(sales)
FROM cookie_sales
GROUP BY name
ORDER BY SUM(sales) DESC
LIMIT 0, 2;
```
```mysql
-- 예시
SELECT DISTINCT COUNT(sale_date)
FROM cookie_sales
ORDER BY sale_date;
```
- GROUP BY
  - 공통된 열 값으로 행들을 집계한다.
- DISTINCT
  - 한 열에 같은 값이 여러개인 레코드들이 많고, 중복된 값들이 외에 어떤 값들이 있는지 확인 할 때 사용
  - 중복 없이 유일한 값을 한번 반환한다.
- LIMIT
  - 몇개의 행을 반환할지, 어디부터 몇개 행을 반환할지 정한다.
  

- NULL 은 MIN, MAX, COUNT 에 영향받지 않음


## 7. 테이블이 여러개인 데이터베이스 설계
**테이블을 잘 설계해서 일을 해결해야 한다. 설계에 문제가 있는 테이블을 해결하려고 복잡한 쿼리를 작성하면 안된다.**
  
**테이블에서 원자적이지 않은 열을 새로운 테이블로 옮긴다.**
```
 주소록(contacts)테이블에서 관심사(interests) 와 같이, 검색하기 어려운 경우
```

### 스키마: 데이터베이스 내의 데이터(열들과 테이블들), 그리고 데이터들 사이의 연결 방식에 대한 표현
- 데이터베이스에서 테이블과 열 등 데이터들이 어떻게 연결되는지 표현한 것.

<br>

**테이블을 그림으로 만들면 테이블 설계와 데이터를 분리하여 생각 할 수 있다.**

<br>

### 하나의 테이블을 두개로 만드는 법 (ex. 관심사(interests))
1. 관심사 열을 없애고 관심사만의 테이블에 넣는다.
2. 관심사가 기존 테이블(contacts)의 어떤 레코드와 관련 있는지를 알 수 있게 하는 열을 추가한다.
   1. 이 때, 관련있는 열이 유일하지 않으면, 연결이 잘못 될 수 있다. (기본키를 쓰는데, 다른 테이블에서는 참조키(Foreign Key)라 한다)
   2. 참조키(외래키, FK)를 통해서, 어느 관심사가 어느 사람연락처에 속하는지 알 수 있다.

### 참조키
- 참조키는 연결되는 기본키와 다른 이름일 수 있다.
- 참조키에서 참조하는 기본키는 부모키(parent key), 부모키가 있는 테이블은 부모테이블(parent table) 이라 한다.
- 한 테이블의 열들이 다른 테이블의 열과 연결되도록 하는데 사용할 수 있다.
- 기본키는 not null 이지만, 참조키 값은 null 일 수 있다.
- 참조키는 유일할 필요가 없다. (not unique)
- 참조키는 부모테이블에 존재하는 키의 값만을 넣을 수 있다. (참조 무결성, referential integrity)
  - 자식테이블의 참조키로는 부모 테이블에 존재하는 값만 넣을 수 있다.

#### 제약조건 (constraint): 테이블이 준수해야 하는 규칙
- 참조키를 테이블 안의 제약조건으로 생성하는 장점으로, 규칙을 위반하면 에러가 발생해서 실수로 테이블 규칙 위반하는 것을 막아준다.

### 참조키가 있는 테이블 생성
```mysql
-- 기존에 my_contacts 테이블에서 분리된다고 가정

CREATE TABLE interests (
    int_id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    interest VARCHAR(50) NOT NULL,
    contact_id INT NOT NULL,
    CONSTRAINT my_contacts_contact_id_fk    -- 제약조건은 어느테이블 어느키를 참조하는지, 제약조간이 fk 라고 명명 (이름 수정 가능)
    FOREIGN KEY (contact_id)                -- 괄호 안의 열 이름이 참조키
    REFERENCES my_contacts (contact_id)     -- REFERENCES 어느 테이블의 (어느 열) 인지 표시한다.
);

-- DESC interests 를 터미널에서 쳐서 정보를 확인 할 때,
-- Key 열에서 MUL (같은 값이 여러번 나올 수 있다) 을 확인하면 참조키 확인 가능
```

* 참조키를 제약조건 없이, 그냥 다른 테이블 참조하여 사용할 수 있지만, 연결을 강제하기 위해 제약조건을 사용한다.
  * 연결을 강제하는 것은 참조무결성을 보장하는데,
    * 한 테이블에 참조키를 가진 행이 있으면, 다른 테이블에 참조키에 대응하는 행 하나가 있어야 함을 보장한다는 것
    * 기본키 테이블에서 하나의 행을 지우거나 기본키 값을 바꿀 때, 기본키 값이 다른 테이블의 참조키 제약조건 있으면 에러가 발생한다.

### 테이블 간의 관계 (일대일, 일대다, 다대다)
#### 일대일 (부모테이블 하나의 행은 자식 테이블 하나의 행과 관련되어 있다)
- ex) 보안을 위해서 분리: 사원 테이블 - 급여 테이블(+SSN)
- 자주 이용은 안됨
- 열을 분리하면 데이터 조회 속도가 향상될 수 있음
- 아직 모르는 값을 포함하는 열이 있어도, 주 테이블에서는 null 값을 피할 수 있음
- 데이터의 일부를 접근하기 힘들게 할 수 있다.
- BLOB 타입처럼 양이 큰 데이터가 있어서 큰 데이터는 따로 저장 하고 싶을 경우.
#### 일대다
- ex) 직업 테이블의 레코드는 사람들 테이블에서 여러번 나올 수 있지만, 사람들 테이블은 1개의 직업ID 만 가지고 연결된다.
- 테이블 A 의 한 레코드가 테이블 B 의 여러 레코드와 연결되지만, 테이블 B 의 레코드는 테이블 A 의 레코드 한개에만 연결될 수 있다.
#### 다대다
- ex) 여러 명의 여자는 여러 켤레의 신발을 갖는다.
- 2개의 일대다 관계로 이루어지고 사이에 연결테이블이 있다.
- 기본키를 다른 테이블 참조키로 추가하고 다대다를 맺으면, 중복데이터가 많이 나온다.
  - 다대다 관계의 테이블에는 중간에 테이블을 하나 추가하여 일대다 관계로 단순하게 할 필요가 있음
  - 중간테이블(연결테이블, junction table)
    - 양 측의 id 값 쌍들을 보관한다.
- 두 테이블 사이에 연결테이블이 없으면, 중복데이터가 생겨서 제1정규형을 위반한다.
  - 위반 시, 중복 데이터 때문에 쿼리의 시간이 길어진다.
- 테이블을 분리하고 연결테이블을 쓰면, 조인을 사용한 쿼리를 사용 가능하다.
  - 테이블 조인을 사용하면 데이터의 무결성(integrity)을 유지하는데 도움이 된다.

#### 제 1 정규형 (1NF)
- 열은 원자적 값만을 포함한다.
- 같은 데이터가 여러 열에 반복되지 않는다.

### 합성키: 두 개 이상의 열로 이루어진 키
- 여러 개의 열들로 구성되어 유일무이한 키를 만드는 기본키

### 한 테이블 내의 열들이 서로 어떻게 연결되는지 이해하는 것: 제2정규형, 제3정규형 이해의 핵심

## 함수적 종속
- 열의 데이터가 변경될 때, 다른 열의 데이터가 변경되어야 하면, 변경되어야 하는 열은 변경되는 열에 함수적으로 종속하고 있는 것.
- ex) 영웅이름 -> 영응이름의 이니셜 (T.x -> T.y, T 라는 테이블에서 열 y는 열 x에 함수적으로 종속된다)
  - 이니셜은 이름에 종속, 약점은 이름에 종속, 도시는 나라에 종속 등...
- T.x -> T.y (shorthand notation, 줄임 표기법)

### 부분적 함수 종속
- 키가 아닌 열이 합성키의 전부가 아닌 일부에 종속되는 경우
- ex) name, power 가 함께 합성키인 경우
  - 이니셜은 name에 종속되지만 power에 종속되지 않음 (부분적 함수종속) 

### 이행적 함수 종속 (transitive dependency)
- 키가 아닌 열이 키가 아닌 다른 열과 관련되는 경우
- 키가 아닌 열이 변경되었을 때, 다른 열의 변경을 초래 한다.
- ex) heroes table 에서, arch_enemy 를 변경하면 arch_enemy, arch_enemy_city 도 변할 수 있다.

<br>

- 부분적 함수 종속을 피하는 방법
  - id 열을 만들어서 필드로 사용한다. (새 키는 테이블 인덱스 용으로만 사용되기 때문에 새 키에 종속되는 것이 없다)
- natural key(자연키) - 기존 있는 것 또는 합성 키, synthetic key(인위적 키) - 키를 위한 키
  - 선택 사항

#### 테이블에 기본키 열을 추가하면 2NF 를 만드는 데 도움이 됨
- 왜냐면, 제2정규형은 테이블의 기본키가 데이터와 어떤 관계에 있는지에 초점을 맞춤

## 제 2 정규형 (2NF)
- 1NF 여야 한다.
- 부분적 함수 의존이 없다.
  - 간단한 방법으로는..
    - 1NF 이면서 하나의 열로 된 기본키를 갖거나 (인위적으로 만든 기본키가 있고, 합성키가 없으면 2NF),
    - 1NF 모든 열이 기본키(합성키)의 일부이거나,
    
## 제 3 정규형 (3NF)
- 2NF 여야 한다.
- 이행적 종속이 없다. (키가 아닌 다른 열이 키가 아닌 다른 열과 관련되어 있지 않다)
  - 3NF 를 고려할 때는 기본키는 무시할 수 있다.

> 제 1, 2, 3 정규화를 통해서..  
> 테이블을 정돈

#### 정리
- 스키마: 관련 객체와 객체들 사이의 연결. 그리고 데이터베이스의 데이터에 대해 설명해 놓은 것
- 일대일 관계: 부모테이블의 한 행이 자식테이블의 한 행과 연결되는 관계
- 일대다 관계: 한 테이블의 한 행이 두 번째 테이블의 여러 행과 연결되지만 두 번째 테이블은 첫번째 테이블의 한 행과 연결되는 관계
- 다대다 관계: 두 테이블이 연결 테이블로 연결되어 첫번째 테이블의 여러 행이 두번째 테이블의 여러 행과 연결, 두번째 테이블의 여러 행도 첫번째 테이블의 여러 행과 연결되는 관계
- 제1정규형: 열이 원자적 값만을 포함하고, 열에 데이터가 반복되지 않는 형태
- 제2정규형: 테이블이 1NF 여야 하고, 부분적 함수 종속이 없음
- 제3정규형: 테이블이 2NF 여야 하고, 이행적 종속 관계가 없음
- 이행적 함수 종속: 키가 아닌 열이 키가 아닌 다른 열과 관련있다는 의미
- 참조키: 테이블에서 다른 테이블의 기본키를 참조하는 열
- 합성키: 여러열로 구성된 기본키로 유일무이 한 값

## 8. 조인과 다중 테이블 연산
- 정규화 되지 않은 테이블 설계 때문에 쿼리를 만들기가 어려움.
  - ex) my_contacts 테이블에서, interests 는 원자화 되어있지 않음
    - 컬럼을 추가해서 나눈다 (아직 같은 데이터 열들이 반복되긴 함)
      - interest1 = SUBSTRING_INDEX(interests, ',', 1);
      - SUBSTR(interests, LENGTH(interest1) + 2);
  

- SELECT 문을 이용하여 새로운 테이블을 채우는 법
  - 여러 방법을 알면 상황에 가장 맞는 방법을 선택 할 수 있고, 같은 일을 여러 방법으로 수행 할 수 있으면 최적화에 도움이 된다.
```mysql
-- 1. CREATE table 그리고 SELECT 를 이용한 INSERT
CREATE TABLE profession
(
    id INT(11) NOT NULL AUTO_INCREMENT PRIMARY KEY,
    profession VARCHAR(20)
);

INSERT INTO profession (profession)
    SELECT profession FROM my_contacts
    GROUP BY profession
    ORDER BY profession;
    
-- 2. SELECT 를 이용해서 테이블을 만들고 ALTER 를 사용해서 기본키 추가
CREATE TABLE profession AS 
    SELECT profession FROM my_contacts
    GROUP BY profession
    ORDER BY profession;

ALTER TABLE profession
ADD COLUMN id INT NOT NULL AUTO_INCREMENT FIRST,
ADD PRIMARY KEY (id);
```
```mysql
-- 3. 기본키와 SELECT 문의 데이터를 포함한 테이블을 한 문장으로 생성
CREATE TABLE profession
(
    id INT(11) NOT NULL AUTO_INCREMENT PRIMARY KEY,
    profession VARCHAR(20)
) AS 
SELECT profession FROM my_contacts
GROUP BY profession
ORDER BY profession;

-- 3번과 같이 새 테이블이 새로운 이름의 2개 열을 갖는다고 표시하지 않았다면,
-- AS 는 한 열을 SELECT 의 결과와 같은 이름, 타입을 만듦 (2번처럼)
```

<br>


#### 별명(alias) 만드는 방법: 처음 나오는 열 이름 뒤에 AS 와 함께 별명을 붙인다.
- AS 는 생략 가능
- 별명은 원래 열의 이름은 변하지 않고, 잠깐 사용된다.
- 테이블 별명은 연결이름(correlation name) 이라고 부른다. 
```mysql
CREATE TABLE profession
(
    id INT(11) NOT NULL AUTO_INCREMENT PRIMARY KEY,
    profession VARCHAR(20)
) AS 
SELECT profession AS mc_prof FROM my_contacts
GROUP BY mc_prof
ORDER BY mc_prof;
```

## Join
### Inner Join (내부조인)
- `내부조인`은 쿼리의 조건에 따라 일부 결과열이 제거된 크로스 조인

<br>

#### 1. 카티젼 조인 (Cartesian join, Cartesian product, Cross product, Cross join...)
- 내부 조인의 한 종류
  - 다른 내부조인 모두 기본적으로 카티젼 조인의 결과 중 일부가 쿼리의 조건에 의해 제거 됨
- 필요한 이유: 
  - 조인 문장 수정에 도움이 되고, 
  - DB 시스템과 환경의 성능 확인에 사용된다. 
  - 슬로 쿼리 사용 시 걸리는 시간을 더 쉽게 비교, 확인 가능하다.
- 너무 큰 테이블을 크로스 조인하면 다운 될 위험
```mysql
SELECT t.toy, b.boy
FROM toys AS t 
    CROSS JOIN
    boys AS b;
 
-- cross join 없애고 콤마로 대체 가능 
SELECT toys.toy, boys.boy
FROM toys, boys;
```

#### 2. 내부 조인 ( 동등조인(=), 비동등조인(<>) )
- **조건을 사용하여 두 테이블의 레코드를 결합**
- 결합된 행들이 조건을 만족할 경우에만 열들이 반환됨
```mysql
SELECT someColumns
FROM table1
    INNER JOIN
    table2
ON table1.something = table2.something  -- ON 대신 WHERE 가능
    OR table1.something <> table2.something;
```
#### 3. 내부 조인 (자연조인)
- 일치하는 열 이름을 사용하는 `내부조인`
- 두 테이블에 같은 이름의 열이 있을 때 동작
- 동등조인과 유사
```mysql
SELECT boys.boy, toys.toy
FROM boys
    NATURAL JOIN 
    toys;
```

<br>

> `외부조인`의 경우에는 테이블을 조인하는 순서가 중요함 (10장)

<br>

#### 정리
- 내부조인(INNER JOIN): 조건을 사용해서 두 테이블의 레코드를 결합하는 모든 조인
  - 동등조인(EQUI JOIN): 같은 행들 반환
  - 비동등조인(NON-EQUI JOIN): 같지 않은 행들 반환
- 자연조인(NATURAL JOIN): ON 절이 없는 내부조인. 같은 열 이름을 가진 두 테이블을 조인할 때만 동작한다. 
- 크로스조인(CROSS JOIN, Comma JOIN): 한 테이블의 모든 행과 다른 테이블의 모든 행이 연결되는 모든 경우를 반환 (=`카티젼조인`)

## 9. 서브 쿼리: 쿼리 안의 쿼리
### 서브쿼리: 다른 쿼리에 싸여진 쿼리 = 내부쿼리
- 연산자를 사용해서 하나의 값, 혹은 한 열의 한 행을 (셀, 스칼라) 반환하며, 반환된 값은 WHERE 절의 열들과 비교된다. (대부분은 하나의 결과)
- 서브 쿼리의 동작
  - 내부 쿼리 동작 후, 그 내용을 이용해서 외부 쿼리 동작함 (비상관쿼리)
- 조인으로도 처리 가능함
- WHERE 절 같은 역할을 함
- 서브쿼리는 언제나 SELECT 문 한개
- SELECT, INSERT, UPDATE, DELETE 와 함께 사용 가능함
- 쿼리할 질문을 나누어 각 부분에 작은 쿼리를 만드는 방법이 좋음
  - ex) 연봉이 가장 많은 웹 디자이너와 같은 연봉을 버는 사람을 찾으려 한다면,
    1. 가장 연봉이 많은 웹 디자이너를 찾는다
    2. 연봉이 X 인 사람을 찾는다
    - 첫번째 쿼리의 답을 X 에다가 넣어서 돌린다.

ex) 아래 동일한 쿼리
```mysql
-- 쿼리 2회
SELECT mc.first_name, mc.last_name, mc.phone, jc.title
FROM job_current AS jc NATURAL JOIN my_contacts AS mc
WHERE
    jc.title IN ('Cook', 'Hairdresser', 'Waiter', 'Web Designer', 'Web Developer');

-- 서브쿼리 사용 (IN 을 사용하는 것은 예외적인 경우이긴 함, 보통 1개 결과를 반환하게 사용함)
SELECT mc.first_name, mc.last_name, mc.phone, jc.title
FROM job_current AS jc NATURAL JOIN my_contacts AS mc
WHERE
    jc.title IN (SELCT title FROM job_listings);
```
- 하나의 쿼리로 충분하지 않으면: 서브 쿼리를 사용한다.

#### 열 별명 (AS 사용) 
- 결과 컬럼의 이름 가독성을 높임 
- 임시로 사용하기 때문에 테이블에 영향을 주지 않음

### 서브 쿼리 생성의 예
- ex) 가장 연봉이 높은 사람의 이름과 연봉을 찾는다. (my_contacts, job_currents) 
```mysql
-- 1
SELECT MAX(salary) FROM job_current;

-- 2
SELECT mc.first_name, mc.last_name
FROM my_contacts AS mc;

-- 3
SELECT mc.first_name, mc.last_name, jc.salary
FROM my_contacts AS mc
NATURAL JOIN job_currents AS jc;

-- 4
SELECT mc.first_name, mc.last_name, jc.salary
FROM my_contacts AS mc
NATURAL JOIN job_currents AS jc
WHERE jc.salary = (SELECT MAX(jc.salary) FROM job_current jc);
```
### 서브 쿼리를 SELECT `열`에서 사용
- 서브 쿼리는 SELECT 문의 열 중 하나로 사용될 수 있음
- 서브쿼리가 열의 표현식으로 사용되었다면, 한 열에서 하나의 값만을 반환할 수 있음
```mysql
SELECT mc.first_name, mc.last_name,
(SELECT state 
FROM zip_code
WHERE mc.zip_code = zip_code) AS state
FROM my_contacts AS mc;
```

### 자연 조인을 포함한 서브 쿼리의 예
```mysql
-- 1
SELECT jc.salary
FROM my_contacts mc NATURAL JOIN job_current AS jc
WHERE email = 'andy@weatherorama.com';

-- 2
SELECT mc.first_name, mc.last_name, jc.salary
FROM my_contacts AS mc
NATURAL JOIN job_current AS jc
WHERE jc.salary > 
    (SELECT jc.salary 
    FROM my_contacts AS mc
    NATURAL JOIN job_current AS jc
    WHERE email = 'andy@weatherorama.com');
```

### 비상관 서브쿼리 (non-correlated)
- 서브쿼리가 외부쿼리의 어떤 것도 참조하지 않고 단독으로 사용되는 것
- 내부쿼리는 외부쿼리의 값과는 아무 상관이 없음 (단독으로 쿼리 실행 가능)

### 상관 서브쿼리 (correlated)
- 응답 시간이 느린 편
- 내부 쿼리의 값이 결정되는데 외부 쿼리에 의존함
- 외부 쿼리에서 만들어진 테이블과 같은 별명 (상관 이름) 을 사용함
- 일반적인 용례
  - 외부 쿼리에 있는 행들에서 관련 테이블에 없는 모든 행을 찾는 것
  - ex) my_contacts 의 사람들 중 job_current 테이블에 없는 모든 사람을 찾는다.
```mysql
SELECT mc.first_name, mc.last_name, mc.email
FROM my_contacts mc
WHERE NOT EXISTS
  (SELECT * FROM job_current jc
    WHERE mc.contact_id = jc.contact_id);
```

### 서브쿼리 예제(Join, Noncorrelated subquery)
```mysql
-- 1. job_listings 테이블에서 가장 연봉이 높은 직업들의 이름을 나열 (my_contacts, job_listings)
SELECT jl.title FROM job_listings AS jl
WHERE jl.salary = (SELECT MAX(jl.salary) FROM job_listings AS jl);

-- 2. 평균 연봉보다 많은 연봉인 사람의 이름을 나열 (my_contacts, job_current) 
SELECT mc.name FROM my_contacts AS mc
INNER JOIN job_current AS jc
    ON mc.contact_id = jc.contact_id
WHERE jc.salary > (SELECT AVG(salary) FROM job_current);

-- 3. job_listings 테이블에서 웹디자이너에 대한 우편번호와 같은 우편번호를 갖는 웹 디자이너를 찾아라 (my_contacts, job_current, job_listings)
SELECT zip FROM job_listings WHERE title = 'Web Developer';

SELECT mc.name FROM my_contacts AS mc
INNER JOIN job_current AS jc
    ON mc.contact_id = jc.contact_id
WHERE jc.title = 'Web Developer'
    AND mc.zip_code IN (
        SELECT zip_code FROM job_listings WHERE title = 'Web Developer'
  );

-- 4. 현재의 연봉이 가장 많은 사람과 같은 우편번호에 사는 모든 사람을 나열 (my_contacts, job_current)
-- 쪼개서 순서대로 구현
-- 연봉 가장 많은 사람
SELECT MAX(salary) FROM job_current;
-- 연봉 가장 많은 사람의 우편번호
SELECT mc.zip_code FROM my_contacts AS mc
INNER JOIN job_current AS jc
    ON mc.contact_id = jc.contact_id
WHERE jc.salary = (SELECT MAX(salary) FROM job_current);
-- 연봉 가장 많은 사람의 우편번호와 같은 사람들
SELECT mc.name FROM my_contacts AS mc
WHERE mc.zip_code = (SELECT mc.zip_code FROM my_contacts AS mc
    INNER JOIN job_current AS jc
        ON mc.contact_id = jc.contact_id
    WHERE jc.salary = (SELECT MAX(salary) FROM job_current)
);
```

#### 정리
- 외부쿼리
  - 내부쿼리, 즉 서브쿼리를 포함한 쿼리
- 내부쿼리 = 서브쿼리
  - 쿼리 안의 쿼리 (쿼리 안에 싸여진 쿼리)
- 비상관 서브쿼리
  - 외부쿼리의 내용을 참조하지 않고 독립적으로 수행할 수 있는 서브쿼리
- 상관 서브쿼리
  - 외부쿼리에서 반환된 값에 의존하는 서브쿼리

<br>

## 10. 외부 조인, 셀프 조인, 유니온
#### `조인이 서브쿼리보다는 훨씬 빠르다.`

- 자신을 참조하는 참조키 (SELF REFERENCE KEY)
  - 같은 테이블 내의 기본키를 가리키는 참조키로, 특정 목적에 이용됨
- 셀프 조인 (INNER JOIN)
  - 셀프 조인을 사용하여 하나의 테이블을 같은 정보의 테이블이 두 개 인 것처럼 쿼리를 실행 가능함
- 왼쪽 외부 조인 (LEFT OUTER JOIN)
  - 왼쪽 외부 조인은 왼쪽 테이블의 모든 행들을 가져다 오른쪽 테이블의 행에 대응시킴
- 오른쪽 외부 조인 (RIGHT OUTUER JOIN)
  - 오른쪽 외부 조인은 오른쪽 테이블의 모든 행을 가져다 왼쪽 테이블의 행에 대응시킴
- UNION, UNION ALL
  - UNION 은 SELECT 의 열 리스트를 기반으로 두 개 이상 쿼리의 결과를 합해 하나의 테이블에 나타냄
  - UNION 은 중복된 값들은 하나만 표시하고, UNION ALL 은 중복값을 모두 표시함
- CREATE TABLE AS
  - SELECT 문의 결과로 테이블을 만들 때 사용함
- INTERSECT
  - 첫번째 쿼리의 결과와 두번째 쿼리의 결과 모두에서 나오는 값들을 구할 때 사용
- EXCEPT
  - 첫번째 쿼리의 결과이면서 두번째 쿼리의 결과는 아닌 값들만을 구할 때 이 키워드 사용

### LEFT OUTER JOIN (왼쪽 외부 조인)
- 왼쪽 테이블의 `모든 행`을 가져다 오른쪽 테이블의 행과 비교한다.
- 왼쪽 테이블과 오른쪽 테이블이 일대다 관계에 있을 때 유용함
- 왼쪽 외부 조인에서는 FROM 뒤에 그리고 조인 전에 나오는 테이블이 왼쪽 테이블이고, 조인 뒤에 나오는 테이블이 오른쪽 테이블
- 외부조인 (OUTER JOIN)
  - 다른 테이블과 일치하는 것이 있는가에 상관없이 행을 반환함
    - 일치하는 것이 없으면 NULL 값으로 표시함
    - 소녀와 장난감의 경우, 결과가 NULL 값이면 특정 장난감이 어느 소녀한테도 속하지 않는다는 의미
- 왼쪽 외부 조인의 결과가 NULL 값이면, 
  - 오른쪽에 왼쪽 테이블에 상응하는 값이 없다는 의미

### RIGHT OUTER JOIN
- 오른쪽 외부 조인은 오른쪽 테이블을 왼쪽 테이블과 비교함
- 방향만 다르고 왼쪽 외부 조인과 같은 방식
- 그럼에도 오른쪽/왼쪽 외부 조인을 사용하는 이유는?
  - 쿼리에서 테이블의 순서를 변경하는 것보다 LEFT/RIGHT 바꾸는 것이 쉬움

#### ex)
**girls** (left table)

|girl_id|girl|toy_id|
|---|---|---|
|1|Jane|3|
|2|Sally|4|
|3|Cindy|1|

<br>

**toys** (right table)

|toy_id|toy|
|---|---|
|1|hula hoop|
|2|balsa glider|
|3|toy soldiers|
|4|harmonica|
|5|baseball cards|
|6|tinker toys|
|7|etch-a-sketch|
|8|slinky|

<br>

**result** of `LEFT OUTER JOIN`

|girl|toy|
|---|---|
|Cindy|hula hoop|
|Jane|toy soldiers|
|Sally|harmonica|

<br>

**result** of `RIGHT OUTER JOIN`

|girl|toy|
|---|---|
|Cindy|hula hoop|
|null|balsa glider|
|Jane|toy soldiers|
|Sally|harmonica|
|null|baseball cards|
|null|tinker toys|
|null|etch-a-sketch|
|null|slinky|

```mysql
SELECT g.girl, t.toy FROM girls g 
LEFT OUTER JOIN toys t 
    ON g.toy_id = t.toy_id;
```

### 자신을 참조하는 참조키 (OUTER, INNER, ...)
- 같은 테이블을 외부 조인의 오른쪽 테이블과 왼쪽 테이블로 사용할 수 있다.
- 같은 테이블에서 다른 목적으로 사용되는 테이블의 기본키

### 셀프 조인 (SELF JOIN)
- 정규화 된 데이터베이스에서, 같은 테이블을 두 개 사용할 일은 없음
  - 대신, 시뮬레이션하기 위해 셀프 조인을 사용
- SELF JOIN 을 통해서 하나의 테이블로 같은 정보를 가진 테이블이 두 개 있는 것처럼 쿼리를 보낼 수 있다.
```mysql
SELECT c1.name, c2.name AS boss
FROM clown_info c1
INNER JOIN clown_info c2
    ON c1.boss_id = c2.id;
```

### UNION
- 두 개 이상의 테이블을 묶는 방법으로 유니온을 쓴다.
  - 유니온은 SELECT 의 열 리스트를 바탕으로 두 개 이상 쿼리의 결과를 하나의 테이블로 합침
  - 유니온의 결과를 각 SELECT 의 값들이 `겹쳐진` 결과라고 보면 됨 (`합쳐진`)
- ORDER BY 는 마지막에 1개만 받을 수 있음 (여러 셀렉문의 결과를 합친 후 결과를 만들어 내기 때문에)
- 각 SELECT 문의 열 수는 같아야 함
- 각 SELECT 문에 표현식과 집계 함수도 같아야 함
- 순서는 무관함
- 유니온 결과에서 중복값은 하나로 나오는 것이 디폴트
- 열의 데이터 타입은 `같거나` 서로 `변환 가능한 값`
- 특별한 이유로 중복을 보고 싶으면 `UNION ALL` 을 사용하면 됨
```mysql
SELECT title FROM job_current
UNION
SELECT title FROM job_desired
UNION
SELECT title FROM job_listings
ORDER BY title;
```

#### UNION 으로 테이블 만들기 (CREATE TABLE AS)
- 유니온으로 반환되는 데이터 타입이 무엇인지 알기 쉽지 않음,
  - CREATE TABLE AS 문으로 유니온 결과를 테이블로 만들고 살펴본다.
```mysql
CREATE TABLE my_union AS
SELECT title FROM job_current
UNION
SELECT title FROM job_desired
UNION
SELECT title FROM job_listings
ORDER BY title;
```

### INTERSECT 와 EXCEPT
- 유니온과 거의 같은 방식으로 사용 됨
  - 겹치는 부분, 겹치지 않는 부분을 찾는다.
```mysql
-- 앞 뒤 테이블 모두에 있는 직업이름들만 나옴
SELECT title FROM job_current
INTERSECT
SELECT title FROM job_desired;

-- 먼저 나온 테이블의 직업이름 중, 뒤의 테이블에 없는 직업이름들만 나옴
SELECT title FROM job_current
EXCEPT
SELECT title FROM job_desired;
```

### 서브쿼리를 조인으로 바꾼다.
- 대부분의 경우 조인이 빠르게 실행 됨 
- 조인이 이해하기 쉬움(?)
- 조인은 SELECT 에 only, 서브쿼리는 CRUD 가능
```mysql
-- subquery
SELECT mc.first_name 
     , mc.last_name 
     , mc.phone
     , jc.title
  FROM job_current AS jc
NATURAL JOIN my_contacts AS mc
WHERE jc.title IN (SELECT title FROM job_listings);

-- join (subquery 를 포함한 WHERE 절은 내부조인으로 대체 가능함)
SELECT mc.first_name, mc.last_name, mc.phone, jc.title
FROM job_current AS jc
INNER JOIN my_contacts AS mc
    ON jc.contact_id = mc.contact_id
INNER JOIN job_listings AS jl
    ON jc.title = jl.title;
```

### 셀프조인을 서브쿼리로 바꾼다. (ex)
- 셀프조인을 서브쿼리로 바꿀 때, 
  - 서브쿼리가 외부쿼리의 결과에 의존하기 때문에, 아래 서브 쿼리는 상관쿼리 
  - 서브쿼리는 열 리스트의 자리에 위치함
```mysql
-- inner join
SELECT c1.name, c2.name AS boss
FROM clown_info c1
INNER JOIN clown_info c2
    ON c1.boss_id = c2.id;
    
-- subquery
SELECT c1.name, 
    (SELECT name FROM clown_info WHERE c1.boss_id = id) AS boss
FROM clown_info AS c1;
```

## 11. 제약사항, 뷰, 트랜잭션
## 12. 보안