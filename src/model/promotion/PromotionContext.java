package model.promotion;

public class PromotionContext {
    private PromotionStrategy promotionStrategy;

    // Set the current strategy
    public void setPromotionStrategy(PromotionStrategy promotionStrategy) {
        this.promotionStrategy = promotionStrategy;
    }

    // Apply the current promotion
    public void applyPromotion() {
        if (promotionStrategy != null) {
            promotionStrategy.applyDiscount();
        } else {
            System.out.println("No promotion selected.");
        }
    }
}