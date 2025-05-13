import java.util.ArrayList;
import java.util.Scanner;

import Files.FileManager;
import booking.Booking;
import booking.Passenger;
import flight.Flight;
import users.Administrator;
import users.Agent;
import users.Customer;
import users.User;

public class BookingSystem {

    Scanner scanner = new Scanner(System.in);
    private User currentUser;
    private boolean logedin = false;
    

    FileManager fm = new FileManager();
    ArrayList<User> users;
    ArrayList<Flight> flights;
    ArrayList<Booking> bookings;
    ArrayList<Passenger> passengers;

    public BookingSystem() {
        loaders();
        addSampleData();

        fm.writeSystemLog("Flight Booking System initialized successfully.");
        
    }

    //👽load everything from the txt files
    public void loaders() {
        users = fm.loadUsers("resources\\users.txt");
        flights = fm.loadFlights("resources\\flights.txt");
        bookings = fm.loadBookings("resources\\bookings.txt");
        passengers = fm.loadPassengers("resources\\passengers.txt");


    }

    public boolean isMaintanceMode(){
        boolean temp = false;
        try(
            Scanner logScanner = new Scanner(new java.io.File("resources\\isMaintainceMode.txt"))
        ) {
            while (logScanner.hasNextLine()) {
                String logEntry = logScanner.nextLine();
                if (logEntry.equals("false")) {
                    fm.writeSystemLog("Maintenance Mode in now disabled the system is Open");   


                    temp =  false;
                }
                else if (logEntry.equals("true")){

                fm.writeSystemLog("Maintenance Mode in now Activated the system is Closed");

                    temp =  true;
                }
            }
        } catch (Exception e) {
            System.out.println("err "+e);
        }

        return temp;
        
    }

     private void addSampleData() {
        if (users.isEmpty()) {
            // Admin
            users.add(new Administrator(1, "admin", "admin123", "System Admin", "admin@flights.com", "123456789", 1, 3));
            
            // Agent
            users.add(new Agent(2, "agent", "agent123", "Travel Agent", "agent@flights.com", "987654321", 1, "International", 0.05));
            
            // Customer
            users.add(new Customer(3, "customer", "cust123", "John Doe", "john@example.com", "555123456", 1, "123 Main St", "", "Window seat"));

            fm.saveUsers(users);
            fm.writeSystemLog("Default users (admin, agent, customer) created and saved to the system.");
        }
     }



    public void start() {
        boolean running = true;
        
        if (!isMaintanceMode()) {
            while (running) {
            if (currentUser == null) {
                
                displayLoginMenu();
            } else if (currentUser instanceof Customer) {
                displayCustomerMenu();
            } else if (currentUser instanceof Agent) {
                displayAgentMenu();
            } else if (currentUser instanceof Administrator) {
                displayAdminMenu();
            }
        }
    }else{
        System.out.println("The System is now Sleeping...");
        System.out.println("Do you want to wake it up ?(yes/no)");

        String temp = scanner.nextLine();

        if (temp.equals("yes")) {
            fm.writeMaintaninceMode("false");
        }else if (temp.equals("no")) {
            fm.writeMaintaninceMode("true");
        }{

        }
    }
        
    }

    private void displayLoginMenu() {
        //👽the system needs to reload the files to be up to date after any changes made to them
        loaders();

        System.out.println("\n===== FLIGHT BOOKING SYSTEM =====");
        System.out.println("1. Login");
        System.out.println("2. Exit");
        System.out.print("Enter your choice: ");

        //👽 Error handling incase of invalid input
        int choice;
        try {
            choice = scanner.nextInt();
        } catch (java.util.InputMismatchException e) {
            scanner.nextLine(); // Clear invalid input
            choice = 0; // Set to invalid choice
        }
        
        switch (choice) {
            case 1:
                login();
                break;
            case 2:
                System.out.println("Thank you for using the Flight Booking System. Goodbye!");
                System.exit(0);
                break;
            default:
                System.out.println("Invalid choice. Please try again.");
        }
    }
    private void login() {
        System.out.print("Enter username: ");
        scanner.nextLine();
        String username = scanner.nextLine();
        
        System.out.print("Enter password: ");
        String password = scanner.nextLine();
        
        boolean userFound = false;
        
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                userFound = true;
                if (user.getPassword().equals(password)) {
                    currentUser = user;
                    System.out.println("Welcome, " + user.getName() + "!");
                    logedin = true;
                    fm.writeSystemLog("User " + user.getUsername() + " logged in successfully.");
                    return;
                } else {
                    System.out.println("Incorrect password. Please try again.");
                    fm.writeSystemLog("User " + user.getUsername() + " tried to log in with an incorrect password.");
                    return;
                }
            }
        }
        
        if (!userFound) {
            System.out.println("Username not found. Please try again.");
            fm.writeSystemLog("Login attempt failed - username '" + username + "' not found.");
        }
    }

    private void logout() {
        if (currentUser != null) {
            currentUser.logout();
            fm.writeSystemLog("User " + currentUser.getUsername() + " logged out successfully.");
            currentUser = null;
            System.out.println("Logged out successfully.");
        }

    }

    private void displayCustomerMenu(){
        Customer customer = (Customer) currentUser;

        if (logedin) {
        System.out.println("\n===== CUSTOMER MENU =====");
        System.out.println("1. Search Flights");
        System.out.println("2. Create Booking");
        System.out.println("3. View My Bookings");
        System.out.println("4. Cancel Booking");
        System.out.println("5. Update Profile");
        System.out.println("6. Logout");
        System.out.print("Enter your choice: ");

            //👽 make sure the user choose a valid option before continuing
            try {
                int choice = scanner.nextInt();
                scanner.nextLine(); // Clear buffer

                switch (choice) {
                    case 1:
                        customer.searchFlights();
                        break;
                    case 2:
                        customer.createBooking(customer);
                        break;
                    case 3:
                        customer.viewBookings(customer);
                        break;
                    case 4:
                        customer.cancelBooking(customer);
                        break;
                    case 5:
                        customer.updateProfile();
                        break;
                    case 6:
                        logout();
                        break;
                    default:
                        System.out.println("Invalid choice. Please enter a number between 1-6.");
            }
        } catch (java.util.InputMismatchException e) {
            System.out.println("Invalid input. Please enter a number.");
            scanner.nextLine(); // Clear invalid input
        }
        }
    }

    private void displayAgentMenu() {
        Agent agent = (Agent) currentUser;
        
        System.out.println("\n===== AGENT MENU =====");
        System.out.println("1. Search Flights");
        System.out.println("2. Create Booking for Customer");
        System.out.println("3. Modify Booking");
        System.out.println("4. Manage Flights");
        System.out.println("5. Generate Reports");
        System.out.println("6. Logout");
        System.out.print("Enter your choice: ");
        int choice;

        //👽 make sure the user choose a valid option before continuing
        try {
            choice = scanner.nextInt();
            scanner.nextLine(); // consume newline
        } catch (java.util.InputMismatchException e) {
            System.out.println("Invalid input. Please enter a number.");
            scanner.nextLine(); // Clear invalid input
            choice = 0; // Set to invalid choice
        }
        switch (choice) {
            case 1:
                agent.searchFlights();
                break;
            case 2:
                agent.createBookingForCustomer();
                break;
            case 3:
                agent.modifyBooking();
                break;
            case 4:
                agent.manageFlights();
                break;
            case 5:
                agent.generateReports();
                break;
            case 6:
                logout();
                break;
            default:
                System.out.println("Invalid choice. Please try again.");
        }
    }

    private void displayAdminMenu(){
        Administrator admin = (Administrator) currentUser;
        
        System.out.println("\n===== ADMINISTRATOR MENU =====");
        System.out.println("1. Create User");
        System.out.println("2. Manage User Access");
        System.out.println("3. View System Logs");
        System.out.println("4. Modify System Settings");
        System.out.println("5. Delete User");
        System.out.println("6. Logout");
        System.out.print("Enter your choice: ");

        //👽 make sure the user choose a valid option before continuing
        int choice;
        try {
            choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline character
        } catch (java.util.InputMismatchException e) {
            System.out.println("Invalid input. Please enter a number.");
            scanner.nextLine(); // Clear invalid input
            choice = 0; // Set to invalid choice
        }

        switch (choice) {
            case 1:
                admin.createUser();
                break;
            case 2:
                admin.manageUserAccess();
                break;
            case 3:
                admin.viewSystemLogs();
                fm.writeSystemLog("Admin has accessed the logs");
                break;
            case 4:
                admin.modifySystemSettings();
                if (isMaintanceMode()) {
                    System.exit(0);
                }
                break;
            case 5:
                System.out.print("Enter the username of the user you want to delete: ");
                String usertoDelete = scanner.nextLine();
                if (usertoDelete.equals("admin")) {
                    System.out.println("Cannot delete the admin user.");
                    break;
                    
                }
                
                admin.deleteUser(usertoDelete);
                break;
            case 6:
                logout();
                break;
            default:
                System.out.println("Invalid choice. Please try again.");
        }
    }
    

}