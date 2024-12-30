package model.order;

public class DeliveryState implements OrderState {
    @Override
    public void handleOrder(Order order) {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        order.setState(new CompletedState()); // Order completed after delivery
    }
}