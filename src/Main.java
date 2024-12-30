/**
 * author k2460782
 */

import model.Customer;
import model.Feedback;
import model.Pizza;
import model.enums.OrderStatus;
import model.enums.PizzaSize;
import model.enums.PizzaTopping;
import model.order.CompletedState;
import model.order.DeliveryState;
import model.order.Order;
import model.order.PreparationState;
import model.payment.CashPayment;
import model.payment.CreditCardPayment;
import model.payment.MobilePayment;
import model.payment.PaymentStrategy;
import model.promotion.PromotionContext;
import service.CustomerService;
import service.OrderService;
import service.PizzaCustomizationService;
import service.impl.CustomerServiceImpl;
import service.impl.OrderServiceImpl;
import service.impl.PizzaCustomizationServiceImpl;

import java.time.LocalDateTime;
import java.util.*;

public class Main {

    static Scanner scanner = new Scanner(System.in);
    private final PizzaCustomizationService pizzaCustomizationService = new PizzaCustomizationServiceImpl();
    private final OrderService orderService = new OrderServiceImpl();
    private static final CustomerService customerService = new CustomerServiceImpl();
    private static List<Order> orders = new ArrayList<>();
    private static Map<Integer, Double> customerPoints = new HashMap<>();
    private static final PromotionContext promotionContext = new PromotionContext();

    private static final PizzaTopping[] AVAILABLE_TOPPINGS = PizzaTopping.values();

    public static void main(String[] args) {
        System.out.println("***************************************");
        System.out.println("\nWelcome to the Pizza Ordering System!\n");
        while (true) {
            System.out.println("************** Main Menu **************\n");

            System.out.println("1. Place Order");
            System.out.println("2. View Orders");
            System.out.println("3. User Profile");
            System.out.println("4. View Order");
            System.out.println("5. Feedback");
            System.out.println("6. Exit");

            System.out.print("Select option : ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    placeOrder();
                    break;
                case 2:
                    viewOrders();
                    break;
                case 3:
                    manageUserProfile();
                    break;
                case 4:
                    viewOrder();
                    break;
                case 5:
                    handleFeedback();
                    break;
                case 6:
                    System.out.println("Thank you for using the Pizza Ordering System!");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void placeOrder() {

        displaySeasonalPromotions();

        Pizza pizza = customizePizza();

        System.out.print("Pizza quantity that you want to buy : ");
        int quantity = getIntInput(1, Integer.MAX_VALUE, "Invalid quantity. Defaulting to 1.");

        String deliveryMethod = selectDeliveryMethod();

        String address = null;
        if ("Delivery".equals(deliveryMethod)) {
            address = getDeliveryAddress();
        } else {
            System.out.println("Please visit our store to collect your order.");
        }

        System.out.print("Enter your contact number : ");
        String phoneNumber = scanner.nextLine();
        Customer customer = getOrCreateCustomer(phoneNumber);

        double orderAmount = pizza.getPrice() * quantity;
        double discount = applyLoyaltyPoints(customer, orderAmount);
        double totalAmount = orderAmount - discount;

        System.out.println("\nOrder Amount: " + orderAmount);
        System.out.println("Discount Applied: " + discount);
        System.out.println("Total Amount after Discount: " + totalAmount);

        PaymentStrategy paymentStrategy = getPaymentStrategy();
        customer.setPaymentStrategy(paymentStrategy);
        customer.makePayment(totalAmount);

        Order order = createOrder(pizza, deliveryMethod, quantity, address, totalAmount);
        orders.add(order);

        System.out.println("Your order has been placed with ID: " + order.getId());

        System.out.println("-----------------------------------------");

        trackOrderStatus(order, deliveryMethod);
    }

    private static void displaySeasonalPromotions() {
        System.out.println("\n************** Seasonal Promotions & Specials **************\n");

        List<String> promotions = getSeasonalPromotions();

        if (promotions.isEmpty()) {
            System.out.println("No seasonal promotions available at the moment.");
        } else {
            for (int i = 0; i < promotions.size(); i++) {
                System.out.println((i + 1) + ". " + promotions.get(i));
            }
        }
    }

    private static List<String> getSeasonalPromotions() {
        List<String> promotions = new ArrayList<>();
        promotions.add("Buy 1 Pizza, Get 1 Free - Winter Special!");
        promotions.add("Free Drink with Any Large Pizza - New Year Offer!");

        return promotions;
    }

    private static Pizza customizePizza() {

        System.out.println("\n************** Pizza size **************\n");
        System.out.println("1. Small");
        System.out.println("2. Medium");
        System.out.println("3. Large");
        System.out.print("Select pizza size : ");
        PizzaSize size = switch (getIntInput(1, 3, "Invalid choice. Defaulting to Small.")) {
            case 1 -> PizzaSize.SMALL;
            case 2 -> PizzaSize.REGULAR;
            case 3 -> PizzaSize.LARGE;
            default -> PizzaSize.SMALL;
        };

        String crust = selectOption("\n************** Crust Type **************\n", new String[]{"None", "Thin Crust", "Thick Crust", "Stuffed Crust"});
        String sauce = selectOption("\n************** Sauce **************\n", new String[]{"None", "Tomato ", "Alfredo Sauce", "BBQ Sauce"});
        String cheese = selectOption("\n************** Cheese Type **************\n", new String[]{"None", "Mozzarella", "Cheddar", "Parmesan"});
        List<PizzaTopping> toppings = selectToppings();

        return new Pizza.PizzaBuilder()
                .setSize(size)
                .setCrust(crust)
                .setSauce(sauce)
                .setCheese(cheese)
                .addToppings(toppings)
                .build();
    }

    private static String selectOption(String prompt, String[] options) {
        System.out.println(prompt);
        for (int i = 0; i < options.length; i++) {
            System.out.println((i + 1) + ". " + options[i]);
        }
        System.out.print("Selection : ");
        int choice = getIntInput(1, options.length, "Invalid choice. Defaulting to None.");
        return choice == 1 ? null : options[choice - 1];
    }

    private static List<PizzaTopping> selectToppings() {
        System.out.println("\n************** Available toppings **************\n");
        for (PizzaTopping topping : PizzaTopping.values()) {
            System.out.println((topping.ordinal() + 1) + ". " + topping);
        }
        System.out.print("Select toppings (comma separated, e.g., 1,3,5), or press Enter for None : ");
        String toppingInput = scanner.nextLine();

        List<PizzaTopping> selectedToppings = new ArrayList<>();
        if (!toppingInput.isEmpty()) {
            String[] toppingChoices = toppingInput.split(",");
            for (String choice : toppingChoices) {
                try {
                    int index = Integer.parseInt(choice.trim()) - 1;
                    if (index >= 0 && index < PizzaTopping.values().length) {
                        selectedToppings.add(PizzaTopping.values()[index]);
                    } else {
                        System.out.println("Invalid topping choice : " + (index + 1));
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input for toppings : " + choice);
                }
            }
        }
        return selectedToppings;
    }

    private static String selectDeliveryMethod() {
        System.out.println("\n************** delivery method **************\n");
        System.out.println("1. Pickup");
        System.out.println("2. Delivery");
        System.out.print("Your option : ");

        int choice = getIntInput(1, 2, "Invalid choice. Defaulting to Pickup.");
        if (choice == 2) {
            return "Delivery";
        }
        return "Pickup";
    }

    private static String getDeliveryAddress() {
        System.out.print("Please enter your delivery address : ");
        return scanner.nextLine();
    }

    private static Customer getOrCreateCustomer(String phoneNumber) {
        Customer customer = customerService.getCustomerByPhoneNumber(phoneNumber);
        if (customer == null) {
            System.out.print("\nNew customer detected.\nPlease provide your name : ");
            String name = scanner.nextLine();
            System.out.print("Please provide your email : ");
            String email = scanner.nextLine();
            customer = customerService.createCustomer(name, phoneNumber, email);
            System.out.println("Welcome, " + name + "! Your loyalty points have been initialized to 0.");
        } else {
            System.out.println("\nWelcome back, " + customer.getName() + "! You currently have " + customer.getLoyaltyPoints() + " loyalty points.\n");
        }
        return customer;
    }

    private static double applyLoyaltyPoints(Customer customer, double orderAmount) {
        double discount = 0;
        if (customer.getLoyaltyPoints() >= 1000) {
            discount = 100;
            System.out.println("You are eligible for a LKR 100 discount!");
            customerService.deductLoyaltyPoints(customer.getPhoneNumber(), 1000);
        }
        customerService.updateLoyaltyPoints(customer.getPhoneNumber(), (int) (orderAmount / 10));
        return discount;
    }

    private static PaymentStrategy getPaymentStrategy() {

        System.out.println("\n************** Payment Method **************\n");
        System.out.println("1. Credit Card");
        System.out.println("2. Cash");
        System.out.println("3. Mobile Payment");
        System.out.print("Select payment method : ");
        int paymentMethodChoice = getIntInput(1, 3, "Invalid choice. Defaulting to Cash.");

        return switch (paymentMethodChoice) {
            case 1 -> {
                System.out.print("Enter your credit card number : ");
                String cardNumber = scanner.nextLine();
                System.out.print("Enter your name on the card : ");
                String cardHolderName = scanner.nextLine();
                yield new CreditCardPayment(cardNumber, cardHolderName);
            }
            case 2 -> new CashPayment();
            case 3 -> {
                System.out.print("Enter your mobile number : ");
                String mobileNumber = scanner.nextLine();
                yield new MobilePayment(mobileNumber);
            }
            default -> new CashPayment();
        };
    }

    private static Order createOrder(Pizza pizza, String deliveryMethod, int quantity, String address, double totalAmount) {
        Order order = new Order();
        order.setId(orders.size() + 1);
        order.setPizza(pizza);
        order.setQuantity(quantity);
        order.setDeliveryMethod(deliveryMethod);

        if ("Delivery".equalsIgnoreCase(deliveryMethod)) {
            order.setDeliveryAddress(address != null ? address : "No address provided");
        }

        order.setTotalAmount(totalAmount);
        order.setOrderStatus(String.valueOf(OrderStatus.ORDERED));
        order.setOrderDate(LocalDateTime.now());
        System.out.println("-----------------------------------------");
        return order;
    }

    private static int getIntInput(int min, int max, String errorMessage) {
        int choice;
        while (true) {
            try {
                choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline
                if (choice >= min && choice <= max) {
                    return choice;
                }
            } catch (Exception e) {
                scanner.nextLine(); // Clear invalid input
            }
            System.out.println(errorMessage);
        }
    }

    private static void viewOrders() {
        if (orders.isEmpty()) {
            System.out.println("No orders have been placed yet.");
            return;
        }

        System.out.println("\n************** All Orders **************\n");
        for (Order order : orders) {
            System.out.println("Order ID: " + order.getId());
            System.out.println("Order State: " + order.getState().getClass().getSimpleName());
            System.out.println("----------------------------");
        }
    }

    private static void manageUserProfile() {
        System.out.println("\n************** Manage Customer Profile **************\n");
        System.out.print("Enter your phone number : ");
        String phoneNumber = scanner.next();

        Customer customer = customerService.getCustomerByPhoneNumber(phoneNumber);

        if (customer == null) {
            System.out.println("Customer with phone number " + phoneNumber + " not found.");
            return;
        }

        System.out.println("Customer Profile : ");
        System.out.println("Name : " + customer.getName());
        System.out.println("Phone Number : " + customer.getPhoneNumber());

        System.out.println("\nWhat would you like to do?");
        System.out.println("1. Update Name");
        System.out.println("2. Update Phone Number");
        System.out.println("3. Go Back");
        System.out.print("Choose an option : ");

        int choice = scanner.nextInt();

        switch (choice) {
            case 1:
                System.out.print("Enter new name: ");
                String newName = scanner.next();
                customer.setName(newName);
                System.out.println("Name updated successfully.");
                break;
            case 2:
                System.out.print("Enter new phone number: ");
                String newMobileNumber = scanner.next();
                customer.setPhoneNumber(newMobileNumber);
                System.out.println("Phone number updated successfully.");
                break;
            case 3:
                System.out.println("Returning to main menu...");
                return;
            default:
                System.out.println("Invalid choice. Returning to main menu...");
        }
    }

    private static void viewOrder() {
        System.out.println("\n************** View Order Status **************");
        System.out.print("Enter Order ID to check status : ");
        int orderId = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        // Find the order by ID in the static orders list
        Order order = findOrderById(orderId);

        if (order != null) {
            // Print detailed order information
            System.out.println("\nOrder Details:");
            System.out.println("Order ID : " + order.getId());
            System.out.println("Total Amount : " + order.getTotalAmount());
            System.out.println("Order Status : " + order.getState().getClass().getSimpleName());
        } else {
            System.out.println("Order with ID " + orderId + " not found.");
        }
    }

    private static Order findOrderById(int orderId) {
        for (Order order : orders) { // Assuming 'orders' is a static or class-level list of orders
            if (order.getId() == orderId) {
                return order;
            }
        }
        return null; // Return null if no matching order is found
    }

    private static void trackOrderStatus(Order order, String deliveryMethod) {
        Thread orderTrackingThread = new Thread(() -> {
            try {
                Thread.sleep(30000);
                order.setState(new PreparationState());
                order.handleOrder();

                if (deliveryMethod.equals("Delivery")) {
                    Thread.sleep(30000);
                    order.setState(new DeliveryState());
                    order.handleOrder();

                    Thread.sleep(30000);
                    order.setState(new CompletedState());
                    order.handleOrder();

                } else {
                    Thread.sleep(30000);
                    order.setState(new CompletedState());
                    order.handleOrder();
                }

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        orderTrackingThread.start();
    }

    private static void handleFeedback() {
        System.out.println("\n************** Provide Feedback **************");

        // Filter completed orders
        List<Order> completedOrders = new ArrayList<>();

        for (Order order : orders) {
            if (order.getState().getClass().getSimpleName().equals("CompletedState")) {
                completedOrders.add(order);
            }
        }

        if (completedOrders.isEmpty()) {
            System.out.println("No completed orders available for feedback.");
            return;
        }

        System.out.println("Fetching your completed orders...");
        for (Order order : completedOrders) {
            System.out.println("Order ID: " + order.getId());
            System.out.println("----------------------------");
        }

        System.out.print("Enter the Order ID to provide feedback: ");
        int orderId = getIntInput(1, Integer.MAX_VALUE, "Invalid Order ID. Please try again.");

        Order selectedOrder = null;
        for (Order order : completedOrders) {
            if (order.getId() == orderId) {
                selectedOrder = order;
                break;
            }
        }

        if (selectedOrder == null) {
            System.out.println("Invalid Order ID. Returning to main menu.");
            return;
        }

        System.out.print("Enter your rating (1-5): ");
        int rating = getIntInput(1, 5, "Invalid rating. Please enter a value between 1 and 5.");

        System.out.print("Enter your comments: ");
        String comments = scanner.nextLine();

        Feedback feedback = new Feedback(
                selectedOrder.getId(),
                rating,
                comments,
                LocalDateTime.now().toString()
        );


        System.out.println("Thank you for your feedback! It has been recorded.");
    }
}
