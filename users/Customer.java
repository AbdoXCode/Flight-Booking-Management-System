package users;

import java.util.ArrayList;
import java.util.Scanner;

import Files.FileManager;
import booking.Booking;
import booking.Passenger;
import flight.Flight;
import payment.Payment;

public class Customer extends User{

    private int customerId;
    private String address;
    private String bookingHistory;
    private String preferences;

    Scanner scanner = new Scanner(System.in);
    FileManager fileManager = new FileManager();
    ArrayList<Flight> flights = fileManager.loadFlights("resources\\flights.txt");

    ArrayList<Passenger> passengers = fileManager.loadPassengers("resources\\passengers.txt");

    Agent agent = new Agent(0, "", "", "", "", "", 0, "", 0.0);

    public Customer(int userId, String username, String password, String name, String email, String contactInfo,int customerId,String address,String bookingHistory,String preferences) {
        super(userId, username, password, name, email, contactInfo);
        this.customerId = customerId;
        this.address = address;
        this.bookingHistory = bookingHistory;
        this.preferences = preferences;
    }

   


    public void searchFlights(){
        System.out.println("\n===== SEARCH FLIGHTS =====");
        System.out.print("Enter the origin city: ");
        String origin = scanner.nextLine();
        System.out.print("Enter the destination city: ");
        String destination = scanner.nextLine();

        ArrayList<Flight> matchingFlights = new ArrayList<>();

        for(Flight flight : flights) {
            if(flight.getOrigin().equalsIgnoreCase(origin) && flight.getDestination().equalsIgnoreCase(destination)) {
                matchingFlights.add(flight);
            }
        }
        if(matchingFlights.isEmpty()) {
            System.out.println("No flights found for the given criteria.");
        } else {
            System.out.println("Matching Flights:");
            agent.displayFlights(matchingFlights);
            
        }
        fm.writeSystemLog("the user has searched for flights");

    }

    public void createBooking(Customer customer){
        ArrayList<Booking> bookings = fileManager.loadBookings("resources\\bookings.txt");
        System.out.println("\n===== CREATE BOOKING =====");
        System.out.print("Enter origin city: ");
        String origin = scanner.nextLine();
        
        System.out.print("Enter destination city: ");
        String destination = scanner.nextLine();

        ArrayList<Flight> matchingFlights = new ArrayList<>();
        
        for (Flight flight : flights) {
            if (flight.getOrigin().equalsIgnoreCase(origin) && 
                flight.getDestination().equalsIgnoreCase(destination)) {
                matchingFlights.add(flight);
            }
        }

        if (matchingFlights.isEmpty()) {
            System.out.println("No flights found matching your criteria.");
            fm.writeSystemLog("the user didnt find any flights matching the criteria");
            return;
        }

        System.out.println("\nAvailable flights:");
        agent.displayFlights(matchingFlights);

        System.out.print("\nEnter the flight number you want to book: ");
        int flightNumber = scanner.nextInt();

        Flight selectedFlight = null;
        for (Flight flight : matchingFlights) {
            if (flight.getFlightNumber() == flightNumber) {
                selectedFlight = flight;
                break;
            }
        }
        if (selectedFlight == null) {
            System.out.println("Invalid flight number.");
            fm.writeSystemLog("the user entered an invalid flight number");
            return;
        }

        System.out.println("\nAvailable seat classes:");
        System.out.println("1. Economy - $" + selectedFlight.getEconomyPrice());
        System.out.println("2. Business - $" + selectedFlight.getBusinessPrice());
        System.out.println("3. First Class - $" + selectedFlight.getFirstClassPrice());

        System.out.print("Enter your seat class choice (1-3): ");
        int seatChoice = scanner.nextInt();
        scanner.nextLine(); // Consume newline character

        String seatType;
        switch (seatChoice) {
            case 1:
                seatType = "Economy";
                break;
            case 2:
                seatType = "Business";
                break;
            case 3:
                seatType = "First Class";
                break;
            default:
                System.out.println("Invalid choice. Defaulting to Economy.");
                seatType = "Economy";
        }

        if (!selectedFlight.checkAvailability(seatType)) {
            System.out.println("Sorry, no " + seatType + " seats available for this flight.");
            fm.writeSystemLog("the user entered an invalid seat type");
            return;
        }

        String bookingReference = generateBookingReference();

        Booking booking = new Booking(bookingReference, customer, selectedFlight, "Reserved", "Pending");

        System.out.println("Please,Enter passenger details: ");
        System.out.print("Name: ");
        String name = scanner.nextLine();

        System.out.print("Passport Number: ");
        String passportNumber = scanner.nextLine();

        System.out.print("Date of Birth (DD/MM/YYYY): ");
        String dateOfBirth = scanner.nextLine();

        System.out.print("Special Requests (or press Enter for none): ");
        String specialRequests = scanner.nextLine();
        specialRequests = specialRequests.isEmpty() ? "None" : specialRequests;

        Passenger passenger = new Passenger((int)(System.currentTimeMillis() % 10000), name, passportNumber, dateOfBirth, specialRequests);

        passengers.add(passenger);
        fileManager.savePassengers(passengers);
        System.out.println("Passenger Saved successfully!");
        
        booking.addPassengerwprint(passenger, seatType);
        

        double totalprice = booking.calculateTotalPrice(seatType);
        System.out.println("\nTotal Price: $" + totalprice);

        System.out.println("\nSelect payment method:");
        System.out.println("1. Credit Card");
        System.out.println("2. Bank Transfer");

        System.out.print("Enter your choice (1-2): ");
        //👽 make sure the user choose a valid option before continuing
        int paymentChoice;
        try {
            paymentChoice = scanner.nextInt();
        } catch (java.util.InputMismatchException e) {
            scanner.nextLine(); // Clear invalid input
            paymentChoice = 1; // Default to credit card
            System.out.println("Invalid input. Defaulting to Credit Card.");
        }
        String paymentMethod;
        

        switch (paymentChoice) {
            case 1:
                paymentMethod = "Credit Card";
                break;
            case 2:
                paymentMethod = "Bank Transfer";
                break;
            default:
                System.out.println("Invalid choice. Defaulting to Credit Card.");
                paymentMethod = "Credit Card";
        }

        scanner.nextLine(); // Consume newline character
        Payment payment = new Payment(bookingReference, totalprice, paymentMethod, "EGP");
        String paymentDetails = null;
        if (paymentMethod .equals("Credit Card")) {
            System.out.print("Enter payment details: ");
            paymentDetails = scanner.nextLine();  

        }else if (paymentMethod .equals("Bank Transfer")) {
            System.out.print("Enter Bank number: ");
            paymentDetails = scanner.nextLine(); 
        }

        if (payment.processPayment(paymentDetails)) {
            System.out.println("\nPayment successful!");
            booking.confirmBooking();
            bookings.add(booking);
            fileManager.saveBookings(bookings);
            booking.generateItinerary();
        }else {
            System.out.println("\nPayment failed. Booking not confirmed.");
        }

        
    }

    public String generateBookingReference(){
        return "BK" + System.currentTimeMillis() % 10000;

    }

    public void viewBookings(Customer customer) {
        ArrayList<Booking> bookings = fileManager.loadBookings("resources\\bookings.txt");
        System.out.println("\n===== VIEW BOOKINGS =====");

        ArrayList<Booking> customerBookings = new ArrayList<>();
        for (Booking booking : bookings) {
            if (booking.getCustomer().getUserId() == customer.getUserId()) {
                customerBookings.add(booking);
            }
        }

        if (customerBookings.isEmpty()) {
            System.out.println("You have no bookings.");
        } else {
            System.out.println("Found " + customerBookings.size() + " booking(s):");
            displayBookings(customerBookings);
        }
        fm.writeSystemLog("the user has viewed his bookings");
    }
    
    public void cancelBooking(Customer customer) {
    ArrayList<Booking> bookings = fileManager.loadBookings("resources\\bookings.txt");
    System.out.println("\n===== CANCEL BOOKING =====");

    // Fetch the customer's bookings
    System.out.println("Booked Flights By the Customer: ");
    ArrayList<Booking> customerBookings = new ArrayList<>();
    for (Booking booking : bookings) {
        if (booking.getCustomer().getUserId() == customer.getUserId() && !booking.isCancelled()) {
            customerBookings.add(booking); // Only add non-canceled bookings
        }
    }

    if (customerBookings.isEmpty()) {
        System.out.println("You have no bookings to cancel.");
        fm.writeSystemLog("the user has no bookings to cancel");
        return;
    }

    // Display the non-canceled bookings
    displayBookings(customerBookings);

    System.out.print("\nEnter the booking reference you want to cancel: ");
    String reference = scanner.nextLine();

    Booking bookingToCancel = null;
    for (Booking booking : customerBookings) {
        if (booking.getBookingReference().equals(reference)) {
            bookingToCancel = booking;
            break;
        }
    }

    if (bookingToCancel == null) {
        System.out.println("Invalid booking reference.");
        fm.writeSystemLog("the user entered an invalid booking reference");
        return;
    }

    // Cancel the booking
    
    /// 3awza ta3del ya omar
    bookingToCancel.cancelBookingwn();

    // Update the bookings list and save it back to the file
    fileManager.saveBookings(bookings);

    System.out.println("Booking cancelled successfully.");
}


    private void displayBookings(ArrayList<Booking> bookingList) {
        for (Booking booking : bookingList) {
            System.out.println("\n------------------------------------------");
            System.out.println("Booking Reference: " + booking.getBookingReference());
            System.out.println("Flight: " + booking.getFlight().getAirline() + " " + booking.getFlight().getFlightNumber());
            System.out.println("Route: " + booking.getFlight().getOrigin() + " to " + booking.getFlight().getDestination());
            System.out.println("Status: " + booking.getStatus());
            System.out.println("Payment Status: " + booking.getPaymentStatus());
            System.out.println("------------------------------------------");
        }
        fm.writeSystemLog("the user has viewed his bookings");
    }
    public int getCustomerId(){
        return customerId;
    }
    public String getBookingHistory() {
        return bookingHistory;
    }
    public String getAddress(){
        return address;
    }
    public String getPreferences(){
        return preferences;
    }
    
}