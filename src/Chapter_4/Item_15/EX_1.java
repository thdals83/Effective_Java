package Chapter_4.Item_15;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/*
클래스와 멤버의 접근 권한을 최소화 하라 
- 클래스 내부 데이터와 내부 구현 정보를 외부 컴포넌트로 부터 얼마나 잘 숨겼느냐 (정보 은닉, 캡슐화와 같은)

정보 은닉의 장점
- 시스템 개발 속도를 높인다. 여러 컴포넌트를 병렬로 개발할 수 있어서 
- 시스템 관리 비용을 낮춘다. 각 컴포넌트를 더 빨리 파악하여 디버깅 할 수 있고, 다른 컴포넌트로 교체하는 부담도 적기 때문
- 성능 최적화에 도움을 준다. 다른 컴포넌트에 영향을 주지 않고 해당 컴포넌트만 최적화 할 수 있기 때문
- 소프트웨어 재사용성을 높인다. 독자적으로 동작할 수 있는 컴포넌트면 다른 환경에서 쓸 수 있기 때문
- 큰 시스템을 제작하는 난이도를 낮춘다.

=> 자바에서는 접근 제한자를 제대로 활용하는 것이 정보 은닉의 핵심
- 그럴려면 모든 클래스와 멤버의 접근성을 가능한 한 좁혀야 한다. (가장 낮은 수준으로 부여해야 한다.)
- 톱레벨 클래스와 인터페이스에 부여할 수 있는건 package-private or public인데 public이면 공개 API 상태가 되니 이를 생각해야 한다. (API와의 하위호환을 관리 해야 한다)	

public 클래스의 인스턴스 필드는 되도록 public이 아니여야 한다.
- 그 필드에 담을 수 있는 값을 제한할 힘을 읽게 되는 것
- 스레드가 안전하지 않다.
* */
//public static final 배열 필드를 두거나, 이 필드를 반환하는 접근자 메서드를 제공하면 안된다. (배열 값이 참조되어 변경 될 수 있음)
class Public_static_final의_해결책1 {
	//public 불변 리스트를 추가하는 것
	private static final Integer[] PRIVATE_VALUES = {1, 2, 3};
	public static final List<Integer> VALUES = Collections.unmodifiableList(Arrays.asList(PRIVATE_VALUES));
}
class Public_static_final의_해결책2 {
	//private 배열과 복사본을 반환하는 public 메서드 추가
	private static final Integer[] VALUES = {5, 3, 2};
	public static final Integer[] values(){
		return VALUES.clone();
	}
}
public class EX_1 {

}
