package model.payment;
/**
 * author k2460782
 */

import model.Customer;

public class MobilePayment implements PaymentStrategy {

    private String mobileNumber;

    public MobilePayment(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    @Override
    public boolean pay(double amount) {
        System.out.println("Paying " + amount + " using Mobile Payment.");
        return true;
    }

    @Override
    public void applyDiscount(Customer customer, double discount) {
        System.out.println("Applying discount of " + discount + " on Mobile Payment.");
    }
}
