# Spring 에서 Bean, 그리고 그 Bean 의 scope 에 관하여..

> 목록  

주요 내용이 Bean scope 의 singleton 과 prototype 이고,  
그 사용 환경에 대한 것이라서 순서를 가장 위로 올려두었다.

- Bean scope 중, singleton vs prototype 언제 사용하는가?
  
- Spring 의 Bean 이란?

- Bean 으로 설정하는 방법은?

- Bean 의 scope 종류

---
### Bean scope 중, singleton vs prototype 언제 사용하는가?
> 포인트는 생성과 동기화의 비용을 저울질 해봐야 한다.
- singleton 과 prototype 둘 다 디자인패턴의 이름인데, 그에 맞게 동작한다.  
  객체 참조 시 객체가 1개인 것을 보장하는 것 (singleton) 과, 객체 참조 시 객체를 clone 해서 생성하는 것 (prototype)
- singleton
    - 상태가 없어서 동기화 비용이 없는 객체
    - 읽기 전용인 객체
    - 공유가 필요한 상태를 지닌 공유 객체 (동기화를 하더라도)
    - 쓰기가 가능한데 사용빈도가 매우 높은 객체 (생성 비용이 클 때)
- prototype
    - 쓰기가 가능한 상태를 가진 객체 (상태가 많아서 동기화 비용이 객체 생성 비용보다 클 때)
    - 다른 의존 객체와 독립적인 작업 수행하는 의존 객체가 있는 경우

### Spring 의 Bean 이란?
- Spring IoC Container 에 의해서 관리되는 Java Object 를 말한다.
- Spring Bean Container 에 존재한다.
- POJO (Plain Old Java Object) 를 Beans 라고 한다.

### Bean 으로 설정하는 방법은?
- Annotation 을 통해서 해당 클래스를 Bean 으로 등록한다. (@Bean, @Component, @Repository, @Service, @Controller)
- Configuration 을 이용해서 등록한다.
    - @Configuration 을 표기한 클래스를 통해서 그 내부에 @Bean 을 등록하는 방법
    - XML 파일에 직접 기입한다.

### Bean 을 의존주입(DI) 하는 Annotation
> 자세한 주입별 차이점 내용은 추후에 추가
- @Autowired
- @Resource
- @Value
- @Qualifier

### Bean 의 scope 종류
- singleton: Bean 은 IoC Container 내에 1개 존재한다.
- prototype: Bean 은 다수의 객체 존재 가능하다.
#### 이하 일반 Spring Application 이 아니라, Spring MVC Web Application 에서만 사용 됨  
- request: Bean 은 하나의 HTTP request life cycle 안에 1개 객체 존재한다.  
  각각의 HTTP request는 자신만의 객체를 갖는다.
- session: Bean 은 하나의 HTTP session life cycle 안에 1개 객체 존재한다.
- global session: Bean 은 하나의 global HTTP session life cycle 안에 1개 객체 존재한다. 

