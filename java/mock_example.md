# Java, Mockito, Mock... example.

```java
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class TestExample {

    class Foo {
        Bar bar;
    
        public Foo(Bar bar) {
            this.bar = bar;
        }
    
        String execute() {
            return bar.execute() + "Foo:";
        }
    }
    
    class Bar {
        String execute() {
            return "Bar:";
        }
    }

    @Test
    void MockTest() {
        // 1. 둘다 실제
        Foo realFoo = new Foo(new Bar());
        System.out.println("realFoo.execute() = " + realFoo.execute());

        // 2. Foo mock
        Foo mockFoo = mock(Foo.class);
        when(mockFoo.execute()).thenReturn("Bar:" + "Foo:");

        // 3. Foo 실제, <- Bar mock
        Bar mockBar = mock(Bar.class);
        when(mockBar.execute()).thenReturn("Bar:");
        Foo innerMockFoo = new Foo(mockBar);

        assertThat(realFoo.execute()).isEqualTo(mockFoo.execute());
        assertThat(realFoo.execute()).isEqualTo(innerMockFoo.execute());
    }
    
}

```