package Chapter_7.Item_46;


/* 스트림에서는 부작용 없는 함수를 사용하라
스트림 패러다임의 핵심: 계산을 일련의 변환으로 재구성 하는 것
	- 각 변환 단계는 가능한 이전 단계의 결과를 받아 처리하는 순수 함수여야 한다.
	- 순수 함수: 오직 입력만이 결과에 영향을 주는 함수, 다른 가변 상태를 참조하지 않고, 함수 스스로도 다른 상태를 변경하지 않는다.
* */

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toList;

class EX_1 {
	/* 스트림 패러다임을 이해하지 못한 코드
	스트림 코드를 가장한 반복적 코드, 모든 작업이 forEach문에서 실행되는데 이때 외부 상태를 수정하는 람다가 실행된다.
	* */
	public static void main(String[] args) {
		InputStream file = InputStream.nullInputStream();
		
		Map<String, Long> freq = new HashMap<>();
		try (Stream<String> words = new Scanner(file).tokens()) {
			words.forEach(word -> {
				freq.merge(word.toLowerCase(), 1L, Long::sum);
			});
		}
	}

	/* 올바르게 작성한 코드
	forEach 코드는 계산 결과를 보고할 때만 사용하고, 계산하는 용도로는 사용하지 말 것
	* */
	public static void main2(String[] args) {
		InputStream file = InputStream.nullInputStream();

		Map<String, Long> freq;
		try (Stream<String> words = new Scanner(file).tokens()) {
			freq = words
					.collect(groupingBy(String::toLowerCase, counting()));
		}

		//상위 10개를 뽑아내는 코드
		List<String> topTen = freq.keySet().stream()
				.sorted(comparing(freq::get).reversed())
				.limit(10)
				.collect(toList());
		
		//toList, toMap
		//groupingBy: 입력으로 분류 함수를 받고, 출력으로는 원소들을 카테고리별로 모아 놓은 맵을 담는 수집기
		//다운스트의 역할
		//joining: 문자열 등의 CharSequence 인스턴스의 스트림에만 적용할 수 있다.
	}
}
public class Item_46 {
}
