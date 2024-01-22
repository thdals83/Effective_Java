package Chapter_10.Item_69;


import java.util.Iterator;

/* 예외는 진짜 예외 상황에만 사용하라
- 예외는 예외 상황에서만 써야 하지, 절대로 일상적인 제어 흐름용으로 쓰여서는 안된다.
  또한, 이를 프로그래머에게 강요하는 API를 만들어서도 안된다.
* */
class EX_1 {
	/* 배열의 원소를 순회하는데, 무한루프를 돌다가 배열의 끝에 도달해 예외가 발생하면 끝내는 로직
	   직관적이지 않다는 점 하나로도 제어 흐름용으로 사용하면 안되는 이유가 충분하다.
	* */
	public static void main(String[] args) {
		int[] arr = new int[] {1, 2, 3};
		try {
			int i = 0;
			while (true)
				System.out.println(arr[i++]);
		} catch (ArrayIndexOutOfBoundsException e) {}
		
		//표준적인 관용구 -> 이게 위에 예외 코드보다 2배 정도 빠르다.
		for (int m : arr) System.out.println(m);
	}
}

/* API 설계와 예외
- 잘 설계된 API는, 클라이언트가 정상적인 제어 흐름에서 예외를 사용할 일이 없어야 한다.
* */

/* 1. 상태 검사 메서드
- 특정 상태에서만 호출할 수 있는 -> 상태 의존적 메서드를 제공하는 클래스는, 
  상태 검사 메서드와 함께 제공한다면 예외를 사용 하지 않을 수 있다.
* */
class EX_2 {
	public static void main(String[] args) {
		//Iterator 인터페이스를 예로 들면, next는 상태 의존적 메서드 / hasNext는 상태 검사 메서드
		// 두 메서드 덕분에 표준 for 관용구를 사용할 수 있음
//		for (Iterator<Foo> i = collections.iterator(); i.hasNext(); ) {
//			Foo foo = i.next();
//		}
		
		//만약 제공하지 않았더라면, 아래와 같이 클라이언트가 대신 했을 것
//		try {
//			Iterator<Foo> i = collection.iterator();
//			while(true) {
//				Foo foo = i.next();
//        ...
//			} catch (NoSuchElementException e) {
//			}
	}
}

/* 2.옵셔널
- 상태 검사 메서드 이외에, 올바르지 않은 상태일 때 빈 Optional 혹은 null 같은 특수한 값을 반환하는 방법도 있다.

언제 무엇을 쓰는 게 좋을까?
1. 외부 동기화 없이 여러 스레드가 동시에 접근 할 수 있거나, 외부 요인으로 상태가 변할 수 있다면
옵셔널이나 특정 값을 사용한다. 상태 검사 메서드와 상태 의존적 메서드 호출 사이에 객체의 상태가 변할 수 있기 때문이다.
2. 성능이 중요한 상황에서 상태 검사 메서드가 상태 의존적 메서드의 작업을 일부 중복 수행한다면 옵셔널이나 특정 값을 선택한다.
3. 다른 모든 경우엔 상태 검사 메서드 방식이 좋다. 가독성이 좋고, 잘못 사용했을 때 발견하기 쉽기 때문
* */















public class Item_69 {
}
