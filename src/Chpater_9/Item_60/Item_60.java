package Chpater_9.Item_60;


import java.math.BigDecimal;

/* 정확한 답이 필요하다면 float와 double은 피하라
- float, double 타입은, 이전 부동소수점 연산에 쓰이며 넓은 범위의 수를 빠르게 정밀한 근사치로 계산하도록 설계되었다.
따라서, 정확한 결과가 필요할때는 부적합하다. 특히나 금융 관련 계산과는 맞지 않는다. 0.1 혹은 10의 음의 거듭제곱 수는 표현할 수 없다.
* 
* */
class EX_1 {
	//주머니에 들은 1달러를 각 센트부터 살 수 있을때까지 샀을때, 총 사탕을 몇 개 살 수 있고 잔돈은 얼마나 남을지에 대한 예제
	//사탕 3개를 구입한 후, 잔돈의 값이 부정확하게 나온다. 1.00 - (0.10 + 0.20 + 0.30) = 0.40 이 나와야 하기 때문이다.
	public static void main(String[] args) {
		double funds = 1.00;
		int itemsBought = 0;
		for (double price = 0.10; funds >= price; price += 0.10) {
			funds -= price;
			itemsBought++;
		}
		System.out.println(itemsBought + "개 구입");
		System.out.println("잔돈(달러): " + funds); // 0.3999999999999
	}
}

//금융 계산에는 정수 타입을 사용해야 한다. BigDecimal, int, long
class EX_2 {
	//하지만 기본 타입보다 속도가 느리고 쓰기 불편하다.
	public static void main(String[] args) {
		final BigDecimal TEN_CENTS = new BigDecimal(".10");

		int itemsBought = 0;
		BigDecimal funds = new BigDecimal("1.00");
		for (BigDecimal price = TEN_CENTS; funds.compareTo(price) >= 0; price = price.add(TEN_CENTS)) {
			funds = funds.subtract(price);
			itemsBought++;
		}
		System.out.println(itemsBought + "개 구입");
		System.out.println("잔돈(달러): " + funds); // 4개 구입시 잔돈 0
	}
}

class EX_3 {
	//아래와 같이 기본타입으로 작성하면 성능은 좋지만, 값의 크기가 제한되고 소수점을 직접 관리해야 한다.
	public static void main(String[] args) {
		int itemsBought = 0;
		int funds = 100;
		for (int price = 10; funds >= price; price += 10) {
			funds -= price;
			itemsBought++;
		}
		System.out.println(itemsBought + "개 구입");
		System.out.println("잔돈(센트): " + funds);
	}
}
public class Item_60 {
}
