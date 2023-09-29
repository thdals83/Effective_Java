package Chapter_2.Item_3;

import org.junit.Test;

import java.lang.reflect.Constructor;

import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertSame;

public class EX_1 {
	@Test
	public void ElvisTest() throws Exception {
		Elvis elvis1 = Elvis.INSTANCE;
		Elvis elvis2 = Elvis.INSTANCE;

		assertSame(elvis1, elvis2); // SUCCESS 
		
		// 리플랙션 API인 AccessibleObject.setAccessible을 이용해서 private
		// 생성자를 호출 할 수 있음
		Constructor<Elvis> constructor = (Constructor<Elvis>) elvis2.getClass().getDeclaredConstructor();
		constructor.setAccessible(true);
		
		Elvis elvis3 = constructor.newInstance();
		assertNotSame(elvis2, elvis3);
	}
	//--------------------------------------------------------------
}

//public static final 필드 방식의 싱글턴
class Elvis {
	public static final Elvis INSTANCE = new Elvis();

	private Elvis() {
		//리플랙션 API에 대한 방어 로직 코드
//		if (INSTANCE != null) {
//			throw new RuntimeException("추가 생성자 안됨");
//		}
	}
}

class Elvis2 {
	private static final Elvis2 INSTANCE = new Elvis2();
	
	private Elvis2() {}
	
	public static Elvis2 getInstance() {return INSTANCE;}
	
	/*
	클래스의 역직렬화가 일어날 때, 새로운 인스턴스를 만들어서 반환한다.
	(역직렬화는 기본적으 기본 생성자를 호출하지 않고, 값을 복사하여 새로운 인스턴스를 반환한다.)
	그때 사용하는 것이, readResolve()
	* */
	private Object readResolve() {
		return INSTANCE;
	}
}

enum Elvis3 {
	INSTANCE;

	public String getName() {
		return "Elvis";
	}

	public void leaveTheBuilding() {
		// 실제 구현은 여기에 작성
	}
}