package com.pattern.structural;

/*
 * 어댑터 패턴 특징 -> 인터페이스 변환
 * 사용시기
 *  1. 레거시 코드를 사용하고 싶지만 새로운 인터페이스가 레거시 코드와 호환되지 않을 때
 *  2. 이미 만든 것을 재사용하고자 하나 이 재사용 가능한 라이브러리를 수정 할 수 없을 떄
 *  3. 이미 만들어진 클래스를 새로운 인터페이스(API)에 맞게 개조할 때
 *  4. 소프트웨어의 구 버전과 신 버전을 공존시키고 싶을 때
 * 장점 ->
 *  1. 프로그램의 기본 비즈니스로직에서 인터페이스 또는 데이터 변환 코드를 분리 할 수 있기 떄문에 단일 책임 원칙(SRP)을 만족
 *  2. 기존 클래스 코드를 건들지 않고 클라이언트 인터페이스를 통해 어댑터와 작동하기 때문에 개방 폐쇄 원칙(OCP)를 만족
 *  3. 만일 추가로 필요한 메소드가 있다면 어댑터에 빠르게 만들 수 있음. 만약 버그가 발생해도 기존의 클래스에는 버그가 없기 때문에 Adapter 역할의 클래스를 중점적으로 조사하면 되고 프로그램 검사도 쉬워짐
 * 단점 ->
 *  1. 새로운 인터페이스와 어댑터 클래스 세트를 도입해야 하기 때문에 코드의 복잡성이 증가
 *  2. 떄로는 직접 서비스(AdapTee)클래스를 변경하는 것이 간단 할 수 있는 경우가 있기 때문에 신중하게 선택
 */
public class Adapter {
    public static void main(String[] args) {

        // 1. 어댑터 생성
        ObjectTarget adapter = new ObjectAdapter(new ObjectService());
        ClassTarget adapter2 = new ClassAdapter();

        // 2. 인터페이스의 스펙에 따라 메소드를 실행하면 기존 서비스의 메소드가 실행
        adapter.method(1);
        adapter2.method(2);
    }
}

/* 객체 어댑터(Object Adaptor)
 * 합성(Composition)된 멤버에게 위임을 이용한 어댑터 패턴 **추천**
 * 자기가 해야 할 일을 클래스 멤버 객체의 메소드에게 다시 시킴으로써 목적을 달성하는 것을 위임
 * 합성을 활용했기 때문에 런타임 중에 Adaptee(Service)가 결정되어 유연하다
 * 그러나 Adaptee(Service) 객체를 필드 변수로 저장해야 되기 떄문에 **공간 차지 비용이 듬**
 * 1. Adaptee(Service) : 어댑터 대상 객체. 기존 시스템 / 외부 시스템 / 써드파티 라이브러리
 * 2. Target(Client Interface) : Adapter 가 구현하는 인터페이스
 * 3. Adapter : Client 와 Adaptee(Service) 중간에서 호환성이 없는 둘을 연결시켜주는 역할을 담당
 *  3-1. Object Adaptor 방식에선 합성을 이용해 구성.
 *  3-2. Adaptee(Service)를 따로 클래스 멤버로 설정하고 위임을 통해 동작을 매치시킴.
 * 4. Client : 기존 시스템을 어댑터를 통해 이용하려는 쪽 Client Interface 를 통하여 Service 를 이용 할 수 있게 됨.
 */

// Adaptee : 클라이언트에서 사용하고 싶은 기존의 서비스 (하지만 호환이 안되서 바로 사용 불가능)
class ObjectService {

    void specificMethod(int specialData) { System.out.println("기존 서비스 기능 호출 + " + specialData); }
}

// Client Interface : 클라이언트가 접근해서 사용하게 할 수 있또록 호환 처리 해주는 어댑터
interface ObjectTarget {
    void method(int data);
}


// Adapter : Adaptee 서비스를 클라이언트에서 사용하게 할 수 있도록 호환 처리 해주는 어댑터
class ObjectAdapter implements ObjectTarget  {

    ObjectService adaptee; // composition 으로 Service 객체를 클래스 필드로

    // 어댑터가 인스턴스화 되면 호환시킬 기존 서비스를 설정
    ObjectAdapter(ObjectService adaptee) {
        this.adaptee = adaptee;
    }

    // 어댑터의 메소드가 호출되면, Adaptee 의 메소르르 호출하도록
    public void method(int data) {
        adaptee.specificMethod(data); // **위임**
    }
}


/* 클래스 어댑터(Class  Adaptor)
 * 클래스 상속을 이용한 어댑터 패턴
 * Adaptee(Service)를 상속했기 때문에 따로 객체 구현없이 바로 코드 재사용 가능
 * 상속은 대표적으로 기존에 구현된 코드를 재사용 하는 방식이지만 자바에서는 다중 상속이 불가 하기 때문에 **전반적으로 권장하지 않는 방법**
 * 1. Adaptee(Service) : 어댑터 대상 객체. 기존 시스템 / 외부 시스템 / 써드파티 라이브러리
 * 2. Target(Client Interface) : Adapter 가 구현하는 인터페이스
 * 3. Adapter : Client 와 Adaptee(Service) 중간에서 호환성이 없는 둘을 연결시켜주는 역할을 담당
 *  3-1. Class Adaptor 방식에선 상속을 이용해 구성
 *  3-2. Existing Class 와 Adaptee(Service)를 동시에 implements, extends 하여 구현
 * 4. Client : 기존 시스템을 어댑터를 통해 이용하려는 쪽 Client Interface 를 통하여 Service 를 이용 할 수 있게 됨.
 */

// Adaptee : 클라이언트에서 사용하고 싶은 기존의 서비스 (하지만 호환이 안되서 바로 사용 불가능)
class ClassService {
    void specificMethod(int specialData) { System.out.println("기존 서비스 기능 호출 + " + specialData); }
}

// Client Interface : 클라이언트가 접근해서 사용할 고수준의 어댑터 모듈
interface ClassTarget {
    void method(int data);
}

// Adapter : Adaptee 서비스를 클라이언트에서 사용하게 할 수 있도록 호환처리 해주는 어댑터
class ClassAdapter extends ClassService implements ClassTarget {

    // 어댑터의 메소드가 호출되면, 부모 클래스 Adaptee 의 메소드를 호출
    public void method(int data) {
        specificMethod(data);
    }
}

