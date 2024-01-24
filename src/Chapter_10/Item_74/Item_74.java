package Chapter_10.Item_74;



/* 메서드가 던지는 모든 예외를 문서화하라
- 메서드가 던지는 예외는, 그 메서드를 올바로 사용하는데 아주 중요한 정보이다.
  발생 가능한 예외를 문서로 남기지 않으면 그 클래스나 인터페이스를 효과적으로 사용하기 어렵거나 불가능 할 수도 있다.
  따라서, 예외를 문서화 하는 것은 매우 중요하다.
  
예외를 문서화 하는 방법
* 검사 예외
- 검사 예외는 항상 따로따로 선언하고, 각 예외가 발생하는 상황을 자바독 @thorws 태그를 사용하여 문서화 하면 된다.
/ **
 * 주석의 설명문
 * 
 * @throws java.io.FileNotFoundException 지정된 파일을 찾을 수 없습니다
 * /
단 한 클래스에 정의된 많은 메서드가 같은 이유로 같은 예외를 던진다면 각각의 메서드가 아닌 클래스에 설명을 추가하는
방법도 있다 EX) NullPointerException

주의 사항
- 메서드가 Exception or Thorwable 같은 공통 상위 클래스를 던진다고 선언하면 안된다. 단, main은 오직
  JVM 만이 호출하므로 Exception을 던지도록 선언해도 괜찬다.
----------------------------------------------------------------

* 비검사 예외
- 필수 요구사항은 아니지만, 비검사 예외도 문서화해두면 좋다. 잘 정비된 비검사 예외는 사실상 그 메서드를
  성공적으로 수행하기 위한 전제조건이 되기 때문이다. 문서화 방법은 검사 예외와 비슷하지만 주의 해야할점이 있다.
- 비검사 예외에서는 메서드 선언의 thorws 목록을 넣지 말아야 한다.

- 일반적으로 프로그래밍 오류를 뜻하므로, 자신이 일으킬 수 있는 오류들이 무엇인지 알려주면
  프로그래머는 해당 오류가 나지 않도록 코딩할 수 있다.
- 특히나, 인터페이스 메서드에서 매우 중요하다. 이 조건이 인터페이스의 일반 규약에 속하게 되어 그 인터페이스를
  구현한 모든 구현체가 일관되게 동작하도록 해주기 때문이다.

핵심 정리
메서드가 던질 가능성이 있는 모든 예외를 문서화하라. 
검사/비검사 예외, 추상/구체 메서드 모두에 해당한다. 
문서화에는 자바독의 @throws 태그를 사용하고, 검사 예외만 메서드 선언의 throws 문에 일일히 선언한다.
* */
public class Item_74 {
}
