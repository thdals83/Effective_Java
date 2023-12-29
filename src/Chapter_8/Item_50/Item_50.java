package Chapter_8.Item_50;

/* 적시에 방어적 복사본을 만들라.

자바가 안전하긴 해도, 다른 클래스로부터의 침범을 아무런 노력 없이 다 막을 수 있는 것은 아니다.
- 클라이언트가 여러분의 불변식을 깨뜨리려고 혈안이 나있다고 생각하고 방어적으로 프로그래밍해야 한다.
* */

import java.util.Date;

/*
객체는 그 객체의 허락 없이는 외부에서 내부를 수정하는 일은 불가능하다.
하지만 자기도 모르게 내부를 수정하도록 허락하는 경우가 생긴다.

EX) 기간을 표현하는 다음 클래스는 한번 값이 정해지면 변하지 않도록 할 생각으로 작성되었다.
* */
final class WorstPeriod {
	private final Date start;
	private final Date end;

	/*
	 * @param start 시작 시간
	 * @param end 종료 시각; 시작 시간보다 뒤여아 한다.
	 * @throws IllegalArgumentException 시작 시간이 종료 시각보다 늦을 때 발생한다.
	 * @throws NullPointerException start나 end가 null이면 발생한다.
	 * */
	WorstPeriod(Date start, Date end) {
		if (start.compareTo(end) > 0)
			throw new IllegalArgumentException(start + "가 " + end + "보다 늦다.");
		this.start = start;
		this.end = end;
	}

	public Date start() {
		return start;
	}

	public Date end() {
		return end;
	}

	public static void main(String[] args) {
		/*
		언뜻 보면 위는 불변처럼 보이지만 Date가 가변이기 때문에, 어렵지 않게 불변식을 깨뜨릴 수 있다.
		아래 예시
		
		그래서 요즘은 LocalDateTime 이나, ZonedDateTime을 사용한다.
		* */
		Date start = new Date();
		Date end = new Date();
		WorstPeriod p = new WorstPeriod(start, end);
		end.setYear(78);  // p의 내부를 변경했다!
		System.out.println(p);
	}
}

/* 
그래서 이와 같은 해결 방법으로 생성자에서 받은 가변 매개변수를 각각을 방어적으로 복사해야 한다.

유효성을 검사하기 전에 방어적 복사본을 만들고, 이 복사본으로 유효성을 검사한 점에는 이유가 있다.
- 멀티스레딩 환경이라면 원본 객체의 유효성을 검사한 후 복사본을 만드는 그 찰나의 순간에 다른 스레드가 원본 객체를 수정할 위험이 있기 때문

매개변수가 제 3자에 의해 확장 될 수 있는 타입이라면 방어적 복사본을 만들 때 clone을 사용해서는 안된다.
-> 상속이 되는 클래스에 에서는 clone()의 반환 값이 악의를 가진 하위 클래스의 인스턴스가 될 수도 있기 때문
* */

final class Period {
	private final Date start;
	private final Date end;

	Period(Date start, Date end) {
		this.start = new Date(start.getTime());
		this.end = new Date(end.getTime());
		
		if (this.start.compareTo(this.end) > 0) {
			if (this.start.compareTo(this.end) > 0)
				throw new IllegalArgumentException(this.start + "가 " + this.end + "보다 늦다.");
		}
	}
	
	//수정 전
//	public Date end() {
//		return this.end;
//	}

	/*
	하지만 위에 처럼 방어적 복사를 하더라도 결국에 접근자를 이용해서 바꾸면 바뀌어 버린다.
	그래서 접근자를 수정하여 방어적 복사본을 반환하게 한다.
	* */
	public static void main(String[] args) {
		Date start = new Date();
		Date end = new Date();
		Period p = new Period(start, end);
		p.end().setYear(78);  // p의 내부를 변경했다!
		System.out.println(p);
	}

	public Date start() {
		return new Date(start.getTime());
	}

	public Date end() {
		return new Date(end.getTime());
	}
}

/* 방어적 복사 주의사항
1. 불변 객체를 조합해 객체를 구성 한다면 방어적 복사를 할 일이 줄어든다. Date -> LocalDateTime을 쓴 것 처럼
2. 호출자가 컴포넌트 내부를 수정하지 않는다고 확신하거나 객체의 통제권을 명백히 이전하는 경우, 방어적 복사를 하지 않고 수정하지 말아야 함을 명확히 문서화 해야 한다.
	-> 방어적 복사에는 성능 저하가 따르고, 항상 쓸 수 있는 것도 아니기 때문이다.
3. 클래스와 그 클라이언트가 상호 신뢰할 수 있을때 혹은 불변식이 깨지더라도, 영향이 오직 호출한 클라이언트로 국한될 때만 방어적 복사를 생략해도 된다.
* */


public class Item_50 {
}
