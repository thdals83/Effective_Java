package Chapter_4.Item_17;

/* 변경 가능성을 최소화하라

불변 클래스를 만드는 다섯 가지 규칙
- 객체의 상태를 변경하는 메서드(setter와 같은)를 제공하지 않는다.
- 클래스를 확장할 수 없도록 한다.
	- 하위 클래스에서 부주의 하게 객체의 상태를 변경하는 것을 막는다.
- 모든 필드를 final로 선언한다.
- 모든 필드를 private로 선언한다.
- 자신 외에는 내부의 가변 컴포넌트에 접근할 수 없도록 한다.
* */

import java.math.BigInteger;

// 아래 메서드들은 인스턴스 자신을 주는게아닌, 새로운 인스턴스를 만들어 반환한다.
// 이는 함수형 프로그래밍
final class Complex {
	private final double re;
	private final double im;

	public Complex(final double re, final double im) {
		this.re = re;
		this.im = im;
	}

	public double realPart(){
		return re;
	} // 실수부

	public double imaginePart(){
		return im;
	} // 허수부

	public Complex plus(Complex complex){
		return new Complex(re + complex.re, im + complex.im);
	}

	public Complex minus(Complex complex){
		return new Complex(re - complex.re, im - complex.im);
	}

	public Complex times(Complex complex){
		return new Complex(re * complex.re - im * complex.im, re * complex.im - im * complex.re);
	}

	public Complex divideBy(Complex complex){
		double temp = complex.re * complex.re + complex.im + complex.im;
		return new Complex((re*complex.re + im * complex.im) / temp, (im * complex.re - re * complex.im) / temp);
	}
}

// 불변 객체는 근본적으로 thread safe해 동기화할 필요가 없다
class threadSafe_예시 {
	public static final Complex ZERO = new Complex(0, 0);
	public static final Complex ONE = new Complex(1, 0);
	public static final Complex I = new Complex(0, 1);
}

/*
불변 클래스와 정적 팩토리
- 불변 클래스는 자주 사용되는 인스턴스를 캐싱해 정적 팩토리를 제공

불변 객체는 내부 데이터를 공유할 수 있다.

객체를 만들 때 다른 불변 객체들을 구성요소로 사용하면 이점이 많다.
- 불변들로 구성하면 구조가 복잡해도 불변을 유지하기 때문에 EX) Map, Set 등등 값이 안바귀니까

불변 객체는 그 자체로 실패 원자성을 제공한다.
- 내부 상태가 바뀌지 않기 때문에 불일치 상태에 빠질 일이 없음
------------------------------------------------------------
불변 클래스의 단점
- 값이 다르면 다 독립된 인스턴스로 생성해야 함 ->  비용이 많이 나감
EX) 백만 비트짜리 BinInteger에서 비트를 하나 바꿔야 할 때, 단지 한 비트만 다른 것을 만들기 위해 시간과 공간을 잡아 먹음
BinInteger moby = ...;
moby = moby.flipBit(0)

이와는 달리 BitSet도 비트 순열을 표현하지만, 얘는 가변이다.
BitSet 클래스는 비트 하나만 상수 시간 안에 바꿔주는 메서드를 제공한다.
BigSet moby = ...;
moby.flip(0);

이러한 성능 문제를 해결하기 위한 두가지 방식이 있음
1. 가변 동반 클래스 제공
BigInteger에서 지수 연산과 같은 다단계 연산이 발생할 때, 불변 클래스의 문제점을 보완하기 위해
다단계 연산을 예측하여 기본 기능을 제공한다.
Math package를 가보면 package-rpivate 형태로 클래스들을 제공하고 있는데 이들을 '가변 동반 클래스'라고 한다.

2. 정적 팩터리 제공
------------------------------------------------------------
* */
class Test {
	/*
BigInteger와 BigDecimal은 둘다 재정의 할 수 있게 설계됨
이 때문에 상속받은 새로운 클래스를 생성할 경우, 자식 클래스는 가변 클래스 일 수 있음
그래서 사용할 때 thread safe하다고 썼지만 그렇지 않을 수도 있다. 그래서 타입을 확인해 주어야함
* */
	public static BigInteger safeInstance(BigInteger val) {
		return val.getClass() == BigInteger.class ? val : new BigInteger(val.toByteArray());
	}
}
/*
정리
- 클래스가 꼭 필요한 경우가 아니면 불변이어야 한다.
- 불변으로 만들 수 없는 클래스는, 변경 할 수 있는 부분을 최소한으로 만든다.
- 다른 합당한 이유가 없으면 모든 필드는 private final
- 
* */

public class EX_1 {
}
