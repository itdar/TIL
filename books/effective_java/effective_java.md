# Effective Java 2/E

1. [서론](#1장.-서론)
2. [객체의 생성과 삭제](#2장.-객체의-생성과-삭제)
3. [모든 객체의 공통 메서드](#3장.-모든-객체의-공통-메서드)
   - [규칙9. equals를 재정의할 때는 반드시 hashCode도 재정의하라](#규칙9.-equals를-재정의할-때는-반드시-hashCode도-재정의하라)
4. [클래스와 인터페이스](#4장.-클래스와-인터페이스)
   - [규칙13. 클래스와 멤버의 접근 권한은 최소화하라](#규칙13.-클래스와-멤버의-접근-권한은-최소화하라)
   - [규칙14. public 클래스 안에는 public 필드를 두지 말고 접근자 메서드를 사용하라](#규칙14.-public-클래스-안에는-public-필드를-두지-말고-접근자-메서드를-사용하라)
   - [규칙19. 인터페이스는 자료형을 정의할 때만 사용하라](#규칙19.-인터페이스는-자료형을-정의할-때만-사용하라)
5. [제네릭](#5장.-제네릭)
6. [열거형(enum)과 어노테이션](#6장.-열거형(enum)과-어노테이션)
7. [메서드](#7장.-메서드)
   - [규칙43. null 대신 빈 배열이나 컬렉션을 반환하라](#규칙43.-null-대신-빈-배열이나-컬렉션을-반환하라)
8. [일반적인 프로그래밍 원칙들](#8장.-일반적인-프로그래밍-원칙들)
9. [예외](#9장.-예외)
10. [병행성](#10장.-병행성)
11. [직렬화](#11장.-직렬화)

---

## 1장. 서론


---
## 2장. 객체의 생성과 삭제


---
## 3장. 모든 객체의 공통 메서드

#### 규칙9. equals를 재정의할 때는 반드시 hashCode도 재정의하라
- 같은 객체는 같은 해시코드 값을 가져야 한다.
- equals(Object) 메서드가 같다고 판정한 두 객체의 hashCode 값은 같아야 한다.
- equals(Object) 메서드가 다르다고 판정한 두 객체의 hashCode 값은 꼭 다를 필요는 없다.
  - 그러나 서로 다른 hashCode 값이 나오면 해시테이블 성능이 향상될 수 있다.
  - 같은 값의 다른 객체의 경우, 다른 해시값을 가지면 성능이 향상 되는 경우.
- 해시 함수는 다른 객체에는 다른 해시 코드를 반환하도록 해야 한다.
- hashCode 구현이 끝나면, 동치 관계의 객체 해시코드 값이 똑같이 계산되는지 확인한다.
  - 객체의 동치 관계: (객체 == 객체)
- equals 계산에 쓰이지 않는 필드는 **반드시** 제외해서 해시 함수를 만든다.
- 해시코드 계산 비용이 높은 변경 불가능 클래스는 객체 안에 캐시해 둘 수 있다.
  - 최초 호출까지 해시코드 초기화를 늦출 수도 있다. (lazy initialization)
```java
// lazy initialization
private volatile int hashCode;

@Override public int hashCode() {
    int result = hashCode;
    if (result == 0) {
        result = 17;
        result = 31 * result + field1;
        result = 31 * result + field2;
        hashCode = result;
    }
    return result;
}
```
- **주의할 점**: 객체의 중요 부분을 해시 코드 계산 과정에 생략하면 안된다.


---
## 4장. 클래스와 인터페이스

#### 규칙13. 클래스와 멤버의 접근 권한은 최소화하라
* 잘 설계된 모듈은 구현 세부사항을 전부 API 뒤쪽에 감춘다.
  * 정보은닉(information hiding) 또는 캡슐화(encapsulation)
* 정보은닉이 중요한 이유는: 모듈 사이의 의존성을 낮춰서(decouple) 
  * 개별적으로 개발, 시험, 최적화, 이해, 변경할 수 있게 한다.
  * 재사용 가능성을 높인다.
* 단순한 원칙: **각 클래스와 멤버는 가능한 한 접근 불가능하도록 만든다**
  * 최상위 레벨 클래스와 인터페이스에 부여할 수 있는 접근 권한은 package-private과 public 두가지인데, 
   가능한 package-private으로 해야한다.
  * 멤버들 중 private과 package-private 멤버들은 구현 세부사항이고 공개 API가 아니지만,
   Serializable을 구현하는 클래스의 멤버라면 공개 API 속으로 새어나갈 수 있다. (leak)
  * public 클래스의 멤버들의 경우, package-private 보다 protected 는 사용 범위가 매우 넓아진다. 자제한다.
* 상위 클래스 메서드를 재정의 할 때는 원래 메서드의 접근 권한보다 낮은 권한 설정이 안된다. (컴파일 에러)
* 테스트 때문에 인터페이스, 또는 멤버들의 접근 권한을 열 때도 어느 선까지는 괜찮으나, package-private 정도 까지만 한다.
* 객체(instance) 필드는 절대 public 선언 안된다.
  * 필드 저장 값을 제한할 수 없고, 따라서 불변식 강제가 안된다. 변경 시 특정 동작 실행도 안된다.
  * 변경 가능 public 필드를 가진 클래스는 다중 스레드에 안전하지 않다.
* 길이가 0 아닌 배열은 변경 가능하므로, public static final 배열 필드를 두거나,
 배열 필드를 반환하는 접근자(accesor)를 정의하면 안된다.
  * 이런 배열은 클라이언트가 배열 내용을 변경할 수 있게 되므로, 보안에 문제가 생긴다.
~~~java
// 보안 문제가 생길 수 있는 코드
public static final Thing[] VALUE = { ... };

///

// 해결방법 1
private static final Thing[] PRIVATE_VALUES = { ... };
public static final List<Thing> VALUES = Collections.unmodifiableList(Arrays.asList(PRIVATE_VALUES));

///

// 해결방법 2
private static final Thing[] PRIVATE_VALUES = { ... };
public static final Thing[] values() {
    return PRIVATE_VALUES.clone();
}

// 선택의 기준은, 클라이언트 측에서 어떤 자료형을 선호할 지, 성능은 뭐가 나을지.
~~~

* 요약하면,
  * 접근 권한은 낮춰라
  * 최소한의 public API 를 설계하고, 다른 모든 클래스/인터페이스/멤버 는 API 에서 제외하라
  * public static final 필드를 제외하고 public 필드는 없어야 한다.
    * public static final 필드가 참조하는 객체는 변경 불가능한 객체로 만든다.
---

#### 규칙14. public 클래스 안에는 public 필드를 두지 말고 접근자 메서드를 사용하라

- public 클래스에서 public 필드를 두는 것은 쓰레기
  - 데이터 캡슐화가 필요하다. 
  - private 으로 바꾸고 public 접근자 메서드 (getter), and 필요하다면 수정자(mutator) 메서드 (setter) 를 만들어 쓴다.
- package-private 클래스나, private 중첩클래스(nested class)는 데이터 필드를 공개해도 잘못이라 할 수 없다.
  - 클래스가 추상화하려는 내용을 제대로 기술하기만 한다면.
  - 클래스 정의나 클라이언트 코드나 getter 메서드보다 시각적으로 깔끔해 보인다.
  - package-private: 클래스 내부 표현을 변경해도 패키지 외부 코드는 변경되지 않는다.
  - private (nested class): 클래스 바깥 클래스 외부의 코드는 영향받지 않는다.
- 변경불가능한(immutable) 객체는 그나마 심각성이 덜하다.

* 요약하면,
  * public 클래스는 변경 가능 필드를 외부로 공개하면 안된다.
  * 변경 불가능 필드인 경우에는 외부로 공개해도 많이 위험하진 않지만, 그럴 필요는 없을 듯 하다.
  * but, package-private, private nested class 의 필드는 변경 가능 여부와 상관없이 외부 공개가 바람직할 때도 있다.

#### 규칙19. 인터페이스는 자료형을 정의할 때만 사용하라
- 인터페이스를 구현해 클래스를 만드는 것은 -> 해당 클래스의 객체로 어떤 일을 할 지 클라이언트에게 알리는 행위이다.
- 메서드 없이, static final 필드만 있는 상수 인터페이스 (constant interface) 는 위 기준에 미달한다.
  - 상수 인터페이스 패턴은 인터페이스를 잘못 사용한 것
~~~java
// 상수 유틸리티 인터페이스 안티패턴의 예 -> 사용 X
public interface PhysicalConstants {
    // 아보가드로 수(1/mol)
    static final double AVOGADROS_NUMBER = 6.02214199e23;
    
    // ...
}
~~~
- 상수를 API 일부로 공개하고 싶을 때
    - 해당 상수가 이미 존재하는 클래스나 인터페이스에 강하게 연결되어 있는 경우,
      - 상수들을 해당 클래스나 인터페이스에 추가한다.
      - 예를 들면, Integer/Double 등 에는 MIN_VALUE/MAX_VALUE 상수가 공개되어 있다.
      - 혹은 enum 자료형의 멤버가 되어야 할 때는 enum 자료형과 함께 공개한다. (규칙30)
    - 연결된 것이 없을 때는,
      - 객체 생성이 불가능한 유틸리티 클래스(규칙4) 에 넣어서 공개한다. (아래 코드 예시)
~~~java
// 상수 유틸리티 클래스
package com.itdar.science;

public class PhysicalConstants {
    private PhysicalConstants() { }
    
    public static final double AVOGADROS_NUMBER = 6.0221499e23;
    // ...
}
~~~
- 유틸리티 클래스를 사용 시, JDK1.5 부터는 static import 를 사용해서 클래스 이름을 제거 가능하다.

* 요약하면,
  * 인터페이스는 자료형을 정의할 때만 사용해야 한다. 특정 상수를 API 일부로 공개할 목적으로는 적절치 않다.
---
## 5장. 제네릭


---
## 6장. 열거형(enum)과 어노테이션


---
## 7장. 메서드
#### 규칙43. null 대신 빈 배열이나 컬렉션을 반환하라
- 어느 상황에 null 을 반환하게 해두면 클라이언트 입장에서는 그에 대비한 코드를 만들게 된다.
- 빈 배열이나 컬렉션 대신 null 을 반환하는 메서드는 구현하기도 더 까다롭다.
- null 을 반환하는 것으로 배열 할당 비용을 피한다?
  - 프로파일링 결과로 해당 메서드가 성능 저하의 원인인 것이 확인 된다면 인정
  - 길이가 0 인 배열은 변경 불가능(immutable) 하므로 아무 제약 없이 재사용 할 수 있다.
~~~java
// 컬렉션에서 배열을 만들어 반환하는 올바른 방법
private final List<Cheese> cheesesInStock = ...;
private static final Cheese[] EMPTY_CHEESE_ARRAY = new Cheese[0];

/**
 * return 재고가 남은 모든 치즈 목록을 배열로 만들어 반환
 */
public Cheese[] getCheeses() {
    // 컬렉션이 비어있는 경우, 인자로 주어진 빈 배열을 쓴다.
    return cheesesInStock.toArray(EMPTY_CHEESE_ARRAY);
}
~~~
~~~java
// 컬렉션 복사본을 반환하는 올바른 방법
public List<Cheese> getCheeseList() {
    if (cheesesInStock.isEmpty()) {
        return Collections.emptyList();
    } else {
        return new ArrayList<Cheese>(cheesesInStock);
    }
}
~~~

* 어쨌든 요약하면,
  * null 대신에 빈 배열이나 빈 컬렉션을 반환하라

* null 반환은 c 언어의 관습으로, c 에서는 배열 길이가 배열과 따로 반환되어서,  
 길이가 0 인 배열을 할당해서 반환해도 아무 이득이 없다.
---
## 8장. 일반적인 프로그래밍 원칙들


---
## 9장. 예외


---
## 10장. 병행성


---
## 11장. 직렬화


---

### Reference
- Effective Java 2/E


