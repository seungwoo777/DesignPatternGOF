package com.pattern.structural;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

/* 프록시 패턴 (접근 제어 및 추가 기능 제공)
 * 클라이언트가 대상 객체를 직접 쓰는게 아니라 중간에 프록시를 거쳐 쓰는 코드 패턴
 * 대상 클래스가 민감한 정보를 가지고 있거나 인스턴스화 하기에 무겁거나 추가 기능을 가미하고 싶은데 원복 객체를 수정할 수 없는 상황일 때 극복
 * 대체적으로 정리 ->
 *  1. 보안 : 프록시는 클라이언트가 작업을 수행할 수 있는 권한이 있는 지 확인하고 검사 결과가 긍정적인 경우에만 요청을 대상으로 전달
 *  2. 캐싱 : 프록시가 내부 캐시를 유지하여 데이터가 캐시에 아직 존재하지 않는 경우에만 대상에서 작업이 실행
 *  3. 데이터 유효성 검사(Data validation) : 프록시가 입력을 대상으로 전달하기 전에 유효성 검사
 *  4. 지연 초기화(Lazy initialization) : 대상의 생성 비용이 비싸다면 프록시는 그것을 필요 할 때 까지 연기 가능
 *  5. 로깅 : 프록시는 메소드 호출과 상대 매개변수를 인터셉트하고 이를 기록
 *  6. 원격 객체(Remote Objects) : 프록시는 원격 위치에 있는 객체를 가져와 로컬처럼 보이게 할 수 있음.
 *
 * 특징
 * 사용시기
 *  1. 접근을 제어하거가 기능을 추가하고 싶은데 기존의 특정 객체를 수정할 수 없는 상황일때
 *  2. 초기화 지연, 접근 제어, 로깅, 캐싱 등, 기존 객체 동작에 수정 없이 가미하고 싶을 때
 * 장점
 *  1. 개방 폐쇄 원칙(OCP) 준수 -> 기존 대상 객체의 코드를 변경하지 않고 새로운 기능을 추가할 수 있다.
 *  2. 단일 책임 원칙(SRP) 준수 -> 대상 객체는 자신의 기능에만 집중하고 그 이외부가 기능을 제공하는 역할을 프록시 객체에 위임하여 다중 책임 회피
 *  3. 원래 하려던 기능을 수행하며 그외의 부가적인 작업(로깅, 인증, 네트워크 통신 등)을 수행하는데 유용
 *  4. 클라이언트는 객체를 신경쓰지 않고, 서비스 객체를 제어하거나 생명 주기를 관리.
 *  5. 사용자 입장에서는 프록시 객체나 실제 객체나 사용법은 유사하므로 사용성에 문제 되지 않음.
 * 단점
 *  1. 많은 프록시 클래스를 도입해야 하므로 코드의 복잡도가 증가
 *      1-1 를들어 여러 클래스에 로깅 기능을 가미 시키고 싶다면, 동일한 코드를 적용함에도 각각의 클래스에 해당되는 프록시 클래스를 만들어서 적용해야 되기 때문에 코드량이 많아지고 중복이 발생
 *      1-2 자바에서는 리플렉션에서 제공하는 동적 프록시(Dynamic Proxy) 기법을 이용해서 해결
 *  2. 프록시 클래스 자체에 들어가는 자원이 많다면 서비스로부터의 응답이 늦어질 수 있음
 */
public class Proxy {
    public static void main(String[] args) {
        ISubject normalProxy = new NormalProxy(new RealSubject());
        normalProxy.action();

        ISubject virtualProxy = new VirtualProxy();
        virtualProxy.action();

        ISubject protectionProxy = new ProtectionProxy(new RealSubject(), false);
        protectionProxy.action();

        ISubject loggingProxy = new LoggingProxy(new RealSubject());
        loggingProxy.action();
    }
}

/* 프록시 패턴 구조
 *  1. Subject : Proxy 와 RealSubject 를 하나로 묶는 인터페이스(다형성)
 *      1-1 대상 객체와 프록시 역할을 동일하게 하는 추상메소드를 정의
 *      1-2 인터페이스가 있기 때문에 클라이언트는 Proxy 역할과 RealSubject 역할의 차이를 의식할 필요 없음
 *  2. RealSubject : 원본대상객체
 *  3. Proxy : 대상 객체(RealSubject)를 중계할 대리자 역할
 *      3-1 프록시는 대상 객체를 합성(Composition)
 *      3-2 프록시는 대상 객체와 같은 이름의 메소드를 호출 하여 별도의 로직을 수행 할 수 있음(인터페이스 구현 메소드)
 *      3-3 프록시는 흐름제어만 할 뿐 결과값을 조작하거나 변경 시키면 안 됨
 *  4. Client : Subject 인터페이스를 이용하여 프록시 객체를 생성해 이용
 *      4-1 클아이언트는 프록시를 중간에 두고 프록시를 통해 RealSubject 와 데이터를 주고 받음.
 */


interface ISubject {
    void action();
}

class RealSubject implements ISubject {

    public void action() {
        System.out.println("RealSubject -> 원본 객체 call");
    }
}

// 기본형 프록시(Normal Proxy)
class NormalProxy implements ISubject {
    // 대상 객체를 composition
    private final RealSubject realSubject;

    NormalProxy(RealSubject realSubject) {
        this.realSubject = realSubject;
    }

    public void action() {
        realSubject.action(); // 위임
        System.out.println("NormalProxy -> 프록시 객체 call");
    }
}

/* 가상 프록시(Virtual Proxy)
 *  1. 지연 초기화(Lazy initialization) 방식
 *  2. 가끔 필요하지만 **항상 메모리에 적재**되어 있는 무거운 서비스 객체가 있는 경우
 *  3. 이 구현은 실제 객체의 생성에 많은 자원이 소모 되지만 사용 빈도는 낮을 때 쓰는 방식
 *  4. 서비스가 시작될 때 객체를 생성하는 대신에 **객체 초기화가 실제로 필요한 시점에 초기화 될수 있도록 지연** 할 수 있음
 */
@NoArgsConstructor
class VirtualProxy implements ISubject {
    // 대상 객체를 composition
    private RealSubject realSubject;

    public void action() {
        // 프록시 객체는 실제 요청 -> action(메소드 호출)이 들어 왔을 때 실제 객체 생성
        if(realSubject == null)
            realSubject = new RealSubject();

        realSubject.action(); // 위임

        System.out.println("VirtualProxy -> 프록시 객체 call");
    }
}

/* 보호 프록시(Protection Proxy)
 *  1. 프록시가 대상 객체에 대한 자원으로의 엑세스 제어(접근 권한)
 *  2. 특정 클라이언트만 서비스 객체를 사용할 수 있도록 하는 경우
 *  3. 프록시 객체를 통해 클라이언트의 자격 증명이 기준과 일치하는 경우에만 서비스 객체에 요청을 전달할 수 있게 함
 */
@AllArgsConstructor
class ProtectionProxy implements ISubject {
    // 대상 객체를 composition
    private RealSubject realSubject;
    // 접근 권한
    boolean access;

    public void action() {
        if(access) {
            realSubject.action(); // 위임

            System.out.println("VirtualProxy -> 프록시 객체 call");
        }
    }
}

/* 로깅 프록시(Logging Proxy)
 *  1. 대상 객체에 대한 로깅을 추가하려는 경우
 *  2. 프록시는 서비스 메소드를 전달하기 전에 로깅을 하는 기능을 추가하여 재정의
 */
@AllArgsConstructor
class LoggingProxy implements ISubject {
    // 대상 객체를 composition
    private RealSubject realSubject;

    public void action() {
        System.out.println("Logging Start");

        realSubject.action();
        System.out.println("LoggingProxy -> 프록시 객체 call");

        System.out.println("Logging End");
    }
}

/* 원격 프록시(Remote Proxy)
 *  1. 프록시 클래스는 로컬에 있고 대상 객체는 원격 서버에 존재하는 경우
 *  2. 프록시 객체는 네트워크를 통해 클라이언트의 요청을 전달하여 네트워크와 관련된 불필요한 작업들을 처리하고 결과값만 반환
 *  3. 클라이언트 입장에서 프록시를 통해 객체를 이용하는 것이니 원격이든 로컬이든 신경 쓸 필요가 ㅇ벗으며, 프록시는 진짜 객체와 통신을 대리
 *          Local Heap                                Remote Heap
 * *-------------------------------*    |    *--------------------------*
 * | MachineMonitor -> Proxy=Stub--|----|----|->Skeleton -> Machine     |
 * |   (클라이언트)                   |    |    |            (원격대상객체)   |
 * *-------------------------------*    |    *--------------------------*
 * 참고로 프록시를 Stub 이라고 부르며 프록시로부터 전달된 명령을 이해하고 적합한 메소드를 호출해주는 역할을 보조객체를 Skeleton 이라 함.
 */


/* 캐싱 프록시(Caching Proxy)
 *  1. 데이터가 큰 경우 캐싱하여 재사용을 유도
 *  2. 클라이언트 요청의 결과를 캐시하고 이 캐시의 수명 주기를 관리
 *  3. HTTP Proxy
 *      3-1 웹서버와 브라우저 사이에서 웹페이지의 캐싱을 실행하는 소프트웨어 웹 브라우저가 어떤 웹페이지를 표시할 때 직접 웹 서버에서 그 페이지를
 *          가져오는 것이 아니고 HTTP Proxy 가 캐시하여 어떤 페이지를 대신하여 취득함.
 *          만일 최신 정보가 필요하거나 페이지의 유효기간이 지났을 때 웹서버에 웹 페이지를 가지러 감.
 *          이를 패턴으로 따져보면
 *          -> 웹 브라우저가 Client 역할
 *          -> HTTP Proxy 가 Proxy 역할
 *          -> 웹 서버가 RealSubject 역할
 *
 *
 *
 *
 *     1. --> www.naver.com 으로 접속       2. --> www.naver.com 이 캐시에 없으면 Web Server Read
 *    *--------*   1   *---------------------------------*   2  *---------------*
 *    |        |  -->  | 3                               |  --> |               |
 *    | Client |       | HTTP 서버 기능 | HTTP 클라이언트 기능 |      |  Web Server   |
 *    |        |  <--  |             캐시                 |  <-- | www.naver.com |
 *    *--------*   4   *---------------------------------*     *---------------*
 *     4. 결과를 반환                        3. www.naver.com 의 내용을 캐시에 보관
 */



