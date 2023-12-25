package Chapter_7.Item_42;

/* 익명 클래스 보다는 람다를 사용하라

익명 클래스는 자바 8 이전에 쓰던 낡은 기법 (코드가 너무 길어짐)
* */

import java.util.function.DoubleBinaryOperator;

class EX_1 {
	enum Operation {
		PLUS("+") {
			public double apply(double x, double y) {
				return x + y;
			}
		},
		MINUS("-") {
			public double apply(double x, double y) {
				return x - y;
			}
		},
		TIMES("*") {
			public double apply(double x, double y) {
				return x * y;
			}
		},
		DIVIDE("/") {
			public double apply(double x, double y) {
				return x / y;
			}
		};

		private final String symbol;

		Operation(String symbol) {
			this.symbol = symbol;
		}

		public abstract double apply(double x, double y);
	}
}

//EX_1의 추상 클래스를 사용해서 apply를 구축한 방법을 
//EX_2 람다를 이용해서 변형시킨 모습

class EX_2 {
	enum Operation {
		PLUS("+", (x, y) -> x + y),
		MINUS("-", (x, y) -> x - y),
		TIMES("*", (x, y) -> x * y),
		DIVIDE("/", (x, y) -> x / y)
		;

		private final String symbol;
		private final DoubleBinaryOperator op;

		Operation(String symbol, DoubleBinaryOperator op) {
			this.symbol = symbol;
			this.op = op;
		}

		public double apply(double x, double y) {
			return op.applyAsDouble(x, y);
		}
	}

	public static void main(String[] args) {
		System.out.println(Operation.PLUS.apply(10, 3));
	}
}

/* 추상 클래스의 인스턴스를 만들 때 람다를 쓸 수는 없으니, 이럴 땐 익명 클래스를 사용해야 한다.
- 람다는 자신을 참조할 수 없다.
* */

public class Item_42 {
}
