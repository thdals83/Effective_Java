package Chapter_1.Item_1;

public class EX_1 {
    public static void main(String[] args) {
        //public 생성자, 정적 팩터리 메서드 비교
        Person_Static person_public = Person_Static.of(11L);
    }

}

class Person_Public {
    public Person_Public() {
    }
}

//정적 팩터리 메서드(static factory method)
class Person_Static {
    private long age;

    public Person_Static(long age) {
        this.age = age;
    }

    private Person_Static() {

    }
    public static Person_Static of(long age) {
        return new Person_Static(age);
    }
}