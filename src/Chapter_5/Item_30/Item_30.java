package Chapter_5.Item_30;

import org.junit.platform.commons.util.StringUtils;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.function.UnaryOperator;

/* 이왕이면 제네릭 메서드로 만들라
* */

//로 타입으로 아래처럼 사용하지 말것, Warning이 발생하고, 
class Ex1 {
	public static Set union(Set s1, Set s2) {
		Set res = new HashSet(s1);
		res.addAll(s2);
		return res;
	}
	public static void main(String[] args) {
		Ex1.union(new HashSet<>(), new HashSet<>());
	}
}

//아래 처럼 개선해서 사용
class Ex2 {
	public static <E> Set<E> union(Set<E> s1, Set<E> s2) {
		Set<E> res = new HashSet<>(s1);
		res.addAll(s2);
		return res;
	}
	public static void main(String[] args) {
		Set<String> s1 = Set.of("1", "2", "3");
		Set<String> s2 = Set.of("4", "5", "6");
		Ex2.union(s1, s2);
	}
}

/*
불변 객체를 여러 타입으로 활용 할 수 있게 만들어야 할 때가 있음
용청한 타입의 매개 변수에 맞게 매번 그 객체의 타입을 바꿔주는 정적 팩터리를 만들어야 한다 -> 이 패턴을 제네릭 싱글턴 패턴

예시로 항등함수 클래스를 만들어 보자
* */
class Ex3 {
	private static UnaryOperator<Object> IDENTITY_FN = (t) -> t;
	
	@SuppressWarnings("unchecked")
	public static <T> UnaryOperator<T> identityFunction() {
		return (UnaryOperator<T>) IDENTITY_FN;
	}

	public static void main(String[] args) {
		List<String> stringList = List.of("삼베", "대마", "나일론");
		UnaryOperator<String> sameString = Ex3.identityFunction();
		stringList.forEach(i -> System.out.println(sameString.apply(i)));
	}
}

/* 
자기 자신이 들어간 표현식을 사용하여 타입 매개변수의 허용 범위를 한정할 수 있다. = 재귀적 타입 한정
- 주로 Comparable 인터페이스에서 많이 사용된다.
* */
class Ex4 {
	//타입 한정을 걸어 E와 E의 자손타입이 비교될 수 있게 한다. 이말은 E가 Comparable 인터페이스를 상속받아 구현해놔야 사용 가능하다.
	public static <E extends Comparable<E>> E max(Collection<E> c) {
		if (c.isEmpty())
			throw new IllegalArgumentException("컬렉션이 비어 있습니다.");
		E result = null;
		for (E e : c)
			if (result == null || e.compareTo(result) > 0)
				result = Objects.requireNonNull(e);
		return result;
	}
}

public class Item_30 {
}
