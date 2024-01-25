package Chapter_11.Item_78;


/* 공유 중인 가변 데이터는 동기화해 사용하라
Q.동기화란 무엇일까?
1. 다른 스레드가 변경중이여서 일관되지 않은 상태를, 락을 걸어 다른 스레드가 보지 못하게 막는다. (일관성을 보장한다.)
2. 락의 보호하에 수행된 모든 이전 수정의 최종 결과를 볼 수 있게 한다.

자바는 synchronized 키워드를 통해, 해당 메서드나 블록을 한번에 한 스레드씩 수행하도록 보장하여 동기화 할 수 있다.

Q.동기화가 필요한 이유
- long, double 외의(기본형 타입) 변수를 읽고 쓰는 동작은 원자적이여서, 
  동기화 없이 여러 스레드가 한 변수를 수정해도 정상적으로 본인이 저장했던 값을 불러올 수 있다.
  * 기본형 타입이 원자적인 이유 - JVM은 데이터를 4바이트 단위로 처리한다.
    - int 보다 작은 타입은 하나의 명령어로 처리되기 때문에, 한 스레드로만 처리된다.
    - long, double은 8 바이트를 넘어가기 때문에, 여러 스레드가 개입될 여지가 생겨 원자적이라 할 수 없다.
- 하지만 수정이 완전히 반영된 값은 보장해주지만, 해당 반영된 값이 다른 스레드에게 보이는가는 보장하지 않는다.
  따라서 스레드 사이 안정적인 통신에 반드시 동기화가 필요하다.
* */

import java.util.concurrent.TimeUnit;

//예시
class EX_1 {
	/* 스레드를 1초 멈춘 후에, 반복문을 빠져나올 수 잇도록 설정
	   정상적이라면 1초 후에 프로그램이 종료되는게 정상이다. 하지만 stopRequested 필드가 동기화 되지 않아,
	   수정된 값을 해당 스레드가 보는 것이 보장되지 않기 대문에 무한 루프로 돌게 된다.
	* */
	private static boolean stopRequested;
	public static void main(String[] args) throws InterruptedException {
		Thread th = new Thread(() -> {
			int i = 0;
			while (!stopRequested) {
				i++;
				System.out.println(i);
			}
		});
		th.start();

		TimeUnit.SECONDS.sleep(3);
		stopRequested = true;
	}
}


/* 동기화를 통해 정상적으로 메인 스레드가 공유 필드를 읽고 수정한 값이 완벽히 반영된 이후에 다른
   스레드가 값을 읽도록 한다면 문제를 해결할 수 있다.
* */
class EX_2 {
	private static boolean stopRequested;
	
	private static synchronized void requestStop() {
		stopRequested = true;
	}
	private static synchronized boolean stopRequested() {
		return stopRequested;
	}
	
	public static void main(String[] args) throws InterruptedException {
		Thread th = new Thread(() -> {
			int i = 0;
			while (!stopRequested()) {
				i++;
				System.out.println(i);
			}
		});
		th.start();

		TimeUnit.SECONDS.sleep(3);
		requestStop();
	}
}


/* volatile 사용
private static volatile boolean stopRequested; 식으로 사용하면, synchornized보다 동기화하는 속도도
더 빨라지며 따로 쓰기와 읽기 동기화 메서드를 만들지 않아도 된다.

volatile 개념
- "공유 변수를 캐시가 아닌 메인 메모리에" 저장하겠다 라고 명시하는 키워드 -> 해당 키워드가 선언된 변수가 있는 코드는
   최적화 되지 않는다.
   - image 참고 -
   기존적으로 CPU는 값에 대한 쓰기/ 읽기 연산을 수행할 때 L1 cache를 사용해 성능을 높인다.
   하지만 이게 멀티 스레드 환경에서는 독이 되는데, 스레드 1이 counter 공유 변수의 값을
   캐시에서만 증가시켜 스레드 2가 변경된 값을 볼 수 없는 문제가 발생한다.
   - image 2 참고 - 

volatile 장점
- 이렇게 캐시가 아닌, 메모리에 읽고 쓰는 연산이 이루어진다면, 다른 스레드라도 같은 메모리 주소를 참조하기 때문에,
  이러한 변수 값 불일치 문제를 해결해 항상 최신의 값을 가져오는 것을 보장해준다.
  따라서 volatile는 하나의 스레드에서만 쓰기/읽기를 하고, 나머지 쓰레드들은 읽기 연산을 하는 환경에서는 좋다.
  
volatile 한계 및 주의사항
- 하지만 멀티 스레드 환경에서, 여러 쓰레드가 쓰기 연산을 하는 경우에는 적합히자 않다. 가시성은 해결해주지만
  상호 배제 문제는 해결할 수 없기 때문이다.
  - Mutual Exclusion(상호 배제) = 하나의 코드 블록은 하나의 스레드 또는 프로세스만 실행할 수 있음
  - Visibility(가시성) = 한 스레드가 공유 데이터를 변경하면 다른 스레드에서도 볼 수 있음
  
  EX) private static volatile int nextNubmer = 0;
	public static int generateNumber() {
		return nextNumber++;
	}
	1. 스레드 1이 메인 메모리 변수 값을 읽어오고, 1을 증가시킨다.
	2. 스레드 2가 스레드 1이 변경시킨 값이 메인 메모리에 반영되기 전에 변수 값을 읽어오고, 1 증가시킨다.
	3. 둘 다 변수 0일 때의 상태에서 1을 증가시켰으므로, 2가아닌 1이 최종적으로 반영되버린다.
	=> 이렇게 공유 블록에 한번에 하나의 스레드만 접근하는 것을 막지 못한다.
	따라서 이런 경우에는 volatile을 제거하고 synchornized를 붙여서 동시에 호출해도 서로 간섭하지 않은 상태에서
	이전 호출이 변경한 값을 얻어올 수 있다.
	
	이전 StopTrhead 예제에서 volatile이 가능했던 이유는, 오직 스레드 한개만 stopRequested 공유 변수에
	쓰기 작업을 하였기 때문
	
락 - 프리 안전한 클래스
java.util.concurrent.atomic패키지의 AtomicLong을 사용하면 멀티 스레딩 환경에서 락 없이도 안전하게 사용할 수 있다.
private static final AtomicLong nextNum = new AtomicLong();
public static long generateNumber() {
	return nextNum.getAndIncrement();
}
* */





public class Item_78 {
}
