package Chapter_6.Item_34;

/* int 상수 대신 열거 타입을 사용하라

자바의 enum은 아래와 같은 특징을 가지고 있다.
- 클래스이다.
- 상수 하나당 자신의 인스턴스를 하나씩 만들어
- public static final 필드로 공개한다.
- 임의이 메서드나 필드를 추가할 수 있다.
- 임의의 인터페이스를 구혈 할 수 있다.
* */


import static Chapter_6.Item_34.EX_3.PayrollDay.PayType.WEEKDAY;
import static Chapter_6.Item_34.EX_3.PayrollDay.PayType.WEEKEND;

/* 
열거 타입에 메서드나 필드 추가가 필요한 경우
- 아래와 같이 사용
* */
class EX_1 {
	enum Planet {
		MERCURY(3.302e+23, 2.439e6),
		VENUS(4.869e+24, 6.052e6),
		EARTH(5.975e+24, 6.378e6),
		MARS(6.419e+23, 3.393e6),
		JUPITER(1.899e+27, 7.149e7),
		SATURN(5.685e+26, 6.027e7),
		URANUS(8.683e+25, 2.556e7),
		NEPTUNE(1.024e+26, 2.477e7);

		private final double mass;           // 질량(단위: 킬로그램)
		private final double radius;         // 반지름(단위: 미터)
		private final double surfaceGravity; // 표면중력(단위: m / s^2)

		// 중력상수(단위: m^3 / kg s^2)
		private static final double G = 6.67300E-11;

		// 생성자
		Planet(double mass, double radius) {
			this.mass = mass;
			this.radius = radius;
			surfaceGravity = G * mass / (radius * radius);
		}

		public double mass() {
			return mass;
		}

		public double radius() {
			return radius;
		}

		public double surfaceGravity() {
			return surfaceGravity;
		}

		public double surfaceWeight(double mass) {
			return mass * surfaceGravity;  // F = ma
		}
	}
}

class EX_2 {
	/*
	worst case
	아래와 같이 작성하면 깨지기 쉬운 코드이다.
	새로운 상수가 추가되면 해당 case 문도 추가해야 한다., 만약 깜박하면 throw를 던질 것 이다.
	* */
	enum Worst_Operation {
		PLUS, MINUS, TIMES, DIVIDE;

		public double apply(double x, double y) {
			switch (this) {
				case PLUS:
					return x + y;
				case MINUS:
					return x - y;
				case TIMES:
					return x * y;
				case DIVIDE:
					return x / y;
			}
			throw new AssertionError("알 수 없는 연산:" + this);
		}
	}

	//apply 라는 추상 메서드를 선언하고, 각 상수에서 자신에 맞게 재정의한다. -> 상수별 메서드 구현
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

class EX_3 {
	/*
	worst case
	값에 따라 분기하여 코드를 공유하는 열거 타입
	- 관리 관점에서 위험한 코드
	* */
	enum WorstPayrollDay {
		MONDAY, TUESDAY, WEDSENDAY, THURSDAY, FRIDAY, SATURDAY, SUNDAY;

		private static final int MINS_PER_SHIFT = 8 * 60;

		int pay(int minutesWorked, int payRate) {
			int basePay = minutesWorked * payRate;

			int overtimePay;
			switch (this) {
				case SATURDAY:
				case SUNDAY:
					overtimePay = basePay / 2;
					break;
				default:
					overtimePay = minutesWorked <= MINS_PER_SHIFT ? 0 : (minutesWorked - MINS_PER_SHIFT) * payRate / 2;
			}
			return basePay + overtimePay;
		}
	}
	
	/* 개선 case
	* */
	// 코드 34-9 전략 열거 타입 패턴 (218-219쪽)
	enum PayrollDay {
		MONDAY(WEEKDAY), TUESDAY(WEEKDAY), WEDNESDAY(WEEKDAY),
		THURSDAY(WEEKDAY), FRIDAY(WEEKDAY),
		SATURDAY(WEEKEND), SUNDAY(WEEKEND);
		// (역자 노트) 원서 1~3쇄와 한국어판 1쇄에는 위의 3줄이 아래처럼 인쇄돼 있습니다.
		// 
		// MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY,
		// SATURDAY(PayType.WEEKEND), SUNDAY(PayType.WEEKEND);
		//
		// 저자가 코드를 간결하게 하기 위해 매개변수 없는 기본 생성자를 추가했기 때문인데,
		// 열거 타입에 새로운 값을 추가할 때마다 적절한 전략 열거 타입을 선택하도록 프로그래머에게 강제하겠다는
		// 이 패턴의 의도를 잘못 전달할 수 있어서 원서 4쇄부터 코드를 수정할 계획입니다.

		private final PayType payType;

		PayrollDay(PayType payType) { this.payType = payType; }
		
		int pay(int minutesWorked, int payRate) {
			return payType.pay(minutesWorked, payRate);
		}
		// 전략 열거 타입
		enum PayType {
			WEEKDAY {
				int overtimePay(int minsWorked, int payRate) {
					return minsWorked <= MINS_PER_SHIFT ? 0 :
							(minsWorked - MINS_PER_SHIFT) * payRate / 2;
				}
			},
			WEEKEND {
				int overtimePay(int minsWorked, int payRate) {
					return minsWorked * payRate / 2;
				}
			};

			abstract int overtimePay(int mins, int payRate);
			private static final int MINS_PER_SHIFT = 8 * 60;

			int pay(int minsWorked, int payRate) {
				int basePay = minsWorked * payRate;
				return basePay + overtimePay(minsWorked, payRate);
			}
		}

		public static void main(String[] args) {
			for (PayrollDay day : values())
				System.out.printf("%-10s%d%n", day, day.pay(8 * 60, 1));
		}
	}
}


public class Item_34 {
}
