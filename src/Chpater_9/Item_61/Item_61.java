package Chpater_9.Item_61;


/* 박싱된 기본 타입보다는 기본 타입을 사용하라

기본 타입: int, double, boolean
참조 타입: String, List

기본 타입에 대응하는 참조 타입: Integer, Double, Boolean
오토 박싱과 오토언박싱 대문에 두 타입이 자동으로 형변환 되고, 크게 구분해서 사용하지 않을 수 있지만 둘은 큰 차이가 존재하니 주의해야 한다.

기본 타입과 박싱된 기본 타입의 차이
1. 기본 타입은 값만 가지고 있으나, 박싱된 기본 타입은 값에 더해 식별성이라는 속성을 가진다.
	- 박싱된 기본 타입의 두 인스턴스는 값이 같아도 서로 다르다고 식별 될 수 있다.
2. 박싱된 기본 타입은 null을 가질 수 있다.
3. 기본 타입이 박싱된 기본 타입보다 시간과 메모리 사용면에서 더 효율적이다.
* */

import java.util.Comparator;

class EX_1 {
	/* 다음은 Integer 값을 오름차순으로 정렬하는 비교자다.
	아래에서 1을 출력한다. (첫번째 Integer 가 두번째 Integer 보다 크다고 주장함)
	
	첫번째 i < j 에서 오토박싱된 Integer 인스턴스는 기본 타입으로 변환되고 값이 작은지 평가한다.
	두번째 i == j 에서는 '객체 참조'의 식별성을 검사하게 된다. 
	결론 박싱된 기본 타입에 == 연산자 사용하면 안됨
	* */
	public static void main(String[] args) {
		Comparator<Integer> naturalOrder = (i, j) -> (i < j) ? -1 : (i == j ? 0 : 1);
		naturalOrder.compare(new Integer(42), new Integer(42)); // 1 출력

		//아래 처럼 오토박싱으로 검사 전에 바꿔서 사용한다.
		Comparator<Integer> naturalOrder2 = (iBoxed, jBoxed) -> {
			int i = iBoxed, j = jBoxed;  // 오토 박싱
			return i < j ? -1 : (i == j ? 0 : 1);
		};
	}
}

// Integer의 초기값이 null이기 때문
class EX_2 {
	static Integer i ;
	public static void main(String[] args) {
		if (i == 42) System.out.println("un believe able");


		//아래처럼  sum을 박싱 타입하면 박싱과 언박싱이 반복해서 일어나 매우 느린 성능을 보여준다.
		Long sum = 0L;
		for (long i = 0; i <= Integer.MAX_VALUE; i++) {
			sum += i;
		}
		System.out.println(sum);
	}
}

/* 박싱된 기본 타입의 용도
1. 컬렉션의 원소, 키 값으로 사용한다.
2. 매개변수화 타입이나 매개변수화 메서드의 타입 매개변수에 사용한다.
3. 리플렉션을 통해 메서드를 호출할 때 사용한다.
* */

public class Item_61 {
}
