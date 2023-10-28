package Chapter_3.Item_13;

import java.util.Arrays;
import java.util.EmptyStackException;
import java.util.Map;
import static java.util.Map.Entry;

/*
clone 재정의는 주의해서 진행하라
clone() -> 객체의 모든 필드를 복사하여 새로운 객체에 넣어 반환하는 동작 = 필드의 값이 같은 객체를 새로 만드는 것

얕은복사: 인스턴스를 그대로 복사한다. 하지만 복제된 인스턴스가 메모리에 새로 생성되지 않는다
=> 인스턴스의 객체들이 값 자체를 복사하는 것이 아닌, 주소값만 복사하여 같은 메모리를 가리키고 있음

Cloneable interface의 역할
- 인터페이스를 구현한다는 것은 일반적으로 해당 클래스가 그 인터페이스의 정의한 기능을 제공한다는 행위이지만,
  Cloneable의 경우 Object의 protected clone()을 어떤 식으로 사용할 것인지 변경한것

- Cloneable를 구현한 클래스의 인스턴스에서 clone을 호출하면, 그 객체의 필드들을 하나하나 복사한 객체를 반환하며,
  그렇지 않은 클래스의 인스턴스에서 호출하면 CloneNotSupportException을 던진다.
  
  이는 인터페이스를 상당히 이례적으로 사용한 예시니 따라하지 말것
* */
public class EX_1 {
}

//
class Person implements Cloneable {
	private String name;
	private int age;
	
	public Person(final String name, final int age) {
		this.name = name;
		this.age = age;
	}
	public String displayInformation() {
		return "이름:" + name + "/나이:" + age;
	}
	
	/*
	만약에 여기서 name과 age의 값들이 불변에서 변할 일이 없는 불변 객체라면 아래처럼 구현하면 된다.
	* */
	@Override
	protected Person clone() throws CloneNotSupportedException {
		return (Person) super.clone();
	}

	public static void main(String[] args) {
		/*
		이경우 얕은 복사가 일어남
		* */
		Person person = new Person("사람1", 29);

		try {
			Person person2 = person.clone();
			System.out.println(person.displayInformation());
			System.out.println(person2.displayInformation());
			System.out.println(person.name.hashCode());
			System.out.println(person2.name.hashCode());
		} catch (CloneNotSupportedException cloneNotSupportedException) {
			cloneNotSupportedException.printStackTrace();
		}
	}
	
}

/*
스택처럼 가변 객체 일 때의 경우
* */
class Stack implements Cloneable {
	private Object[] elements;
	private int size = 0;
	private static final int DEFAULT_INITIAL_CAPACITY = 16;

	public Stack(final Object[] elements) {
		this.elements = new Object[DEFAULT_INITIAL_CAPACITY];
	}

	public void push(Object obj) {
		ensureCapacity();
		elements[size++] = obj;
	}

	public Object pop() {
		if (size == 0) {
			throw new EmptyStackException();
		}

		Object result = elements[--size];
		elements[size] = null;
		return result;
	}

	private void ensureCapacity() {
		if (elements.length == size) {
			elements = Arrays.copyOf(elements, 2 * size + 1);
		}
	}

	//elements 필드는 원본 Stack 인스턴스와 똑같은 배열을 참조한다. (하나만 수정되도 다른 인스턴스도 변경됨)
	//그래서 clone는 원본 객체에 아무런 해를 끼치지 않는 동시에, 복제된 객체에 불변식을 보장해야 한다.
	@Override
	protected Stack clone() {
		try {
			return (Stack) super.clone();
		} catch (CloneNotSupportedException cloneNotSupportedException) {
			throw new AssertionError();
		}
	}
	
	//가장 쉬운 방법인 elements 배열의 clone을 재귀적으로 호출하는 것
	public Stack clone2() {
		try {
			Stack result = (Stack) super.clone();
			result.elements = elements.clone();
			return result;
		} catch (CloneNotSupportedException e) {
			throw new AssertionError();
		}
	}
}

class HashTable implements Cloneable {
	private Entry[] buckets = new Entry[50];
	private int size = 0;

	public void put(Entry entry){
		buckets[size++] = entry;
	}

	public void printAll(){
		for (int i=0;i<size;i++){
			System.out.println(buckets[i].toString());
		}
	}

	static class Entry{
		final Object key;
		Object value;
		Entry next;

		public Entry(final Object key, final Object value, final Entry next) {
			this.key = key;
			this.value = value;
			this.next = next;
		}

		Entry deepCopy(){
			return new Entry(key,value, next==null ? null : next.deepCopy());
		}
	}

	/*
	복제본은 자신만의 버킷 배열을 갖지만, 배열은 원본과 같은 연결 리스트를 참조해 원본과 복제본 모두 영향을 감
	그래서 각 버킷을 구성하는 연결 리스트를 복사해야 한다.
	* */
	@Override
	public HashTable clone() {
		try {
			HashTable result = (HashTable) super.clone();
			result.buckets = new Entry[buckets.length];
			return result;
		}catch (CloneNotSupportedException cloneNotSupportedException){
			throw new AssertionError();
		}
	}
	
	public HashTable resolveClone() throws CloneNotSupportedException {
		HashTable result = (HashTable) super.clone();
		result.buckets = new Entry[buckets.length];
		for (int i = 0; i < buckets.length; i++) {
			if (buckets[i] != null)
				result.buckets[i] = buckets[i].deepCopy();
		}
		return result;
	}
}
/*
정리
- 인터페이스를 만들때 절대 Cloneable을 확장해서는 안된다. Cloneable은 믹스인 용도로 만들어진 것이다.
-final 클래스라면 성능 최적화 관점에서 검토후 문제가 없을때만 Cloneable을 구현하자.
- 객체의 복제 기능은 Cloneable/clone 방식보다 복사 팩터리와 복사 생성자를 이용하는것이 가장 좋다. 단, 배열같은 경우는 clone방식을 가장 적합하므로 예외로 친다.
* */