package Chpater_9.Item_62;



/* 다른 타입이 적절하다면 문자열 사용을 피하라

문자열을 쓰지 않아야 할 상황들
- 문자열은 다른 값 타입을 대신하기에 적합하지 않다.
EX) 키보드 입력으로부터 데이터를 받을때 실제 데이터가 수치형임에도 불구하고, 문자열로 받는 경우가 흔하다.
받는 데이터가 수치형이라면 int, float, BigInteger 등 적당한 수치 타입으로 변환해야 한다.

- 문자열은 열거 타입을 대신하기에 적합하지 않다.
EX) 예/아니오 질문의 답이라면 적절한 열거 타입이나 boolean으로 변환해야 한다.

- 문자열은 혼합 타입을 대신하기에 적합하지 않다. -> 여러 요소가 혼합된 데이터를 하나의 문자열로 표현하는 것은 대체로 좋지 않다.
EX) String compoundKey = className + "#" + i.next();
문자열로 처리하면, 각 요소를 개별로 접근하려 할때 문자열을 파싱해야 해서 느리고 오류 가능성도 커진다.(적절한 equals(), toString(), compareTo()도 못씀)
해결 방법은 전용 클래스를 private 정적 멤버 클래스에 새로 만든다.
public class A {
	private class B {
    ...
    }
}
* */

/*
- 문자열은 권한을 표현하기에 적합하지 않다.
EX) 각 스레드가 자신만의 변수를 갖게 해주는 스레드 지역변수 기능을 설계한다고 했을 때, 흔한 방법은 클라가 제공한 문자열 키로 스레드별 지역변수를 식별하는 것
* */
class EX_1 {
	/* 잘못된 예 - 문자열을 사용해 권한을 구분
	스레드 구분용 문자열 키가 전역 이름공간에서도 공유됨, 클라가 실수로 같은 키를 써버리면 의도치 않게 변수를 공유하게 된다
	* */
	class ThreadLocal {
		private ThreadLocal() { } //객체 생성 불가

		//현 스레드의 값을 키로 구분해 저장한다.
		public static void set(String key, Object value) {
			// ...
		}
		//현 스레드의 값을 반환한다.
		public static Object get(String key) {
			// ...
			return null;
		}
	}
}

class EX_2 {
	/* 개선방법 - Key 클래스로 권한 부여
	하지만 여기서도 set, get은 정적 메서드일 이유가 없다. -> 인스턴스 메서드로 변경한다.
	이렇게 되면 더이상 key는 지역변수를 구분하기 위함이 아닌, 이 자체가 스레드 지역변수가 된다.
	따라서 할 일이 없어진 ThreadLocal을 없애고, 중첩 클래스 Key의 이름을 ThreadLocal로 바꾼다.
	* */
	class ThreadLocal2 {
		private ThreadLocal2() {}
		public static class Key {
			Key() {}
		}
		// 위조 불가능한 고유 키를 생성한다.
		public static Key getKey() {
			return new Key();
		}
		public static void set(Key key, Object value) {}
		public static Object get(Key key) {return null;}
	}
}

class EX_3 {
	final class ThreadLocal3<T> {
		public ThreadLocal3() {}
		public void set(T value) {}
		public T get() {return null;}


	}
}







public class Item_62 {
}
