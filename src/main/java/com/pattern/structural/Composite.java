package com.pattern.structural;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

/* 복합체 패턴(Composite Pattern) - 트리 구조 처리
 * 쉽계 얘기해서 OS 디렉토리 파일 시스템 구조라고 보면됨.
 * -> 폴더는 Composite(자식이 있음) / 파일은 Leaf(자식이 없음)
 * 복합체 패턴은 폴더와 파일을 동일한 타입으로 취급하여 구현을 단순화 시키는 것이 목적
 * 폴더 안에는 파일뿐만 아니라 서브 폴더도 올 수 있고 또 올 수 있고 또 올수 있고... 이런식에 계층 형 구조를 구현 하다보면 자칫 복잡해 질 수도 있는 복합객체를 재귀 동작을 통해 하위객체들에게 작업을 위임
 * 그러면 복합객체와 단일 객체를 대상으로 똑같은 작업을 적용할 수 있어 / 복합객체를 구분 할 필요가 없어짐.
 */
public class Composite {
    // Client
    public static void main(String[] args) {
        // 1. 최상위 복합체 생성
        CompositeC compositeC1 = new CompositeC();

        // 2, 최상위 복합체에 저장할 Leaf 와 또 다른 서브 복합체 생성
        Leaf leaf1 = new Leaf();
        CompositeC compositeC2 = new CompositeC();

        // 3. 최상위 복합체에 개체들을 등록
        compositeC1.add(leaf1);
        compositeC2.add(compositeC2);

        // 4. 서브 복합체에 저장할 Leaf 생성
        Leaf leaf2 = new Leaf();
        Leaf leaf3 = new Leaf();
        Leaf leaf4 = new Leaf();

        // 5. 서브 복합체에 개체들을 등록
        compositeC2.add(leaf2);
        compositeC2.add(leaf3);
        compositeC2.add(leaf4);

        // 6. 최상위 복합체에 모든 자식 노드들을 출력
        compositeC1.operation();
    }
}

/* Composite 패턴 구조
 * Client -> Component <- CompositeC(복합 객체) - Leaf(단일 객체)
 *  1. Component : Leaf 와 CompositeC 를 묶는 공통적인 상위 인터페이스
 *  2. CompositeC : 복합 객체로서 Leaf 역할이나 CompositeC 역할을 넣어 관리하는 역할
 *      2-1 Component 구현체들을 내부 리스트로 관리한다.
 *      2-2 add 와 remove 메소드는 내부 리스트에 단일 / 복합 객체를 저장
 *      2-3 Component 인터페이스의 구현 메서드인 operation 은 복합 객체에서 호출되면 재귀 하여, 추가 단일 객체를 저장한 하위 복합 객체를 순회함.
 * 3. Leaf : 단일 객체로서 단순하게 내용물을 표시하는 역할을 함.
 *      3-1 Component 인터페이스의 구현 메서드인 operation 은 단일 객체에서 호출되면 적절한 값만 반환
 * 4. Client : 클라이언트는 Component 를 참조하여 단일 / 복합 객체를 하나의 객체로서 다룸.
 * 복합체 패턴의 핵심은 CompositeC 와 Leaf 가 동시에 구현하는 operation() 인터페이스 추상메소드를 정의 하고 CompositeC 객체의 operation() 메소드는 자기 자신을 호출하는 재귀형태로 구현
 * 폴더 안에 폴더 넣고 폴더에 또 넣고... 파일을 넣는 트리 구조를 생각해보면 재귀적으로 반복되는 형식이 나타나기 떄문 그래서 단일체와 복합체를 동일한 개체로 취급하여 처리하기위해 재귀 함수 원리를 이용
 */

interface Component {
    void operation();
}

class Leaf implements Component {

    public void operation() {
        System.out.println(this + " call");
    }
}

@Getter
class CompositeC implements Component {

    // Leaf 와 Composite 객체 모두 저장하여 관리하는 내부 리스트
    List<Component> componentList = new ArrayList<>();

    public void add(Component component) {
        componentList.add(component);
    }

    public void remove(Component component) {
        componentList.remove(component);
    }

    public void operation() {
        System.out.println(this + " call");

        // 내부 리스트를 순회하여 단일 Leaf 이면 값을 출력하고
        // 또 다른 서브 복합 객체이면 다시 그 내부를 순회하는 재귀 함수 동작
        componentList.forEach(Component::operation);
    }

}

/* Composite 패턴 특징
 * 패턴 사용시기
 *  1. 데이터를 다룰 때 계층적 트리 표현을 다루어야 할 때
 *  2. 복잡하고 난해한 단일 / 복합 객체를 관계를 간편히 단순화하여 균일하게 처리하고 싶을 때
 *
 * 장점
 *  1. 단일체와 복합체를 동일하게 여기기 때문에 묶어서 연산하거나 관리할 때 편리
 *  2. 다형성 재귀를 통해 복잡한 트리 구조를 보다 편리하게 구성
 *  3. 수평적 수직적 모든 방향으로 객체를 확장 가능
 *  4. 새로운 Leaf 클래스를 추가하더라도 클라이언트는 추상화된 인터페이스 만을 바라보기 떄문에 개방 폐쇄 원칙(OCP)를 준수
 *
 * 단점
 *  1. 재귀 호출 특징 상 트리의 깊이가 깊어질 수록 디버깅에 어려움이 생김
 *  2. 설계가 지나치게 범용성을 갖기 떄문에 새로운 요소를 추가할 때 복합 객체에서 구성 요소에 제약을 갖기 힘듬
 *  3. 예를들어 계층형 구조에서 leaf 객체와 composite 객체들을 모두 동일한 인터페이스로 다루어야 하는데 이 공통 인터페이스 설계가 까다로울 수 있음.
 *      3-1 복합 객체가 가지는 부분 객체의 종류를 제한할 필요가 있을 때
 *      3-2 수평적 방향으로만 확장이 가능하도록 Leaf 를 제한하는  CompositeC 를 만들 때
 */