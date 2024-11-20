package com.pattern.creational;

import lombok.Getter;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/*
 * 객체를 하나로 제한 -> 설정 관리, 로깅, 캐싱
 * 싱글톤 패턴 클래스를 만들기 위해서는 Bill Pugh Solution | Enum 으로 만들어 사용하자
 * LazyHolder -> 성능이 중요시 되는 환경
 * Enum -> 직렬화, 안정성 중요시 되는 환경
 * **싱글톤 문제점**
 * 1. 모듈간 의존성이 높아짐
 * -> 대부분의 싱글톤을 이용하는 경우 인터페이스가 아닌 클래스의 객체를 미리 생성하고 정적메소드를 사용하기 떄문에 클래스 사이에 강한 의존성과 높은 결합도가 생김
 * 2. S.O.L.I.D -> 단일 책임 원칙(SRP) / 개방 폐쇄 원칙(OCP) / 리스코프 치환 원칙(LSP) / 인터페이스 분리 원칙(ISP) / 의존 역전 원칙(DIP)
 * -> SOLID 원칙에 위배되는 사례가 많음 싱글톤 인스턴스 자체가 하나만 생성하기 때문에 여러가지 책임을 지니게 되는 경우가 많아 단일 책임 원칙 위반
 * -> 싱글톤 인스턴스가 혼자 너무 많은 일을 하거나 많은 데이터를 공유 시키면 다른 클래스들 간의 결합도를 높아지게 되어 개방 폐쇄 원칙 위반
 * -> 의존 관계상 클라이언트가 인터페이스와 같은 추상화가 아닌 구체 클래스에 의존하게 되어 의존 역전 원칙 위반
 * -> 싱글톤 인스턴스를 너무 많은 곳에서 사용할 경우 잘못된 디자인 형태가 될 수 있음. 그래서 싱글톤 패턴을 객체 지향 프로그래밍의 안티패턴이라고 불리기도 함.
 * 3. TDD 단위 테스트에 애로사항이 있음.
 * -> 싱글톤 클래스를 사용하는 모듈을 테스트 하기 어려움
 * -> 단위 테스트를 할 때 단위 테스트는 테스트가 서로 독립적이어야 하며 테스트를 어떤 순서로든 실행 할 수 있어야 하는데
 * -> 싱글톤 인스턴스는 자원을 공유하고 있기 때문에 테스트가 결함없이 수행되려면 매번 인스턴스의 상태를 초기화 시켜주어야 함.
 * -> 그러지 않으면 어플리케이션 전역에서 상태를 공유하기 때문에 테스트가 온전하게 수행되지 못할 수 도 있다.
 * -> 많은 테스트 프레임워크가 Mock 객체를 생성 할 때 상속에 의존하기 때문에 싱글톤의 클라이언트 코드를 테스트하기 어려움.
 */
public class Singleton {
    public static void main(String[] args) {
        // 싱글톤 객체를 담을 배열
        LazyInitialization[] lazyInitializations = new LazyInitialization[10];

        // 스레드 풀 생성
        ExecutorService executorService = Executors.newCachedThreadPool();

        // 10개 스레드 동시에 인스턴스 생성
        for(int i = 0; i < 10; i++) {
            final int num = i;
            executorService.submit(() -> {
               lazyInitializations[num] = LazyInitialization.getInstance();
            });
        }

        // 종료
        executorService.shutdown();

        // 싱글톤 객체 주소를 출력 해볼까
        for(LazyInitialization lazyInitialization : lazyInitializations) {
            System.out.println(lazyInitialization.toString());
        }

        /* output (WANING!!!!!!!!) 싱글톤 클래스인데 객체가 두개 만들어졌다
         * com.pattern.creational.LazyInitialization@1b6d3586
         * com.pattern.creational.LazyInitialization@1b6d3586
         * com.pattern.creational.LazyInitialization@4554617c -- 요기
         * com.pattern.creational.LazyInitialization@1b6d3586
         * com.pattern.creational.LazyInitialization@1b6d3586
         * com.pattern.creational.LazyInitialization@1b6d3586
         * com.pattern.creational.LazyInitialization@1b6d3586
         * com.pattern.creational.LazyInitialization@1b6d3586
         * com.pattern.creational.LazyInitialization@1b6d3586
         * com.pattern.creational.LazyInitialization@1b6d3586
         */

        // 싱글톤 객체를 담을 배열
        ThreadSafeInitialization[] threadSafeInitializations = new ThreadSafeInitialization[10];

        // 스레드 풀 생성
        ExecutorService executorService1 = Executors.newCachedThreadPool();

        // 10개 스레드 동시에 인스턴스 생성
        for(int i = 0; i < 10; i++) {
            final int num = i;
            executorService1.submit(() -> {
                threadSafeInitializations[num] = ThreadSafeInitialization.getInstance();
            });
        }

        // 종료
        executorService1.shutdown();

        // 싱글톤 객체 주소를 출력 해볼까
        for(ThreadSafeInitialization threadSafeInitialization : threadSafeInitializations) {
            System.out.println(threadSafeInitialization.toString());
        }
        /* output ThreadSafe initialization synchronized 키워드를 통해 하나하나씩 접근 주소가 같다
         * 하지만 여러개의 모듈들이 매번 객체를 가져올 때 synchronized 메서드를 매번 호출하여 동기화 처리 작업에 overhead 가 발생(WANING!!!!!!!!) 성능 하락 발생
         * com.pattern.creational.LazyInitialization@7d4991ad
         * com.pattern.creational.LazyInitialization@7d4991ad
         * com.pattern.creational.LazyInitialization@7d4991ad
         * com.pattern.creational.LazyInitialization@7d4991ad
         * com.pattern.creational.LazyInitialization@7d4991ad
         * com.pattern.creational.LazyInitialization@7d4991ad
         * com.pattern.creational.LazyInitialization@7d4991ad
         * com.pattern.creational.LazyInitialization@7d4991ad
         * com.pattern.creational.LazyInitialization@7d4991ad
         * com.pattern.creational.LazyInitialization@7d4991ad
         */
    }
}

/* 싱글톤 패턴 코드 기법 -> 1번 부터 조금씩 단점을 보완 하는 식으로 흘러감.
 *  1. Eager Initialization
 *  2. Static block initialization
 *  3. Lazy initialization
 *  4. Thread safe initialization
 *  5. Double-Checked Locking
 *  6. Bill Pugh Solution
 *  7. Enum 이용
 */


/* 1. Eager Initialization
 * 한번만 미리 만들어 두는 가장 직관적이면서 심플한 기법
 * static final 이라 멀티 쓰레드 환경에서도 안전함
 * 그러나 static 멤버는 당장 객체를 사용하지 않더라도 메모리에 적재하기 때문에 만일 리소스가 큰 객체일 경우 공간 자원낭비가 발생
 * 예외 처리를 할 수 없음.
 * 싱글톤을 적용한 객체가 그리 크지 않은 객체라면 이 기법으로 적용해도 무리는 없음.
 */
class EagerInitialization {

    // 싱글톤 클래스 객체를 담을 인스턴스 변수
    private static final EagerInitialization INSTANCE = new EagerInitialization();

    // 생성자를 private 으로 선언 (외부에서 new 사용 X)
    private EagerInitialization() {}

    public static EagerInitialization getInstance() {
        return INSTANCE;
    }
}

/* 2. static block initialization
 * static block 을 이용해 예외를 잡을 수 있음
 * 그러나 여전히 static 의 특성으로 사용하지도 않는데도 공간을 차지
 * static block : 클래스가 로딩되고 클래스 변수가 준비된 후 자동으로 실행되는 블록
 */
class StaticBlockInitialization {

    // 싱글톤 클래스 객체를 담을 인스턴스 변수
    private static StaticBlockInitialization instance;

    // static 블록을 이용해 예외 처리
    static {
        try {
            instance = new StaticBlockInitialization();
        } catch (Exception e) {
            throw new RuntimeException("싱글톤 객체 생성 오류");
        }
    }

    public static StaticBlockInitialization getInstance() {
        return instance;
    }
}

/* 3. Lazy initialization
 * 객체 생성에 대한 관리를 내부적으로 처리
 * 메서드를 호출했을 때 인스턴스 변수의 null 유무에 따라 초기화 하거나 있는 걸 반환하는 기법
 * 위의 미사용 고정 메모리 차지의 한계점을 극복
 * 그러나 Thread Safe 하지 않는 **치명적인 단점**을 가지고 있음
 */
class LazyInitialization {
    private static LazyInitialization instance;

    // 생성자를 private 으로 선언 (외부에서 new 사용 X)
    private LazyInitialization() {}

    // 외부에서 정적 메서드를 호출하면 그제서야 초기화 진행 (lazy)
    public static LazyInitialization getInstance() {
        if(instance == null)
            instance = new LazyInitialization();

        return instance;
    }


}

/* 4. Thread Safe initialization
 * synchronized 동기화를 통해 메서드에 쓰레드들을 하나하나씩 접근하도록 설정
 * 하지만 여러개의 모듈들이 매번 객체를 가져올 때 synchronized 메서드를 매번 호출하여 동기화처리 작업에 overhead 발생 성능 하락
 * synchronized 키워드는 멀티 쓰레드 환경에서 두개 이상의 쓰레드가 하나의 변수에 동시에 접근을 할때 Race condition(경쟁 상태)이 발생하지 않도록 한다.
 * 쓰레드가 해당 메서드를 실행하는 동안 다른 쓰레드가 접근하지 못하도록 lock(잠금)을 거는 것
 * 만약 Thread 가 4개가 있다 가정 thread-1 가 메소드에 진입하는 순간 나머지 thread2~4 의 접근을 제한 thread-1 이 완료되면 다음 스레드 접근
 */
class ThreadSafeInitialization {
    private static ThreadSafeInitialization instance;

    public static synchronized ThreadSafeInitialization getInstance() {
        if(instance == null)
            instance = new ThreadSafeInitialization();

        return instance;
    }
}

/* 5. Double-Checked Locking
 * 매번 synchronized 동기화 실행하는 것이 문제라면 최초 초기화 할때만 적용하고 이미 만들어진 인스턴스를 반환할때는 사용하지 않도록 하는 기법
 * 이때 인스턴스 필드에 volatile 키워드를 붙어주어야 I/O 불일치 문제를 해결
 * 그러나 volatile 키워드를 이용하기 위해선 JVM 1.5 이상이어야 함 **JVM 에대한 심층적인 이해가 필요** JVM 에 따라서 여전히 쓰레드 세이프 하지 않는 경우가 발생 **사용하기를 지양**
 */

/* volatile
 * 자바에서는 쓰레드를 여러개 사용할 경우, 성능을 위해서 각각의 쓰레드들은 변수를 메인 메모리로부터 가져오는 것이 아닌 캐시 메모리에서 가져오게 됨
 * 문제는 비동기로 변수값을 캐시에 저장하다가, 각 쓰레드마다 할당되어있는 캐시 메모리의 변수값이 불일치 할 수 있음.
 * 그래서 volatile 키워드를 통해 이 변수는 캐시에서 읽지 말고 메인 메모리에서 읽어오도록 지정
 */
class DoubleCheckedLocking {
    private static volatile DoubleCheckedLocking instance;

    private DoubleCheckedLocking() {}

    public static DoubleCheckedLocking getInstance() {
        if(instance == null) {
            // 메서드에 동기화 거는게 아닌, **DoubleCheckedLocking 클래스** 자체를 동기화
            synchronized (DoubleCheckedLocking.class) {
                if(instance == null) {
                    instance = new DoubleCheckedLocking(); // 최초 초기화만 동기화 작업이 일어나서 리소스 낭비를 최소화
                }
            }
        }
        return instance; // 최초 초기화가 되면 앞으로 생성된 인스턴스만 반환
    }
}

/* 6. Bill Pugh Solution(LazyHolder)
 * **권장되는 두가지 방법중 하나**
 * 멀티쓰레드 환경에서 안전하고 Lazy Loading(나중에 객체 생성) 도 가능한 **완벽한 싱글톤** 기법
 * 클래스 안에 내부 클래스(holder)를 두어 JVM 의 클래스 로더 매커니즘과 클래스가 로드되는 시점을 이용한 방법(스레드 세이프함)
 * static 메소드에서는 static 멤버만을 호출할 수 있기 때문에 내부 클래스를 static으로 설정
 * 이 밖에도 내부 클래스의 치명적인 문제점인 메모리 누수 문제를 해결하기 위하여 내부 클래스를 static 으로 설정
 * 다만 클라이언트가 임의로 싱글톤을 파괴할 수 있다는 단점을 지님 (Reflection API, 직렬화/역직렬화를 통해)
 */
class BillPughSolution {

    private BillPughSolution() {}

    // static 내부 클래스를 이용
    // Holder 로 만들어, 클래스가 메모리에 로드되지 않고 getInstance 메소드가 호출되어야 로드됨
    private static class BillPughSolutionHolder {
        private static final BillPughSolution INSTANCE = new BillPughSolution();
    }

    public static BillPughSolution getInstance() {
        return BillPughSolutionHolder.INSTANCE;
    }

    /*
     *  1. 우선 내부클래스를 static 으로 선언 하였기 때문에, 싱글톤 클래스가 초기화되어도 BillPughSolutionHolder 내부 클래스는 메모리에 로드되지 않음
     *  2. 어떠한 모듈에서 getInstance() 메소드를 호출할 때, BillPughSolutionHolder 내부 클래스의 static 멤버를 가져와 리턴하게 되는데 이 때 내부 클래스가 한번만 초기화 되면서 싱글톤 객체를 최초로 생성 및 리턴
     *  3. 마지막으로 final 로 지정함으로서 다시 값이 할당되지 않도록 방지.
     */
}

/* 7. Enum
 * **권장되는 두가지 방법중 하나**
 * enum 은 애초에 멤버를 만들때 private 으로 만들고 한번만 초기화 하기 때문에 thread safe 함.
 * enum 내에서 상수 뿐만 아니라, 변수나 메소드를 선언해 사용이 가능하기 때문에, 이를 이용해 싱글톤 클래스처럼 응용이 가능
 * 위의 Bill Pugh Solution 기법과 달리 클라이언트에서 **Reflection 을 통한 공격에도 안전**
 * 하지만 만일 싱글톤 클래스를 멀티톤(일반적인 클래스)로 마이그레이션 해야할때 처음부터 코드를 다시 짜야되는 단점이 존재함
 * 클래스 상속이 필요할 때, enum 외의 클래스 상속은 불가능
 */
@Getter
enum SingletonEnum {
    INSTANCE;

    private final Thread thread;

    SingletonEnum() {
        thread = Thread.currentThread();
    }

    public static SingletonEnum getInstance() {
        return INSTANCE;
    }

}
