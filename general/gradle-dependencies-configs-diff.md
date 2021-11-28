# Gradle 의존 키워드의 차이점 (compile vs implementation + api, Gradle dependency configurations diff)
 
- 배경
- 각각의 특징 및 차이점
- Reference

---

## 배경
- gradle 3.0 부터 compile 키워드가 deprecated 되었다.

---

## 각각의 특징 및 차이점
- api 또는 compile
  - 가장 상위의 의존을 시작하는 app 에서, 직접 의존하고 있는 것이 의존하고 있는 하위의 간접 의존에도 접근이 가능하다.
- implementation
  - 가장 상위의 의존을 시작하는 app 에서, 직접 의존하는 것만 가능하다.

---
## 추천 사용법
~~~
api : app 에서 직접의존+간접의존 하는 내용을 전부 보여주고 싶다면
implementation : app 에서 직접의존 하는 내용만 보여주고 싶다면
~~~
- compile 은 implementation 또는 api 로 변경하여 사용한다.
- testCompile 은 testImplementation 으로,
- debugCompile 은 debugImplementation 으로,
- androidTestCompile 은 androidTestImplementation 으로,
- compileOnly 는 유효함
---
## Reference
https://stackoverflow.com/questions/44493378/whats-the-difference-between-implementation-api-and-compile-in-gradle
