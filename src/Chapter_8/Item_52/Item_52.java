package Chapter_8.Item_52;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/* 다중정의(overloading)은 신중히 사용하라
 * */

/*
재정의(override)한 메서드는 동적으로 선택되고
다중정의(overloading) 메서드는 정적으로 선택되기 때문이다.

아래는 override해서 동적으로 선택된 모습 EX
* */
class Wine {
	String name() { return "포도주";}
}
class SparklingWine extends Wine {
	@Override
	String name() { return "발포성 포도주";}
}
class Champagne extends SparklingWine {
	@Override
	String name() { return "샴페인"; }
}
class Overriding {
	public static void main(String[] args) {
		List<Wine> wineList = List.of(new Wine(), new SparklingWine(), new Champagne());

		//여기서 가장 하위에 재정의한 메서드가 실행된다.
		for (Wine wine : wineList)
			System.out.println(wine.name());  // "포도주", "발포성 포도주", "샴페인"
	}
}

// 컬렉션을 집합, 리스트, 그 외로 구분하고자 만든 프로그램
class CollectionClassifier {
	public static String classify(Set<?> s) {
		return "집합";
	}
	
	public static String classify(List<?> lst) {
		return "리스트";
	}
	
//	public static String classify(Collection<?> c) {
//		return "그 외";
//	}

	public static void main(String[] args) {
		Collection<?>[] collections = {
				new HashSet<String>(),
				new ArrayList<BigInteger>(),
				new HashMap<String, String>().values()
		};
		
		
		/* 여기서 실행해 보면 그 외만 세번 출력된다.
		3개의 classify 중에 어떤 것이 실행될 지는 컴파일타임에 정해지기 때문이다.
		여기서 for문이 Collection 타입이기 때문에 항상 이만 출력되게 되는 것이다.
		* */
		for (Collection<?> c : collections)
			System.out.println(classify(c));
	}
	
	//이를 해결하려면 classify 메서드를 하나로 합친 후 instanceof로 검사한다.
	public static String classify(Collection<?> c) {
		return c instanceof Set ? "집합" : 
				c instanceof List ? "리스트" : "그 외";
	}
	
	/* 다중정의를 올바르게 사용하는 방법
	1. 매개변수 수가 같은 다중정의는 만들지 말자.
	2. 가변인수(varargs)를 사용하는 메서드라면 다중정의를 아예 하지 말아야 한다.
	3. 다중정의하는 대신 메서드 이름을 다르게 지어주는 길도 열려 있다.
	   EX) ObjectOutputStream 클래스 writeBoolean(boolean), writeInt(int), writeLong(long)
	* */
}

/* 다중정의 주의사항
생성자의 다중정의
- 생성자는 이름을 다르게 지을 수 없으니, 정적팩터리를 사용한다.

오토박싱과 다중정의
- 매개변수 수가 같은 다중정의 메서드가 많더라도, 매개변수 중 하나 이상이 "근본적으로 다르다"면 헷갈릴 일이 없다.
  여기서 다르다는 건 두 타입의 값이 서로 어느쪽으로든 형변환 할 수 없다는 뜻

하지만 자바 5에서 오토박싱이 도입되면서 문제가 발생했다.
* */
class SetList {
	public static void main(String[] args) {
		Set<Integer> set = new TreeSet<>();
		List<Integer> list = new ArrayList<>();

		for (int i = -3; i < 3; i++) {
			set.add(i);
			list.add(i);
		}
		for (int i = 0; i < 3; i++) {
			set.remove(i);
			list.remove(i);
		}
		System.out.println(set + " " + list);
		// [-3, -2, -1] [-2, 0, 2] 
		
		/* 
		출력이 이상하게 되는 이유는
		List<E> 인터페이스 가 remove(Object)와 remove(int)를 다중정의 했기 때문이다.
		
		list.remove(i)는 다중정의된 remove(int idex)를 선택하여 원소가 아닌 "지정한 위치"의 원소를 제거하니, 
		다른 결과를 내게 되는 것이다. Integer로 형변환하여 올바른 다중정의 메서드를 선택하게 하면 해결된다.
		* */
		for (int i = 0; i < 3; i++) {
			set.remove(i);
			list.remove((Integer) i);
		}
	}
}

/* 람다/메서드 참조와 다중정의
다중정의 메서드들이 함수형 인터페이스 인수로 받을 때
서로 다른 함수형 인터페이스라도 인수 위치가 같으면 혼란이 생긴다.
* */
class EX_1 {
	public static void main(String[] args) {
		// 1. Thread의 생성자 호출
		new Thread(System.out::println).start();

		// 2. ExecutorService의 submit 메서드 호출
		ExecutorService exec = Executors.newCachedThreadPool();
//		exec.submit(System.out::println); // 컴파일 에러
	}
}




public class Item_52 {
}
