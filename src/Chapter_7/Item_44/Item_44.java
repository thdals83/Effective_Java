package Chapter_7.Item_44;

import java.util.function.BinaryOperator;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

/* 표준 함수형 인터페이스를 사용하라

람다를 지원하면서, 템플릿 메서드패턴의 매력이 크게 줄었다.
아래 예시
* */
abstract class TemplateMethod {
	abstract String process(String input);

	// 기본 메서드
	public final void printProcessed(String input) {
		System.out.println(process(input));
	}
}
class useTemplateMethod extends TemplateMethod {
	@Override
	String process(String input) {
		return input.toUpperCase();
	}

	public static void main(String[] args) {
		TemplateMethod processor = new useTemplateMethod();
		processor.printProcessed("Hello World");
	}
}

//람다 이용한 버전
class useLambda {
	private Function<String, String> processor;

	public useLambda(Function<String, String> processor) {
		this.processor = processor;
	}

	public void printProcessed(String input) {
		System.out.println(processor.apply(input));
	}

	public static void main(String[] args) {
		useLambda processor = new useLambda(s -> s.toUpperCase());
		processor.printProcessed("Hello World");
	}
}

/*
책의 예시에서는 미리 만들어져 있는 표준 함수형 인터페이스가 있으면 그것을 활용하라고 한다.
- 유용한 디폴트 메서드를 많이 제공하기도 한다. (다른 코드와 상호 운용성도 크게 좋아질 것임)

기본 함수형 인터페이스 6가지
* */
class functionInterfaceEx {
	public static void main(String[] args) {
		//단일 인수를 받고, 동일한 타입의 결과를 반환
		UnaryOperator<Integer> square = x -> x * x;
		System.out.println(square.apply(5)); // res = 25
		
		//두 인수를 받고, 동일한 타입의 결과를 반환
		BinaryOperator<Integer> sum = Integer::sum;
		System.out.println(sum.apply(1, 3)); // res = 4
		
		//하나의 인수를 받고, boolean 타입을 반환
		Predicate<Integer> isPositive = x -> x > 0;
		System.out.println(isPositive.test(5)); // res = true
		
		//하나의 인수를 받고, 다른 타입으로 결과를 반환
		Function<String, Integer> length = String::length;
		System.out.println(length.apply("dd")); // res = 2
		
		//인수를 받지 않고, 결과를 반환
		Supplier<Double> randomMath = Math::random;
		System.out.println(randomMath.get()); // res = random numer
		
		//하나의 인수를 받고, 반환 값이 없음
		Consumer<String> printer = System.out::println;
		printer.accept("hello world");
	}
}

/*
표준 함수형 인터페이스 대부분은 기본 타입만 지원한다. 
그렇다고 기본 함수형 인터페이스에 박싱된 기본 타입을 넣어 사용하게 되면, 동작은 하지만 계산량이 많을 땐 성능이 처참해질 수 있다.

표준 함수형 인터페이스를 사용하지 않고, 직접 작성해야 할 때
- 필요한 용도에 맞는게 없다면
- 직접 만든 함수형 인터페이스에는 항상 @FunctionalInterface 를 사용할 것
* */

public class Item_44 {
}
