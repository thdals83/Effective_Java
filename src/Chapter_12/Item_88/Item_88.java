package Chapter_12.Item_88;





import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Date;

/* readObejct 메서드는 방어적으로 작성하라

방어적 복사를 사용하는 불변 클래스 예시
- 불변식을 지키고 불변을 유지하기 위해 생성자와 접근자에서 Date 객체를 방어적으로 복사했다.
- 이클래스를 직렬화 하기로 결정했다고 가정하면, Period 객체의 물리적 표현과 논리적 표현이 부합해 기본 직렬화 사용해도 낫뱃
- 하지만 여기서 implements Serializable 만 추가하면 불변식을 보장할 수 없다.
	- readObject 메서드가 실질적으로 또 다른 public 생성자이기 때문이다.
	- 보통의 생성자처럼 readObject 메서드에서도 인수가 유효한지 검사해야 하고, 매개변수를 방어적으로 복사해야 한다. (필요하다면)
	- 이 작업을 제대로 수행 못하면, 클래스 불변이 깨진다.
* */
final class Period {
	private final Date start;
	private final Date end;

	/**
	 * @param  start 시작 시각
	 * @param  end 종료 시각; 시작 시각보다 뒤여야 한다.
	 * @throws IllegalArgumentException 시작 시각이 종료 시각보다 늦을 때 발생한다.
	 * @throws NullPointerException start나 end가 null이면 발생한다.
	 */
	public Period(Date start, Date end) {
		// 가변인 Date 클래스의 위험을 막기 위해 방어적 복사 진행
		this.start = new Date(start.getTime());
		this.end = new Date(end.getTime());

		if (this.start.compareTo(this.end) > 0) {
			throw new IllegalArgumentException(start + " after " + end);
		}
	}

	public Date start() { return new Date(start.getTime()); }
	public Date end() { return new Date(end.getTime()); }
	public String toString() { return start + " - " + end; }

	// ... 나머지 코드는 생략
}

/*
- 유효성 검사를 수행한 readObject 메서드 (보완 필요)
- 유효성 검사로 허용되지 않은 Period 인스턴스가 생성 되는건 막을 수 있다. 하지만 문제가 하나 더 있음
- 정상 Period 인스턴스에서 시작된 바이트 스트림 끝에 
  private Date 필드로의 참조를 추가하면 가변 Period 인스턴스를 만들어 낼 수 있다.
  
private void readObject(ObjectInputStream s)
        throws IOException, ClassNotFoundException {
    // 불변식을 만족하는지 검사한다.
    if (start.compareTo(end) > 0) {
        throw new InvalidObjectException(start + " after " + end);
    }
}
* */
//가변 공격 예시
class MutablePeriod {
	// Period 인스턴스
	public final Period period;
	// 시작 시각 필드 - 외부에서 접근할 수 없어야 한다.
	public final Date start;
	// 종료 시각 필드 - 외부에서 접근할 수 없어야 한다.
	public final Date end;

	public MutablePeriod() {
		try {
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			ObjectOutputStream out = new ObjectOutputStream(bos);

			// 유효한 Period 인스턴스를 직렬화한다.
			out.writeObject(new Period(new Date(), new Date()));

			/*
			 * 악의적인 '이전 객체 참조', 즉 내부 Date 필드로의 참조를 추가한다.
			 */
			byte[] ref = { 0x71, 0, 0x7e, 0, 5 }; // 참조 #5
			bos.write(ref); // 시작(start) 필드
			ref[4] = 4; // 참조 # 4
			bos.write(ref); // 종료(end) 필드

			// Period 역직렬화 후 Date 참조를 '훔친다.'
			ObjectInputStream in = new ObjectInputStream(new ByteArrayInputStream(bos.toByteArray()));
			period = (Period) in.readObject();
			start = (Date) in.readObject();
			end = (Date) in.readObject();
		} catch (IOException | ClassNotFoundException e) {
			throw new AssertionError(e);
		}
	}

	/*
	- 이처럼 불변식을 유지한채 생성되었지만, 의도적으로 내부의 값을 수정할 수 있다.
	- 겍체를 역직렬화 할 때는
	* */
	public static void main(String[] args) {
		MutablePeriod mp = new MutablePeriod();
		Period p = mp.period;
		Date pEnd = mp.end;

		// 시간을 되돌리자!
		pEnd.setYear(78);
		System.out.println(p);

		// 60년대로 회귀!
		pEnd.setYear(69);
		System.out.println(p);
	}
}
public class Item_88 {
}
