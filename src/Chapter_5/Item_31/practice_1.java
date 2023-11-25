package Chapter_5.Item_31;

/*
제네릭의 장점
- 컴파일 단계에서, 잘못된 타입을 확인할 수 있다.
- 코드 재사용성 증가

보통 쓰는 타입정의
타입		설명
<T>		Type
<E>		Element
<K>		Key
<V>		Value
<N>		Number
* */

//class, interface EX -> {} 안까지 T가 유효함
class Class_ex1<T> {
	private T ex;
	
	public T test(T t) {
		return t;
	}
	
	public Class_ex1(T t) {
		this.ex = t;
	}
}
interface Interface_ex1<T> {}
//n개 가능
class Class_ex2<A, B, C, D, E> {}

//메서드에 한정한 제네릭 타입
class Class_ex3 {
	public <T> String ex(T ex) {
		return "d";
	}
	public <T> T ex2(T ex) {
		return ex;
	}
}

//에러가 발생하는 상황 - static의 경우, 객체 생성 전에 생기기 때문에, 아래와 같이 선언하면, 타입을 알 수가 없음
class Class_Fail1<T> {
	//static T test(T ex) {return ex}
}

/* 제네릭의 범위를 지정하고(좁히거나, 제한) 싶을 때
- extends
- super
- ? (wild card)

<K extends T> // T와 T의 자손타입만 가능하다. (K는 들어오는 타입으로 지정)
<K super T> // T와 T의 부모타입만 가능하다. (K는 들어오는 타입으로 지정)

(?는 클래스와 인터페이스에 사용할 수 없음)
<? extends T> // T와 T의 자손타입만 가능
<? super T> // T와 T의 부모타입만 가

<?> // 모든 타입 가능. <? extends Object>랑 같은 의미
- 와일드 카드는 위와 마찬가지로 제네릭타입에 유연성을 제공하기 위해 사용된다. (특정한 타입을 지정하는게 아니라, 타입의 범위지정하는데에 초점을 맞춘다)
* */

/*
Number의 자손 타입들이 가능하다. EX) Integer, Short, Double, Long 등의
* */
class Class_ex4<K extends Number> {
	
}

/*
Number의 자식 타입들이 가능하다. EX) Integer, Short, Double, Long 등의
- "?"는 클래스나 인터페이스에는 불가능하다.
* */
//class Class_ex5<? extends Number> {}

/* 
아래를 분석해보면 E는 Comparable<? super E>의 자손 타입이여야 한다
=> 이말은 E가 Comparable 인터페이스를 상속받아 구현해놔야 사용 가능하다.
* */
class Class_ex5 <E extends Comparable<? super E>> {}


public class practice_1 {
}
