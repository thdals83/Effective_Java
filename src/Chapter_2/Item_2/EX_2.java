package Chapter_2.Item_2;

/*
    자바 빈즈 패턴
    - 매개변수가 없는 생성자를 객체로 만들고 setter 메서드를 호출해 원하는 매개변수 값을 설정
    
    단점
    1. 객체 하나를 만들기 위해 너무 많은 메서드들이 호출
    2. 객체가 완성되기 전까지는 일관성이 무너짐
    -> 클래스를 불변 상태로 만들 수 없어, Thread Safe하려면 추가 작업 필요
*/

class NutritionFacts2 {
    private int servingSize = -1; //필
    private int servings = -1;    //필
    private int calories = 0;
    private int fat = 0;
    private int sodium = 0;
    private int carbohydrate = 0;

    public NutritionFacts2() {}

    public void setServingSize(int servingSize) {
        this.servingSize = servingSize;
    }

    public void setServings(int servings) {
        this.servings = servings;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }

    public void setFat(int fat) {
        this.fat = fat;
    }

    public void setSodium(int sodium) {
        this.sodium = sodium;
    }

    public void setCarbohydrate(int carbohydrate) {
        this.carbohydrate = carbohydrate;
    }
}

class test2 {
    public static void main(String[] args) {
        NutritionFacts2 cocaCola = new NutritionFacts2();
        cocaCola.setServingSize(240);
        cocaCola.setServings(8);
        cocaCola.setCalories(100);
        cocaCola.setFat(0);
        cocaCola.setSodium(35);
        cocaCola.setCarbohydrate(27);
    }
}

public class EX_2 {

}
