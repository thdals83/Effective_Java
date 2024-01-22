package Chapter_9.Item_59;



/* 라이브러리를 익히고 사용하라
* */

import java.util.Random;

class EX_1 {
	/* // 무작위 정수 하나를 생성하는 메서드를 만든다. 0 ~ n까지 중에
	아래 코드는 문제점을 세가지나 내포하고 있다.
	1. n이 그리 크지 않은 2의 제곱수라면 얼마 지나지 않아 같은 수열이 반복된다.
	2. n이 2의 제곱수가 아니라면 몇몇 숫자가 평균적으로 더 자주 반환한다.
	3. 지정된 범위 '바깥'의 수가 종종 튀어나올 수있다.
		- Math.abs를 이용해 음수가 아닌 정수로 매핑하기 때문
	* */
	
	static Random rnd = new Random();
	static int random(int n) {
		return Math.abs(rnd.nextInt()) % n;
	}
	
	//그래서 그냥 Random.nextInt(int)써라, 똑똑한 분들이 킹왕짱으로 만들어 놨다. 자바 7 이상은 ThreadLocalRandom을 써라
}
public class Item_59 {
}
