package com.pattern.creational;

/*
 * 복잡한 객체를 단계적으로 생성 -> 불변 객체 생성, 복잡한 초기화
 */
public class Builder {
    public static void main(String[] args) {
        // 점층적 생성자 패턴 Start
        // 소주, 위스키, 맥주, 와인, 사케
        AlcoholicDrink alcoholicDrink1 = new AlcoholicDrink("진로", "블랜디드", "아사히", "레드", "닷사이");
        // 소주, 위스키, 맥주, 와인
        AlcoholicDrink alcoholicDrink2 = new AlcoholicDrink("참이슬", "싱글몰트", "카스", "화이트");
        // 소주, 위스키, 맥주
        AlcoholicDrink alcoholicDrink3 = new AlcoholicDrink("처음처럼", "버번", "테라");
        // 점층적 생성자 패턴 End


        // 자바 빈 패턴 Start
        AlcoholicDrinkByJavaBean alcoholicDrinkByJavaBean = new AlcoholicDrinkByJavaBean();
        alcoholicDrinkByJavaBean.setSoju("진로");
        alcoholicDrinkByJavaBean.setWine("레드");
        alcoholicDrinkByJavaBean.setWhiskey("블랜디드");
        alcoholicDrinkByJavaBean.setBeer("라거");
        // 자바 빈 패턴 End

        // 빌더 패턴 Start
        Drink drink = new AlcoholicDrinkByBuilder()
                .soju("대선")
                .whiskey("그레인")
                .beer("에일")
                .wine("스파클링")
                .sake("간바레오또상")
                .build();
        // 빌더 패턴 End

        // 디렉터 패턴 Start
        Wine wine = new Wine("샤또 마고", 20);

        // 일반 텍스트 포맷
        WineBuilder wineBuilder1 = new PlainTextBuilder(wine);
        Director director1 = new Director(wineBuilder1);
        String result1 = director1.build();
        System.out.println("test = " + result1);

        WineBuilder wineBuilder2 = new JSONBuilder(wine);
        Director director2 = new Director(wineBuilder2);
        String result2 = director2.build();
        System.out.println("JSON = " + result2);

        WineBuilder wineBuilder3 = new XMLBuilder(wine);
        Director director3 = new Director(wineBuilder3);
        String result3 = director3.build();
        System.out.println("XML = " + result3);
        // 디렉터 패턴 End
    }
}

// 점층적 생성자 패턴 -> 단점 : 인스턴스 필드들이 많으면 많을수록 들어갈 인자의 수가 늘어나서 햇갈림
//@RequiredArgsConstructor
class AlcoholicDrink {

    // 필수 매개변수
    private String soju;
    private String whiskey;

    // 선택 매개변수
    private String beer;
    private String wine;
    private String sake;

    public AlcoholicDrink(String soju, String whiskey, String beer, String wine, String sake) {
        this.soju = soju;
        this.whiskey = whiskey;
        this.beer = beer;
        this.wine = wine;
        this.sake = sake;
    }

    public AlcoholicDrink(String soju, String whiskey, String beer, String wine) {
        this.soju = soju;
        this.whiskey = whiskey;
        this.beer = beer;
        this.wine = wine;
    }

    public AlcoholicDrink(String soju, String whiskey, String beer) {
        this.soju = soju;
        this.whiskey = whiskey;
        this.beer = beer;
    }
}

// 자바 빈 패턴 -> 단점 : 객체 생성 시점에 모든 값들을 주입하지 않아 일관성 및 불변성 문제가 나타남
//@NoArgsConstructor
//@Setter
class AlcoholicDrinkByJavaBean {
    // 필수 매개변수
    private String soju;
    private String whiskey;

    // 선택 매개변수
    private String beer;
    private String wine;
    private String sake;

    public AlcoholicDrinkByJavaBean() {}

    public void setSoju(String soju) {
        this.soju = soju;
    }

    public void setWhiskey(String whiskey) {
        this.whiskey = whiskey;
    }

    public void setBeer(String beer) {
        this.beer = beer;
    }

    public void setWine(String wine) {
        this.wine = wine;
    }

    public void setSake(String sake) {
        this.sake = sake;
    }
}


//@AllArgsConstructor
//@ToString
class Drink {
    private String soju;
    private String whiskey;
    private String beer;
    private String wine;
    private String sake;

    public Drink(String soju, String whiskey, String beer, String wine, String sake) {
        this.soju = soju;
        this.whiskey = whiskey;
        this.beer = beer;
        this.wine = wine;
        this.sake = sake;
    }

    @Override
    public String toString() {
        return "Drink{" +
                "soju='" + soju + '\'' +
                ", whiskey='" + whiskey + '\'' +
                ", beer='" + beer + '\'' +
                ", wine='" + wine + '\'' +
                ", sake='" + sake + '\'' +
                '}';
    }
}

/* 빌더 패턴 -> 단점 :
 * 1. 코드 복잡성 증가 -> 클래스 수가 기하급수적으로 늘어나 관리해야 할 클래스가 많아지고 복잡해 질 수 있음.
 * 2. 생성자보다는 성능이 떨어짐 -> 매번 메서드를 호출하여 빌더를 걸쳐 인스턴스화 하기 떄문 생성 비용 자체는 크지는 않지만 애플리케이션 성능을 극으로 중요시하면 상황에 문제가 됨.
 * 3. 지나친 벌더 남용은 금지 -> 클래스의 필드가 4개보다 작고 변경 가능성이 없으면 정적 팩토리 메서드 이용하는게 좋음.
 */
//@Builder
class AlcoholicDrinkByBuilder {
    private String soju;
    private String whiskey;
    private String beer;
    private String wine;
    private String sake;

    // 빌더 객체 자신을 리턴 함으로써 메서드 호출 후 연속적으로 빌더 메서드들을 Chaining 하여 호출함.
    public AlcoholicDrinkByBuilder soju(String soju) {
        this.soju = soju;
        return this;
    }

    public AlcoholicDrinkByBuilder whiskey(String whiskey) {
        this.whiskey = whiskey;
        return this;
    }

    public AlcoholicDrinkByBuilder beer(String beer) {
        this.beer = beer;
        return this;
    }

    public AlcoholicDrinkByBuilder wine(String wine) {
        this.wine = wine;
        return this;
    }

    public AlcoholicDrinkByBuilder sake(String sake) {
        this.sake = sake;
        return this;
    }

    public Drink build() {
        return new Drink(soju, whiskey, beer, wine, sake);
    }
}

/* 심플 빌더 패턴 (이벡티브 자바) -> 빌더랑 별 차이 없지만 클래스가 구현할 클래스의 **정적내부클래스로 구현된다는 점이 다름**
 * 빌더클래스가 static inner class로 구현되는 이유
 *  1. 하나의 빌더 클래스는 하나의 대상 객체 생성만을 위해 사용 그러므로 두 클래스를 물리적으로 그루핑함으로 두 클래스간의 관계에 대한 파악을 쉽게 함.
 *  2. 대상 객체는 오로지 빌더 객체에 의해 초기화 즉 생성자를 외부에 노출시키면 안되기 때문에 생성자를 private 하고 내부 빌더 클래스에서  private 생성자를 호춤함으로 오로지 빌더객체에 의해 초기화 하도록 설계
 *  3. 정적 내부 클래스는 외부 클래스의 인스턴스 없이도 생성 할 수 있는데 만일 일반 내부클래스로 구성한다면 내부 클래스를 생성하기도 전에 외부 클래스를 인스턴스화 해야함. 빌더가 최종적으로 생성할 클래스의 인스턴스를 먼저 생성해야 한다면 모순이 생김.
 *  4. 메모리 누수 문제 때문에 static으로 내부 클래스를 정의해줘야 함
 */
class Whiskey {
    String name;
    int year;

    // 정적 내부 빌더 클래스
    public static class Builder {
        String name;
        int year;

        Builder name(String name) {
            this.name = name;
            return this;
        }

        Builder year(int year) {
            this.year = year;
            return this;
        }

        // 대상 객체의 private 생성자를 호출하여 최종 인스턴스화
        public Whiskey build() {
            return new Whiskey(this); // 빌더 객체를 넘김
        }
    }

    // private 생성자 - 생성자는 외부에서 호출되는것이 아닌 빌더 클래스에서만 호출되기 때문에
    private Whiskey(Builder builder) {
        this.name = builder.name;
        this.year = builder.year;
    }

}

/* 디렉터 빌더 패턴 -> 일반적인 자바 데이터를 저장하고 있는 Data 객체를 Builder 인터페이스를 통해 적절한 문자열 포맷으로 변환함
 * 1. PlainTextBuilder : Data 인스턴스의 데이터들을 평이한 텍스트 형태로 만드는 API
 * 2. JSONBuilder : Data 인스턴스의 데이터들을 JSON 형태로 만드는 API
 * 3. XMLBuilder : Data 인스턴스의 데이터들을 XML 형태로 만드는 API
 */
class Wine {
    private String name;
    private int year;

    public Wine(String name, int year) {
        this.name = name;
        this.year = year;
    }

    public String getName() {
        return name;
    }

    public int getYear() {
        return year;
    }
}

abstract class WineBuilder {
    protected Wine wine;

    public WineBuilder(Wine wine) {
        this.wine = wine;
    }

    public abstract String head();
    public abstract String body();
    public abstract String foot();

}

class PlainTextBuilder extends WineBuilder {

    public PlainTextBuilder(Wine wine) {
        super(wine);
    }

    @Override
    public String head() {
        return "";
    }

    @Override
    public String body() {
        StringBuilder sb = new StringBuilder();

        sb.append("Name : ");
        sb.append(wine.getName());
        sb.append("|| Year : ");
        sb.append(wine.getYear());

        return sb.toString();
    }

    @Override
    public String foot() {
        return "";
    }
}

class JSONBuilder extends WineBuilder {


    public JSONBuilder(Wine wine) {
        super(wine);
    }

    @Override
    public String head() {
        return "{\n";
    }

    @Override
    public String body() {
        StringBuilder sb = new StringBuilder();


        sb.append("\t\"Name\" : ");
        sb.append("\"" + wine.getName() + "\",\n");
        sb.append("\t\"Year\" : ");
        sb.append(wine.getYear());
        return sb.toString();
    }

    @Override
    public String foot() {
        return "\n}";
    }
}

class XMLBuilder extends WineBuilder {

    public XMLBuilder(Wine wine) {
        super(wine);
    }

    @Override
    public String head() {
        StringBuilder sb = new StringBuilder();

        sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>\n");
        sb.append("<WINE>\n");

        return sb.toString();
    }

    @Override
    public String body() {
        StringBuilder sb = new StringBuilder();

        sb.append("\t<NAME>");
        sb.append(wine.getName());
        sb.append("<NAME>");
        sb.append("\n\t<YEAR>");
        sb.append(wine.getYear());
        sb.append("<YEAR>");

        return sb.toString();
    }

    @Override
    public String foot() {
        return "\n</WINE>";
    }
}

class Director {
    private WineBuilder wineBuilder;

    public Director(WineBuilder wineBuilder) {
        this.wineBuilder = wineBuilder;
    }

    public String build() {
        StringBuilder sb = new StringBuilder();

        // 빌더 구현체에서 정의한 생성 알고리즘이 실행됨
        sb.append(wineBuilder.head());
        sb.append(wineBuilder.body());
        sb.append(wineBuilder.foot());

        return sb.toString();
    }
}