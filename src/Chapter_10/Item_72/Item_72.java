package Chapter_10.Item_72;


/* 표준 예외를 사용하라
- 예외를 사용할 때는, 이미지 자바 라이브러리에서 제공하는 예외들, 즉 표준 예외를 사용하는 것이 좋다.

표준 예외를 재사용 한다면, 아래와 같은 장점이 존재한다.
1. 나의 API가 다른 사람이 익히고 사용하기 쉬워진다.
2. 가독성이 향상되어 프로그램이 읽기 쉬워진다.
3. 예외 클래스 수가 적을수록 메모리 사용량도 줄고 클래스를 적재하는 시간도 적게 걸린다.

재사용 하기 좋은 예외
IllegalArgumentException
- 호출자가 인수로 부적절한 값을 넘길 때 던지는 예외 EX) 반복 횟수를 지정하는 매개변수에 음수를 건낼 때

IllegalStateException
- 대상 객체의 상태가 호출된 메서드를 수행하기에 적합하지 않을 때 EX) 제대로 초기화되지 않은 객체를 사용하려고 할 때

ConcurrentModificationException
- 단일 스레드에서 사용하려고 설계한 객체를, 여러 스레드가 동시에 수정하려 할 때 던지는 예외, 동시 수정을 확실히 검출
  할 수 없으니, 문제가 생길 가능성을 알려주는 정도
  
UnsupportedOperationException
- 클라가 요청한 동작을 대상 객체가 지원하지 않을 때, 보통 구현하려는 인터페이스의 메서드 일부를 구현할 수 없을 때
  EX) 원소를 넣을 수만 있는 List 구현체에 대고 누군가 remove를 호출하면 예외를 던진다
  
재사용하면 안좋은 예외
- Exception, RuntimeException, Throwable, Error은 직접 재사용하지 말자
- 다른 예외들의 상위 클래스이며, 여러 성격의 예외들을 포괄하는 클래스이므로 안정적으로 테스트 할 수 없기 때문이다.
-> 더 많은 정보를 제공하기 원한다면, 표준 예외를 확장해도 좋지만, 예외는 직렬화 해야하고 이는 많은 부담이 따른다.
  
  

* */


public class Item_72 {
}
