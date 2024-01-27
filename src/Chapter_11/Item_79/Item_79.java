package Chapter_11.Item_79;

import java.util.ArrayList;
import java.util.Collection;
import java.util.ConcurrentModificationException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/* 과도한 동기화는 피하라
- 과도한 동기화는 오히려 성능을 떨어트리고, 교착상태에 빠뜨린다.

과도한 동기화 예시
- 재정의 가능한 메서드나 클라이언트가 넘겨준 함수 객체를 동기화 영역 안에서 호출하면, 어떤 짓을 하는지
  통재할 수 없기 때문에 예외를 일으키거나 교착상태에 빠지거나, 데이터를 훼손할 수 있다.
* */
interface SetObserver<E> {
	void added(ObservableSet<E> set, E element);
}

class ForwardingSet<E> implements Set<E> {
	private final Set<E> s;
	public ForwardingSet(Set<E> s) { this.s = s; }

	public void clear()               { s.clear();            }
	public boolean contains(Object o) { return s.contains(o); }
	public boolean isEmpty()          { return s.isEmpty();   }
	public int size()                 { return s.size();      }
	public Iterator<E> iterator()     { return s.iterator();  }
	public boolean add(E e)           { return s.add(e);      }
	public boolean remove(Object o)   { return s.remove(o);   }
	public boolean containsAll(Collection<?> c)
	{ return s.containsAll(c); }
	public boolean addAll(Collection<? extends E> c)
	{ return s.addAll(c);      }
	public boolean removeAll(Collection<?> c)
	{ return s.removeAll(c);   }
	public boolean retainAll(Collection<?> c)
	{ return s.retainAll(c);   }
	public Object[] toArray()          { return s.toArray();  }
	public <T> T[] toArray(T[] a)      { return s.toArray(a); }
	@Override public boolean equals(Object o)
	{ return s.equals(o);  }
	@Override public int hashCode()    { return s.hashCode(); }
	@Override public String toString() { return s.toString(); }
}
class ObservableSet<E> extends ForwardingSet<E> { // 래퍼 클래스
	public ObservableSet(Set<E> set) {
		super(set);
	}

	private final List<SetObserver<E>> observers = new ArrayList<>();

	public void addObserver(SetObserver<E> observer) {
		synchronized (observers) {
			observers.add(observer);
		}
	}

	public boolean removeObserver(SetObserver<E> observer) {
		synchronized (observers) {
			return observers.remove(observer);
		}
	}

	private void notifyElementAdded(E element) {
		synchronized (observers) {
			for (SetObserver<E> observer : observers)
				observer.added(this, element);
		}
	}
	@Override public boolean add(E element) {
		boolean added = super.add(element);
		if (added)
			notifyElementAdded(element);
		return added;
	}
	@Override public boolean addAll(Collection<? extends E> c) {
		boolean result = false;
		for (E element : c)
			result |= add(element);  // notifyElementAdded를 호출한다.
		return result;
	}
}

/* 하지만, 이 코드는 외부에서 함수 객체(setObserver)를 받아오기 때문에 아래와 같은 에러가 발생 할 수 있다.
1. ConcurrentModificationException 예외
- 한 스레드만 observer 리스트에 접근할 수 있기 때문에 동기화 관련 예외가 터지지 않을 것 같아 보인다.

하지만 외부 함수 객체 메서드인 added()가 동기화 블럭 내에 존재하게 되면서, 다음과 같은 문제를 야기한다.
1. addObserver() -> add() -> notifyElementAdded -> added() 호출
2. add() -> removerObserver() 호출

이미 1번 과정에서 observers를 synchronized 감싸고 있음에도, 콜백을 거쳐 돌아와서 수정되는 것 까지는
막지는 못하기 때문에 같은 리스트를 동시에 수정하려고 접근하게 되고, ConcurrentModificationException 발생
* */
class EX_1 {
	public static void main(String[] args) {
		ObservableSet<Integer> observableSet = new ObservableSet<>(new HashSet<>());
		SetObserver<Integer> observer = new SetObserver<Integer>() {
			@Override public void added(ObservableSet<Integer> set, Integer e) {
				System.out.println("added" + e);
				if (e == 3) {
					set.removeObserver(this); //ConcurrentModificationException
				}
			}
		};
		observableSet.addObserver(observer);
		try {
			for (int i = 0; i < 100; i++) {
				observableSet.add(i);
			}
		} catch (ConcurrentModificationException e) {
			System.out.println("ConcurrentModificationException 발생");
		}
	}
}

/* 교착 상태 (DeadLock)
1번의 예외를 피하기 위해 아예 다른 스레드를 통해 작업을 수행한다.
예외가 일어나진 않지만 메인 스레드가 addObserver에서 이미 observers에 대한 락을 쥐고 있기 때문에,
다른 백그라운드 스레드가 removeObserver를 호출 시 락을 얻을 수 없어 교착상태가 발생한다.
- image - 1참고
* */
class EX_2 {
	public static void main(String[] args) {
		ObservableSet<Integer> observableSet = new ObservableSet<>(new HashSet<>());
		SetObserver<Integer> observer = new SetObserver<Integer>() {
			@Override public void added(ObservableSet<Integer> s, Integer e) {
				System.out.println(e);
				if (e == 3) {
					//새로운 스레드에 삭제 작업 한다.
					ExecutorService exec = Executors.newSingleThreadExecutor();
					try {
						exec.submit(() -> s.removeObserver(this)).get();
					} catch (ExecutionException | InterruptedException ex) {
						throw new AssertionError(ex);
					} finally {
						exec.shutdown();
					}
				}
			}
		};
		observableSet.addObserver(observer);
		try {
			for (int i = 0; i < 100; i++) {
				observableSet.add(i);
			}
		} catch (ConcurrentModificationException e) {
			System.out.println("ConcurrentModificationException 발생");
		}
	}
}

/* 데이터의 훼손 (불변)
자바 고유의 락은 재진입이 가능하다. 따라서 위의 교착 상태는 회피할 수 있다.
락의 획득이 메서드 호출 단위가 아닌, 스레드 단위로 일어난다. 같은 스레드가 synchornized 영역을 만나게 되면
대기하지 않고 바로 접속 할 수 있다.

하지만 만약 다른 동기화 메서드에서 락이 보호하고 있는 데이터에 대해 관련이 없는 다른 작업이 진행중이라면
데이터 훼손이 될 수 있는 문제가 발생한다.
* */
class EX_3 {
	public synchronized void a() {
		System.out.println("a");
		b();    // 2. 동기화 구간 바로 접근 가능
	}

	public synchronized void b() {
		System.out.println("b");
	}

	public static void main(String[] args) {
		new EX_3().a(); // 1. 락 획득
	}
}

/* 과도한 동기화 문제 해결 방법
1. 동기화 블럭 외부로 외계인 메서드 빼기 (스냅샷 방법)
- 메서드의 호출을 동기화 블럭 바깥으로 뺀다. 가능한 동기화 영역에서 수행하는 일을 최소화 시킨다.
  더불어 리스트를 복사해서 사용하기 때문에 굳이 락 없이도 안전하게 순회 가능하다.

AS-IS
	private void notifyElementAdded(E element) {
		synchronized (observers) {
			for (SetObserver<E> observer : observers)
				observer.added(this, element);
		}
	}
TO-BE
 private void notifyElementAdded(E element) {
     List<SetObserver<E>> snapshot = null;
     synchronized(observers) {
    	snapshot = new ArrayList<>(observers);
     }
     for (SetObserver<E> observer : snapshot) // 외부로 빼내기
        observer.added(this, element);
 }
 

2. CopyOnWriteArrayList 사용
내부를 변경하는 작업은 항상 복사본을 통해 이루어지기 때문에, 데이터 순회 시 동기화가 필요 없어 속도가 
매우 빠르다는 장점이 존재한다. 따라서 수정할 일이 드물고 순회만 자주 일어나느 경우 사용하기 좋다.
private final List<SetObserver<E>> observers = new CopyOnWriteArrayList<>();
...
private void notifyElementAdded(E element) {
     for (SetObserver<E> observer : observers)
          observer.added(this, element);
}
* */

/* 동기화 시 주의점
- 과도하게 동기화를 하면, 병렬로 실행을 못하기 때문에 경쟁하고 메모리를 일관되게 보기 위한 지연 시간이 발생해 비 효율적
불변이 아닌 가변 클래스를 설계할 때는, 아래 두 가지를 고민해야 한다.
1. 외부에서 동기화 = 해당 클래스를 동시에 사용해야 하는 클래스가 외부에서 객체 전체에 동기화를 거는 방식
   EX) java.util
2. 내부에서 동기화
- 외부보다 동시성을 월등히 개선 가능할 때만 ex) java.util.concurrent 의 ConcurrentHashMap, BlockingQueue .
* */
public class Item_79 {
}
