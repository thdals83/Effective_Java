package Chapter_1.Item_2;

import java.util.EnumSet;
import java.util.Objects;
import java.util.Set;

public class EX_4 {
	public static void main(String[] args) {
		NYPizza pizza = new NYPizza.Builder(NYPizza.Size.SMALL)
				.addTopping(Pizza.Topping.SAUSAGE)
				.addTopping(Pizza.Topping.ONION)
				.build();
	}
}

abstract class Pizza {
	public enum Topping {HAM, MUSHROOM, ONION, PEPPER, SAUSAGE}

	final EnumSet<Topping> toppings;

	Pizza(Builder<?> builder) {
		toppings = builder.toppings.clone();
	}
	
	abstract static class Builder<T extends Builder<T>> {
		EnumSet<Topping> toppings = EnumSet.noneOf(Topping.class);
		
		public T addTopping(Topping topping) {
			toppings.add(Objects.requireNonNull(topping));
			return self();
		}
		
		abstract Pizza build();
		
		protected abstract T self();
	}
}

class NYPizza extends Pizza {
	public enum Size {SMALL, MEDIUM, LARGE}
	private final Size size;

	private NYPizza(Builder builder) {
		super(builder);
		this.size = builder.size;
	}
	
	public static class Builder extends Pizza.Builder<Builder> {
		private final Size size;
		
		public Builder(Size size) {
			this.size = Objects.requireNonNull(size);
		}
		
		@Override
		public NYPizza build() {
			return new NYPizza(this);
		}
		@Override
		protected Builder self() {
			return this;
		}
	}
	

}