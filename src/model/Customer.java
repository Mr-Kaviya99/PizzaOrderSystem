package model;
/**
 * author k2460782
 */

import model.payment.CreditCardPayment;
import model.payment.PaymentStrategy;

public class Customer {
    private String name;
    private String phoneNumber;
    private String email;
    private int loyaltyPoints;
    private PaymentStrategy paymentStrategy;

    public Customer(String name, String phoneNumber, String email) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.loyaltyPoints = 0;
    }

    public String getName() {
        return name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public int getLoyaltyPoints() {
        return loyaltyPoints;
    }

    public void addLoyaltyPoints(int points) {
        this.loyaltyPoints += points;
    }

    public void setPaymentStrategy(PaymentStrategy paymentStrategy) {
        this.paymentStrategy = paymentStrategy;
    }

    public boolean makePayment(double amount) {
        double discount = 0;
        if (this.paymentStrategy instanceof CreditCardPayment) {
            discount = 10;
        }
        paymentStrategy.applyDiscount(this, discount);

        return paymentStrategy.pay(amount - discount);
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "Customer{name='" + name + "', phoneNumber='" + phoneNumber + "', email='" + email + "', loyaltyPoints=" + loyaltyPoints + '}';
    }
}
