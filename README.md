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



## 행위(Behavioral)
