package Chapter_4.Item_22;

/* 인터페이스는 타입을 정의하는 용도로만 사용하라
- 클래스가 어떤 인터페이스를 구현한다는 것은, 자신의 인스턴스로 어떤걸 할 수 있는지 클라이언트에게 얘기해주는 것
* */

//인터페이스를 잘 못 쓴 예 - 상수 인터페이스
interface PhysicalConstnats {
	//메서드 없이 상수(static final)필드로만 가득 찬 인터페이스
	//java.io.ObjectStreamConstants 같은 것도 잘못 쓴거
	static final double EX_1 = 1.213123e3;
	static final double EX_2 = 2.213123e3;
	static final double EX_3 = 3.213123e3;
}

class PhysicalConstants {
	private PhysicalConstants() {}

	public static final double EX_1 = 1.213123e3;
	public static final double EX_2 = 2.213123e3;
	public static final double EX_3 = 3.213123e3;
}

public class Item_22 {
}
