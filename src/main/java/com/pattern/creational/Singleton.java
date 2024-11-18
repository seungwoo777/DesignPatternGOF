package com.pattern.creational;

/*
 * 객체를 하나로 제한 -> 설정 관리, 로깅, 캐싱
 */
public class Singleton {
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