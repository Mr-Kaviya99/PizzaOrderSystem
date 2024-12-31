package model.payment;
/**
 * author k2460782
 */

import model.Customer;

public class CreditCardPayment implements PaymentStrategy {
    private String cardNumber;
    private String cardHolderName;

    public CreditCardPayment(String cardNumber, String cardHolderName) {
        this.cardNumber = cardNumber;
        this.cardHolderName = cardHolderName;
    }

    @Override
    public boolean pay(double amount) {
        System.out.println("Paying " + amount + " using Credit Card.");
        return true;
    }

    @Override
    public void applyDiscount(Customer customer, double discount) {
        System.out.println("Applying discount of " + discount + " on Credit Card payment.");
    }
}
