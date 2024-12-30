package model.order;

public class PreparationState implements OrderState {
    @Override
    public void handleOrder(Order order) {

        // Simulate preparation time
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        order.setState(new DeliveryState());
    }
}
