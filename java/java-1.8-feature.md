## Java 1.8 에서 달라진 것들은?

```
모던자바인액션 책을 보고 정리를 안했었는데..
그 내용과, 관련 인프런 강의를 정리한 블로그를 참조해서 다시 정리함
```

1. Lambda 표현식: 익명함수를 단순화한 표현문법, 근데 → 컴파일 시 동작방식이 다름
   1. 익명함수의 경우는 별도의 클래스파일이 생기지만, 람다식은 별도의 클래스파일이 안생김
   2. 람다식은 내부함수로 컴파일 해두고 invokedynamic 을 통해서 런타임으로 사용 전환이 미루어진다.
   3. 이 때, 람다 바디에서는 파라미터 말고 바디 외부 변수를 참조 가능한데.. (자유변수, free variable → lambda capturing)
   4. 지역변수는 final 이거나 final 처럼 동작해야한다. → 값 재할당 안됨
   5. 이유는 → 람다가 자신의 스택으로 다른 쓰레드 스택의 변수 값을 복사해다가 사용하기 때문이다. → 그래서 다른 쓰레드가 사라져도 에러가 없고, 인스턴스는 어차피 힙에 있는거 쓰는거라서 문제가 없다.
 
<br> 

2. 함수형인터페이스, FunctionalInterface
   1. 1개의 추상메서드를 갖는 인터페이스
   2. 람다표현식에서 간단하게 표현 가능하다.

<br> 

3. 디폴트메서드, default
   1. 인터페이스에서 구현된 메서드 사용 가능
   2. 코드 호환성 유지하면서 새로운 기능 추가 가능

<br> 

4. 스트림, stream
   1. 콜렉션에 대한 처리를 직관적으로 가능
   2. 메서드체이닝으로 파이프라이닝 가능 (스트림객체를 연속 처리)
   3. 재사용이 안되는 소비중심이기 때문에, 필요 시 스트림을 따로 변수에 저장해서 재사용한다.
   4. 함수형인터페이스와 람다표현식은 결국 스트림을 통한 함수형프로그래밍을 제공하기 위함

<br> 

5. 옵셔널, Optional
   1. NPE 를 보완하기 위해 반환객체를 래핑한다.
   2. null 이 나올 수 있다는 것을 명시한다.
   3. 방어코드 제거로 로직 파악이 쉽다.

<br> 

7. 날짜 API
   1. Instant, LocalDate, LocalTime, LocalDateTime, Duration, Period, DateTimeFormatter → 전부 불변객체
   2. Instant: Unix Epoch time 기준 시간을 초로 표현
   3. LocalDateTime: 직관적이지 않던 것들 개선 (1월을 0으로 표현 등)
   4. Duration, Period: 시간의 차이, 날짜의 차이
   5. DateTimeFormatter: 날짜 포맷 상수 등록된 것 사용 또는 포맷 변환 가능

<br> 

8. CompletableFuture
   1. Java 5 의 ExecutorService 와 Future 이 후에 나온 것
   2. Future interface 의 구현체인데, 여러 스레드를 묶어 처리에 용이하고, 콜백도 지원, 예외 처리도 지원함
   3. CompletableFuture.supplyAsync() → thenCombine((), ()), 순서필요하면 thenCompose() → exceptionally(); → 콜백이 필요하면 .thenApply();

<br> 

9. JVM
   1. Metaspace 영역이 생겼다. 메모리 옵션에서 PermanantGeneration 영역이 사라짐.
   2. 기존 PermGen 영역은 클래스 메타정보 관리하는 메모리공간 → 클래스이름, 애노테이션, static 필드 등 클래스 구성정보 → 근데 이게 힙에 포함되어 있어서 크기가 제한됨 → 그래서 클래스가 많은 애플리케이션 구동하면 PermGen 영역 OOM 발생이 있었음
   3. 동일 기능하는 Metaspace 가 생김 → Native 영역에서 OS 가 크기를 자동으로 조정함 → 가변적으로 늘어나서 동적으로 클래스가 많이 생기더라도 OOM 이 발생할 여지가 크게 줄어든다. → MaxMetaspaceSize 옵션은 반드시 넣어야 최악의 경우 서버가 죽는걸 방지함

---

#### Reference
- 모던 자바 인 액션 (책)
- https://bbubbush.tistory.com/23
- https://perfectacle.github.io/2019/06/30/java-8-lambda-capturing/
- https://ntalbs.github.io/2019/java-lambda/
