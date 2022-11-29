package Chapter_1.Item_1;

import java.util.EnumSet;

public class EX_4 {
    enum Alphabet {
        A, B, C, D
    }

    public static void main(String[] args) {
        //이때 내부적으로 원소가 64개 이하면 RegularEnumSet 인스턴스 반환,
        //65개 이상이면 JumboEnumSet 인스턴브 반환
        EnumSet<Alphabet> alphabets = EnumSet.allOf(Alphabet.class);
    }
}

interface MyInt {
    static MyInt of(int v) {
        MyInt instance = null;
        
        if (v > 100) {
            instance = new MyBigInt(v);
        } else {
            instance = new MySmallInt(v);
        }
        
        return instance;
    }

    String getValue();

    class MySmallInt implements MyInt {
        private int value;
        MySmallInt(int v) {
            this.value = v;
        }

        @Override
        public String getValue() {
            return "MySmallInt: " + this.value;
        }
    }

    class MyBigInt implements MyInt {
        private int value;

        MyBigInt(int v) {
            this.value = v;
        }

        @Override
        public String getValue() {
            return "MyBigInt: " + this.value;
        }
    }
}
