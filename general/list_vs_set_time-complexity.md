# List vs Set, Time complexity

- 보통 2개의 다른 콜렉션 등에서 같은 것들을 찾는 등의 동작은 List 의 경우는 n^2 시간복잡도이다 (빅오)
  - 그래서 중복이 없다면 기준이 되는 콜렉션을 Set 으로 사용하면 n 시간복잡도로 해결 할 수 있다.
- UUID 교집합을 골라내는 작업 중 최적화가 필요한 부분이 있었는데, List 두개를 retainAll() 을 사용해서 구하고 있었다.
  - n^2 의 경우에 약 50만^2 정도의 연산횟수가 필요했는데 
  - retainAll() 을 사용할 필요없이 기준을 Set 으로 해서 O(n) 으로 마칠 수 있을 것 같다는 의견을 전달했다.
- 최초 내 생각은 Set 을 사용하되 retainAll 에 해당하는 메서드를 만들면 된다는 말이었지만
  - Set, List 로 기존 콜렉션 메서드 retainAll 을 사용해도 이미 최적화된 결과가 나왔다.

```java
@Test
void testest() {
    List<String> a = new ArrayList<>();
    List<String> b = new ArrayList<>();

    // setUp
    for (int i = 0; i < 500000; i++) {
        a.add(UUID.randomUUID().toString());
    }

    for (int i = 0; i < 10000; i++) {
        b.add(a.get(i+10000));
    }

    // List
    long start = System.currentTimeMillis();

    a.retainAll(b);

    long finish = System.currentTimeMillis();
    long timeElapsed = finish - start;

    System.out.println(" List retainAll() >> " + timeElapsed / 1000 + " sec");

    // Set
    Set<String> set = new HashSet<>(a);

    start = System.currentTimeMillis();

    set.retainAll(b);

    finish = System.currentTimeMillis();
    timeElapsed = finish - start;

    System.out.println(" Set retainAll() >> " + timeElapsed / 1000 + " sec");
}
```
