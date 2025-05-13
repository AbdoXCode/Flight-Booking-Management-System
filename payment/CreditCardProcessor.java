package payment;

public class CreditCardProcessor implements PaymentProcessor{

    @Override
    public boolean validateDetails(String details) {
        return true;
    }

    @Override
    public boolean processPayment(double amount) {
        return amount > 0;
    }

    
    
}
