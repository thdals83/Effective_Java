package Chpater_4.Item_23;

/* 태그 달린 클래스보다는 클래스 계층구조를 활용하라
* */

//태그 달린 클래스 -> 클래스 계층 구조보다 나쁘다.
class Figure1 {
	enum Shape {RECTANGLE, CIRCLE};

	// 태그 필드 - 현재 모양을 나타낸다.
	final Shape shape;

	// 다음 필드들은 모양이 사각형(RECTANGLE)일 때만 쓰인다.
	double length;
	double width;

	// 다음 필드는 모양이 원(CIRCLE)일 때만 쓰인다.
	double radius;

	// 원용 생성자
	public Figure1(double radius) {
		this.shape = Shape.CIRCLE;
		this.radius = radius;
	}

	// 사각형용 생성자
	public Figure1(double length, double width) {
		this.shape = Shape.RECTANGLE;
		this.length = length;
		this.width = width;
	}

	double area() {
		switch (shape) {
			case RECTANGLE:
				return length * width;
			case CIRCLE:
				return Math.PI * (radius * radius);
			default:
				throw new AssertionError(shape);
		}
	}
}

/* 태그 달린 클래스에 단점
- 열거 타입 선언, 태그 필드, switch 문 등 쓸데없는 코드가 많다.
- 여러 구현이 한 클래스에 혼합돼 있어서 가독성도 나쁘다.
- 결국 장황하고, 오류를 내기 쉽고, 비효율적
- 태그 달린 클래스는 클래스의 계층구조를 어설프게 흉내내는 아류
* */

/* 태그 달린 클래스를 클래스 계층구조로 바꾸는 방법
1. 루트가 될 추상 클래스를 정의
2. 태그 값에 따라 동작이 달라지는 클래스를 추상 메서드로 선언
* */
abstract class Figure {
	abstract double area();
}

class Circle extends Figure {
	final double radius;
	Circle(double radius) {this.radius = radius;}
	@Override
	double area() {
		return Math.PI * (radius * radius);
	}
}
class Rectangle extends Figure {
	final double length;
	final double width;

	public Rectangle(double length, double width) {
		this.length = length;
		this.width = width;
	}

	@Override
	double area() {
		return length * width;
	}
}

public class EX_1 {
}
