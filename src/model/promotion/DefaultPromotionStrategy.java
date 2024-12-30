package model.promotion;

import java.util.Arrays;
import java.util.List;

public class DefaultPromotionStrategy implements PromotionStrategy {
    @Override
    public double  applyDiscount() {
        return 0;
    }

    @Override
    public List<String> getPromotions() {
        return Arrays.asList(
                "Buy 1 Pizza, Get 1 Free - Winter Special!",
                "20% Off on All Large Pizzas - Holiday Season!"
        );
    }
}
