package Chapter_3;

import org.junit.Test;

import java.util.Collections;
import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;

/*
Comparable을 구현할지 고려하라
- Comparable을 구현했다는 것은 그 클래스의 인스턴스들에는 자연적인 순서가 있음을 뜻함
- 알파벳, 숫자, 연대 같이 순서가 명확한 값은 Comparable 인터페이스를 구현하자

compareTo를 사용하기 위한 몇가지 규약
1. 두 객체 참조의 순서를 바꿔 비교해도 예상한 결과가 나와야 한다.

핵심
- 가장 핵심적인 필드부터 비교하자
* */
public class Item_14 {
	public static void main(String[] args) {
		//String이 Compareable 예시 알파벳 순으로 출력한다.
		Set<String> s = new TreeSet<>();
		Collections.addAll(s, args);
		System.out.println(s);
	}
	
	@Test
	public void test() {
		PhoneNumber phoneNumber = new PhoneNumber(1, 2, 3);
		PhoneNumber pn = new PhoneNumber(3,4,5);
		phoneNumber.compareTo2(pn);
	}
}

class PhoneNumber implements Comparable<PhoneNumber> {
	private int areaCode;
	private int prefix;
	private int lineNum;

	public PhoneNumber(int areaCode, int prefix, int lineNum) {
		this.areaCode = areaCode;
		this.prefix = prefix;
		this.lineNum = lineNum;
	}

	@Override
	public int compareTo(PhoneNumber o) {
		int result = Integer.compare(areaCode, o.areaCode);
		if (result == 0) {
			result = Integer.compare(prefix, o.prefix);
			if (result == 0) {
				result = Integer.compare(lineNum, o.lineNum);
			}
		}
		return result;
	}
	
	//자바 8에 도입된 Comparator 인터페이스를 이용하는 방법
	private static final Comparator<PhoneNumber> COMPARATOR = 
			Comparator.comparingInt((PhoneNumber pn) -> pn.areaCode)
					.thenComparingInt(pn -> pn.prefix)
					.thenComparingInt(pn -> pn.lineNum);
	
	public int compareTo2(PhoneNumber pn) {
		return COMPARATOR.compare(this, pn);
	}
}
/*
정리

* */