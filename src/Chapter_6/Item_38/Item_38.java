package Chapter_6.Item_38;

/* 확장할 수 있는 열거 타입이 필요하면 인터페이스를 사용하라

타입 안전 열거 패턴은 확장 할 수 있으나, 열거 타입은 그럴 수 없다.

확장 할 수 있는 열거 타입이 어울리는 쓰임이 있는데, 연산 코드에서다.
- 기본 연산 이외에 확장 연산을 추가해줘야 하는 상황

이를 열거 타입으로 해결하는 방법은 인터페이스를 이용하는 방법이다.
* */
interface Operation {
	double apply(double x, double y);
}

enum BasicOperation implements Operation {
	PLUS("+") {
		public double apply(double x, double y) { return x + y; }
	},
	MINUS("-") {
		public double apply(double x, double y) { return x - y; }
	},
	TIMES("*") {
		public double apply(double x, double y) { return x * y; }
	},
	DIVIDE("/") {
		public double apply(double x, double y) { return x / y; }
	};
	
	private final String symbol;
	BasicOperation(String symbol) {this.symbol = symbol;}
}

/* 여기에 추가하고 싶은 연산 타입 지수연산, 나머지 연산을 추가한다.
* */

enum ExtendedOperstaion implements Operation {
	EXP("^") {
		public double apply(double x, double y) {
			return Math.pow(x, y);
		}
	},
	REMAINDER("%") {
		public double apply(double x, double y) {
			return x % y;
		}
	};
	private final String symbol;
	ExtendedOperstaion(String symbol) {this.symbol = symbol;}
}

/*
이렇게 하면, Operation 인터페이스를 사용하도록 작성되어있으면 어디서든 사용할 수 있다.
* */















public class Item_38 {
}
