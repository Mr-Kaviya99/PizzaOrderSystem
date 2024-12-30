package model.order;

/**
 * author k2460782
 */
public interface OrderState {
    void handleOrder(Order order);
}