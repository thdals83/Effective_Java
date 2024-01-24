package Chapter_10.Item_73;


import java.util.ListIterator;

/* 추상화 수준에 맞는 예외를 던지라 
- 메서드가 저수준 예외를 처리하지 않고 바깥으로 전파(throws) 해버릴 때, 수행하려는 일과 관련없는 예외가 튀어나오면
  내부 구현 방식을 드러내어 윗 레벨의 API를 오염시킬 수 있으므로 위험하다.
- 대표적인 예시로, 스프링에서도 데이터 계층에서 특정 DB에 종속되는 SQLException과 같은 저수준 예외를,
  예외 변환기 (PersistenceExceptionTranslator)를 통해 고수준 DataAccessException으로 변환시킨다.
  
예외 번역 (Exception Translation)
- 이 문제를 피하려면, 상위 계층에서는 저수준 예외를 잡아 자신의 추상화 수준에 맞는 예외로 바꾸어 던져야 한다. 
  이를 예외 번역이라 함
try {
} catch (LowerLevelException e) {
	throw new HigherLevelException(...);
}
예외 번역을 사용한 예로 AbstractSequentialList를 들어보자
public E get(int idex) {
   ListIterator<E> i = listIterator(index);
   try {
      return i.next();
   } catch (NoSuchElementException e) {
   	  throw new IndexOutOfBoundsException("인덱스: " + index);
   }
}

예외 연쇄 (Exception Chaining)
- 문제의 근본 원인인 저수준 예외를 고수준 예외에 실어 보내는 방식, 따라서 예외를 번역할 때, 저수준 예외가 
  디버깅에 도움이 된다면 예외 연쇄를 사용하자
  try { 
	...
} catch (LowerLevelException cause) {
    // 저수준 예외를 고수준 예외에 실어 보낸다.
	throw new HigherLevelException(cause);
}
class HigherLevelException extends Exception {
  	HigherLevelException(Throwable cause) {
  	   super(cause);
    }
}
* */


















public class Item_73 {
}
