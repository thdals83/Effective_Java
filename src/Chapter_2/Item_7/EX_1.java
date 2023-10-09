package Chapter_2.Item_7;

import java.util.Arrays;
import java.util.EmptyStackException;

public class EX_1 {
}

/*
1. 메모리 누수가 일어날 수 있는 상황
- 스택이 커졌다가 줄어들었을 때, 스택에서 꺼내진 객체들을 회수가 되지 않는다.
  = 스택이 꺼내진 객체들을 다 쓴 참조 하고 있기 때문이다.
* */
class Stack {
	private Object[] elements;
	private int size = 0;
	private static final int DEFAULT_INITIAL_CAPACITY = 16;

	public Stack() {
		elements = new Object[DEFAULT_INITIAL_CAPACITY];
	}

	public void push(Object e) {
		ensureCapacity();
		elements[size++] = e;
	}

	public Object pop1() {
		if (size == 0)
			throw new EmptyStackException();
		return elements[--size];
	}
	
	//참조를 다 썼을 때 null 처리를 해 메모리 누수가 일어나지 않게 한다.
	public Object resolvePop() {
		if (size == 0) 
			throw  new EmptyStackException();
		Object result = elements[--size];
		elements[size] = null;
		return result;
	}

	/**
	 * 원소를 위한 공간을 적어도 하나 이상 확보한다.
	 * 배열 크기를 늘려야 할 때마다 대략 두 배씩 늘린다.
	 */
	private void ensureCapacity() {
		if (elements.length == size)
			elements = Arrays.copyOf(elements, 2 * size + 1);
	}
}