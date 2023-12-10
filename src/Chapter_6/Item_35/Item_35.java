package Chapter_6.Item_35;

/* ordinal 메서드 대신 인스턴스 필드를 사용하라
ordinal: 열거 타입에서 몇번째 위치에 있는지를 반환하는 메서드
* */

//ordinal을 잘못 사용한 예 - 절대 사용하지 말 것
// 상수 순서가 바귀는 순간 오작동 한다.
enum Ensemble {
	SOLO, DUET, TRIO, QUARTET
	;
	
	public int numberOfMusicians() { return ordinal() + 1;}
}

//인스턴스 필드에 저장하라
enum RightEnsemble {
	SOLO(1), DUET(2), TRIO(3), QUARTET(4)
	;
	
	private final int numberOfMusicians;
	RightEnsemble(int size) {this.numberOfMusicians = size;}
	public int getNumberOfMusicians() {return numberOfMusicians;}

	public int numberOfMusicians() { return ordinal() + 1;}
}

public class Item_35 {
}
