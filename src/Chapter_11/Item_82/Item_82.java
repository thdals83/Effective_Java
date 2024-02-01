package Chapter_11.Item_82;


/* 스레드 안전성 수준을 문서화하라

1. API 문서 synchronized 한정자
- 메서드 선언에 synchronized 한정자를 선언할지는 구현 이슈일 뿐 API에 속하지 않는다.
  API 문서에 synchronized 한정자가 보인다고 해서 이 메서드가 thread safe하다고 믿기 어렵다.
  멀티 스레드 환경에서 안전하게 사용하려면 지원하는 스레드 안전성 수준을 정확히 명시해야 한다.
  
2. 스레드 안전성 수준
불변 (immutable)
- 이 클래스의 인스턴스는 마치 상수와 같아서 외부 동기화가 필요없다.
- String, Long, BigInteger
- @Immutable

무조건적 스레드 안전 (unconditionally thread-safe)
- 이 클래스의 인스턴스는 수정될 수 있으나, 내부에서 충실히 동기화하여 별도의 외부 동기화 없이 동시에 사용해도 안전하다
- AtomciLong, ConcureenthashMap
- @ThreadSafe

조건부 스레드 안전 (conditionally thread-safe)
- 무조건적 스레드 안전과 같으나, 일부 메서드는 동시에 사용하려면 외부 동기화가 필요하다.
- Colelctions.synchronized 래퍼 메서드가 반환한 컬렉션들
- @ThreadSafe

스레드 안전하지 않음 (not thread-safe)
- 이 클래스의 인스턴스는 수정될 수 있다. 동시에 사용하려면 각각의 메서드 호출을 클라가 선택한 외부 동기화 메커니즘으로 감싸야한다.
- ArrayList, HashMap
- @NotThreadSafe

- 스레드 절대적
- 이 클래스는 모든 메서드 호출을 외부 동기화로 감싸더라도 멀티 스레드 환경에서 안전하지 않다.
- 이 수준의 클래스는 일반적으로 정적 데이터를 아무 동기화 없이 수정한다.
- 문제를 고쳐 재배포 하던가 deprecated 해야함

3. 조건부 스레드 안전 (Conditonally Thread-safe의 문서화)
- 어떤 순서로 호출할 때, 외부 동기화가 필요한지
- 그 순서로 호출하면 어떤 락 혹은 락들을 얻어야 하는지


4. Lock 제공
외부에서 사용할 수 있는 락을 제공하면 클라이언트에서 메서드 호출에 원자적으로 수행할 수 있다.

- 내부에서 처리하는 고성능 동시성 제어 메커니즘과 혼용이 안된다. (CurrentHashMap과는 사용 x)
- 클라이언트가 공개된 락을 오래 쥐고 놓지 않는 서비스 거부 공격을 수행할 수도 있다.
- synchronsized 메서드도 공개된 락에 속한다.
- 비공개 락 객체를 사용해야 한다.

// 비공개 락 객체, final 선언! : 락 객체 교체 상황 방지를 위함
private final Object lock = new Object();

public void someMethod() {
  synchronized(lock) {
    // do something
  }
}

* */







public class Item_82 {
}
