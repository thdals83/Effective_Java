package Chapter_6.Item_39;

import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
/* 명명 패턴보다 애너테이션을 사용하라 

명명 패턴의 단점
1. 명명 패턴은 오타가 나면 안된다.
2. 올바른 프로그램 요소에서만 사용되리라 보증 할 수 없다.
* */


//아래 처럼 애너테이션에 선언하는 애너테이션을 메타애너테이션이라고 한다.
@Retention(RetentionPolicy.RUNTIME) //@Test가 런타임에도 유지되어야 한다는 표시
@Target(ElementType.METHOD) //타겟이 메서드 선언에서만 사용되어야 한다. (클래스 선언, 필드 선언 등 다른 프로그램 요소로는 달 수 없다.)
@interface Test {
}

//아래 EX_1처럼 내가 정적 메서드만 실행되게 하고 싶지만 이를 할 수 없다.
class EX_1 {
	@Test
	public static void m1() {
	} //성공 해야 한다.

	public static void m2() {
	}

	@Test
	public static void m3() {
		throw new RuntimeException("실패");
	}
}

// 애너테이션에 의미를 부여한(애너테이션쪽 처리 코드)
/*
해당 테스트 러너는 명령줄로 부터 완전 정규화된 클래스의 이름을 받아,
클래스에서 @Test애너테이션이 달린 메서드를 차례로 호출한다.
그리고 애너테이션에 잘못된 예외가 발생하면 오류 메세지를 낸다. 
* */
class EX_2 {
	public static void main(String[] args) throws Exception {
		int tests = 0;
		int passed = 0;
		Class<?> testClass = Class.forName(args[0]);
		for (Method m : testClass.getDeclaredMethods()) {
			if (m.isAnnotationPresent(Test.class)) {
				tests++;
				try {
					m.invoke(null);
					passed++;
				} catch (InvocationTargetException wrappedExc) {
					Throwable exc = wrappedExc.getCause();
					System.out.println(m + " 실패: " + exc);
				} catch (Exception exc) {
					System.out.println("잘못 사용한 @Test: " + m);
				}
			}
		}
		System.out.printf("성공: %d, 실패: %d%n",
				passed, tests - passed);
	}
}

//특정 예외를 던져야만 성공하는 테스트를 만드는 애너테이션
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@interface ExceptionTest {
	//여기서 와일드 카드는 Throwable을 상속한 하위 객체의 뜻 즉 모든 예외 타입을 수용한다.
	Class<? extends Throwable> value();
}

class EX_3 {
	@ExceptionTest(ArithmeticException.class)
	public static void m1() {
			int i = 0;
			i = i / i;
	}
	@ExceptionTest(ArithmeticException.class)
	public static void m2() {  // 실패해야 한다. (다른 예외 발생)
		int[] a = new int[0];
		int i = a[1];
	}
	@ExceptionTest(ArithmeticException.class)
	public static void m3() { }  // 실패해야 한다. (예외가 발생하지 않음)

	public static void main(String[] args) throws Exception {
		int tests = 0;
		int passed = 0;
		Class<?> testClass = Class.forName(args[0]);
		for (Method m : testClass.getDeclaredMethods()) {
			if (m.isAnnotationPresent(ExceptionTest.class)) {
				tests++;
				try {
					m.invoke(null);
					System.out.printf("테스트 %s 실패: 예외를 던지지 않음%n", m);
				} catch (InvocationTargetException wrappedEx) {
					Throwable exc = wrappedEx.getCause();
					Class<? extends Throwable> excType =
							m.getAnnotation(ExceptionTest.class).value();
					if (excType.isInstance(exc)) {
						passed++;
					} else {
						System.out.printf(
								"테스트 %s 실패: 기대한 예외 %s, 발생한 예외 %s%n",
								m, excType.getName(), exc);
					}
				} catch (Exception exc) {
					System.out.println("잘못 사용한 @ExceptionTest: " + m);
				}
			}
		}
		System.out.printf("성공: %d, 실패: %d%n",
				passed, tests - passed);
	}
}


//배열 매개변수를 받는 애너테이션 타입
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@interface ExceptionTest2 {
	//여기서 와일드 카드는 Throwable을 상속한 하위 객체의 뜻 즉 모든 예외 타입을 수용한다.
	Class<? extends Throwable>[] value();
}	
class EX_4 {
	@ExceptionTest2({ IndexOutOfBoundsException.class, NullPointerException.class })
	public static void doublyBad() {   // 성공해야 한다.
		List<String> list = new ArrayList<>();

		// 자바 API 명세에 따르면 다음 메서드는 IndexOutOfBoundsException이나
		// NullPointerException을 던질 수 있다.
		list.addAll(5, null);
	}
}

/*
배열 매개변수 대신 @Repeatable을 다는 방식으로 애너테이션을 여러개 사용 할 수도 있다.

주의할 점
1. @Repeatable을 단 애너테이션을 반환하는 컨테이너 애너테이션을 하나 더 정의해야 한다. @Repeatable에 이 컨테이너 애너테이션의 class 객체를 매개변수로 전달해야 한다.
2. 컨테이너 애너테이션은 내부 애너테이션 타입의 배열을 반환하는 value를 정의해야 한다.
3. 컨테이너 애너테이션 타입에는 적절한 @Retenion 과 @Target이 명시되어야 한다.

아래 이를 구현한 예시
* */

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Repeatable(ExceptionTestContainer.class)  //컨테이너 애너테이션 class 객체
@interface ExceptionTest3 {
	Class<? extends Throwable> value();
}
//컨테이너 애너테이션
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@interface ExceptionTestContainer {
	ExceptionTest3[] value(); // value 메서드 정의
}



public class Item_39 {
}
