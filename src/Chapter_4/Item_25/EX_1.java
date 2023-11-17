package Chapter_4.Item_25;

/* 톱레벨 클래스는 한 파일에 하나만 담으라

- 굳이 여러 톱레벨 클래스를 한 파일에 담지마라
* */
public class EX_1 {
	public static void main(String[] args) {
		System.out.println(Utensil.NAME + Dessert.NAME);
	}
	private static class Utensil {
		static final String NAME = "pan";
	}
	private static class Dessert {
		static final String NAME = "cake";
	}
}
