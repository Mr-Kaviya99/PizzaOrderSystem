package model.promotion;

import java.util.List;

public class BuyOneGetOnePromotion implements PromotionStrategy {

    @Override
    public double applyDiscount() {
        return 0;
    }

    @Override
    public List<String> getPromotions() {
        return List.of();
    }
}
