package Chapter_4.Item_20;

import java.util.AbstractList;
import java.util.List;
import java.util.Objects;

/* 추상 클래스보다는 인터페이스를 우선하라
둘의 차이: 추상 클래스가 정의한 타입을 구현하는 클래스는 반드시 추상 클래스의 하위 클래스가 되어야 한다.

인터페이스는 믹스인 정의에 안성맞춤

인터페이스는 계층구조가 없는 타입 프레임워크를 만들 수 있다.
인터페이스의 메서드 중 구현 방법이 명백한 것이 있으면, 디폴트 메서드로 제공해 일감을 덜어준다.
	- 디폴트 메서드를 제공할 땐 상속을 고려해 @implSpec를 붙여 문서화 해야한다.
	
인터페이스와 추상골격 구현 클래스를 함께 제공하는 식으로 두 장점을 모두 취하는 방법도 있다.
	- 인터페이스로는 타입을 정의 + 필요하면 디폴트 메서드도 몇개 정의
	- 골격 구현 클래스는 나머지 메서들까지 구현한다.
	- 이를 "템플릿 메서드 패턴"이라고 한다. EX) AbstractCollection, AbstractList
* */
public class EX_1 {
	static List<Integer> intArrayAsList(int[] a) {
		Objects.requireNonNull(a);
		
		return new AbstractList<Integer>() {
			@Override
			public Integer get(int index) {
				return a[index];
			}
			@Override
			public Integer set(int i, Integer val) {
				int oldVal = a[i];
				a[i] = val;
				return oldVal;
			}
			@Override
			public int size() {
				return a.length;
			}
		};
	}
}

/* 정리
- 일반적인 다중 구현용 타입으로는 인터페이스가 가장 적합하다.
복잡한 인터페이스라면 수고를 덜어주는 골격 구현을 함께 제공하는 방법을 고려하라
* */
