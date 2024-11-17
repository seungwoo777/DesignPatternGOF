package com.pattern.creational;

/* 추상팩토리 패턴
 * 관련된 객체군을 생성 -> 플랫폼 독립 Graphical User Interface
 * 장점
 *  1. 객체를 생성하는 코드를 분리해 클라이언트 코드와 결합도를 남춤
 *  2. 제품 군을 쉽게 대체 할수 있음.
 *  3. 단일 책임 원칙 준수
 *  4. 개방 / 폐쇄 원칙 준수
 * 단점
 *  1. 각 구현체마다 팩토리 객체들을 모두 구현해 주어야 하기 때문에 객체가 늘어날때 마다 클래스가 증가하여 코드의 복잡성 증가(팩토리 패턴의 공통적인 문제점)
 *  2. 기존 추상 팩토리의 세부사항이 변경되면 모든 팩토리에 대한 수정 필요 이는 추상 팩토리와 모든 서브클래스의 수정을 가져옴
 *  3. 새로운 종류의 제품을 지원하는 것이 어려움. 새로운 제품이 추가되면 팩토리 구현 로직 자체를 변경
 */
public class AbstractFactory {

    public static void main(String[] args) {
        Bar bar = null;

        bar = new DistributeFactory1(); // 1공장 가동

        // 1공장에서 소주를 생성(클라이언트는 구체적인 구현 모름 인터페이스에 의존)
        KoreaAlcoholicDrink soju = bar.createKoreaAlcoholicDrink();
        System.out.println(soju.getClass().getName());

        bar = new DistributeFactory2(); // 2공장 가동

        // 2공장에서 맥주를 생성(클라이언트는 구체적인 구현 모름 인터페이스에 의존)
        KoreaAlcoholicDrink cass = bar.createKoreaAlcoholicDrink();
        System.out.println(cass.getClass().getName());
    }
}

interface Bar {
    KoreaAlcoholicDrink createKoreaAlcoholicDrink();
    JapanAlcoholicDrink createJapanAlcoholicDrink();
}

// 소주랑 사케 생상하는 유통 공장 1
class DistributeFactory1 implements Bar {
    public KoreaAlcoholicDrink createKoreaAlcoholicDrink() {
        return new Soju();
    }

    public JapanAlcoholicDrink createJapanAlcoholicDrink() {
        return new Sake();
    }
}


// 맥주랑 소츄 생상하는 유통 공장 2
class DistributeFactory2 implements Bar {

    public KoreaAlcoholicDrink createKoreaAlcoholicDrink() {
        return new Cass();
    }

    public JapanAlcoholicDrink createJapanAlcoholicDrink() {
        return new Shochu();
    }
}

interface KoreaAlcoholicDrink {} // 한국 술

class Soju implements KoreaAlcoholicDrink {} // 소주

class Cass implements KoreaAlcoholicDrink {} // 맥주

interface JapanAlcoholicDrink {} // 일본 술

class Sake implements JapanAlcoholicDrink {} // 사케

class Shochu implements JapanAlcoholicDrink {} // 소츄

