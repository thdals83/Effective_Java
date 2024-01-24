package Chapter_10.Item_75;



/* 예외의 상세 메시지에 실패 관련 정보를 담으라
- 예외를 잡지 못해 프로그램이 실패하면, 자바 시스템은 그 예외의 스택 추적 정보를 자동으로 추적한다.
  스택 추적은 예외 객체의 toString 메서드를 호출해 얻는 문자열이다.
  -> 이 정보가 실패 원인을 분석해야 하는 프로그래머 or SRE가 얻을 수 있는 유일한 정보인 경우가 많다. 따라서
     toString 메서드에 실패 원인에 대한 정보를 가능한 한 많이 담아 반환하는 일은 아주 중요하다.
- 실패 순간을 포착하려면 발생한 예욍 관여된 모든 매개변수와 필드의 값을 실패 메시지에 담아야 한다.
  EX) IndexOutBoundsException의 상태 메시지는 범위의 최솟값, 최댓값, 그리고 그 범위를 벗어났다는 인덱스 값을 담아야 한다.
      (그렇다고 보안과 관련된 상세 메시지에 비밀번호나 암호키 같은 정보까지 담아서는 안된다)

- 문서와 소스코드에서 얻을 수 있는 정보는 길게 늘어놔바야 군더더기 될 뿐
- 예외의 상세 메시지와 최종 사용자에게 보여줄 오류 메시지를 혼동해서는 안된다. 최종 사용자에게는 친절할 안내 메시지를,
  예외 메시지는 가독성보다는 담긴 내용이 중요하다.
  
 * IndexOutOfBoundsException을 생성한다.
 *
 * @param lowerBound 인덱스의 최솟값
 * @param upperBound 인덱스의 최댓값 + 1
 * @param index 인덱스의 실젯값
 */
//public IndexOutOfBoundsException(int lowerBound, int upperBound, int index) {
//	// 실패를 포착하는 상세 메시지를 생성한다.
//	super(String.format("최솟값: %d, 최댓값: %d, 인덱스: %d", lowerBound, upperBound, index));
//
//	// 프로그램에서 이용할 수 있도록 실패 정보를 저장해둔다.
//	this.lowerBound = lowerBound;
//	this.upperBoudn = upperBound;
//	this.index = index;
//}

public class Item_75 {
}
