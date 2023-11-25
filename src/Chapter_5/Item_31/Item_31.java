package Chapter_5.Item_31;

/* 한정적 와일드 카드를 사용해 API 유연성을 높이라
* */

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

class Stack1<E> {
	public Stack1() {};
	public void push(E e) {};
	public boolean isEmpty() {return true;}
	public void pushAll(Iterable<E> src) {
		for (E e : src) {
			push(e);
		}
	}

	public static void main(String[] args) {
		/*
		아래와 같이 Number의 하위 객체인 Integer를 선언하면, 정상적으로 작동해야 할 것ㅅ 같지만
		매개변수화 타입이 불공변이기 때문에 작동하지 않는다.
		* */
		Stack1<Number> numberStack = new Stack1<>();
		Iterable<Integer> integers = Arrays.asList(1, 2, 3);
//		numberStack.pushAll(integers);
	}
}

/*
이를 위해 와일드카드 타입을 사용 <? extends E>
-> Number의 포함 자식 클래스 사용 가능
* */
class Stack2<E> {
	public Stack2() {};
	public void push(E e) {};
	public boolean isEmpty() {return true;}
	public void pushAll(Iterable<? extends E> src) {
		for (E e : src) {
			push(e);
		}
	}

	public static void main(String[] args) {

		Stack2<Number> numberStack = new Stack2<>();
		Iterable<Integer> integers = Arrays.asList(1, 2, 3);
		numberStack.pushAll(integers);
	}
}

class Stack3<E> {
	private E[] elements;
	private int size = 0;
	
	public Stack3() {};
	public void push(E e) {}
	public E pop() {return elements[--size];};
	public boolean isEmpty() {return true;}
	public void popAll(Collection<E> dst) {
		while (!isEmpty()) 
		dst.add(pop());
	}

	public static void main(String[] args) {
		/*
		여기서도 마찬가지로 Object는 Number의 하위타입이 아니기 때문에 오류가 난다.
		* */
		Stack3<Number> numberStack = new Stack3<>();
		Collection<Object> objects = List.of(4, 2, 3);
//		numberStack.popAll(objects);
	}
}

class Stack4<E> {
	private E[] elements;
	private int size = 0;

	public Stack4() {};
	public void push(E e) {}
	public E pop() {return elements[--size];};
	public boolean isEmpty() {return true;}
	public void popAll(Collection<? super E> dst) {
		while (!isEmpty())
			dst.add(pop());
	}

	public static void main(String[] args) {
		/*
		? super E -> E와 E의 부모타입만 허용
		* */
		Stack4<Number> numberStack = new Stack4<>();
		Collection<Object> objects = List.of(4, 2, 3);
		numberStack.popAll(objects);
	}
}

/*
- 유연성을 극대화하려면 원소의 생산자나 소비자용 입력 매개변수에 와일드카드 타입을 사용하라
  PECS: Producer-Extends, Consumer-Super
  - 매개변수화 타입 T가 생산자라면 ? extends T (stack에서 pushAll이 Stack이 사용할 E의 인스턴스를 생성하므로)
  - 소비자라면 ? super T  (stack에서 popAll이 Stack이 사용할 E의 인스턴스를 소비하므로)
  
- 입력 매개변수가 생성자와 소비자의 역할을 동시에 하면 와일드 카드를 써서 좋을게 없다. 정확한 타입을 지정해야 한다.

* */

/*
전 Item 30에 만들었던 Union 코드에 적용 시켰을 때
* */
class Ex2 {
	public static <E> Set<E> union(Set<? extends E> s1, Set<? extends E> s2) {
		Set<E> res = new HashSet<>(s1);
		res.addAll(s2);
		return res;
	}
	public static void main(String[] args) {
		Set<Integer> s1 = Set.of(1, 2, 3);
		Set<Double> s2 = Set.of(2.0, 4.0, 2.2);
		Set<Number> numbers = Ex2.union(s1, s2);
	}
}

//메서드 정의시에 타입 매개변수 와일드 카드에는 공통되는 부분이 있어 어떤걸 사용해야 할지 고민되는 순간이 있음
class Ex3 {
	public static <E> void swap1(List<E> list, int i, int j) {}
	public static void swap2(List<?> list, int i, int j) {
	//	list.set(i, list.set(j, list.get(i)));
	}
	/* 
	어떤게 더 나은 선택일까?
	- public Api라면 swap2가 나음 -> 어떤 리스트든 메서드에 넘겨주면 원소들을 교환해 줄것이니
	
	# 메서드 선언에 타입 매개변수가 한번만 나오면 와일드 카드를 사용한다.
	하지만 위의 swap2는 컴파일 에러가 난다.
	* */
}

/*
아래와 같이 도우미 메서드를 사용한다.
swaphelper 메서드는 리스트가 List<E>임을 알고있음
즉 꺼낸 타입의 값이 항상 E이고 E타입이면 리스트에 넣어도 된다는 안전함이 있음
* */
class Ex4 {
	public static void swap(List<?> list, int i, int j) {
		swapHelper(list, i, j);
	}
	private static <E> void swapHelper(List<E> list, int i, int j) {
		list.set(i, list.set(j, list.get(i)));
	}
}







public class Item_31 {
}
