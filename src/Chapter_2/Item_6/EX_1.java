package Chapter_2.Item_6;

import org.junit.Test;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

public class EX_1 {
	/*
	불필요한 객체 생성을 피하라.
	- 똑같은 기능의 객체를 매번 생성하기 보단, 객체 하나를 재사용하는 편이 좋다.
	- 재사용은 빠르고 세련됨
	- 불변 객체는 언제든 재사용 할 수 있다.
	 * */
	@Test
	public void 객체_같을_때_다를_때() {
		/*
		아래 코드는 절대 따라해선 안된다.
		- 문장이 실행될 때 마다 String 인스턴스가 새롭게 만들어진다.
		* */
		String s1_1 = new String("bikini");
		String s1_2 = new String("bikini");
		assertFalse(s1_1 == s1_2);

		//하나의 String 인스턴스를 사용한다.
		//이렇게 한다면 똑같은 VM 안의 문자열 리터럴을 사용하는 모든 코드가 같은 객체 재사용 보장
		//cf) String pool의 플라이웨이트 패턴: 같은 내용의 String 객체가 선언된다면 기존의 객체를 참조하게 한다
		String s2_1 = "bikini";
		String s2_2 = "bikini";
		assertTrue(s2_1 == s2_2);

		//생성자 대신 정적 팩터리 메서드 사용 (불필요한 객체 생성을 막는 방법)
		Boolean test1 = Boolean.valueOf("true");
	}
	/////////////////////////////////////////////////////////
	/*
	- 만드는데 메모리나 시간이 오래걸리는 객체 즉 비싼 객체를 반복적으로 만들어야 한다면
	캐싱하여 재사용 할 수 있는지 보아야 한다.
	
	아래 방식의 문제는 String.matchs 메서드가 문자열의 형태를 확인하는 가장 쉬운 방법이지만,
	내부에서 Pattern 인스턴스를 생성하고 바로 GC의 대상이 된다. 그리고 유한 상태 머신을 만들기 때문에 생성 비용이 높다.
* */
	static boolean isRomanNumeralSlow(String s) {
		return s.matches("^(?=.)M*(C[MD]|D?C{0,3})"
				+ "(X[CL]|L?X{0,3})(I[XV]|V?I{0,3})$");
	}

	//성능을 개선하려면 Pattern 인스턴스를 클래스 초기화 시점에 생성 시켜두어 캐싱한다.
	private static final Pattern ROMAN = Pattern.compile(
			"^(?=.)M*(C[MD]|D?C{0,3})"
					+ "(X[CL]|L?X{0,3})(I[XV]|V?I{0,3})$");

	static boolean isRomanNumeralFast(String s) {
		return ROMAN.matcher(s).matches();
	}
	/////////////////////////////////////////////////////////

	//불변 객체일 때도 발생할 수 있는 문제점들이 있다.
	@Test
	public void 불변객체의_발생할_수_있는_문제점_어뎁터_패턴에서() {
		Map<String, Object> map = new HashMap<>();

		Set<String> set1 = map.keySet();
		Set<String> set2 = map.keySet();
		//여기서 keySet을 사용하면, 동일한 객체가 사용된다.
		assertSame(set1, set2);

		set1.remove("Hello");
		//동일한 객체이기 때문에, set1에서 값을 삭제하면, 다른 객체들도 전부 영향을 받는다.
		assertTrue(set1.size() == set2.size());
	}

	//오토박싱 = 기본 타입과 박싱된 기본 타입을 섞어서 사용할 때 자동으로 상호 변환해 주는 기술
	//아래에선 sum을 Long으로 생성하여, long타입인 i가 더해질 때매다 새로운 Long 타입 인스턴스가 생성되었음
	private static long sum() {
		Long sum = 0L;
		for (long i = 0; i <= Integer.MAX_VALUE; i++) {
			sum += i;
		}
		return sum;
	}
	/*
	정리
	- 요즘엔 JVM 성능이 좋아 작은 객체를 생성하고 회수하는 일이 크게 부담되지 않음
	- 그래서 명확성, 간결성, 기능을 위해 추가로 생성하는거면 오케이
	- 그래서 단순히 객체 생성을 피하기 위해 객체 풀을 만들지는 말자
	* */
}