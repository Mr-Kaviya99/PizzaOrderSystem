package model.order;

public class OrderedState implements OrderState {
    @Override
    public void handleOrder(Order order) {
        order.setState(new PreparationState());
    }
}
