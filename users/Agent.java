package users;

import java.util.ArrayList;
import java.util.Scanner;

import Files.FileManager;
import booking.Booking;
import booking.Passenger;
import flight.Domestic;
import flight.Flight;
import flight.International;

public class Agent extends User {
    
    private int agentId;
    private String department;
    private double commission;

    

    FileManager fileManager = new FileManager();
    ArrayList<Flight> flights = fileManager.loadFlights("resources\\flights.txt");

    Scanner scanner = new Scanner(System.in);

    public Agent(int userId, String username, String password, String name, String email, String contactInfo,int agentId,String department,double commission) {
        super(userId, username, password, name, email, contactInfo);
        this.agentId = agentId;
        this.department = department;
        this.commission = commission;
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
            displayFlights(matchingFlights);
            
        }
        fm.writeSystemLog("the agent has searched for the flights");

    }
    public void manageFlights() {
        System.out.println("\n===== MANAGE FLIGHTS =====");
        System.out.println("1. Add New Flight");
        System.out.println("2. Update Flight Schedule");
        System.out.println("3. View All Flights");
        System.out.println("4. Back to Main Menu");
        System.out.print("Enter your choice: ");

        //👽 make sure the user choose a valid option before continuing
        int choice;
        try {
            choice = scanner.nextInt();
        } catch (java.util.InputMismatchException e) {
            scanner.nextLine(); // Clear invalid input
            choice = 0; // Set to invalid choice
        }
        
        switch (choice) {
            case 1:
                addNewFlight();
                break;
            case 2:
                updateFlightSchedule();
                break;
            case 3:
                displayAllFlights();
                break;
            case 4:
                return;
            default:
                System.out.println("Invalid choice.");
        }
    }

    private void addNewFlight() {
        System.out.println("\n===== ADD NEW FLIGHT =====");

        System.out.print("Enter flight number: ");
        int flightNumber = scanner.nextInt();
        scanner.nextLine();

        for (Flight flight : flights) {
            if (flight.getFlightNumber() == flightNumber) {
                System.out.println("Flight number already exists.");
                fm.writeSystemLog("a new flight already exists with the same flight number");
                return;
            }
        }

        //👽 easier to use with error handling
        String flightType;
        while (true) {
            System.out.println("Enter the type of the flight: ");
            System.out.println("1. Domestic");
            System.out.println("2. International");
            System.out.print("Enter choice: ");

            try {
                int choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice) {
                    case 1:
                        flightType = "Domestic";
                        break;
                    case 2:
                        flightType = "International";
                        break;
                    default:
                        System.out.println("Invalid choice. Please enter 1 or 2.");
                        continue;
                }
                break;

            } catch (java.util.InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number (1 or 2).");
                scanner.nextLine();
            }
        }




        System.out.print("Enter airline: ");
        String airline = scanner.nextLine();
        
        System.out.print("Enter origin: ");
        String origin = scanner.nextLine();
        
        System.out.print("Enter destination: ");
        String destination = scanner.nextLine();
        
        System.out.print("Enter number of Economy seats: ");
        int economySeats = scanner.nextInt();
        
        System.out.print("Enter number of Business seats: ");
        int businessSeats = scanner.nextInt();
        
        System.out.print("Enter number of First Class seats: ");
        int firstClassSeats = scanner.nextInt();

        scanner.nextLine();
        System.out.print("Enter the departure date (YYYY-MM-DD hh:mm AM/PM): ");
        String departureDate = scanner.nextLine();

        System.out.print("Enter the arrival date (YYYY-MM-DD hh:mm AM/PM): ");
        String arrivalDate = scanner.nextLine();

        System.out.print("Enter Economy price: ");
        double economyPrice = scanner.nextDouble();
        
        System.out.print("Enter Business price: ");
        double businessPrice = scanner.nextDouble();
        
        System.out.print("Enter First Class price: ");
        double firstClassPrice = scanner.nextDouble();

        if (flightType.equalsIgnoreCase("Domestic")) {
            Domestic domestic = new Domestic(flightNumber, airline, origin, destination, economySeats, businessSeats, firstClassSeats, departureDate, arrivalDate, economyPrice, businessPrice, firstClassPrice);
            flights.add(domestic);
        } else if (flightType.equalsIgnoreCase("International")) {
            International international = new International(flightNumber, airline, origin, destination, economySeats, businessSeats, firstClassSeats, departureDate, arrivalDate, economyPrice, businessPrice, firstClassPrice);
            flights.add(international);
        }

        fileManager.saveFlights(flights);
        
        System.out.println("Flight added successfully.");

    }
    
    private void displayAllFlights() {
        if (flights.isEmpty()) {
            System.out.println("No flights in the system.");
        } else {
            displayFlights(flights);
        }
    }
    protected void displayFlights(ArrayList<Flight> flightList) {
        // Header format for the table
        String headerFormat = "| %-15s | %-10s | %-12s | %-10s | %-15s | %-15s | %-15s | %-10s | %-10s | %-12s | %-15s | %-15s | %-15s | %-15s |%n";
        // Row format for the table
        String rowFormat = "| %-15s | %-10.2f | %-12s | %-10d | %-15s | %-15s | %-15s | %-10d | %-10d | %-12d | %-15s | %-15s | %-15.2f | %-15.2f | %-15.2f |%n";
    
        System.out.println("\n------------------------------------------------------------------------------------------------------------");
        System.out.printf(headerFormat, 
                          "Flight Type", "Tax", "Visa Required", "Flight ID", "Airline", "Origin", "Destination", 
                          "Economy Seats", "Business Seats", "First Class Seats", "Departure", "Arrival", 
                          "Economy Price", "Business Price", "First Class Price");
        System.out.println("------------------------------------------------------------------------------------------------------------");
    
        // Loop through each flight and display its details
        for (Flight flight : flightList) {
            String flightType = flight instanceof Domestic ? "Domestic" : "International";
            int flightNumber = flight.getFlightNumber();
            String airline = flight.getAirline();
            String origin = flight.getOrigin();
            String destination = flight.getDestination();
            String departureTime = flight.getDepartureTime();
            String arrivalTime = flight.getArrivalTime();
            double economyPrice = flight.getEconomyPrice();
            double businessPrice = flight.getBusinessPrice();
            double firstClassPrice = flight.getFirstClassPrice();
            int economySeats = 0, businessSeats = 0, firstClassSeats = 0;
    
            // Depending on the flight type, get the seat count and flight specifics
            if (flight instanceof Domestic) {
                Domestic domesticFlight = (Domestic) flight;
                economySeats = domesticFlight.getEconomySeatCount();
                businessSeats = domesticFlight.getBusinessSeatCount();
                firstClassSeats = domesticFlight.getFirstClassSeatCount();
                System.out.printf(rowFormat, flightType, domesticFlight.getTax(), domesticFlight.getRequiresVisa(), flightNumber, airline, origin, destination, 
                                  economySeats, businessSeats, firstClassSeats, departureTime, arrivalTime, economyPrice, businessPrice, firstClassPrice);
            } else if (flight instanceof International) {
                International internationalFlight = (International) flight;
                economySeats = internationalFlight.getEconomySeatCount();
                businessSeats = internationalFlight.getBusinessSeatCount();
                firstClassSeats = internationalFlight.getFirstClassSeatCount();
                System.out.printf(rowFormat, flightType, internationalFlight.getTax(), internationalFlight.getRequiresVisa(), flightNumber, airline, origin, destination, 
                                  economySeats, businessSeats, firstClassSeats, departureTime, arrivalTime, economyPrice, businessPrice, firstClassPrice);
            }
        }
    
        System.out.println("------------------------------------------------------------------------------------------------------------");
    }
    
    
    private void updateFlightSchedule(){
        System.out.println("\n===== UPDATE FLIGHT SCHEDULE =====");

        displayAllFlights();

        System.out.print("\nEnter flight number to update: ");
        int flightNumber = scanner.nextInt();

        Flight flightToUpdate = null;

        for (Flight flight : flights) {
            if (flight.getFlightNumber() == flightNumber) {
                flightToUpdate = flight;
                break;
            }
        }
        if (flightToUpdate == null) {
            System.out.println("Flight not found.");
            return;
        }


        scanner.nextLine();
        
        System.out.print("Enter the departure date (YYYY-MM-DD-hh:mm AM/PM): ");
        String departureDate = scanner.nextLine();


        System.out.print("Enter the arrival date (YYYY-MM-DD-hh:mm AM/PM): ");
        String arrivalDate = scanner.nextLine();

        flightToUpdate.updateSchedule(departureDate,arrivalDate);
        fileManager.saveFlights(flights);

    }
    
    public void createBookingForCustomer(){
        System.out.println("\n===== CREATE BOOKING FOR CUSTOMER =====");
        System.out.print("Enter customer ID: ");
        int customerId = scanner.nextInt();
        scanner.nextLine();

        Customer customerToBook = null;

        for (User user : fileManager.loadUsers("resources\\users.txt")) {
            if (user.getUserId() == customerId) {
                customerToBook = (Customer) user;
                break;
            }
        }

        if (customerToBook == null) {
            System.out.println("Customer not found.");
            return;
        }

        customerToBook.createBooking(customerToBook);
    }

    public void modifyBooking() {
    System.out.println("\n===== MODIFY BOOKING =====");

    ArrayList<Booking> bookings = fileManager.loadBookings("resources\\bookings.txt");


    System.out.print("Enter booking reference to modify: ");
    String reference = scanner.nextLine();

    Booking bookingToModify = null;
    for (Booking booking : bookings) {
        if (booking.getBookingReference().equals(reference)) {
            bookingToModify = booking;
            break;
        }
    }

    if (bookingToModify == null) {
        System.out.println("Booking not found.");
        return;
    }

    System.out.print("Booking is found: ");
    System.out.println(bookingToModify.getBookingReference());

    System.out.println("\nWhat would you like to modify?");
  
    System.out.println("1. Update passenger info");
    System.out.println("2. Change Payment Status");
    System.out.println("3. Change Booking Status");
    System.out.println("4. Cancel Booking");
    System.out.print("Enter choice: ");
    int choice = scanner.nextInt();
    scanner.nextLine(); // consume newline

    switch (choice) {
        case 1:
            System.out.println("Passengers:");
            for (int i = 0; i < bookingToModify.getPassengers().size(); i++) {
                System.out.println((i + 1) + ". " + bookingToModify.getPassengers().get(i).getName());
            }

            System.out.print("Enter the index of the passenger you want to edit: ");
            int passengerIndex = scanner.nextInt() - 1;
            scanner.nextLine(); // consume newline

            if (passengerIndex >= 0 && passengerIndex < bookingToModify.getPassengers().size()) {
                System.out.print("Enter the new name for the passenger: ");
                String newName = scanner.nextLine().trim();

                // Update passenger in booking
                Passenger updatedPassenger = bookingToModify.getPassengers().get(passengerIndex);
                updatedPassenger.setName(newName);

                // Load all passengers
                ArrayList<Passenger> allPassengers = fileManager.loadPassengers("resources\\passengers.txt");

                // Find and update the passenger in the full list
                for (Passenger p : allPassengers) {
                    if (p.getPassengerId() == (updatedPassenger.getPassengerId())) {
                        p.setName(newName);
                        break;
                    }
                }

            // Save the updated passenger list
            fileManager.savePassengers(allPassengers);

            System.out.println("Passenger name updated successfully.");
        } else {
            System.out.println("Invalid passenger index.");
        }
            break;
        case 2:
            System.out.print("Enter new payment status (Paid/Pending): ");
            String newPaymentStatus = scanner.nextLine().trim();
            bookingToModify.setPaymentStatus(newPaymentStatus);
            break;
        case 3:
            System.out.print("Enter new Booking status: ");
            String newStatus = scanner.nextLine().trim();
            bookingToModify.setStatus(newStatus);
            break;
        case 4:
            System.out.print("Enter cancellation reason: ");
            String cancellationReason = scanner.nextLine().trim();
            bookingToModify.cancelBookingwn();
            System.out.println("We will focus in improving this Problem: "+ cancellationReason +" Soon");
            fileManager.saveBookings(bookings);
            break;
        default:
            System.out.println("Invalid choice.");
            return;
    }

    fileManager.saveBookings(bookings);
    System.out.println("Booking updated successfully.");
}


    public void generateReports(){       
                    ArrayList<Booking> bookings = fileManager.loadBookings("resources\\bookings.txt");
 
        try{

            for (Booking booking : bookings) {
            System.out.println("Booking Reference: " + booking.getBookingReference() + 
                            ", Customer: " + booking.getCustomer().getName() +
                            ", Flight: " + booking.getFlight().getFlightNumber() +
                            ", Status: " + booking.getStatus());
            }
        }catch(Exception e){
            System.out.println("I can't generate a report as there is a data deleted from the system recheck the data of the users.txt , bookings.txt, flights.txt and passengers.txt");
        }
    }


    public int getAgentId(){
        return agentId;
    }
    public String getDepartment(){
        return department;
    }
    public double getCommission(){
        return commission;
    }
}