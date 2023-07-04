package Chapter_1.Item_2;

/*
    빌더 패턴
    - Builder 객체를 이용해 필수 매개변수를 객체로 생성
    - 일종의 setter를 사용하여 선택 매개변수를 초기화한 뒤
    - build() 메서드를 호출해 완전한 객체를 생성
    
    특징
    - 불변성 (자바 빈즈 패턴과 가장 큰 차이점)
    - 객체를 생성 전 setter 메서드를 통해 값을 넣고 build 메서드를 호출해
    객체를 생성해 변경될 우려 X, 불변성과 안정성이 올라간다.
    - 빌더 패턴 사용시에는 setter를 선언하면 안된다.
*/

class NutritionFacts3 {
    private final int servingSize;
    private final int servings;
    private final int calories;
    private final int fat;
    private final int sodium;
    private final int carbohydrate;

    private NutritionFacts3(Builder builder) {
        servingSize = builder.servingSize;
        servings = builder.servings;
        calories = builder.calories;
        fat = builder.fat;
        sodium = builder.sodium;
        carbohydrate = builder.carbohydrate;
    }
    
    public static Builder builder(int servingSize, int servings) {
        return new Builder(servingSize, servings);
    }
    
    public static class Builder {
        //필수 매개변수
        private final int servingSize;
        private final int servings;

        private int calories = 0;
        private int fat = 0;
        private int sodium = 0;
        private int carbohydrate = 0;
        
        // 필수 매개변수만을 담은 Builder 생성자
        public Builder(int servingSize, int servings) {
            this.servingSize = servingSize;
            this.servings = servings;
        }
        
        //선택 매개변수만의 setter, Builder 인스턴스 자신을 반환해 연쇄적 호출 가능
        public Builder calories(int val) {
            calories = val;
            return this;
        }
        public Builder fat(int val) {
            fat = val;
            return this;
        }
        public Builder sodium(int val) {
            sodium = val;
            return this;
        }
        public Builder carbohydrate(int val) {
            carbohydrate = val;
            return this;
        }
        
        //build() 호출로 최종 불변 객체를 얻는다.
        public NutritionFacts3 build() {
            return new NutritionFacts3(this);
        }
        
    }
}

class test3 {
    public static void main(String[] args) {
        NutritionFacts3 cocaCola = NutritionFacts3
                .builder(240, 8)
                .calories(100)
                .sodium(35)
                .carbohydrate(30)
                .build();
    }
}

public class EX_3 {
}
