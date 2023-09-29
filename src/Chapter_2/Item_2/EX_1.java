package Chapter_2.Item_2;

/*
    생성자에 매개변수가 많다면 빌더를 고려하라
    1. 선택적 매개변수가 많을 시 대응하기 어려움
* */

/*
    - 점층적 생성자 패턴
    - 필수 생성자를 두고, 선택 매개변수를 1개씩 늘려나가는 방식
    
    단점
    1. 초기화 하고 싶은 생성자가 없으면, 힘듬
    2. 매개변수 많아지면 복잡하고 더 어려워짐
*/
class NutritionFacts {
    private final int servingSize; //필
    private final int servings;    //필  
    
    private final int calories;    //선
    private final int fat;         //선
    private final int sodium;      //선
    private final int carbohydrate;//선

    public NutritionFacts(int servingSize, int servings) {
        this(servingSize, servings, 0);
    }

    public NutritionFacts(int servingSize, int servings, int calories) {
        this(servingSize, servings, calories, 0);
    }

    public NutritionFacts(int servingSize, int servings, int calories, int fat) {
        this(servingSize, servings, calories,  fat, 0);
    }

    public NutritionFacts(int servingSize, int servings, int calories, int fat, int sodium) {
        this(servingSize, servings, calories,  fat,  sodium, 0);
    }

    public NutritionFacts(int servingSize, int servings, int calories, int fat, int sodium, int carbohydrate) {
        this.servingSize = servingSize;
        this.servings = servings;
        this.calories = calories;
        this.fat = fat;
        this.sodium = sodium;
        this.carbohydrate = carbohydrate;
    }
}

class Test1 {
    public static void main(String[] args) {
        NutritionFacts cocaCola = new NutritionFacts(240, 8, 100, 0, 35, 27);
    }
}

public class EX_1 {
    
}

