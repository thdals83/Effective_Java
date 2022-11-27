package Chapter_1.Item_1;

public class EX_1 {
    public static void main(String[] args) {
        //public 생성자, 정적 팩터리 메서드 비교
        Person_Public person_public = new Person_Public();
        Person_Static person_static = Person_Static.getInstance();
    }

}

class Person_Public {
    public Person_Public() {
    }
}

//정적 팩터리 메서드(static factory method)
class Person_Static {
    private static Person_Static person_static;

    private Person_Static() {

    }
    public static final Person_Static getInstance() {
        return person_static;
    }
}