package model;
/**
 * author k2460782
 */

import model.enums.PizzaSize;
import model.enums.PizzaTopping;

import java.util.ArrayList;
import java.util.List;

public class Pizza {
    private Integer id;
    private PizzaSize size;
    private String crust;
    private String sauce;
    private List<PizzaTopping> toppings;
    private String cheese;
    private double price;

    private Pizza(PizzaBuilder builder) {
        this.size = builder.size;
        this.crust = builder.crust;
        this.sauce = builder.sauce;
        this.toppings = builder.toppings;
        this.cheese = builder.cheese;
        this.price = calculatePrice(); // Set price upon creation
    }

    private double calculatePrice() {
        double basePrice = 800;
        switch (this.size) {
            case SMALL:
                basePrice = 800;
                break;
            case REGULAR:
                basePrice = 1300;
                break;
            case LARGE:
                basePrice = 1800;
                break;
        }

        if (this.crust.equals("Thick Crust")) {
            basePrice += 100;
        } else if (this.crust.equals("Stuffed Crust")) {
            basePrice += 150;
        }

        double toppingPrice = this.toppings.size() * 50;
        return basePrice + toppingPrice;
    }

    public double getPrice() {
        return this.price;
    }

    public String getCheese() {
        return cheese;
    }

    public void setCheese(String cheese) {
        this.cheese = cheese;
    }

    public List<PizzaTopping> getToppings() {
        return toppings;
    }

    public void setToppings(List<PizzaTopping> toppings) {
        this.toppings = toppings;
    }

    public String getSauce() {
        return sauce;
    }

    public void setSauce(String sauce) {
        this.sauce = sauce;
    }

    public String getCrust() {
        return crust;
    }

    public void setCrust(String crust) {
        this.crust = crust;
    }

    public PizzaSize getSize() {
        return size;
    }

    public void setSize(PizzaSize size) {
        this.size = size;
    }

    public static class PizzaBuilder {
        private PizzaSize size;
        private String crust;
        private String sauce;
        private List<PizzaTopping> toppings = new ArrayList<>();
        private String cheese;

        public PizzaBuilder setSize(PizzaSize size) {
            this.size = size;
            return this;
        }

        public PizzaBuilder setCrust(String crust) {
            this.crust = crust;
            return this;
        }

        public PizzaBuilder setSauce(String sauce) {
            this.sauce = sauce;
            return this;
        }

        public PizzaBuilder addTopping(PizzaTopping topping) {
            this.toppings.add(topping);
            return this;
        }

        public PizzaBuilder addToppings(List<PizzaTopping> toppings) {
            this.toppings.addAll(toppings);
            return this;
        }

        public PizzaBuilder setCheese(String cheese) {
            this.cheese = cheese;
            return this;
        }

        public Pizza build() {
            return new Pizza(this);
        }
    }

    @Override
    public String toString() {
        return "Pizza [size=" + size + ", crust=" + crust + ", sauce=" + sauce + ", toppings=" + toppings + ", cheese=" + cheese + "]";
    }
}
