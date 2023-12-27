package Chapter_7.Item_48;


/* 스트림 병렬화는 주의해서 적용하라
* */

import java.math.BigInteger;
import java.util.stream.Stream;

import static java.math.BigInteger.ONE;
import static java.math.BigInteger.TWO;

class EX_1 {
	/* 아래 예시에서 소수를 찍는 속도를 높이기 위해서 스트림 파이프라인의 parallel()를 호출한다
	이로 인해 성능이 개선 될 것 처럼보이지만 실제로는 아무것도 출력하지 못하면서
	CPU의 성능을 90% 까지 잡아먹는 상태가 무한히 계속된다.
	
	이유
	스트림 라이브러리가 병렬화하는 방법을 찾아내지 못해서
	환경이 아무리 좋더라도 중간 연산에 limit나, Stream.iterate를 사용하면 성능 개선을 쓸 수가 없다.
	* */
	public static void main(String[] args) {
		primes().map(p -> TWO.pow(p.intValueExact()).subtract(ONE))
				.parallel() // 스트림 병렬화
				.filter(mersenne -> mersenne.isProbablePrime(50))
				.limit(20)
				.forEach(System.out::println);
	}

	static Stream<BigInteger> primes() {
		return Stream.iterate(TWO, BigInteger::nextProbablePrime);
	}
}

/* 병렬화를 쓰기 좋을 때
스트림의 소스가 ArrayList, HashMap, HasSet, ConcurrentHashMap의 인스턴스 거나 
배열, int or long 범위일 때 -> 데이터를 원하는 크기로 정확하고 손쉽게 나눌 수 있을 때

나누는 작업은 Spliterator가 담당

참조 지역성의 3가지
시간 지역성 : 최근에 참조된 주소는 빠른 시간 내에 다시 참조되는 특성
공간 지역성 : 참조된 주소와 인접한 주소의 내용이 다시 참조되는 특성
순차 지역성 : 데이터가 순차적으로 엑세스 되는 특성(공간 지역성)

- 배열의 경우 이 참조 지역성이 좋음
- ArrayList or Hash도 내부적으로 배열을 사용하고 있어 참조 지역성이 좋음 
* */

/* 종단 연산과 병렬화
종단 연산에서 수행하는 작업량이 전체 작업의 대부분을 차지하면서 순차적인 작업이라면 병렬 수행의 효과가 떨어진다.

적합 하지 않은 것
- collect

적합한 종단 연산은 아래와 같다.
- reduce
- anyMatch, allMatch, noneMatch
* */

public class Item_48 {
}
