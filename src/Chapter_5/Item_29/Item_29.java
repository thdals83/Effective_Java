package Chapter_5.Item_29;

/* 이왕이면 제네릭 타입으로 만들어라
* */

import java.util.Arrays;
import java.util.EmptyStackException;

class ObjectStack {
	private Object[] elements;
	private int size = 0;
	private static final int DEFAULT_INITIAL_CAPACITY = 16;

	public ObjectStack() {
		this.elements = new Object[DEFAULT_INITIAL_CAPACITY];
	}

	public void push(Object e) {
		ensureCapacity();
		elements[size++] = e;
	}

	public Object pop() {
		if (size == 0) {
			throw new EmptyStackException();
		}

		Object result = elements[--size];
		elements[size] = null; // 다 쓴 참조 해제

		return result;
	}

	public boolean isEmpty() {
		return size == 0;
	}

	private void ensureCapacity() {
		if (elements.length == size) {
			elements = Arrays.copyOf(elements, 2 * size + 1);
		}
	}

	public static void main(String[] args) {
		ObjectStack os = new ObjectStack();
		os.push("dd");
		os.push(1);

		try {
			// 첫 번째 원소 꺼내기 (정수 1)
			String num = (String) os.pop();
			System.out.println(num);

			// 두 번째 원소 꺼내기 (문자열 "hello")
			String str = (String) os.pop(); // 런타임 오류 발생 가능
			System.out.println(str);
		} catch (ClassCastException e) {
			System.out.println("Type casting error!");
		}
	}
}

class GenericStack<E> {
	private E[] elements;
	private int size = 0;
	private static final int DEFAULT_INITIAL_CAPACITY = 16;

	@SuppressWarnings("unchecked")
	public GenericStack() {
		//아래처럼 작성하면 오류가 발 E와 같 실체화 불가 타입은 배열로 만들 수 없기 때문
		//this.elements = new E[DEFAULT_INITIAL_CAPACITY];
		
		//이처럼 작성하고 SuppressWarnings으로 하던가 그냥 Object 두고 pop 할때 형변환 해도 된다.
		this.elements = (E[]) new Object[DEFAULT_INITIAL_CAPACITY];
	}

	public void push(E e) {
		ensureCapacity();
		elements[size++] = e;
	}

	public E pop() {
		if (size == 0) {
			throw new EmptyStackException();
		}

		E result = elements[--size];
		elements[size] = null; // 다 쓴 참조 해제

		return result;
	}

	public boolean isEmpty() {
		return size == 0;
	}

	private void ensureCapacity() {
		if (elements.length == size) {
			elements = Arrays.copyOf(elements, 2 * size + 1);
		}
	}

	public static void main(String[] args) {
		GenericStack<String> os = new GenericStack<String>();
		os.push("dd");
		os.push("dd");

		try {
			// 첫 번째 원소 꺼내기 (정수 1)
			String num = (String) os.pop();
			System.out.println(num);

			// 두 번째 원소 꺼내기 (문자열 "hello")
			String str = (String) os.pop(); // 런타임 오류 발생 가능
			System.out.println(str);
		} catch (ClassCastException e) {
			System.out.println("Type casting error!");
		}
	}
}


public class Item_29 {
}
