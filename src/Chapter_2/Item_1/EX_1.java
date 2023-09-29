package Chapter_2.Item_1;

public class EX_1 {
	public static void main(String[] args) {
		Order prime = Order.primeOrder("11", true);
		Order urgent = Order.urgentOrder("11", false);
	}

}

//정적 팩터리 메서드(static factory method)
/*
- 자바의 생성자는 기본적으로 같은 타입을 여러개 생성 할 경우, 자바 컴파일러에서 인식하지 못한다. EX)아래 주석 처리된 생성자
- EX_1은 생성자의 시그니처가 아래처럼 중복된 상황에서 사용할 경우 좋음
* */
class Order {
	private boolean prime;
	private boolean urgent;
	private String product;
	
//	public Order(String product, boolean prime) {
//		this.product = product;
//		this.prime = prime;
//	}
//	public Order(String product, boolean urgent) {
//		this.product = product;
//		this.urgent = urgent;
//	}
	
	public static Order primeOrder(String product, boolean prime) {
		Order order = new Order();
		order.product = product;
		order.prime = prime;
		return order;
	}
	
	public static Order urgentOrder(String product, boolean urgent) {
		Order order = new Order();
		order.product = product;
		order.urgent = urgent;
		return order;
	}
	
}