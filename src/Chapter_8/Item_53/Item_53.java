package Chapter_8.Item_53;


/* 가변인수는 신중히 사용하라
- 가변인수 메서드를 호출하면, 가장 먼저 인수의 개수와 길이가 같은 배열을 만들고 
  인수들을 그 배열에 저장하여 가변인수 메서드에 건내준다.
* */

//만약 인수가 1개 이상이어야 하는데, 0개만 넣어도 호출되는 상황을 방지하고 싶다면 length를 체크하여 throw를 던지는 것 보단,
//매개변수를 2개 받도록해서 처리한다.
class EX_1 {
	static int min(int firstArg, int... remainingArgs) {
		int min = firstArg;
		for (int arg: remainingArgs)
			if (arg < min)
				min = arg;
		return min;
	}
}

/* 가변인수 활용 방법
1. System.out.printf()
2. ReflectionUtils.findMethod()
* */

/* 가변인수 문제점
메서드 호출때마다, 새로운 배열이 생성되고 초기화 된다.
그래서 가변인수 확률에 따른 메서드를 몇가지 오버로딩 해두는 것이다.

EnumSet.of() 정적 팩터리 메서드에서도 동일한 방법으로 생성 비용을 최소화 하고 있다.
* */
class EX_2 {
	public void foo() {}
	public void foo(int a1) {}
	public void foo(int a1, int a2) {}
	public void foo(int a1, int a2, int a3) {}
	public void foo(int a1, int a2, int a3, int... rest) {}
}





public class Item_53 {
}
