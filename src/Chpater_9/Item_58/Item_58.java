package Chpater_9.Item_58;



import javax.lang.model.element.Element;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/* 전통적인 for문 보다는 for-each 문을 사용하라
 * */
class EX_1 {
	List<String> list = new ArrayList<>();

	/* 아래는 전통적인 for문을 사용하는 방법이다.
	이 방법이 while 보다는 낫지만, 가장 좋은 방법은 아니다.
	- 반복자와 인덱스 변수는 코드만 지저분하게 할 뿐, 여기서 정말 우리가 필요한건 원소들 뿐
	- 반복자와 인덱스 변수를 잘못 사용할 확률이 있다.
	- 컬렉션이냐 배열이냐의 따라서 코드 형태가 달라진다.
	* */
	// 컬렉션을 순회하는 방법 - for loop
//	for (Iterator<String> i = list.iterator(); i.hasNext()) {
//		String e = i.next();
//		// do something with e...
//	}
	
	//배열을 순회하는 방법
//	Iterator<String> iterator = list.iterator();
//	while (iterator.hasNext()) {
//			String element = iterator.next();
//			// do something
//		}
}

class EX_2 {
	/* 이러한 단점들을 for-each문을 사용하여 해결할 수 있다. (for-each 문이 향상된 for문을 의미한다.)
	- 반복자와 인덱스 변수를 사용하지 않아, 코드가 깔끔해지고 오류가 날 일도 없다.
	
	for-each 문의 장점
	* */
	//이와 같이 작성하면, RANK의 숫자만큼 반복되서, 숫자가 바닥나면, NoSuchElementException을 던진다.
	enum Suit { CLUB, DIAMOND, HEART, SPADE }
	enum Rank { ACE, DEUCE, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT,
		NINE, TEN, JACK, QUEEN, KING }

	static Collection<Suit> suits = Arrays.asList(Suit.values());
	static Collection<Rank> ranks = Arrays.asList(Rank.values());
    
//    for (Iterator<Suit> i = suits.iterator(); i.hasNext(); )
//			for (Iterator<Rank> j = ranks.iterator(); j.hasNext(); )
//			deck.add(new Card(i.next(), j.next()));
	
	//아래처럼 작성하면 깔끔해진다.
//	for (Suit suit : suits) {
//		for (Rank rank : ranks)
//			deck.add(new Card(suit, rank));
//	}
}

/* for-each 문을 사용할 수 없는 상황
1. 파괴적인 필터링
	- 컬렉션을 순화하면서, 선택된 원소를 제거해야 하는 경우, 반복자의 removew를 사용해야 한다.
	- java 8 이상부터는 Collection의 removeIf 메서드를 사용해 컬렉션을 명시적으로 순회하는 일을 피할 수 있다.
2. 변형
	- 중간에 순회하면서 일부 값을 교체해야할 때 인덱스가 필요함
3. 병렬 반복
	- 여러 커렉션을 병렬로 순회해야 한다면, 각 컬렉션의 원소들을 동시에	 접근하기 위해
* */
public class Item_58 {
}
