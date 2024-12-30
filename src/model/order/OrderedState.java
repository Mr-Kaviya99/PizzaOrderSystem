package model.order;

/**
 * author k2460782
 */
public class OrderedState implements OrderState {
    @Override
    public void handleOrder(Order order) {
        order.setState(new PreparationState());
    }
}
