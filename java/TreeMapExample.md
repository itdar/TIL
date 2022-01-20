# Java TreeMap 을 이용하여 삽입 시 정렬되도록 예제

- TreeMap 의 경우, 생성 시 Comparator를 구현해서 넣어주어 사용한다.
~~~
// Order
Comparator<String> comparator = (o1, o2) -> o1.compareTo(o2);

// ReverseOrder
Comparator<String> comparator = (o1, o2) -> o2.compareTo(o1);
~~~

#### 코드
```java
import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;

public static void main(String[] args) {
        Comparator<Integer> comparator = Integer::compareTo;
        Map<Integer, String> map = new TreeMap<>(comparator);
        map.put(2, "Two");
        map.put(0, "Zero");
        map.put(-3, "Minus Three");
        map.put(13, "Thirteen");
        map.put(5, "Five");

        for (Map.Entry<Integer, String> entry : map.entrySet()) {
            System.out.println("Key: " + entry.getKey() + "\t\t" + "Value: " + entry.getValue());
        }
}
```
#### 결과
~~~
Key: -3		Value: Minus Three
Key: 0		Value: Zero
Key: 2		Value: Two
Key: 5		Value: Five
Key: 13		Value: Thirteen
