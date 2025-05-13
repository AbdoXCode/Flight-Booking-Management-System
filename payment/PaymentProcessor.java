package payment;

public interface PaymentProcessor {

    boolean validateDetails(String details);
    boolean processPayment(double amount);
    
}
