package Chapter_11.Item_83;


/* 지연 초기화는 신중히 사용하라.
지연 초기화(lazy initialization) = 필드의 초기화 시점을 그 값을 처음 필요로 할 때까지 늦추는 기법, 값이 쓰이지 않으면 초기화도 X
-> 클래스와 인스턴스 초기화 때 발생하는 위험한 순환 문제를 해결하는 효과가 있다.

지연 초기화는 양날의 검
- 지연 초기화하는 필드에 접근하는 비용이 커진다.
- 초기화가 이뤄지는 비율, 초기화에 드는 비용, 초기화 된 필드를 호출하는 빈도에 따라 실제로는 성능이 느려질 수도 있다.

지연 초기화가 필요할 때
- 클래스의 인스턴스 중 그 필드를 사용하는 인스턴스의 비율이 낮은 반면, 그 필드를 초기화하는 비용이 크다면 지연 초기화가 좋다.
  (이를 실험해 보는건 적용 전 후를 성능 측정을 통해 비교해 보는 것 밖엔 없다.)

멀티스레드 환경에서는 지연 초기화를 하기가 까다롭다.
- 지연 초기화하는 필드를 둘 이상의 스레드가 공유한다면 어떤 형태로든 동기화해야 한다. (그렇지 않으면 버그로 이어진다.)
* */

/* synchronized 접근자를 이용한 인스턴스 필드의 지연 초기화 예시
- 지연 초기화가 초기화 순환성(두 스레드가 서로 초기화를 기다리며 무한 대기에 빠지는 것)
  을 깨뜨릴 것 같으면 synchronized 접근자를 사용하라
- 이상의 두 관용구는 정적 필드에도 똑같이 적용된다.
- 필드와 접근자 메서드 선언에 ㄴㅅㅁ샻 gkswjdwkfmf cnrkgodi gksek.
* */
class EX_1 {
	private Integer ex;

	private synchronized Integer getInt() {
		if (ex == null) {
			ex = emptyVoid();
		}
		return ex;
	}

	private int emptyVoid() {
		return 1;
	}
}

/* 정적 필드용 지연 초기화 홀더 클래스 관용구
- 성능 때문에 정적 필드를 지연 초기화 해야 한다면, 지연 초기화 홀더 클래스 관용구를 사용하라
- 클래스는 클래스가 처음 쓰일 때, 비로소 초기화된다는 특성을 이용한 관용구 이다.
- getField()가 처음 호출되는 순간 FieldHolder.field가 처음 읽히면서, 비로소 FieldHolder 클래스 초기화를 촉발한다.
- getField 메서드가 필드에 접근하면서, 동기화를 전혀하지 않아 성능이 느려질 거리가 없다.
- 일반적인 VM은 오직 클래스를 초기화 할때만 필드 접근을 동기화 한다.
- 클래스 초기화가 끝난 후에는 VM이 동기화 코드를 제거하여, 그 다음부터는 아무런 검사나 동기화 없이 필드에 접근한다.
* */
class EX_2 {
	private static class FieldHolder {
		static final Integer field = Item_83.ex();
	}

	public static Integer getField() {
		return FieldHolder.field;
	}
}

/* 인스턴스 필드 지연 초기화용 이중검사 관용구 예시
- 성능 때문에 인스턴스 필드를 지연 초기화해야 한다면, 이중 검사 관용구를 사용하라
- 이 관용구는 초기화된 필드에 접근할 때 동기화 비용을 없애준다.
- 필드의 값을 두번 검사하는 방식으로, 한 번은 동기화 없이 검사하고, 두번째는 동기화하여 검사한다.
- 두번째 검사에서도 필드가 초기화되지 않았을 때만, 초기화한다.
- 필드가 초기화 된 후로는 동기화하지 않으므로 해당 필드는 반드시 volatile로 선언해야 한다.
- result 지역 변수가 필요한 이유 -> 필드가 이미 초기화된 상황에서는 그 필드를 딱 한번만 읽도록 보장
* */
class EX_3 {
	private volatile Integer field;

	private Integer getField() {
		Integer result = field;
		if (result != null) { //첫 번째 검사 (락 사용을 안함)
			return result;
		}

		synchronized (this) { //두번째 검사 락 사용
			if (field == null)
				field = Item_83.ex();
			return field;
		}
	}
}

/* 단일 검사 관용구 (초기화가 중복해서 일어날 수 있다.)
- 이중검사의 변종 관용구
- 반복해서 초기화해도 상관없는 인스턴스 필드를 지연 초기화하는 경우, 초기화가 중복해서 일어날 수도 있다.
* */
class EX_4 {
	private volatile Integer field;

	private Integer getField() {
		Integer result = field;
		if (result != null) 
			field = result = Item_83.ex();
		
		return result;
	}
}








public class Item_83 {
	public static Integer ex() {
		return 1;
	}
}
