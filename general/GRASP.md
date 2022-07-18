# GRASP (General Responsibility Assignment Software Pattern) 에 관하여..

### 책임 기반 객체지향 관점에서 객체에 책임을 할당하기 위한 패턴을 정리한 것

---

#### Information Expert
- 역할을 수행할 수 있는 정보를 가지고 있는 객체에 역할을 부여한다.
  - Problem: What is a basic principle by which to assign responsibilities to objects?
  - Solution: Assign a responsibility to the class that has the information needed to fulfill it.

<br>

#### Creator
- 객체의 생성은, 생성되는 객체의 컨텍스트를 알고 있는 다른 객체가 있다면 컨텍스트를 알고 있는 객체 부여한다.
- 포함하거나 라이프사이클을 공유하고 데이터 내용을 알 정도로 가까운 관계인 경우   
  - Problem: Who creates object A?
  - Solution: Assign class B the responsibility to create object A if one of these is true (more is better)
    -  B contains or compositely aggregates A
    -  B records A
    -  B closely uses A
    -  B has the initializing data for A
  <br>

#### Controller
- 시스템 이벤트(사용자의 요청)를 처리할 객체를 만든다. 시스템, 서브시스템으로 들어오는 외부 요청을 처리하는 객체를 만들어 사용한다.
- 돌고 있는 SW, 혹은 주요한 서브시스템의 전체 시스템이나 루트 객체를 표현한다. (퍼사드 컨트롤러의 범주)
- 유즈케이스 시나리오에서 시스템 동작이 발생하는 것을 표현하도록 한다.
  - Problem: What first object beyond the UI layer receives and coordinates “controls” a system operation?
  - Solution: Assign the responsibility to an object representing one of these choices:
    - Represents the overall “system”, “root object”, device that the software is running within, or a major subsystem (these are all variations of a facade controller)
    - Represents a use case scenario within which the system operation occurs (a use case or session controller)

<br>

#### Low Coupling
- 객체들간, 서브 시스템들간의 상호의존도가 낮게 역할을 부여한다.
  - Problem: How to reduce the impact of change? How to support low dependency and increased reuse?
  - Solution: Assign responsibilities so that (unnecessary) coupling remains low. Use this principle to evaluate alternatives.

<br>

#### High Cohesion
- 각 객체가 밀접하게 연관된 역할들만 가지도록 역할을 부여한다.
  - Problem: How to keep objects focused, understandable, manageable and as a side effect support Low Coupling?
  - Solution: Assign a responsibility so that cohesion remains high. Use this to evaluate alternatives.

<br>

#### Indirection
- 두 객체 사이의 직접적인 Coupling 을 피하고 싶으면, 그 사이에 다른 객체를 사용한다.
  - Problem: Where to assign a responsibility to avoid direct coupling between two or more things?
  - Solution: Assign the responsibility to an intermediate object to mediate between other components or services so that they are not directly coupled.

<br>

#### Polymorphism
- 객체의 종류에 따라 행동양식이 바뀌면, Polymorphism 을 사용한다.
  - Problem: How handle alternatives based on type?
  - Solution: When related alternatives or behaviors vary by type (class), assign responsibility for the behavior (using polymorphic operations) to the types for which the behavior varies.

<br>

#### Pure Fabrication
- Information Expert 등의 다른 패턴을 적용 시 Low Coupling 과 High Cohesion 원칙이 깨진다면, 기능적인 역할을 별도의 한 곳으로 모은다.
  - Problem: What object should have the responsibility, when you do not want to viloate High Cohesion and Low Coupling but solutions offered by other principles are not appopriate?>
  - Solution: Assign a highly cohesive set of responsibilites to an artifical or convenience class that does not represent a problem domain conecept.

<br>

#### Protected Variations
- 변경될 여지가 있는 곳에 안정된 인터페이스를 정의해서 책임을 부여한다.
  - Problem: How to design objects, subsystems and systems so that the variations or instability in these elements does not have an undesirable impact on other elements?
  - Solution: Identify points of predicted variation or instability, assign responsibilities to create a stable interface around them.

<br>

---

#### Reference
- http://www.kamilgrzybek.com/design/grasp-explained/
- https://edu.nextstep.camp/
