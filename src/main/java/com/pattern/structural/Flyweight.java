package com.pattern.structural;

import lombok.AllArgsConstructor;

import java.util.HashMap;
import java.util.Map;

/* 플라이웨이트 패턴(공유 객체를 활용해 메모리 절약)
 * 재사용 가능한 객체 인스턴스를 공유시키 메모리 사용량을 최소화하는 구조 패턴
 * 간단히 요약하면 캐시(Cache) 개념을 코드로 패턴화 한것으로 보면 되는데
 * 자주 변화는 속성과 변하 지않는 속성을 분리하고 변하지 않는 속성을 캐시하여 재사용해 메모리 사용을 줄이는 방식
 * 그래서 동일하거나 유사한 객체들 사이에 가능한 많은 데이터를 서로 공유하여 사용하도록 하여 최적화를 노리는 경량 패턴이라고도 불림.
 */
public class Flyweight {

    // Client
    public static void main(String[] args) {
        FlyweightFactory flyweightFactory = new FlyweightFactory();

        // 공유 Flyweight 객체
        iFlyweight flyweight1 = flyweightFactory.getFlyweight("A");
        iFlyweight flyweight2 = flyweightFactory.getFlyweight("B");
        iFlyweight flyweight3 = flyweightFactory.getFlyweight("A"); // 이미 생성된 객체 재사용

        flyweight1.render("Position 1");
        flyweight2.render("Position 2");
        flyweight3.render("Position 3");


        // 비공유 Flyweight 객체
        iFlyweight unsharedFlyweight = new UnsharedConcreteFlyweight("unique");
        unsharedFlyweight.render("Position 4");

    }
}

/* 플라이웨이트 패턴 구조
 *  1. Flyweight : 경량 객체를 묶는 인터페이스
 *  2. ConcreteFlyWeight : 공유 가능하여 재사용 되는 객체 (intrinsic state)
 *  3. UnsharedConcreteFlyweight : 공유 불가능한 객체 (extrinsic state)
 *  4. FlyweightFactory : 경량 객체를 만드는 공장역할과 캐시 역할을 겸비하는 Flyweight 객체 관리 클래스
 *      4-1 getFlyweight() 메소드는 팩토리 메소드 역할을 한다고 보면 됨
 *      4-2 만일 객체가 메모리에 존재하면 그대로 가져와 반환하고 없다면 새로 생성해 반환
 *  5. Client : 클라이언트는 FlyweightFactory 를 통해 Flyweight 타입의 객체를 얻어 사용한다.
 */

// 공통 인터페이스
interface iFlyweight {
    void render(String extrinsicState); // 외부 상태 (위치, 색상 등) 전달
}

//  공유 가능한 객체
@AllArgsConstructor
class ConcreteFlyweight implements iFlyweight {
    private final String intrinsicState;


    public void render(String extrinsicState) {
        System.out.println("렌더링 플라이웨이트 intrinsicState -> " + intrinsicState + " extrinsicState ->" + extrinsicState);
    }
}

// 공유 되지 않는 객체
@AllArgsConstructor
class UnsharedConcreteFlyweight implements iFlyweight {
    private final String uniqueState;

    public void render(String extrinsicState) {
        System.out.println("렌더링 비공유 플라이 웨이트" + uniqueState + " extrinsicState ->" + extrinsicState);
    }
}

// 경량 객체를 관리하는 팩토리
class FlyweightFactory {
    private final Map<String, iFlyweight> flyweight = new HashMap<>();

    public iFlyweight getFlyweight(String key) {
        if(!flyweight.containsKey(key)) {
            System.out.println("새로운 플라이웨이트 키 생성 -> " + key);
            flyweight.put(key , new ConcreteFlyweight(key));
        } else {
            System.out.println("재사용 존재하는 플라이웨이트 키 -> " + key);
        }
        return flyweight.get(key);
    }
}

/*
 * 특징
 *  사용시기
 *  1. 어플리케이션에 의해 생성되는 객체의 수가 많아 저장 비용이 높아 질 때
 *  2. 생성된 객체가 오래도록 메모리에 상주하며 사용되는 횟수가 많을 때
 *  3. 공통적인 인스턴스를 많이 생성하는 로직이 포함된 경우
 *  4. 임베디드와 같이 메모리를 최소한 사용해야 하는 경우에 활용
 * 장점
 *  1. 애플리케이션에서 사용하는 메모리를 줄일 수 있음
 *  2. 프로그램 속도를 개선 할 수 있다.
 *      2-1 new 로 인스턴스화를 하면 데이터가 생성되고 메모리에 적재 되는 미령의 시간이 걸림
 *      2-2 객체를 공유하면 인스턴스를 가져오기만 하면 되기 때문에 메모리 뿐만 아니라 속도도 향상 시킬 수 있게 됨
 * 단점
 *  1. 코드 복잡도가 증가
 */