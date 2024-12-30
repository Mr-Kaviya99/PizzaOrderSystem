package model.payment;
/**
 * author k2460782
 */

import model.Customer;

public class CashPayment implements PaymentStrategy {

    @Override
    public boolean pay(double amount) {
        // Process payment using cash
        System.out.println("Paying " + amount + " using Cash.");
        // Logic for cash payment
        return true;
    }

    @Override
    public void applyDiscount(Customer customer, double discount) {
        // Cash payments may not have discounts, or may have specific ones
        System.out.println("No discount available for cash payments.");
    }
}

