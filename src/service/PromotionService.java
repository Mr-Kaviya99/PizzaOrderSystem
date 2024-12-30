package service;
/**
 * author k2460782
 */

import model.promotion.PromotionStrategy;

public interface PromotionService {
    void setPromotionStrategy(PromotionStrategy promotionStrategy);

    double applyPromotion(double price);
}
