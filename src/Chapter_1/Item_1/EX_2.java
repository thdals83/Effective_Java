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

    public Foo_Static() {}

    public static Foo_Static withName(String name) {
        Foo_Static foo = new Foo_Static();
        foo.name = name;
        return foo;
    }

    public static Foo_Static withAddress(String address) {
//        ...
        return null;
    }
}

class Foo_Static2 {
    private static Foo_Static2 foo_static2;
    private String name;

    public Foo_Static2() {}

    public static Foo_Static2 withName(String name) {
        foo_static2.name = name;
        return foo_static2;
    }

    public static Foo_Static2 withAddress(String address) {
        foo_static2.name = address;
        return foo_static2;
    }
}

