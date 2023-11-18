package Chapter_5.Item_28;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/* 배열보다는 리스트를 사용하라
배열 = 공변
제네릭 = 불공변
* */
class ex1 {
	public static void main(String[] args) {
		/*
		첫번째는 런타임 시점에
		두번째는 컴파일 시점에 에러가 난다.
		이러면 당연히 컴파일 시점에 알아내는게 낫다.
		* */
		Object[] objArr = new Long[1];
		objArr[0] = "d";

		//문법상 불공변이라 허용하지 않음
		//List<Object> ol = new ArrayList<Long>();
		//ol.add("")
		
		//배열은 제네릭으로 만들 수가 없다. 그 이유는 아래와 같다.
		//List<String>[] stringLists = new List<String>[1]; // (1) 허용된다고 가정해보자.
		//List<Integer> intList = List.of(42);          // (2) 원소가 하나인 List<Integer> 생성
		//Object[] objects = stringLists;                   // (3) stringLists를 objects에 할당
		//objects[0] = intList;                             // (4) intList를 objects의 첫번째 원소로 저장한다.
		//String s = stringLists[0].get(0);                 // (5) stringList[0]에 들어가있는 첫번째 요소는 Integer이므로 형변환 오류 발생.
	}
}

/* 배열을 제네릭으로 만들 수 없어 귀찮을 때도 있다.
- 제네릭 컬렉션에서는 자신의 원소 타입을 담은 배열을 반환하는게 보통은 불가능하다.
* */

//아래 순차적으로 제네릭을 사용하는 방법으로 개선
//제네릭을 사용하지 않았을 때
//아래에서 choose 메서드를 호출할 때마다 반환된 Object를 원하는 타입으로 형변환을 해야 한다.
class Chooser1 {
	private final Object[] choiceArray;
	
	public Chooser1(Collection choices) {
		choiceArray = choices.toArray();
	}
	public Object choose(){
		Random random = ThreadLocalRandom.current();
		return choiceArray[random.nextInt(choiceArray.length)];
	}
}

//아래 개선된 코드에선 동작은 되지만, 컴파일러가 안전을 보장하지 못한다.
class Chooser2<T> {
	private final T[] choiceArr;
	
	public Chooser2(Collection<T> choices) {
		choiceArr = (T[]) choices.toArray();
	}

	public Object choose(){
		Random random = ThreadLocalRandom.current();
		return choiceArr[random.nextInt(choiceArr.length)];
	}
}

// 배열대신 리스트 쓰삼
class Chooser3<T> {
	private final List<T> exArr;
	
	public Chooser3(Collection<T> cho) {
		exArr = new ArrayList<>(cho);
	}
	
}

public class Item_28 {
}
