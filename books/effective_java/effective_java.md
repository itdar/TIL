# Effective Java 2/E

1. [서론](#1장.-서론)
2. [객체의 생성과 삭제](#2장.-객체의-생성과-삭제)
3. [모든 객체의 공통 메서드](#3장.-모든-객체의-공통-메서드)
   - [규칙9. equals를 재정의할 때는 반드시 hashCode도 재정의하라](#규칙9.-equals를-재정의할-때는-반드시-hashCode도-재정의하라)
4. [클래스와 인터페이스](#4장.-클래스와-인터페이스)
   - [규칙13. 클래스와 멤버의 접근 권한은 최소화하라](#규칙13.-클래스와-멤버의-접근-권한은-최소화하라)
   - [규칙14. public 클래스 안에는 public 필드를 두지 말고 접근자 메서드를 사용하라](#규칙14.-public-클래스-안에는-public-필드를-두지-말고-접근자-메서드를-사용하라)
5. [제네릭](#5장.-제네릭)
6. [열거형(enum)과 어노테이션](#6장.-열거형(enum)과-어노테이션)
7. [메서드](#7장.-메서드)
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

####

---
## 5장. 제네릭


---
## 6장. 열거형(enum)과 어노테이션


---
## 7장. 메서드


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


