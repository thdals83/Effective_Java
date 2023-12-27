package Chapter_7.Item_47;

import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/* 반환 타입으로는 스트림보다 컬렉션이 낫다.

1) 스트림 도입 이전
스트림이 도입되기 이전에 원소 시퀀스
즉 일련의 원소를 반환하는 메서드의 반환 타입으로 Collection, Set, List과 같은 컬렉션 인터페이스나 Iterable 또는 배열을 사용했다.

2) 스트림 도입 이후
스트림은 반복(iteration)을 지원하지 않는다. 따라서 원소 시퀀스를 반환할때 스트림을 사용하면 아래와 같이 for-each 로 반복을 수행할 수 없다.

static Stream<ProcessHandle> allProcesses()
// Stream.iterator 사용
for(ProcessHandle p: ProcessHandle.allProcesses.iterator()) {}
for-each 와 같이 향상된 for 문이 가능한 컬렉션은 Iterable 인터페이스를 구현하고 있어야 하기 때문이다. 
Stream 인터페이스는 Iterable 인터페이스가 정의한 추상 메서드를 포함하고 정의한 방식대로 동작하지만, 확장(extend) 하지 않았기 때문에 반복이 불가능하다.

그렇다면 스트림을 반복할 수 있게 하려면 어떻게 해야 할까?
* */
class EX_1 {
	//어댑터 패턴을 이용
	public static <E> Iterable<E> iterableOf(Stream<E> stream) {
		return stream::iterator;
	}
	public static void main(String[] args) {
		for (ProcessHandle p : iterableOf(ProcessHandle.allProcesses())) {
			
		}
	}
	//Iterable -> Stream도 만들어 주면 아래와 같다.
	public static <E> Stream<E> streamOf(Iterable<E> iterable) {
		return StreamSupport.stream(iterable.spliterator(), false);
	}
}

/* Collection interface
- Collection는 Iterable의 하위 타입이고, stream 메서드도 제공하여 반복과 스트림 동시에 지원한다.
  따라서 원소 시퀀스를 반환하는 곳에선 Collection이나 그 하위 타입을 써주자.
  
  원소 사이즈가 작은 경우: 표준 컬렉션
  원소 사이즈가 큰 경우: 전용 컬렉션을 구현한다.
* */

public class Item_47 {
}
