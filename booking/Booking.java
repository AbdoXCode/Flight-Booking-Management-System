package booking;
import java.util.ArrayList;
import users.Customer;
import flight.Domestic;
import flight.Flight;
import flight.International;

public class Booking {
    private String bookingReference;
    private Customer customer; 
    private Flight flight;
    private ArrayList<Passenger> passengers;
    private ArrayList<String> seatSelections;
    private String status;
    private String paymentStatus;

    public Booking(String bookingReference, Customer customer, Flight flight, String status, String paymentStatus) {
        this.bookingReference = bookingReference;
        this.customer = customer;
        this.flight = flight;
        this.passengers = new ArrayList<>();
        this.seatSelections = new ArrayList<>();
        this.status = status;
        this.paymentStatus = paymentStatus;
    }

    public void addPassenger(Passenger passenger, String seatType) {
        passengers.add(passenger);
        seatSelections.add(seatType);

        flight.reserveSeat(seatType);
        
    }
    public void addPassengerwprint(Passenger passenger, String seatType) {
        if (flight.checkAvailability(seatType)) {
            passengers.add(passenger);
            seatSelections.add(seatType);

            flight.reserveSeat(seatType);
        
            System.out.println("Passenger " + passenger.getName() + " added to booking " + bookingReference);
            
        }else{
            System.out.println("Seat " + seatType + " is not available for booking.");
        }
    }
    
    public double calculateTotalPrice(String seatType) {
        double totalPrice = 0;
            if (flight instanceof Domestic) {
                totalPrice += ((Domestic) flight).totalprice(seatType);
            }else if(flight instanceof International){
                totalPrice += ((International) flight).totalprice(seatType);
            }
            
        return totalPrice;
    }
    
    public void confirmBooking() {
        this.status = "Confirmed";
        System.out.println("Booking " + bookingReference + " confirmed successfully.");
        setStatus("Confirmed");
        setPaymentStatus("Paid");
    }
    
    public void cancelBooking(int seatNumber,String seatType) {
        this.status = "Cancelled";
        this.paymentStatus = "Refunded";
        flight.cancelReservation(seatNumber, seatType);
        System.out.println("Booking " + bookingReference + " cancelled successfully.");
        
        
    }
    public void cancelBookingwn() {
        this.status = "Cancelled";
        this.paymentStatus = "Refunded";
        System.out.println("Booking " + bookingReference + " cancelled successfully.");
        
        
    }
    
    public void generateItinerary() {
        System.out.println("===== ITINERARY =====");
        System.out.println("Booking Reference: " + bookingReference);
        System.out.println("Customer: " + customer.getName());
        System.out.println("Status: " + status);
        System.out.println("Payment Status: " + paymentStatus);
        // Add more details about flight and passengers
    }
    public String getBookingReference() {
        return bookingReference;
    }
    public ArrayList<Passenger> getPassengers() {
        return passengers;
    }
    public ArrayList<String> getSeatSelections() {
        return seatSelections;
    }
    public String getStatus() {
        return status;
    }
    public String getPaymentStatus() {
        return paymentStatus;
    }
    public Customer getCustomer() {
        return customer;
    }

    public Flight getFlight() {
        return flight;
    }
    public void setStatus(String status){
        this.status = status;
    }
    public void setPaymentStatus(String paymentStatus){
        this.paymentStatus = paymentStatus;
    }

    public boolean isCancelled(){
        if (status.equals("Cancelled")) {
            return true;
        }
        return false;
    }

    
    
}