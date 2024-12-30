package model.order;
import java.util.ArrayList;
import java.util.List;

public class OrderManager {
    private static List<Order> allOrders = new ArrayList<>();

    // Retrieve all orders
    public static List<Order> getAllOrders() {
        return allOrders;
    }

    // Add a new order
    public static void addOrder(Order order) {
        allOrders.add(order);
    }

    // Update the state of an order
    public static void updateOrderState(int orderId, OrderState newState) {
        for (Order order : allOrders) {
            if (order.getId() == orderId) {
                order.setState(newState);
                break;
            }
        }
    }

    // Find an order by its ID
    public static Order findOrderById(int orderId) {
        for (Order order : allOrders) {
            if (order.getId() == orderId) {
                return order;
            }
        }
        return null; // Order not found
    }
}
