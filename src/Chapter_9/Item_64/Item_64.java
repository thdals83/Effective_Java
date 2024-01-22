package Chapter_9.Item_64;



/* 문자열 연결은 느리니 주의하라
문자열 연결 연산자로 문자열 n개를 잇는 시간은 "n의 제곱"에 비례한다
	- 문자열은 불변이라, 두 문자열을 연결할 경우 양쪽의 내용을 모두 복사해야 하므로 성능 저하는 피할 수 없다.	
* */

//문자열 연결을 잘못 사용한 예
class EX_1 {
	public String statement() {
		String result = "";
		for (int i = 0; i < 3; i++) {
			result += i;
		}
		return result;
	}
	
	//StringBuilder를 사용하면 문자열 연결 성능이 크게 개선된다.
	public String statement2() {
		StringBuilder b = new StringBuilder(3 * 10);
		for (int i = 0; i < 3; i++)
			b.append(i);
		return b.toString();
	}
}


public class Item_64 {
}
