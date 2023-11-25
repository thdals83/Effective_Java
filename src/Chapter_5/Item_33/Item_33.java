package Chapter_5.Item_33;

/* 타입 안전 이종 컨테이너를 고려하라
이란?
컨테이너 대신 키를 매개변수화한 다음, 컨테이너에 값을 넣거나 뺄 때 매개변수화한 키를 함께 제공하면 제네릭 타입 시스템이 값의 타입이 키와 같음을 보장해준다.
* */

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

//예시
class Favorites1 {
	public <T> void putFavorite(Class<T> type, T instance) {}
	public <T> T getFavorite(Class<T> type) {return (T) type;}

	public static void main(String[] args) {
		Favorites1 favorites1 = new Favorites1();
		favorites1.putFavorite(String.class,"morning");
		favorites1.putFavorite(Integer.class, 0xcafebabe);
		favorites1.putFavorite(Class.class, Favorites1.class);

		String favoriteString = favorites1.getFavorite(String.class);
		Integer favoriteInteger = favorites1.getFavorite(Integer.class);
		Class<?> favoriteClass = favorites1.getFavorite(Class.class);

		System.out.printf("%s %x %s", favoriteString, favoriteInteger, favoriteClass.getName());
	}
}

//타입 안전 이종 컨테이너 패턴 구현
class Favorites {
	private Map<Class<?>, Object> favorites = new HashMap<>();
	
	public <T> void putFavorite(Class<T> type, T instance) {
		favorites.put(Objects.requireNonNull(type), instance);
	}
	
	public <T> T getFavorite(Class<T> type) {
		return type.cast(favorites.get(type));
	}

	public static void main(String[] args) {
		Favorites favorites = new Favorites();
		favorites.putFavorite(String.class,"morning");
		// String의 클래스 타입은 Class<String>이다.
		favorites.putFavorite(Integer.class, 0xcafebabe);
		// Integer의 클래스 타입은 Class<Integer>이다.
		favorites.putFavorite(Class.class, Favorites.class);

		String favoriteString = favorites.getFavorite(String.class);
		Integer favoriteInteger = favorites.getFavorite(Integer.class);
		Class<?> favoriteClass = favorites.getFavorite(Class.class);

		System.out.printf("%s %x %s", favoriteString, favoriteInteger, favoriteClass.getName());
	}
}
		
		
		
		
		
		
		
		
public class Item_33 {
}
