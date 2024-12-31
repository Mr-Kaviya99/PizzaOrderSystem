package model.payment;
/**
 * author k2460782
 */

import model.Customer;

public class CashPayment implements PaymentStrategy {

    @Override
    public boolean pay(double amount) {
        System.out.println("Paying " + amount + " using Cash.");
        return true;
    }

    @Override
    public void applyDiscount(Customer customer, double discount) {
        System.out.println("No discount available for cash payments.");
    }
}

