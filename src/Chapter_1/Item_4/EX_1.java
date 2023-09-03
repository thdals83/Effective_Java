package Chapter_1.Item_4;

public class EX_1 {
}

//정적 메서드, 정적 필드만을 담은 클래스를 만들고 싶을 때
//이 경우, 인스턴스를 막는게 좋기 때문에, 생성자를 private 하여서 사용한다.	
final class UtilityClass {
	// 정적 필드
	public static final double PI = 3.141592653589793;

	// private 생성자
	private UtilityClass() {
		throw new AssertionError("UtilityClass 인스턴스를 생성할 수 없습니다.");
	}

	// 정적 메서드
	public static double square(double num) {
		return num * num;
	}

	public static double cube(double num) {
		return num * num * num;
	}
}
