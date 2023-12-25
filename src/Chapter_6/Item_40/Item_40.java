package Chapter_6.Item_40;



import java.util.HashSet;
import java.util.Set;

/* @Override 애너테이션을 일관되게 사용하라

 * */

class WorstBigram {
	private final char first;
	private final char second;

	public WorstBigram(char first, char second) {
		this.first  = first;
		this.second = second;
	}

	public boolean equals(WorstBigram b) {
		return b.first == first && b.second == second;
	}

	public int hashCode() {
		return 31 * first + second;
	}

	/*
	아래 예시에서 s.size()가 26이 출력 될 것 같지만 (equals와 hashCode를 재정의 했으니까), 실제로는 260이 출력된다.
	그 이유는, equals를 override 하지 않았음으로 다중정의(overloading)된것이다
	이를 코치려면 @override를 달아준다.
	* */
	public static void main(String[] args) {
		Set<WorstBigram> s = new HashSet<>();
		
		for (int i = 0; i < 10; i++) {
			for (char ch = 'a'; ch <= 'z'; ch++) {
				s.add(new WorstBigram(ch, ch));
			}
		}
		System.out.println(s.size());
	}
}

class Bigram {
	private final char first;
	private final char second;

	public Bigram(char first, char second) {
		this.first  = first;
		this.second = second;
	}

	@Override 
	public boolean equals(Object o) {
		if (!(o instanceof Bigram)) return false;
		Bigram b = (Bigram) o;
		return b.first == first && b.second == second;
	}

	public int hashCode() {
		return 31 * first + second;
	}

	/*
	아래 예시에서 s.size()가 26이 출력 될 것 같지만 (equals와 hashCode를 재정의 했으니까), 실제로는 260이 출력된다.
	그 이유는, equals를 override 하지 않았음으로 다중정의(overloading)된것이다
	이를 코치려면 @override를 달아준다.
	* */
	public static void main(String[] args) {
		Set<Bigram> s = new HashSet<>();

		for (int i = 0; i < 10; i++) {
			for (char ch = 'a'; ch <= 'z'; ch++) {
				s.add(new Bigram(ch, ch));
			}
		}
		System.out.println(s.size());
	}
}

public class Item_40 {
}
