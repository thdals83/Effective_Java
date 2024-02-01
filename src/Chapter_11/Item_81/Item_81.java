package Chapter_11.Item_81;


import java.util.PrimitiveIterator;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/* wait와 notify 보다는 동시성 유틸리티를 애용하라

concurrent 패키지
-> 고수준의 동시성 유틸리티 제공. 크게 세가지로 분류해보면 아래와 같이 나뉜다. 실행자 프레임워크는 이전 아이템에서 다뤘다.
   - 실행자 프레임워크 (Item 80)
   - 동시성 컬렉션
   - 동기화 장치
   
동시성 컬렉션
- List, Queue, Map 같은 표준 컬렉션 인터페이스에 동시성을 추가한 것이다. 높은 동시성을 위해 동기화를 내부에서 수행한다.
  동시성을 무력화하는 것이 불가능하며, 외부에서 락을 걸면 오히려 속도가 더 느려진다.
  
상태 의존적 메서드
- 동시성 컬렉션의 동시성을 무력화하지 못하기 때문에, 여러 메서드들을 원자적으로 묶어 호출하는 것도 못한다.
  그래서 여러 동작을 하나의 원자적 동작으로 묶는 상태 의존적 메서드가 추가되었다. 몇몇 메서드는 매우 유용하여 일반 컬렉션
  인터페이스의 디폴트 메서드 형태로 추가되었다.
* */

/* pusIfAbsent - Map의 디폴트 메서드
   - 인자로 넘겨진 key가 없을 때 value를 추가한다.
   - 만약 key가 이미 map에 존재하고, 그 키에 대응하는 값이 null이 아닐 경우, 키에 저장된 값을 반환한다.

- 동기화한 컬렉션보다는 동시성 컬렉션을 사용해야 한다. Collections의 synchronizedMap 보다는, ConcurrentHashMap
  을 사용하는 것이 훨씬 좋다. 
* */
class EX_1 {
	private static final ConcurrentMap<String, String> map = new ConcurrentHashMap<>();
	public static String intern(String s) {
		String result = map.get(s);
		if (result == null) {
			result = map.putIfAbsent(s, s);
			if (result == null) {
				result = s;
			}
		}
		return s;
	}
}

/* 동기화 장치
- 스레드가 다른 스레드를 기다릴 수 있게 하여, 서로의 작업을 조율할 수 있도록 해준다. 대표적인 동기화 장치로는
  CountDownLatch와 semaphore가 있으며 CyclicBarrier와 Exchanger도 있다. 가장 강력한 동기화 장치로는 Phaser
  
  CountDownLatch는 하나 이상의 스레드가 또 다른 하나 이상의 스레드 작업이 끝날 때까지 기다린다.
  생성자 인자로 받는 정수값은 래치의 countdown 메서드를 몇 번 호출해야 대기하고 있는 스레드들을 깨우는지 결정한다. 
  
예를 들어 어떤 동작들을 동시에 시작해서, 모두 완료하기 까지의 시간을 재는 코드를 아래와 같이 작성할 수 있다.
* */
class CountDownLatchTest {
	public static void main(String[] args) {
		ExecutorService executorService = Executors.newFixedThreadPool(5);
		try {
			final long result = time(executorService, 3, () -> System.out.println("hello"));
			System.out.println(result);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			executorService.shutdown();
		}
	}

	private static long time(Executor executor, int concurrency, Runnable action) 
			throws InterruptedException {
		CountDownLatch ready = new CountDownLatch(concurrency);
		CountDownLatch start = new CountDownLatch(1);
		CountDownLatch done = new CountDownLatch(concurrency);
		
		for (int i = 0; i < concurrency; i++) {
			executor.execute(() -> {
				//타이머에게 준비가 되었음을 알린다.
				ready.countDown();
				try {
					//모든 작업자 스레드가 완료될 때 까지 기다린다.
					start.await();
					action.run();
				} catch (InterruptedException e) {
					Thread.currentThread().interrupt();
				} finally {
					//타이머에게 작업을 마쳤음을 알린다.
					done.countDown();
				}
			});
		}
		ready.await(); // 모든 작업자가 준비될 때까지 기다린다.
		long startNanos = System.nanoTime();
		start.countDown(); // 작업자들을 깨운다.
		done.await(); // 모든 작업자가 일을 끝마치기를 기다린다.
		return System.nanoTime() - startNanos;

	}
}

/* wait와 notify 메서드
- 새로운 코드라면, waith, notify가 아닌 동시성 유틸리티를 사용해야 한다. 하지만 사용할 수 밖에 없는 상황이라면
  반드시 동기화 영역 안에서 사용해야 하며, 항상 반복문 안에서 사용해야 한다.

synchronized (obj) {
    while (조건이 충족되지 않았다) {
        obj.wait(); // 락을 놓고, 깨어나면 다시 잡는다.
    }

    ... // 조건이 충족됐을 때의 동작을 수행한다.
}

반복문은 wait 호출 전후로 조건이 만족하는지를 검사하는 역할을 한다. 대기 전에 조건을 검사하여 조건이 충족되었다면
wait를 건너뛰게 한 것은 응답 불가 상태를 예방하는 조치이다.
만약 조건이 이미 충족되었는데 스레드가 notify 또는 notifyAll 메서드로 먼저 호출한 후 대기 상태로 빠지면, 그 스레드를 
다시 깨우지 못할 수도 있다.

한편, 대기 후에 조건을 검사하여 조건을 충족하지 않았을 때 다시 대기하게 하는 것은 잘못된 값을 계산하는 안전 실패를 막기 위함

그런데 조건이 만족되지 않아도 스레드가 깨어날 수 있는 상황이 있다
- notify를 호출하여 대기 중인 스레드가 깨어나는 사이에 다른 스레드가 락을 거는 경우
- 조건이 만족되지 않았지만 실수 혹은 악의적으로 notify를 호출하는 경우
- 대기 중인 스레드 중 일부만 조건을 충족해도 notifyAll로 모든 스레드를 깨우는 경우
- 대기 중엔 스레드가 드물게 notify 없이 깨어나는 경우, (허위 각성이라고 한다.)

보통은 notify 보단, NotifyAll을 사용하는 것이 안전하며 wait는 while문 내부 안에서 호출하도록 해야 한다.
* */














public class Item_81 {
}
