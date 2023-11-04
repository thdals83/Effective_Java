package Chpater_4.Item_16;

/* public 클래스에서는 public 필드가 아닌 접근자 메서드를 사용하라
* */

import java.awt.*;

//데이터 필드에 직접 접근하기 대문에 캡슐화의 이점을 제공하지 못함
class Point1 {
	public double x;
	public double y;
}

// getter, setter 메서드를 활용한 데이터 캡슐화
class Point2 {
	public double x;
	public double y;

	public Point2(double x, double y) {
		this.x = x;
		this.y = y;
	}

	public double getX() {
		return x;
	}
	public void setX(double x) {
		this.x = x;
	}
	public double getY() {
		return y;
	}
	public void setY(double y) {
		this.y = y;
	}
}

// package-private or private 중첩 클래스라면 데이터 필드를 노출한다 해도 문제가 없다. (접근자 방식보다 더 깔끔하다고 한다.)
class ColorPoint {
	private static class Point{
		public double x;
		public double y;
	}

	public Point getPoint(){ // 클라이언트 코드가 클래스 내부에 묶인다.
		Point point = new Point(); //ColorPoint 외부에서는 
		point.x = 1.2; // Point 클래스 내부 조작이 불가능하다.
		point.y = 5.3;

		return point;
	}
}

/* public 클래스의 필드가 불변이라면 어떨까?
	- 불변은 보장되지만, API를 변경하지 않고는 표현 방식을 바꿀 수 없음
	- 필드를 읽을 때 부수작업을 수행할 수 없다는 단점
* */
class Time {
	private static final int HOURS_PER_DAY = 24;
	private static final int MINUTES_PER_HOUR = 60;
	
	public final int hour;
	public final int minute;
	public Time(final int hour, final int minute) {
		this.hour = hour;
		this.minute = minute;
	}
}

public class EX_1 {
}
