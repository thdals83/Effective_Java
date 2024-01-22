package Chapter_9.Item_67;



/* 최적화는 신중히 하라
- 최적화를 섣불리 진행하면 해로운 결과로 이어지기 쉽다.
- 성능 때문에 견고한 구조를 희생하지 말자. 빠른 프로그램보단 좋은 프로그램을 작성하라
그러니 프로그램이 우선 좋은 아키텍처를 가질 수 있도록 한 이후 최적화를 고려하라

좋은 설계
성능을 제한하는 설계를 피하라
- 구현상의 문제가 아닌, 아키텍처 결함이 성능을 제한하는 상황이라면, 완성된 기본 틀을 변경해야 하기 때문에 
유지보수나 개선하기 어려운 시스템이 만들어진다. 성능을 염두해두고 설계하자

API 설계 시 성능에 주는 영향을 고려하라
1. public 타입을 가변으로 만들어 내부 데이터를 변경할 수 있게 만드는 것은, 불필요한 방어적 복사를 유발한다.
2. 컴포지션으로 해결할 수 있음에도, 상속 방식으로 설계한 public 클래스는 상위 클래스에 영원히 종속되며 그 성능 제약도 물려 받는다.
3. 인터페이스가 있는데 구현 타입을 사용하는 것은 특정 구현체에 종속되게 하여, 나중에 더 빠른 구현체가 나와도 사용 못한다.
* */

import java.awt.*;
import java.awt.geom.Dimension2D;
import java.awt.image.ImageObserver;
import java.io.Serializable;

class EX_1 {
	//1번의 예시로 java.awt.Component 클래스의 getSize()가 있다.
	abstract class Component implements ImageObserver, MenuContainer, Serializable {
		public Dimension getSize() {
			return size();
		}

		@Deprecated
		public Dimension size() {
			return new Dimension(0, 0); // 방어적 복사 수행 
		}
	}
	
	/* 
	아래와 같이 Dimension을 가변으로 설계해, getSize를 호출하는 모든 곳에서
	Dimesion 인스턴스를 새로 생성하고 있다. 요즘의 VM은 
	* */
	class Dimesion extends Dimension2D implements Serializable {
		public double width;
		public double height;
		@Override
		public double getWidth() {return 0;}
		@Override
		public double getHeight() {return 0;}
		@Override
		public void setSize(double width, double height) {
			this.width = width;
			this.height = height;
		}
	}
}
/* 성능 측정
jmh 같은 프로파일링 도구는 최적화 노력을 어디에 집중해야 할지 찾는데 도움을 준다.

가장 먼저 살펴볼 것은, 개별 메서드의 소비 시간과 호출 횟수 같은 런타임 정보의 제공을 이용해
알고리즘을 알맞게 골랐는지 확인하는 것이다. 알고리즘을 잘못 골랐다면 다른 저수준 최적화는 아무런 소용이 없다.
* */


public class Item_68 {
}
