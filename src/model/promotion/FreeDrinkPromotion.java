package model.promotion;
/**
 * author k2460782
 */

import java.util.List;

public class FreeDrinkPromotion implements PromotionStrategy {

    @Override
    public double applyDiscount() {
        return 0;
    }

    @Override
    public List<String> getPromotions() {
        return List.of();
    }
}
