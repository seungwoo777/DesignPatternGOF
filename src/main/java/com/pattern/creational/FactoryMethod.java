package com.pattern.creational;

/* 팩토리 메서드 패턴
 * 객체 생성 책임을 서브클래스에 위임 -> 객체 생성 과정 캡슐화
 *
 * 패턴 사용 시기
 *  1. 클래스 생성과 사용의 처리 로직을 분리하여 결합도를 낮출 때
 *  2. 코드가 동작해야 하는 객체의 유형과 종속성을 캡슐화를 통해 정보 은닉 처리 할 경우
 *  3. 라이브러리 혹은 프레임워크 사용자에게 구성 요소를 확장하는 방법을 제공하려는 경우
 *  4. 기존 객체를 재구성하는 대신 기존 객체를 재사용 하여 리소르를 절약하고자 하는 경우
 *
 * 패턴 장점
 *  1. 생성자와 구현객체의 강한 경합을 피할 수 있음.
 *  2. 팩토리 메서르를 통해 객체의 생성 후 공통으로 할일을 수행하도록 지정
 *  3. 캡슐화, 추상화를 통해 생성되는 객체의 구체적인 타입을 감출 수 있음.
 *  4. 단일 책임원칙 준수 : 객체 생성 코드를 한 곳 으로 이동하여 코드를 유지보수 하기 쉽게 할수 있으므로 원칙을 만족
 *  5. 개방/폐쇄 원칙 준수 : 기존 코드를 수정하지 않고 새로운 유형의 제품 인스턴스를 프로그램에 도입할 수 있어 원칙을 만족(확장성 있는 전체 프로젝트 구성이 가능)
 *  6. 생성에 대한 인터페이스 부분가 생성에 대한 구현 부분을 따로 나뉘었기 때문에 패키지 분리하여 개별로 여러 개발자가 협업을 통해 개발
 *
 * 패턴 단점
 *  1. 각 제품 구현체마다 팩토리 객체들을 모두 구현해주어야 하기 때문에, 구현체가 늘어날때 마다 팩토리 클래스가 증가하여 서브클래스 수가 폭발!
 *  2. 코드의 복잡성이 증가
 */
public class FactoryMethod {
    public static void main(String[] args) {

        // 공장 객체 생성
        AbstractBeerFactory[] factories = {
                new LagerFactory(),
                new AleFactory()
        };

        Beer lager = factories[0].createOperation();
        Beer ale = factories[1].createOperation();
    }
}

// 맥주 추상화
interface Beer {
    void setting();
}

// 맥주 구현체
class Lager implements Beer { public void setting() {} }
class Ale implements Beer { public void setting() {} }

// 공장 객체 추상화
abstract class AbstractBeerFactory {

    // 객체 생성 전처리 후처리 메소드 (final 로 오버라이딩 방지, 템플릿 화)
    final Beer createOperation() {
        Beer beer = createBeer();
        beer.setting();
        return beer;
    }


    // 펙토리 메소드 : 구체적인 객체 생성 종류는 각 서브 클래스에 위임
    // protected 이기 떄문에 외부에 노출이 안됨
    abstract protected Beer createBeer();
}

class LagerFactory extends AbstractBeerFactory {
    @Override
    public Beer createBeer() {
        return new Lager();
    }
}


class AleFactory extends AbstractBeerFactory {
    @Override
    public Beer createBeer() {
        return new Ale();
    }
}