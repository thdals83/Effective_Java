package Chapter_4.Item_24;

/* 멤버 클래스는 되도록 static으로 만들어라

중첩 클래스: 다른 클래스 안에 정의된 클래스
	- 정적 멤버 클래스, 비정적 멤버 클래스, 익명 클래스, 지역 클래스 존재
	
정적 멤버 클래스
- 다른 클래스 안에 선언됨
- 바깥 클래스의 private 멤버에도 접근 할 수 있다.
- 중첩 클래스의 인스턴스가 바깥 인스턴스와 독립적으로 존재할 수 있다면 이로 만들어야 한다.
- 바깥 인스턴스에 접근할 일이 없다면 무조건 이걸로 사용하자.

비정적 멤버 클래스
- 어댑터를 정의할 때 자주 사용됨
- 바깥 클래스의 인스턴스가 있어야만 생성할 수 있음
- 어떤 클래스의 인스턴스를 감싸 마치 다른 클래스의 인스턴스처럼 보이게 하는 뷰로 사용

* */
public class EX_1 {
}
