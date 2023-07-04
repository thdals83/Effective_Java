package Chapter_1.Item_1;

public class EX_2 {
	public static void main(String[] args) {
		Foo_Static a = Foo_Static.of("주소는 필수").withName("test");
	}
}
/*
- 자바의 생성자는 기본적으로 같은 타입을 여러개 생성 할 경우, 자바 컴파일러에서 인식하지 못한다. 아래 케이스
* */
class Foo_Public {
	//아래 에러 발생
	String name;
	String address;

	public Foo_Public(String name) {
		this.name = name;
	}

//	public Foo_Public(String address) {
//
//	}
}

class Foo_Static {
	private String name;
	private String address;

	private Foo_Static(String address) {
		this.address = address;
	}

	public static Foo_Static of(String address) {
		return new Foo_Static(address);
	}

	public Foo_Static withName(String name) {
		this.name = name;
		return this;
	}
}

