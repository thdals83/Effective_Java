package Chpater_4.Item_19;

/* 상속을 고려해 설계하고 문서화 하라. 그러지 않았다면 상속을 금지해라
- 상속용 클래스는 재정의 할 수 있는 메서드들을 내부적으로 어떻게 이용하는지 문서로 남겨야 한다.

1. protected메서드를 제공한다.
	- 상속용으로 설계한 클래스는 배포 전에 반드시 하위 클래스를 만들어 검증해야 한다.
* */

import java.time.Instant;

class Super {
	//2. 상속용 클래스의 생성자는 직접적이든 간접적으로든 재정의 가능 메서드를 호출해서는 안된다.
	//쓰고 싶다면 override가 안되게 private, final, staic으로 메서드를 정의해야 한다.ㅌ
	public Super() {
		overrideMe();
	}
	public void overrideMe() {}
}
final class Sub extends Super {
	private final Instant instant;
	Sub() { instant = Instant.now();}
	
	@Override
	public void overrideMe() {
		System.out.println(instant);
	}

	public static void main(String[] args) {
		Sub sub = new Sub();
		sub.overrideMe();
	}
}

/*
cloneable, Serialiable을 구현한 클래스를 상속 받게 설계하는 거은 좋지 않다.

상속을 금지하게 하는 방법	
1. 클래스 final로 선언
2. 모든 생성자를 private or package-private로 선언하고 public 정적 팩터리로 만드는 것
* */
public class EX_1 {
}
