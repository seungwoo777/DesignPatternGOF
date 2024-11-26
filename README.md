# Java 실무 패턴 정리

## 생성(Creational)

#### 추상팩토리 메소드 패턴(AbstractFactory)
    javax.xml.parsers.DocumentBuilderFactory -> newInstance()  
    javax.xml.transform.TransformerFactory -> newInstance()
    javax.xml.xpath.XPathFactory -> newInstance()
#### 빌더 패턴(Builder)
    java.lang.StringBuilder -> append()
    java.lang.StringBuffer -> append()
    java.nio.ByteBuffer -> put() - CharBuffer, ShortBuffer, IntBuffer, LongBuffer, FloatBuffer, DoubleBuffer
    javax.swing.GroupLayout.Group -> addComponent()
    java.lang.Appendable -> 구현체
    java.util.stream.Stream.Builder
#### 팩토리 메소드 패턴(Factory Method)
    java.util.Calendar -> getInstance()
    java.util.ResourceBundle -> getBundle()
    java.text.NumberFormat -> getInstance()
    java.nio.charset.Charset -> forName()
    java.net.URLStreamHandlerFactory -> createURLStreamHandler(String)
    java.util.EnumSet -> of()
    javax.xml.bind.JAXBContext -> createMarshaller() and other similar methods
#### 싱글톤 패턴(Singleton)
    java.util.Calendar -> getInstance()
    java.util.TimeZone -> getDefault()
    java.lang.Runtime -> getRuntime()



## 구조(Structural)

#### 어댑터 패턴(Adaptor)
    java.util.Arrays -> asList()
    java.util.Collections -> list()
    java.util.Collections -> enumeration()
    java.io.InputStreamReader(InputStream) (returns a Reader)
    java.io.OutputStreamWriter(OutputStream) (returns a Writer)
    javax.xml.bind.annotation.adapters.XmlAdapter -> marshal() and unmarshal()

#### 프록시 패턴(Proxy)

##### Java
    java.lang.reflect.Proxy
    java.rmi.* (원격 프록시 모듈)
    javax.ejb.EJB 
    javax.inject.Inject
    javax.persistence.PersistenceContext
Dynamic Proxy
개발자가 직접 디자인 패턴으로서 프록시 패턴을 구현해도 되지만, 자바 JDK에서는 별도로 프록시 객체 구현 기능을 지원한다. 이를 동적 프록시(Dynamic Proxy) 기법이라고 불리운다.
동적 프록시는 개발자가 직접 일일히 프록시 객체를 생성하는 것이 아닌, 애플리케이션 실행 도중 java.lang.reflect.Proxy 패키지에서 제공해주는 API를 이용하여 동적으로 프록시 인스턴스를 만들어 등록하는 방법으로서, 자바의 Reflection APIVisit Website 기법을 응용한 연장선의 개념이다. 그래서 별도의 프록시 클래스 정의없이 런타임으로 프록시 객체를 동적으로 생성해 이용할 수 있다는 장점이 있다
##### Spring
스프링 AOP
스프링 프레임워크에서는 내부적으로 프록시 기술을 정말 많이 사용하고 있다. (AOP, JPA 등)
스프링에서는 Bean을 등록할 때 SingletonVisit Website을 유지하기 위해 Dynamic Proxy 기법을 이용해 프록시 객체를 Bean으로 등록한다. 또한 Bean으로 등록하려는 기본적으로 객체가 Interface를 하나라도 구현하고 있으면 JDK를 이용하고 Interface를 구현하고 있지 않으면 내장된 CGLIB 라이브러리를 이용한다.


## 행위(Behavioral)
