package payment;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;


public class Payment{
    private String paymentId;
    private String bookingReference;
    private double amount;
    private String method;
    private String status;
    private String currency;
    private String transactionDate;
    private PaymentProcessor processor;
    
    public Payment (String bookingReference,double amount,String method,String currency){
        this.paymentId = UUID.randomUUID().toString();
        this.bookingReference = bookingReference;
        this.amount = amount;
        this.method = method;
        this.status = "Pending";
        this.currency = currency;
        updateTransactionTime();

        switch (method.toLowerCase()) {
            case "credit card":
                this.processor = new CreditCardProcessor();
                break;
            case "bank transfer":
                this.processor = new BankTransferProcessor();
                break;
            default:
                System.out.println("This Payment Method Isn't Available Right now !");
                this.processor = null;
        }
        
        
    }
    //to process the payment, we need to validate the details and then process the payment
    public boolean processPayment(String details) {
        if (processor != null && processor.validateDetails(details) && processor.processPayment(amount)) {
            this.status = "Confirmed";
            updateTransactionTime();
            return true;
        }
        this.status = "Failed";
        return false;
    }

    private void updateTransactionTime() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy, hh:mm a");
        this.transactionDate = LocalDateTime.now().format(formatter);
    }

    public void updateStatus(String status){
        this.status = status;
    }
    
    public String getPaymentId() {
        return paymentId;
    }
    public String getBookingReference() {
        return bookingReference;
    }
    public double getAmount() {
        return amount;
    }
    public String getMethod() {
        return method;
    }
    public String getStatus() {
        return status;
    }
    public String getCurrency() {
        return currency;
    }
    public String getTransactionDate() {
        return transactionDate;
    }
}

