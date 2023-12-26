package Chapter_7.Item_45;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Stream;

import static java.util.stream.Collectors.groupingBy;

/* 스트림은 주의해서 사용하라
스트림: 데이터 원소의 연속적인 흐름
스트림 파이프라인: 여러 단계의 연산들을 연결하여 복잡한 데이터를 처리한다. 보통 소스에서 출발해 중간 연산과 최종 연산을 거친다. 

중간 연산
- 스트림을 어떠한 방식으로 변환하는 역할 
- 한 스트림을 다른 스트림으로 변한 
- EX) filter(), map(), sorted() ...

종단 연산
- 스트림에 최후의 연산을 가하는 것
- EX) forEach(), collect(), match(), count() ...

스트림 파이프라인 특징
- 지연 평가: 결과값이 필요할 때까지 계산을 늦추는 것
	- 이러한 특징으로 무한 스트림을 다룰 수 있다. -> sorted()와 같이 지연평가를 쓸 수 없는 상황을 생각할 것
- 기본적으로 순차적으로 실행된다. 하지만 병렬로 실행하고 싶다면 parallel 메서드를 호출하면 되지만, 효과를 볼 수 있는 상황이 많지 않다.

반복문을 스트림으로 바꾸는 것은 무조건 좋을까?
* */
//모든 반복문을 스트림으로 바꾸기 보단, 적절히 조합시키자
class Anagrams {
	public static void main(String[] args) throws IOException {
		File dictionary = new File(args[0]);
		Path dictionary2 = Paths.get(args[0]);
		int minGroupSize = Integer.parseInt(args[1]);

		Map<String, Set<String>> groups = new HashMap<>();
		//
		try (Scanner s = new Scanner(dictionary)) {
			while (s.hasNext()) {
				String word = s.next();
				groups.computeIfAbsent(alphabetize(word), (unused) -> new TreeSet<>()).add(word);
			}
		}

		
		//스트림을 과하게 쓴 모습 오히려 가독성이 떨어진다.
//		try (Stream<String> words = Files.lines(dictionary)) {
//			words.collect(
//							groupingBy(word -> word.chars().sorted()
//									.collect(StringBuilder::new,
//											(sb, c) -> sb.append((char) c),
//											StringBuilder::append).toString()))
//					.values().stream()
//					.filter(group -> group.size() >= minGroupSize)
//					.map(group -> group.size() + ": " + group)
//					.forEach(System.out::println);
//		}
		
		//적당히 쓴 스트림
		try (Stream<String> words = Files.lines(dictionary2)) {
			words.collect(groupingBy(word -> alphabetize(word)))
					.values().stream()
					.filter(group -> group.size() >= minGroupSize)
					.forEach(g -> System.out.println(g.size() + ": " + g));
		}

		for (Set<String> group : groups.values())
			if (group.size() >= minGroupSize)
				System.out.println(group.size() + ":" + group);
	}

	public static String alphabetize(String s) {
		char[] a = s.toCharArray();
		Arrays.sort(a);
		return new String(a);
	}
}

/*
char 값을 처리할 때는 스트림을 사용하면 안된다. 지원을 하지 않음
* */

/*
스트림의 적절한 활용
- 스트림 파이프라인은 되풀이 되는 계산을 주로 함수 객체로 표현하고 반복 코드에는 코드 블록을 사용해 표현한다.

스트림으로 처리하기 힘든 경우
- 스트림과 함수 객체로는 할 수 없지만, 코드 블록으로는 할 수 있는 경우
1. 코드 블록에서는 범위 안의 지역변수를 읽고 수정할 수 있다. 하지만 람다는 사실상 final 변수만 읽고 지역변수 수정은 불가능하다.
2. 코드 블록에서는 return / break / continue 문으로 블록의 흐름을 제어하거나, 메서드 선언에 명시된 검사 예외를 던질 수 있지만 람다는 불가능

스트림을 적용하기 좋은 경우
원소들의 시퀀스를 일관되게 변환해야 하는 경우 : map()
원소들의 시퀀스를 필터링 해야 하는 경우 : filter()
원소들의 시퀀스를 하나의 연산을 사용해 결합해야 하는 경우(더하기, 연결하기, 최솟값 구하기 등) :
원소들의 시퀀스를 컬렉션에 모으는 경우(공통된 속성을 기준으로) : collect()
원소들의 시퀀스에서 특정 조건을 만족하는 원소를 찾을 경우 : filter()

처리하기 어려운 경우
- 한 데이터가 파이프라인의 여러 단계를 통과해야 할 때 (각 단계에서 값들이 동시에 접근하는 경우)
	- 이유는 한 값을 다른 값에 매핑하고 나면 원래의 값은 잃는 구조이기 때문
* */

public class Item_45 {
}
