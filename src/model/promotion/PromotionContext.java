package model.promotion;

/**
 * author k2460782
 */
public class PromotionContext {
    private PromotionStrategy promotionStrategy;

    public void setPromotionStrategy(PromotionStrategy promotionStrategy) {
        this.promotionStrategy = promotionStrategy;
    }

    public void applyPromotion() {
        if (promotionStrategy != null) {
            promotionStrategy.applyDiscount();
        } else {
            System.out.println("No promotion selected.");
        }
    }
}