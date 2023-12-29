package Chapter_8.Item_49;

/* 매개변수가 유효한지 검사하라

메서드와 생성자의 입력 매개변수는 특정 조건을 만족하기를 바라는 경우가 많다. EX) 인덱스 값이 음수이면 안되거나, null이 아니여야 된다는 등
이러한 제약은 문서화를 해야 하며, 메서드 몸체가 시작되기 전에는 검사해야 한다.

매개변수 검사를 제대로 하지 못하면 생길 수 있는 문제점들이 존재한다.
1. 메서드가 수행되는 중간에 모호한 예외를 던지며 실패할 수 있다. 
2. 메서드는 잘 수행되지만 잘못된 결과를 반환
3. 메서드는 잘 수행되지만 객체를 이상한 상태로 만들어 놓아서 미래에 메서드와는 관련 없는 오류를 발생시킴 (dnjswktjddmf djrla)

- public과 protected 메서드는 매개변수 값이 잘못 되었을 때, 던지는 예외를 문서화 해야한다. -> @throws 자바독 태그를 사용한다.
예외는 보통 IllegalArgumentException, IndexOutOfBoundsException, NullPointerException 중에 일어남
- 매개변수의 제약을 문서화 한다면, 그 제약을 어겼을 때 발생하는 예외도 함께 기술해야 한다.
* */

import java.math.BigInteger;

//아래는 전형적인 문서화의 예시 
// 매개변수를 검사하는 법 - 공개 메서드 에서
class EX_1 {
	
	/*
	* (현재 값 mod m) 값을 반환한다. 이 메서드는 항상 음이 아닌 BigInteger를 반환한다는 점에서 remainder 메서드와 다르다.
	* @param m 계수 (양수여야 한다.)
	* @return 현재 값 m
	* @throws ArithmeticException m이 0보다 작거나 같은면 발생한다.ㅌ
	* */
	public BigInteger mod(BigInteger m) {
		if (m.signum() <= 0) 
			throw new ArithmeticException("계수는 양수여야 함");
		
		return m;
	}
}

/* 
requireNonNull 메서드를 사용해서, null 검사를 수동으로 하지 않아도 된다.
this.strategy = Objects.requireNonNull(strategy, "전략");  // 예외 메시지 지정

자바 9에서는 Objects에 범위 검사 기능도 가능해졌다. checkFromIndexSize(), checkFromToIndex(), checkIndex()
단 예외 메시지를 지정할 수 없으며 리스트와 배열 전용으로 설계되었다. 또한 닫힌 범위(양 끝단의 값을 포함하는)는 다루지 못한다는 단점이 존재한다.
* */

/* 매개변수를 검사하는 법 2 - 비공개 메서드에서
공개되지 않은 메서드면 패키지 제작자가 메서드가 호출되는 상황을 통제할 수 있다.
따라서 오직 유효한 값만이 메서드에 넘겨지리라는 것을 보증할 수 있음
이를 assert를 사용해 매개변수의 유효성을 검증한다.

단언문(assert) 특징
1) 실패시 AssertionError를 던진다.
2) 런타임에 아무런 효과도, 아무런 성능 저하도 없다.
* */
class EX_2 {
	private static void sort(long a[], int offset, int length) {
		assert a != null;
		assert offset >= 0 && offset <= a.length;
		assert length >= 0 && length <= a.length - offset;
	}
}


/*
메서드 몸체 실행 전에 매개변수 유효성을 검사해야 한다는 규칙에도 예외는 존재한다. 
바로, 유효성 검사 비용이 지나치게 높거나 실용적이지 않을 때, 혹은 계산 과정에서 암묵적으로 검사가 수행될 때이다.
	-> Collections.sort(List) 처럼 객체 리스트를 정렬하는 메서드를 생각해보면 리스트 안의 객체들은 모두 상호 비교되어 있어야 하며 
	비교될 수 없는 타입의 객체가 들어있다면 ClassCastException을 던진다. 하
	지만 암묵적 유효성 검사에 너무 의존한다면 실패 원자성(아이템 76)을 해칠 수 있으니 주의하자.


* */









public class Item_49 {
}
