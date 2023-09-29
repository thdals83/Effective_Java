package Chapter_2.Item_5;


import org.junit.Test;

import java.util.List;
import java.util.Objects;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

//아이템 5
//자원을 직접 명시하지 라고 의존 객체 주입을 사용하라.
public class EX_1 {
	/*
	SpellChecker1에 단위 테스트에서 발생하는 문제점
	내부적으로 현재 EnglishDict를 사용하고 있음
	그러면, SpellChecker1에 대한 단위 테스트인데 내부적으로 사용되는
	EnglishDict에 의존적인 문제가 있어 EnglishDict에 오류가 있다면 테스트도 실패한다.
	이는 SpellChecker1만 테스트 해야 하는 단위 테스트의 원칙에 어긋난다.
	
	SpellChecker2의 싱글턴을 사용한 방식도 결국 다를바가 없다.
	* */
	@Test
	public void testIsValid() {
		assertTrue(SpellChecker1.isValid("hello"));
	}
	@Test
	public void testSuggestions() {
		assertEquals(List.of("suggestion1", "suggestion2"), SpellChecker1.suggestions("helo"));
	}

}

/*
1번 2번 방법의 경우, 
* */
interface Lexicon {
	boolean contains(String word);
	List<String> suggestions(String typo);
	
}
class EnglishDict implements Lexicon {
	@Override
	public boolean contains(String word) {
		return true;
	}
	@Override
	public List<String> suggestions(String typo) {
		return null;
	}
}
class KoreanDict implements Lexicon {
	@Override
	public boolean contains(String word) {
		return true;
	}
	@Override
	public List<String> suggestions(String typo) {
		return null;
	}
}

/*
5-1. 정적 유틸리티를 잘못 사용한 예
	- 유연성이 떨어진다. -> EnglishDict에 의존하고 있기 때문에, 다른 사전을 사용하고 싶으면 SpellChecker1 코드를 직접 수정해야 한다.
* */
class SpellChecker1 {
	private static final Lexicon dict = new EnglishDict();
	
	private SpellChecker1() {}

	public static boolean isValid(String word) {
		return dict.contains(word);
	}
	public static List<String> suggestions(String typo) {
		return dict.suggestions(typo);
	}
}

/*
- 5-2 싱글톤도 마찬가지로, Lexicon의 인스턴스를 하나만 사용하므로 좋지 않다.
* */
class SpellChecker2 {
	private final Lexicon dict = new EnglishDict();

	private SpellChecker2() {}
	public static SpellChecker2 INSTANCE = new SpellChecker2();

	public boolean isValid(String word) {
		return dict.contains(word);
	}
	public List<String> suggestions(String typo) {
		return dict.suggestions(typo);
	}

}

/*
- 5-3 의존 객체 주입방식 - 저자가 제안하는 방식
	- SpellChecker3가 생성될 때 쓰고 싶은 Lexicon의 인터페이스를 선택할 수 있음
	- 또한 주입된 후 불변성도 보장
* */
class SpellChecker3 {
	private final Lexicon dict;
	
	public SpellChecker3(Lexicon dict) {
		this.dict = Objects.requireNonNull(dict);
	}

	public boolean isValid(String word) {
		return dict.contains(word);
	}
	public List<String> suggestions(String typo) {
		return dict.suggestions(typo);
	}
}