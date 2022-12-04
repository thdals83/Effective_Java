package Chapter_1.Item_1;

public class EX_2 {
}

class Foo_Public {
    //아래 에러 발생
    String name;
    String address;

    public Foo_Public(String name) {
        this.name = name;
    }

//    public Foo_Public(String address) {
//
//    }
}

class Foo_Static {
    private String name;
    private String address;

    public Foo_Static(String address) {
        this.address = address;
    }

    public static Foo_Static of(String address) {
        return new Foo_Static(address);
    }

    public Foo_Static withName(String name) {
        this.name = name;
        return this;
    }
}

class Test {
    private void test() {
        Foo_Static a = Foo_Static.of("줏소는 필수").withName("test");
    }
}

class Foo_Static2 {
//    private final static Foo_Static2 FOO_STATIC_2 = ;
    private String name;

    public Foo_Static2() {
    }
//
//    public static Foo_Static2 withName(String name) {
//        foo_static2.name = name;
//        return foo_static2;
//    }
//
//    public static Foo_Static2 withAddress(String address) {
//        foo_static2.name = address;
//        return foo_static2;
//    }
}

