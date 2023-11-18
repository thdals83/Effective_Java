package Chapter_5.Item_27;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/*
비검사 경고를 제거하라 제거할 수 있으면 항상 하라
경고를 제거할 수는 없지만 타입이 안전하다고 확신하면 @Suppress 애너테이션을 달아 경고를 숨긴다. 하지만 웬만하면 사용하지 말것
* */
public class Item_27 {
	private static final int size = 10;
	public <T> T[] toArray(T[] a) {
		Object[] elements = new Object[0];
		if (a.length < size) {
			// 생성한 배열과 매개변수로 받은 배열이 모두 T[]로 같으므로
			// 올바른 형변환이다.
			@SuppressWarnings("unchecked")
			T[] result = (T[]) Arrays.copyOf(elements, size, a.getClass());
			return result;
		}
		System.arraycopy(elements, 0, a, 0, size);
		if (a.length > size)
			a[size] = null;
		return a;
	}
}
