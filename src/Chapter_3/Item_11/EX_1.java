package Chapter_3.Item_11;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

/*
equals 재정의시 hashCode도 재정의 해라
- 그렇지 않으면 HashSet과 같은 컬렉션의 원소를 사용할 때 문제를 일으킬 것임

Object 명세
- equals 비교에 사요되는 정보가 변경되지 않았다면, 객체의 hashcode 메서드는 몇 번 호출되도 같은 값을 반환
- equals가 두 객체가 같다고 판단했으면, hashCode도 똑같은 값을 반환한다.
- equals가 두 객체가 다르다고 판단했더라도, 두 객체의 hasCode가 서로 다른 값을 반환할 필요는 없다. (두 객체가 달라도 hashCode는 같아도 됨) (하지만 다른 값을 반환해야 해시테이블 성능이 좋다)

좋은 hashCode를 만드는 요령

* */
public class EX_1 {
	@Test
	public void hascode를_바꿔야_하는이유() {
		Map<PhoneNumber1, String> m = new HashMap<>();
		m.put(new PhoneNumber1(1,2,3), "제니");
		//
		assertTrue(null == m.get(new PhoneNumber1(1,2,3)));
		
	}
	
	public static void main(String[] args) {
		
	}
}

class PhoneNumber1 {
	Integer first;
	Integer second;
	Integer third;
	private int hashCode;

	public PhoneNumber1(int first, int second, int third) {
		this.first = first;
		this.second = second;
		this.third = third;
	}
	
//	@Override
//	public int hashCode() {
//		int result = first.hashCode();
//		result = 31 * result + second.hashCode();
//		result = 31 * result + third.hashCode();
//		return result;
//	}
	
	/*
	클래스가 불변이고 해시코드 계산 비용이 큰 경우
	매번 새로 계산하기 보단 캐싱 방식을 고려
	-> 인스턴스가 만들어 질 때 해시코드를 계산한다. (lazy)
	* */
	public int lazyHashCode() {
		int result = hashCode;
		if (result == 0) {
			result = first.hashCode();
			result = 31 * result + second.hashCode();
			result = 31 * result + third.hashCode();
		}
		return result;
	}
}

