package Chapter_6.Item_36;

/* 비트 필드 대신 EnumSet을 사용하라
* */

import java.util.Set;

/*
비트 필드 = 비트별 OR를 사용해서 여러 상수를 하나의 집합으로 모을 때
이전에 비트 필드를 사용하는 방식

아래는 옛날에 사용한 비트 필드 열거 상수 방식
* */
class WorstText {
	public static final int STYLE_BOLD      = 1 << 0; // 1
	public static final int STYLE_ITALIC    = 1 << 1; // 2
	public static final int STYLE_UNDERLINE = 1 << 2; // 4
	public static final int STYLE_STRIKETHROUGH = 1 << 3; // 8

	// 매개변수 styles는 0개 이상의 STYLE_ 상수를 비트별 OR한 값이다.
	public void applyStyles(int styles) {}
}

//개선 버전
class Text {
	public enum Style {
		BOLD, ITALIC, UNDERLINE, STRIKETHROUGH;
		
		public void applyStyles(Set<Style> styles) {}
	}
}

public class Item_36 {
}
