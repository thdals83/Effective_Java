package Chapter_4.Item_18;

/* 상속 보다는 컴포지션을 사용하라
이번 아이템에서 이야기 하는 상속은 클래스가 다른 클래스를 확장하는 구현 상속이다.
(클래스 - 인터페이스 or 인터페이스 - 인터페이스 사이에 상속은 해당 안됨)
* */

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

class ex1<E> extends HashSet<E> {
	/* 메서드 호출과 달리 상속은 캡슐화를 깨뜨린다.
- 상위 클래스의 구현에 따라 하위 클래스의 동작에 이상이 생길 수 있음
- 상위 클래스 내부 구현이 달라지면 하위 클래스 까지 영향을 미친다.
* */
	private int addCount = 0;
	public ex1() {}

	@Override
	public boolean add(E e) {
		addCount++;
		return super.add(e);
	}

	@Override
	public boolean addAll(Collection<? extends E> c) {
		addCount += c.size();
		return super.addAll(c);
	}

	public int getAddCount() {
		return addCount;
	}

	public static void main(String[] args) {
		ex1<String> s = new ex1<>();
		s.addAll(Arrays.asList("a","b","c"));
		System.out.println(s.getAddCount());
		/*
		여기서 3개만 더했는데 출력값은 6이 나온다. 내부에서 add 호출받아서 6나온다.
		
		* */
	}
	
}

class ex2<E> extends ForwardingSet<E> {
	/* 상속 대신 컴포지션을 사용
- private 필드로 기존 클래스를 생성함
* */
	private int addCount = 0;

	public ex2(Set<E> s) {
		super(s);
	}

	@Override 
	public boolean add(E e) {
		addCount++;
		return super.add(e);
	}
	@Override 
	public boolean addAll(Collection<? extends E> c) {
		addCount += c.size();
		return super.addAll(c);
	}
	public int getAddCount() {
		return addCount;
	}
}
//새 클래스에 메서드를 전달할 클래스를 생성해서 전달
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

public class EX_1 {
}
