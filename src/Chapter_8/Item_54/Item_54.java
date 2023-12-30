package Chapter_8.Item_54;


/* null이 아닌, 빈 컬렉션이나 배열을 반환하라
* */

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class EX_1 {
	/*
	컬렉션이 비어있으면 null을 반환하는 코드 -> 이런식으로 짜 하면 안된다.
	null을 반환하게 되면, 클라쪽에서 이 상황을 처리하는 코드를 추가로 작성해야 하기 때문
	* */
	private final List<EX_1> cheesesInStock = new ArrayList<>();

	public List<EX_1> getCheeses() {
		return cheesesInStock.isEmpty() ? null : new ArrayList<>(cheesesInStock);
	}
	
	//그래서 null말고 빈 컨테이너를 반환시켜 준다.
	public List<EX_1> getCheeses2 () {
		return new ArrayList<>(cheesesInStock);
	}
	
	/* 사용 패턴에 따라서 빈 컬렉션 할당이 성능을 떨어 뜨릴 수도 있다.
	   그래서 매번 똑같은 빈 '불변'컬렉션을 반환해준다. (불변 객체는 자유롭게 공유해도 안전하기 때문)
	   EX) Collections.emptyList, Collections.emptySet, Collections.emptyMap
	* */
	public List<EX_1> getCheeses3() {
		return cheesesInStock.isEmpty() ? Collections.emptyList() : new ArrayList<>(cheesesInStock);
	}
	
	/* 배열도 마찬가지로 null이 아닌, 0인 배열을 반환해주자.
	* */
	
	//길이가 0일 수도 있는 배열을 반환하는 올바른 방법
	public EX_1[] getCheeses4() {
		return cheesesInStock.toArray(new EX_1[0]);
	}
	
	//최적화 - 빈 배열을 매번 새로 할당하지 않게 만든다.
	private static final EX_1[] EMPTY_ARR = new EX_1[0];
	public EX_1[] getCheeses5() {
		return cheesesInStock.toArray(EMPTY_ARR);
	}
}

public class Item_54 {
}
