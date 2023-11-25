package Chapter_5.Item_32;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/* 제네릭과 가변인수를 함께 쓸 때는 신중하라.

가변인수 = ... (파라미터 갯수를 클라가 조절할 수 있게 해주는 것)
-> 여기서 가변인수 메서드를 호출하면 가변인수를 담기 위한 배열이 자동으로 만들어진다.

여기서 문제점은 그 배열을 내부로 감췄어야 했지만 그만 클라이어튼에 노출하는 문제가 생김
그 결과 제네릭이나 매개변수화 타입이 포함되면 알기 어려운 컴파일 경고가 발생한다.
* */
class Ex1 {
	static void dangerous(List<String> ... stringLists) {
		List<Integer> integers = List.of(42);
		
		Object[] objects = stringLists;
		objects[0] = integers; //힙 오염 발생
		String s = stringLists[0].get(0); //ClassCastException
	}
}

/*
위에 처럼 타입 안전성이 깨지니, 제네릭 varargs 배열 매개변수에 값을 저장하는 것은 안전하지 않다.

제네릭 배열을 프로그래머가 직접 생성하지 않게 하는데는 이유가 있다.
그런데도 제네릭 가변인수를 받는 메서드를 되게 한 이유가 있다. 실무에서 유용하기 때문

EX) Arrays.asList(T... a), Collections.addAll(Collection<? super T> c, T... elements) => 얘네는 안전함
자바7 에서는 @SafeVarargs이 추가되어 제네릭 가변인수 메서드에 대한 경고를 없앨 수 있음

그럼 안전을 보장한 근거는 어디있을까?
- 가변 인수 메서드를 호출할 때 varargs 매개변수를 담는 제네릭 배열이 만들어 진다는 것을 기억해야 한다.
메서드가 이 배열에 아무것도 저장하지 않고, 그 배열의 참조가 밖으로 노출되지 않는다면 타입 안전하다.
* */

// 아래처럼 자신의 제네릭 매개변수 배열의 참조를 노출하면 안전하지 않다.
// 반환하는 배열의 타입이 컴파일 시점에 결정되다. 그 시점에는 컴파일러에게 충분한 정보가 없어서 타입을 잘못 판단할 수 있다.

class Ex2 {
	static <T> T[] toArray(T... args) {
		return args;
	}
}

/*
제네릭 varargs 매개변수를 안전하게 사용하는 메서드
@SafeVarargs에 안전한지 아래 두개를 점검 할 것

- varargs 매개변수 배열에 아무것도 저장하지 않는다.
- 그 배열을 신뢰할 수 없는 코드에 노출하지 않는다.
* */
class Ex3 {
	@SafeVarargs
	static <T> List<T> flatten(List<? extends T>... lists) {
		List<T> result = new ArrayList<>();
		for (List<? extends T> list: lists) {
			result.addAll(list);
		}
		return result;
	}
}


/*
@SafeVarargs를 사용하지 말고, 그냥 List 매개변수로 바꿔버려도 된다.
* */
class Ex4 {
	static <T> List<T> flatten(List<List<? extends T>> lists) {
		List<T> result = new ArrayList<>();
		for (List<? extends T> list: lists) {
			result.addAll(list);
		}
		return result;
	}
}








public class Item_32 {
}
