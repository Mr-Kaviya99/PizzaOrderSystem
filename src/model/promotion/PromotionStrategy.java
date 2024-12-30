package model.promotion;

import java.util.List;

public interface PromotionStrategy {
    double applyDiscount();
    List<String> getPromotions();
}
