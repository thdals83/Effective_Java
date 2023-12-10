package Chapter_6.Item_37;

/* ordinal 인덱싱 대신 EnumMap을 사용하라
* */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toMap;

class Plant {
	enum LifeCycle {ANNUAL, PERENNIAL, BIENNIAL}
	
	final String name;
	final LifeCycle lifeCycle;

	public Plant(String name, LifeCycle lifeCycle) {
		this.name = name;
		this.lifeCycle = lifeCycle;
	}
}

/* 위의 클래스 식물들을 배열 하나로 관리하고, 생애주기별로 묶어본다.
아래는 ordinal값을 배열의 인덱스로 사용하는 안좋은 방법의 예시다.
* */
class WorstPlant {
	/*
	문제점
	1. 첫째줄의 배열과 제네릭과의 호환되지 않아, 비검사 형변환을 수행해야 한다.
	2. 배열은 각 인덱스의 의미를 모르니 출력 결과에 직접 레이블을 달아야 한다.
	3. 정수는 열거 타입과 달리 타입 안전하지 않다.
	* */
	public static void worstCase1(String[] args) {
		Set<Plant>[] plantsByLifeCycle = (Set<Plant>[]) new Set[Plant.LifeCycle.values().length];
		
		for (int i = 0; i < plantsByLifeCycle.length; i++)
			plantsByLifeCycle[i] = new HashSet<>();

		List<Plant> garden = new ArrayList<>();
		for (Plant p : garden)
			plantsByLifeCycle[p.lifeCycle.ordinal()].add(p);

		// 결과 출력
		for (int i=0; i<plantsByLifeCycle.length; i++) {
			System.out.printf("%s: %s%n", Plant.LifeCycle.values()[i], plantsByLifeCycle[i]);
		}
	}
	/*
	Map을 사용한 방법
	열거 타입을 key로 사용하도록 한다.
	* */
	public static void goodCase1(String[] args) {
		Map<Plant.LifeCycle, Set<Plant>> plantsByLifeCycle = new EnumMap<>(Plant.LifeCycle.class);
		
		for (Plant.LifeCycle lc: Plant.LifeCycle.values())
			plantsByLifeCycle.put(lc, new HashSet<>());

		List<Plant> garden = new ArrayList<>();
		for (Plant p: garden)
			plantsByLifeCycle.get(p.lifeCycle).add(p);
		
		//스트림까지 응용하면 아래와 같이 가능
		garden.stream()
				.collect(groupingBy(p -> p.lifeCycle,
						() -> new EnumMap<>(Plant.LifeCycle.class), Collectors.toSet()));
	}
}

/*
다른 예시
* */
enum Phase {
	SOLID, LIQUID, GAS;
	
	public enum Transition {
		MELT(SOLID, LIQUID), FREEZE(LIQUID, SOLID),
		BOILD(LIQUID, GAS), CONDENSE(GAS, LIQUID),
		SUBLIME(SOLID, GAS), DEPOSIT(GAS, SOLID);

		private final Phase from;
		private final Phase to;

		Transition(Phase from, Phase to) {
			this.from = from;
			this.to = to;
		}
		
		//상전이 Map을 추가한다.
		private static final Map<Phase, Map<Phase, Transition>>
				m = Stream.of(values())
				.collect(groupingBy(t -> t.from, () -> new EnumMap<>(Phase.class),
				toMap(t -> t.to, t -> t, (x, y) -> y, () -> new EnumMap<>(Phase.class))));

		public static Transition from(Phase from, Phase to) {
			return m.get(from).get(to);
		}

	}
}











public class Item_37 {
}
