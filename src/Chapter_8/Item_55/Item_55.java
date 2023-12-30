package Chapter_8.Item_55;


/* 옵셔널 반환은 신중히 하라
* */

import javax.lang.model.util.Elements;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

class EX_1 {
	//optional을 사용하지 않았을 때
	public static <E extends Comparable<E>> E max1(Collection<E> c) {
		if (c.isEmpty())
			throw new IllegalArgumentException("빈 컬렉션");

		E result = null;
		for (E e : c)
			if (result == null || e.compareTo(result) > 0)
				result = Objects.requireNonNull(e);
		return result;
	}
	
	//optional을 사용했을 때
	/*
	Optional.empty() : 빈 옵셔널 반환
	Optional.of(value) : 값이 든 옵셔널 반환. null 값이 들어가면 NullPointerException 을 던짐
	Optional.ofNullable(value) : null 값도 허용하는 옵셔널
	* */
	public static <E extends Comparable<E>> Optional<E> max2(Collection<E> c) {
		if (c.isEmpty())
			return Optional.empty();  // 정적 팩터리 메소드

		E result = null;
		for (E e : c)
			if (result == null || e.compareTo(result) > 0)
				result = Objects.requireNonNull(e);
		return Optional.of(result);  // 정적 팩터리 메소드
	}
	
	//optional + stream 버전
	public static <E extends Comparable<E>> Optional<E> max(Collection<E> c) {
		return c.stream().max(Comparator.naturalOrder());
	}
	
	
	//optional의 활용 예시 - 1 기본값을 정해둘 수 있다.
	List<String> words = List.of("1", "2");
	String lastWordInLexicon = max(words).orElse("단어 없음...");
	
	//활용 예시 2 - 원하는 예외를 던질 수 있다.
	String myWord = max(words).orElseThrow(IllegalArgumentException::new);
	
	//예시 3 - 항상 값이 채워져 있다고 가정한다. -> 여기서 값이 없으면 NPE 날 것임
	String lastNobleGas = max(words).get();
	
	//예시 4 - orElseGet
	//기본값을 설정하는 비용이 커 Supplier<T>를 인수로 받는 orElseGet을 사용하면 초기 설정 비용을 줄일 수 있다.
	
	//예시 5 - isPresent
	//안전 벨브 역할을 하는 메서드, optional이 채워져 있으면 true, 비워져 있으면 false를 반환한다.
	public static void main(String[] args) {
		ProcessHandle ph = ProcessHandle.current();
		Optional<ProcessHandle> parentProcess = ph.parent();
		System.out.println("부모 PID: " + (parentProcess.isPresent() ? String.valueOf(parentProcess.get().pid()) : "N/A"));
		
		//map을 이용해 위를 개선한다면 아래와 같다.
		System.out.println("부모 PID: " + ph.parent().map(h -> String.valueOf(h.pid())).orElse("N/A"));
	}
}

/* optional 주의사항

1. 컬렉션, 스트림, 배열, 옵셔널과 같은 컨테이너 타입은 옵셔널로 감싸면 안된다.
2. 결과가 없을 수 있으며, 클라이언트가 이 상황을 특별하게 처리해야 하는 경우라면 Optional<T> 를 반환하자.(일종의 규칙)
3. 박싱된 기본 타입을 담은 옵셔널은 반환하지 말자 -> 기본타입에 optional을 사용하고 싶다면 
OptionalInt, OptionalLong, OptionalDouble을 사용하는 것이 좋다. (Boolean, Byte, Caracter, Short, Float은 예외)

4. 옵셔널을 키,값,원소나 배열의 원소로 사용하면 절대 안된다. 
* */

/* 정리
값을 반환하지 못할 가능성이 있고, 호출할 때마다 반환값이 없을 가능성을 염두에 둬야 한다면 옵셔널을 사용할 수 있다.
하지만 옵셔널을 사용하면 성능 저하가 뒤 따르니 이를 고려해야 한다.
* */


public class Item_55 {
}
