# 자바의 final 키워드에 관하여..

- 인터뷰에서 버벅대서 정리한다.

> 자바에서 final 키워드를 사용할 수 있는 곳에는,  
> 
> class, method, variable 에 사용할 수 있다.

### 사용하는 이유는?
- Immutable (불변)  
 -> 따라서 쓰기가 불가하다. (read-only)
  
#### 1. 클래스를 상속받을 수 없도록 만든다.


#### 2. 메서드를 override 가 불가하도록 만든다.


#### 3. 변수를 read-only 상수로 만든다.

> 가독성을 해치지 않는 범위에서 써야한다고 하는데,  
> 개인적으로는 불변해야 하는 엔티티를 정확히 지정해주고 명시해준다는 점에서 가독성이 더 좋아지는 경우가 있다고 본다.  
> 그리고.. 상속해서 공통으로 사용하는 변수의 경우 등, 변하지 않아야 하는 내용에 붙여서 버그를 방지할 수 있다.

### 요새는 IDE 설정을 통해서 기본적으로 final 을 붙이는 것이 좋은 방향으로 보인다.