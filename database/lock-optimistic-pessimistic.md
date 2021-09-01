# Optimistic lock(낙관적 잠금), Pessimistic lock(비관적 잠금) 에 관하여..

### 우선 데이터베이스의 격리수준(Isolation level) 에 대하여 알아야 한다.

[트랜잭션 격리수준에 관하여(MySQL).. https://itdar.tistory.com/389](https://itdar.tistory.com/389)

---

> 목차

- 각각 어느 상황에서 사용하는 것이 좋을지?

- Lock 을 하는 이유는?

- Optimistic lock (낙관적 잠금) 이란?

- Pessimistic lock (비관적 잠금) 이란?



---

### 각각 어느 상황에서 사용하는 것이 좋을지?
- Optimistic lock (낙관적 잠금)
    - 동시 레코드 업데이트가 드물거나, lock 오버헤드가 높은 것으로 예상될 때 사용한다.
    - 데이터베이스에 연결을 유지하고 있을 필요가 없는 대용량 시스템, 3계층 시스템 등 (클라이언트가 pool 에서 연결을 가져와서 lock 을 유지할 수 없음)
    - 낙관적으로 바라보는 곳에 사용한다 (Data 충돌이 거의 일어나지 않을 것이라는 가정을 한다)
    - 아예 데이터 충돌이 나서 dirty read 가 되더라도 무관한 곳에 사용한다.
- Pessimistic lock (비관적 잠금)
    - 이전 업데이트, 다음 업데이트 직렬적으로 대기 할 수 있는 경우 사용할 수 있다. (짧은 업데이트 시간간격)
    - 데이터베이스에 직접 연결하는 시스템, 2계층 시스템 등
    - 비관적으로 바라보는 곳에 사용한다 (Data 충돌이 분명 일어날 것이라는 가정을 한다)
    - 금융 관련 등 항상 정확해야 하는 곳에 사용한다.

### Lock 을 하는 이유는?
- Database 가 Concurrency control (동시성 제어) 을 할 때,  
  트랜잭션의 격리수준과 비지니스 로직에 맞추어 Lock 을 하여
  데이터 충돌이 나지 않도록 하고,  
  충돌이 났다면 문제를 뒷처리를 하기 위함이다.

### Optimistic lock (낙관적 잠금) 이란?
- 레코드(데이터) 잠금을 사용하지 않는 RDBMS 에서 사용되는 동시성 제어 방법이다.
- 여러 트랜잭션이 동일한 데이터에 업데이트를 시도할 수 있고, 커밋할 때만 유효성이 검사된다.
- 데이터베이스 수준의 Roll-back 이 없기에, 충돌 시 대처방안을 구현해야함
- Application level (JPA 등) 에서 동작한다.
    - version 등의 컬럼을 추가해서 여러 트랜잭션 내에서 하나의 데이터에 중복 업데이트를 확인한다.

### Pessimistic lock (비관적 잠금) 이란?
- 레코드(데이터) 에 대한 동시 업데이트를 방지한다.
- 하나의 트랜잭션이 레코드를 업데이트하기 시작하자마자 잠금이 설정된다.
    - 다음 사용자는 대기한다.
- Database level 의 transaction 을 이용한다.

---

#### Reference
- https://www.ibm.com/docs/en/rational-clearquest/7.1.0?topic=clearquest-optimistic-pessimistic-record-locking
- https://stackoverflow.com/questions/129329/optimistic-vs-pessimistic-locking
- https://sabarada.tistory.com/175
- https://velog.io/@lsb156/JPA-Optimistic-Lock-Pessimistic-Lock

나중에 https://stackoverflow.com/questions/129329/optimistic-vs-pessimistic-locking  
위 질답은 전체 번역해보고 싶다. 