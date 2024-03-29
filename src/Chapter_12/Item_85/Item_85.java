package Chapter_12.Item_85;



/* 자바 직렬화의 대안을 찾으라
직렬화
직렬화 = 자바가 객체를 바이트 스트림으로 인코딩하고
역 직렬화 = 바이스 스트림을 다시 객체로 구성

자바의 직렬화
- 자바의 직렬화는 위험하다. (보이지 않는 생성자, API와 구현 사이의 모호해진 경계, 잠재적인 정확성 문제, 성능, 보안, 유지보수)
- 직렬화의 근본적인 문제는 공격 범위가 너무 넓고 지속적으로 더욱 넓어져 방어하기 어렵다는 것
- ObjectInputStream의 readObject 메서드를 호출하면서 객체 그래프가 역직렬화 되기 때문
- readObject 메서드 = Serializable 인터페이스를 구현했다면, class path안의 거의 모든 타입의 객체를 만들어 낼 수 있는 생성자
- 바이트 스트림을 역직렬화 하는 과정에서, 이 메서드는 그 타입들 안의 모든 코드를 수행할 수 있다. 즉 그 타입들의 코드 전체가 공격 범위에 들어간다.


가젯
- 가젯 = 자바 라이브러리와 널리 쓰이는 서드파트 라이브러리에서 역직렬화 과정에서 호출되어 잠재적으로 위험한 동작을 수행하는 메서드
- 여러 가젯을 함께 사용하여 가젯 체인을 구성할 수도 있는데, 공격자가 하드웨어의 네이티브 코드를 마음대로 실행 할 수 있는 강력한 가젯 체인도 존재한다.
- 이러한 이유로 아주 신중하게 제작된 바이트 스트림만 역직렬화해야 한다.
* */

import java.util.HashSet;
import java.util.Set;

/* 역직렬화 폭탄의 얘시
역직렬화 폭탄 = 역직렬화에 시간이 오래 걸리는 짧은 스트림
- 이런걸 역직렬하 하면 서비스 거부 공격에 쉽게 노출 됨
- 아래 객체 그래프는 201개의 HashSet 인스턴스로 구성되며, 각각 3개 이하의 객체 참조를 갖는다.
- 스트림의 전체 크기는 5744byte 지만, 역직렬화는 태양이 식을 때까지도 끝난지 않는다.
- 이유는 HashSet에 담긴 두 원소는 다른 HashSet 2개씩을 원소로 갖는 HashSet이며, 반복문에 의해 구조가 깊이 100까지 만들어진다.
- 따라서 HashSet을 역직렬화 하려면 hasCode 메서드를 2^100번 넘게 호출해야 한다.

- 직렬화 위험을 회피하는 가장 좋은 방법은 아무것도 역직렬화 하지 않는 것
- 마찬가지로, 작성하는 새로운 시스템에서 자바 직렬화를 써야할 이유는 없다.

크래스-플랫폼 구조화된 데이터 표현
- 자바 직렬화보다 훨씬 간단하다.
- 임의 객체 그래프를 자동으로 직렬화/역직렬화 하지 않고, key-value 쌍의 집합으로 구성된 간단하고 구조화된 데이터 객체 사용
- 기본 타입 몇개와 배열 타입만 지원한다.
- 이러한 간단한 추상화만으로 강력한 분산 시스템을 구축하기에 충분하고, 자바 직려화의 문제를 회피할 수 있다.
- 대표적인 예로 JSON, protocol buffer

JSON
- 브라우저와 서버의 통신용으로 설계되어있으며, js용으로 만들어졌다.
- 텍스트 기반이라서 사람이 읽을 수 있따.
- 오직 데이터를 표현하는 데만 쓰인다.

protocol buffer
- 구글이 서버 사이에 데이터를 교환하고 저장하기 위해 설계되었으며, C++ 용으로 만들어짐
- 이진 표현이라 효율이 훨씬 높다.
- 문서를 위한 스키마를 제공하고 올바로 쓰도록 강요한다.
- 이진 표현 뿐만아니라 사람이 읽을 수 있는 텍스트 표현도 지원한다.
* */
class EX_1 {
	static byte[] bomb() {
		Set<Object> root = new HashSet<>();
		Set<Object> s1 = root;
		Set<Object> s2 = new HashSet<>();

		for (int i=0; i < 100; i++) {
			Set<Object> t1 = new HashSet<>();
			Set<Object> t2 = new HashSet<>();

			t1.add("foo"); // t1을 t2과 다르게 만든다.
			s1.add(t1); s1.add(t2);

			s2.add(t1); s2.add(t2);
			s1 = t1; s2 = t2;
		}
//		return serialize(root);
		return new byte[10];
	}
}

















public class Item_85 {
}
