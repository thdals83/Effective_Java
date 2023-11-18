package Chapter_5.Item_26;

/* 로 타입은 사용하지 말라
로타입(raw type)이란?
- 제네릭 타입에서 타입 매개변수를 전혀 사용하지 않을 때 EX) List<E>의 로 타입은 List

EX)
private final Collection stamp = ...;
stamps.add(new Coin(...)));

그래서 제네릭을 활용하여 컴파일 시점부터 알 수 있도록 한다.
private fianl Collection<Stamp> stamps = ...;

//List<Object>와 List<T>는 다름 공변 불공변
* */

import java.util.Set;

class EX1 {
	//로타입보단 아래처럼 비한정적 와일드 카를 사용한다.
	//제네릭 타입을 사용하고 싶지만 실제 타입 매개변수가 무엇인지 신경 쓰고 싶지 않다면 ? 사용
	static int numEX(Set<?> s1, Set<?> s2) {return 2;}
}

public class EX_1 {
}
