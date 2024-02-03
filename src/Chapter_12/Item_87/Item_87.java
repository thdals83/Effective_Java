package Chapter_12.Item_87;


import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/* custom 작렬화 형태를 고려해보라
- 클래스가 Serializable을 구현하고 기본 직렬화 형태를 사용한다면, 다음 릴리즈 때 버리려 한 현재의 구현이 발이 묶임
- 먼저 고민해보고 괜찬다고 판단할 때만 기본 직렬화 형태를 사용하라
- 객체의 물리적 표현과 논리적 내용이 같다면 기본 직렬화 형태라도 무방하다.
	- EX) 성명을 표현하는 클래스 (이름, 성) 2개의 멤버로 구성된 클래스는 지원해도 무방
- 기본 직렬화 사용해도, 불변식 보장과 보안을 위해 readObject 메서드를 제공해야 할 때가 많다.
* */

/* 기본 직렬화 형태에 적합하지 않은 예
- 이 클래스는 논리적으론 일련의 문자열 표현, 물리적으로는 문자열들을 이중 연결 리스트로 ㅇ녀결함
- 이 클래스에 기본 직렬화를 사용하면, 각 노드의 양방향 연결 정보를 포함한 모든 엔트리를 기록한다.
- 객체의 물리적 표현과, 논리적 표현의 차이가 클때 기본 직렬화를 사용하면 4가지 문제가 생긴다.
	- 공개 API가 현재의 내부 표현 방식에 영구히 묶인다.
	- 많은 공간을 차지 할 수 있다.
	- 시간이 많이 소요될 수 있다.
	- 스택 오버플로우가 발생할 수 있다.
* */
final class StringList2 implements Serializable {
	private int size = 0;
	private Entry head = null;

	private static class Entry implements Serializable {
		String data;
		Entry next;
		Entry previous;
	}
	// ... 생략
}

/* ex_1을 올바르게 직렬화한 예
transient
- 기본 직렬화를 수용하던 하지 않던 default WriteObject 메서드를 호출하면 transient로 선언하지 않은 모든 인스턴스 필드가 직렬화 됨
- 따라서 transient로 선언해도 되는 인스턴스 필드에는 모두 transient 한정자를 붙여야 한다.
- 캐시된 해시 값처럼 다른 필드에서 유도되는 필드도 여기에 해당한다.
- 해당 객체의 논리적 상태와 무관한 필드라고 확실할 때만 transient 한정자를 생략해야 한다.
- 커스텀 직렬화 형태를 사용한다면, 대부분의 인스턴스 필드를 transient로 선언해야 한다.
- 기본 직렬화를 사용한다면 transient필드들은 역직렬화 될 때 기본값으로 초기화 된다.

- StringList
* */
final class StringList implements Serializable {
	private transient int size = 0;
	private transient Entry head = null;

	// 이제는 직렬화 하지 않는다.
	private static class Entry {
		String data;
		Entry next;
		Entry previous;
	}

	// 지정한 문자열을 이 리스트에 추가한다.
	public final void add(String s) { }

	// StringList 인스턴스를 직렬화한다.
	private void writeObject(ObjectOutputStream s)
			throws IOException {
		s.defaultWriteObject();
		s.writeInt(size);

		// 모든 원소를 올바른 순서대로 기록한다.
		for (Entry e = head; e != null; e = e.next) {
			s.writeObject(e.data);
		}
	}

	private void readObject(ObjectInputStream s)
			throws IOException, ClassNotFoundException {
		s.defaultReadObject();
		int numElements = s.readInt();

		for (int i = 0; i < numElements; i++) {
			add((String) s.readObject());
		}
	}
	// ... 생략
}


/* 동기화 메커니즘 직렬화
- 기본 직렬화 사용 여부와 상관없이 객체의 전체 상태를 읽는 메서드에 적용해야 하는 동기화 메커니즘을 직렬화에도 적용해야 한다.
- 따라서 에컨데 모든 메서드를 synchronized로 선언하여, 스레드 안전하게 만든 객체에서 기본 직렬화를 사용하려면 writeObject도
  다음 코드처럼 선언해야 한다.
private synchronized void writeObject(ObjectOutputStream stream)
        throws IOException {
    stream.defaultWriteObject();
}
* */


/* 직렬화와 UID
- 어떤 직렬화 형태를 택하든 직렬화 가능 클래스 모두에 직렬 버전 UID를 명시적으로 부여하자.
- 이렇게 하면 직렬 버전 UID가 일으키는 잠재적인 호환성 문제가 사라진다.
- 직렬 버전 UID를 명시하지 않으면, 이 값을 생성하기 때문에, 직렬 버전 UID를 명시하면 성능이 조금이랃 빨라진다.
- 직렬 버전 UID 선언은 각 클래스에 아래 같은 한 줄만 추가해주면 된다.

private static final long serialVersionUID = <무작위로 고른 long 값>;
- 직렬 버전 UID가 없는 기존 클래스를 구버전으로 직렬화된 인스턴스와 호환성을 유지한 채 수정하고 싶다면, 구 버전에 서 사용한
  자동 생성된 값을 그대로 사용해야 한다.
  (이 값은 직렬화된 구버전 클래스의 인스턴스를 serialver 유틸리티에 입력하면 얻을 숭 ㅣㅆ음
  

* */



public class Item_87 {
}
