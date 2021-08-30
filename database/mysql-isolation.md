# [MySQL] 데이터베이스 트랜잭션의 격리수준에 관하여.. (Database transaction isolation level)

> 목차

트랜잭션 격리수준 이란 무엇인가?

격리수준에 따라 발생할 수 있는 문제점들은?
  
격리수준의 종류는?

격리수준에 따른 문제 발생 표

---

### 트랜잭션 격리수준 이란 무엇인가? (Transaction isolation level)
- 각 트랜잭션 사이의 격리된 정도
    - 같은 데이터에 여러 트랜잭션이 겹쳤을 경우, 어떤 상태로 읽을지를 결정한다.
- 비지니스 모델에 따라서, 어떤 결과가 나오는지에 맞춰서 격리수준을 결정한다.

### 격리수준에 따라 발생할 수 있는 문제점들은? (부정합 문제 Problems)
- Dirty read
    - A 트랙잭션이 데이터를 핸들링하고 있는데, B 트랜잭션에서 해당 데이터를 읽을 수 있는 것을 칭한다.  
    - Commit / Roll-back 에 따라서 데이터의 부정합이 발생하기 쉽다.

- Non-repeatable read
    - 하나의 트랜잭션에서 데이터를 읽는데, 때에 따라서 값이 달라질 수 있는 경우를 말한다.  
    - 다른 트랜잭션과 겹쳤을 때 데이터가 Undo 영역 등에 있다가 Commit 되거나 하면 해당 문제가 생길 수 있다.
  
- Phantom read
    - 하나의 트랜잭션에서 데이터를 읽을 때, 데이터가 없다가 생기는 경우를 말한다.  
    - 있다가 사라지는 경우도 같이 칭하는기도 한다?

### 격리수준의 종류는? (Sort of Isolation level)
- Read uncomitted
    - A 트랜잭션이 데이터를 핸들링하고 있을 때, B 트랜잭션에서 해당 데이터를 직접 접근해서 읽는 것을 칭한다.  
    - 읽었는데 문제가 생겨서 rollback 을 하는 경우 등의 데이터의 부정합이 발생하기 쉽다.
 
- Read comitted
    - 트랜잭션에서 변경된 데이터는 Commit 이 완료되어야만 읽을 수 있다.   
    - A 트랜잭션이 데이터를 핸들링하고 있을 때 B 트랜잭션에서 읽는다면, 해당 데이터의 commit 되기 전에는 undo 영역에서 데이터를 읽는다.  
    - A 트랜잭션에서 어떤 변화를 일으켜도, commit 전에는 B 트랜잭션에서 undo 영역의 기존 데이터를 읽으므로 Dirty read 문제가 없다.  
    하지만, A 트랜잭션은 Commit 을 하고, B 트랜잭션은 그 이전부터 이후까지 지속한다면 시작과 끝의 값이 다른 문제 (Non-repeatable read) 가 생길 수 있다.  
  
- Repeatable read
    - 트랜잭션에 순차적으로 고유의 트랜잭션 번호를 부여한다. 접근하는 데이터의 트랜잭션 번호와 비교해서, 진행 중인 트랜잭션의 번호보다 뒤에 있으면 백업되어있는 (Undo 영역) 데이터를 읽도록 한다.   
    - 트랜잭션이 오래 지속되면 Undo 영역 사이즈가 커져서 성능 이슈가 생길 수 있다.  
    - Phantom read 문제는 발생 가능하다.  

- Serializable
    - 하나의 트랜잭션이 진행되고 있는 동안에는 다른 트랜잭션이 접근 할 수 없다.  
     즉, Serialize 하게 진행된다는 것으로, 따라서 동시성(Parallel) 하게 진행되어야 하는 성능이 중요한 곳은 사용 불가하다.


### 격리수준에 따른 문제발생 표 (Comparison table)
| |Dirty read|Non-Repeatable read|Phantom read| |
|:---:|:---:|:---:|:---:|---|
|Read uncomitted|O|O|O| 거의 사용안함 |
|Read comitted  |X|O|O| |
|Repeatable read|X|X|O (InnoDB는 X)| |
|Serializable   |X|X|X| 동시성이 중요한 곳에선 사용 못함 (성능저하) |