package Chapter_2.Item_1;


public class EX_3 {
	public static void main(String[] args) {
		//동일한 객체 (해쉬코드 일치)
		Setting setting1 = Setting.getInstance();
		Setting setting2 = Setting.getInstance();

		System.out.println(setting1);
		System.out.println(setting2);
	}
}


/*
	- 생성자를 private 하게 사용하고, 정적 팩터리 메서드만을 이용해서 나가게 하여, 새로운 객체가 생성 되는 상황을 만들지 않을 수 있다.
		(객체를 계속하여 재활용 가능)
	* */
class Setting {
	private boolean a;
	private boolean b;
	private String c;

	private Setting() {
	}

	private static final Setting SETTING = new Setting();

	public static Setting getInstance() {
		return SETTING;
	}
}
