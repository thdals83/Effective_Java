package Chapter_11.Item_80;


/* 스레드보다는 실행자, 테스크, 스트림을 애용하라
* */

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

//실행자 프레임워크 (Executor FrameWork)
//과거에는 단순한 작업 큐를 만들기 위해 수많은 코드를 작성했지만, 이제 간단하게 작업 큐 생성이 가능
class EX_1 {
	public static void main(String[] args) {
		//큐 생성
		ExecutorService exec = Executors.newSingleThreadExecutor();
		Runnable runnable = new Runnable() {
			@Override
			public void run() {
				System.out.println("dd");
			}
		};
		//테스크 실행
		exec.execute(runnable);
		//실행자 종료
		exec.shutdown();

	}
}
/* 실행자 프레임워크의 주요 기능
	- 특정 태스크가 완료되기를 기다린다.
	- 태스크 모음 중 아무것 하나(invokeAny) 혹은 모든 태스크가(invokeAll) 완료되기를 기다린다.
	- 실행자 서비스가 종료하기를 기다린다. (await Termination method)
	- 완료된 태스크들의 결과를 차례로 받는다. (ExecutorCompletionService 이용)
	- 태스크를 특정 시간에 혹은 주기적으로 실행하기 한다 (Scheduled ThreadPoolExecutor)
	
java.util.concurrent.Executors
- 큐를 둘 이상의 스레드가 처리하게 하고 싶다면 간단히 다른 정적 팩터리를 이용하여, 다른 종류의 실행자 서비스를 생성하면 된다.
- 스레드 풀의 스레드 개수는 고정할 수도 있고, 유동적으로 상황에 맞게 설정되게 할 수도 있다.
- 실행자 대부분은 Executors의 정적 팩터리들을 이용해 생성 할 수 있다.
- 이 클래스로는 스레드 풀 동작을 결정하는 거의 모든 속성을 설정할 수 있다.

newCachedThreadPool
- 특별히 설정하렉 없고 일반적인 용도로 적합하게 동작한다.
- 무조거운 프로덕션 서버에는 좋지 않다.
  -> 요청받은 태스크들이 큐에 쌓이지 않고, 즉시 스레드에 위임되어 실행된다.
- 가용한 스레드가 없다면 새로 하나 생성한다.
- 서버가 아주 무겁다면 CPU 이용률이 100%로 치닫고, 새로운 태스크가 도착하는 족족 또 다른 스레드를 생성
- 따라서 프로적션에서는 스레드 개수를 고정한 newFixedThreadPool이나, ThreadPoolExecutor를 사용하라
* */

/* 태스크
- 스레드를 직접 다루면 thread가 작업 단위와 수행 메커니즘 역할을 모두 수행한다.
- 반면 실행자 프레임워크 에서는 작업 단위와 실행 메커니즘이 분리된다.

태스크는 두가지가 있다.
- Runnable
- Callable (Runnable와 비슷하지만, 값을 반환하고, 임의이 예외를 던질 수 있음)
- 태스크 수행을 실행자 서비스에 맡기면 원하는 태스크 수행 정책을 선택 할 수 있고, 변경할 수 있다.
- 핵심은 실행자 프레임워크가 작업 수행을 담당한다는 것
* */

/* Fork - Join FrameWork
- 자바 7부터 지원
- 포크-조인 태스크는 포크-조인 풀 이라는 특별한 실행자 서비스를 실행
- 인스턴스는 작은 하위 태스크로 나눌 수 있으며, 스레드들이 작은 하위 태스크를 처리하고, 다른 태스크를 도와준다.
- 각 스레드들이 처리한 겨로가를 취합하는 작업도 진행한다.
* */








public class Item_80 {
}
