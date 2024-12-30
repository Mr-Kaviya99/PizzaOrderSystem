package model.payment;
/**
 * author k2460782
 */

import model.Customer;

public interface PaymentStrategy {
    boolean pay(double amount);

    void applyDiscount(Customer customer, double discount);
}