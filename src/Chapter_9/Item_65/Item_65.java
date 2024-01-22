package Chapter_9.Item_65;


import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.Set;

/* 리플렉션 보다는 인터페이스를 사용하라
리플렉션: 컴파일이 아닌 런타임 도중에 임의의 클래스에 접근해 정보를 가져오는 것
1. 클래스의 정보를 가져올 수 있다. EX) 생성자, 메서드, 필드, 인스턴스의 멤버 이름, 필드 타입, 메서드
2. 각각에 연결된 실제 생성자, 필드, 메서드에 접근해 조작할 수 있다.
	- Method.invoke: 어떤 클래스의 어떤 객체가 가진 메서드라도 호출할 수 있게 해주는 메서드

장점: 컴파일 당시 존재하지 않는 인스턴스도 사용할 수 있는 장점, 하지만 많은 단점이 따른다.
단점
- 리플렉션은 아주 제한된 형태로만 사용해야, 그 단점을 피하고 이점만 취할 수 있다.
1. 컴파일타임 타입 검사가 주는 이점을 누릴 수 없다.
	- 리플렉션 기능을 써서 존재하지 않는 혹은 접근할 수 없는 메서드를 호출하려 하면 런타임 오류가 발생한다.
2. 리플렉션을 이용하면 코드가 지저분하고 장황해진다.
3. 성능이 떨어진다. 리플렉션을 통한 메서드 호출은 일반 메서드 호출보다 훨씬 느리다.

따라서 컴파일타임에 이용할 수 없는 클래스를 사용해야만 하는 상황이라면,
리플렉션은 인스턴스 생성에만 쓰고 인스턴스 참조는 적절한 인터페이스나 상위 클래스를 이용하도록 하자.
* */
class EX_1 {
	/* 아래 코드는 Set<String> 인터페이스의 인스턴스를 생성하고, 인수를 확정
	여기서 리플렉션의 단점 두 가지 드러난다.
	1. 런타임에 여섯 가지나 되는 예외를 던질 수 있다.
	2. 클래스 이름만으로 인스턴스를 생성하기 위해 무수히 긴 코드를 작성해야 한다.
	* */
	public static void main(String[] args) {
		//클래스 이름을 Class 객체로 변환
		Class<? extends Set<String>> cl = null;
		try {
			cl = (Class<? extends Set<String>>) Class.forName(args[0]);
		} catch (ClassNotFoundException e) {
			throw new RuntimeException(e);
		}
		
		//생성자를 얻는다.
		Constructor<? extends Set<String>> cons = null;
		try {
			cons = cl.getDeclaredConstructor();
		} catch (NoSuchMethodException e) {
			fatalError("매개변수 없는 생성자를 찾을 수 없습니다.");
		}

		// 집합의 인스턴스를 만든다.
		Set<String> s = null;
		try {
			s = cons.newInstance();
		} catch (IllegalAccessException e) {
			fatalError("생성자에 접근할 수 없습니다.");
		} catch (InstantiationException e) {
			fatalError("클래스를 인스턴스화할 수 없습니다.");
		} catch (InvocationTargetException e) {
			fatalError("생성자가 예외를 던졌습니다: " + e.getCause());
		} catch (ClassCastException e) {
			fatalError("Set을 구현하지 않은 클래스입니다.");
		}

		// 생성한 집합을 사용한다.
		s.addAll(Arrays.asList(args).subList(1, args.length));
		System.out.println(s);
	}

	private static void fatalError(String s) {
	}
}
















public class Item_65 {
}
