package model.promotion;
/**
 * author k2460782
 */

import java.util.List;

public interface PromotionStrategy {
    double applyDiscount();

    List<String> getPromotions();
}
