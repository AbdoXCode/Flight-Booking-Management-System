package Files;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import booking.Booking;
import booking.Passenger;
import flight.Domestic;
import flight.Flight;
import flight.International;
import users.Administrator;
import users.Agent;
import users.Customer;
import users.User;




public class FileManager {
    private static final String USERS_FILE = "resources\\users.txt";
    private static final String FLIGHTS_FILE = "resources\\flights.txt";
    private static final String BOOKINGS_FILE = "resources\\bookings.txt";
    private static final String PASSENGERS_FILE = "resources\\passengers.txt";

    public void saveUsers(ArrayList<User> users) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(USERS_FILE))) {
            for (User user : users) {
                StringBuilder line = new StringBuilder();
                
                // Determine user type and write type-specific data
                if (user instanceof Customer) {
                    Customer customer = (Customer) user;
                    line.append("Customer,")
                        .append(customer.getUserId()).append(",")
                        .append(customer.getUsername()).append(",")
                        .append(customer.getPassword()).append(",")
                        .append(customer.getName()).append(",")
                        .append(customer.getEmail()).append(",")
                        .append(customer.getContactInfo()).append(",")
                        .append(customer.getCustomerId()).append(",")
                        .append(customer.getAddress()).append(",")
                        .append(customer.getBookingHistory()).append(",")
                        .append(customer.getPreferences());
                } else if (user instanceof Agent) {
                    Agent agent = (Agent) user;
                    line.append("Agent,")
                        .append(agent.getUserId()).append(",")
                        .append(agent.getUsername()).append(",")
                        .append(agent.getPassword()).append(",")
                        .append(agent.getName()).append(",")
                        .append(agent.getEmail()).append(",")
                        .append(agent.getContactInfo()).append(",")
                        .append(agent.getAgentId()).append(",")
                        .append(agent.getDepartment()).append(",")
                        .append(agent.getCommission());
                } else if (user instanceof Administrator) {
                    Administrator admin = (Administrator) user;
                    line.append("Administrator,")
                        .append(admin.getUserId()).append(",")
                        .append(admin.getUsername()).append(",")
                        .append(admin.getPassword()).append(",")
                        .append(admin.getName()).append(",")
                        .append(admin.getEmail()).append(",")
                        .append(admin.getContactInfo()).append(",")
                        .append(admin.getAdminId()).append(",")
                        .append(admin.getSecurityLevel());
                }
                
                writer.write(line.toString());
                writer.newLine();
            }
            // System.out.println("Users saved successfully to " + USERS_FILE);
        } catch (IOException e) {
            System.err.println("Error saving users: " + e.getMessage());
        }
        writeSystemLog("Users saved successfully to " + USERS_FILE);
    }
    public ArrayList<User> loadUsers(String path) {
        
        ArrayList<User> users = new ArrayList<>();

        try(BufferedReader usersReader = new BufferedReader(new FileReader(USERS_FILE))){
            String line;

            while ((line = usersReader.readLine()) != null) {
                String[] userDetails = line.split(",");

                String userType = userDetails[0];
                int userId = Integer.parseInt(userDetails[1]);
                String username = userDetails[2];
                String password = userDetails[3];
                String name = userDetails[4];
                String email = userDetails[5];
                String contactInfo = userDetails[6];
                
                if (userType.equals("Customer")) {

                    int customerId = Integer.parseInt(userDetails[7]);
                    String address = userDetails[8];
                    String bookingHistory = userDetails[9];
                    String preferences = userDetails[10];
                    
                    Customer customer = new Customer(userId, username, password, name, email, contactInfo, customerId, address, bookingHistory, preferences);

                    users.add(customer);


                } else if (userType.equals("Agent")) {
                    int agentId = Integer.parseInt(userDetails[7]);
                    String department = userDetails[8];
                    double commission = Double.parseDouble(userDetails[9]);
                    
                    Agent agent = new Agent(userId, username, password, name, email, contactInfo,agentId, department, commission);

                    users.add(agent);

                } else if (userType.equals("Administrator")) {
                    int adminId = Integer.parseInt(userDetails[7]);
                    int securityLevel = Integer.parseInt(userDetails[8]);
                    
                    Administrator admin = new Administrator(userId, username, password, name, email, contactInfo,adminId, securityLevel);

                    users.add(admin);

                }
                
                
            }

           
        }catch(Exception e){
            System.out.println("An Error Happened: "+e.getMessage());
        }

        return users;
    
    }

    public void saveFlights(ArrayList<Flight> flights) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FLIGHTS_FILE))) {
            for (Flight flight : flights) {
                StringBuilder line = new StringBuilder();
                
                // Determine user type and write type-specific data
                if (flight instanceof Domestic) {
                    Domestic domesticFlight = (Domestic) flight;
                    line.append("Domestic,")
                        .append(domesticFlight.getTax()).append(",")
                        .append(domesticFlight.getRequiresVisa()).append(",")
                        .append(domesticFlight.getFlightNumber()).append(",")
                        .append(domesticFlight.getAirline()).append(",")
                        .append(domesticFlight.getOrigin()).append(",")
                        .append(domesticFlight.getDestination()).append(",")
                        .append(domesticFlight.getEconomySeatCount()).append(",")
                        .append(domesticFlight.getBusinessSeatCount()).append(",")
                        .append(domesticFlight.getFirstClassSeatCount()).append(",")
                        .append(domesticFlight.getDepartureTime()).append(",")
                        .append(domesticFlight.getArrivalTime()).append(",")
                        .append(domesticFlight.getEconomyPrice()).append(",")
                        .append(domesticFlight.getBusinessPrice()).append(",")
                        .append(domesticFlight.getFirstClassPrice());

                } else if (flight instanceof International) {
                    International internationalFlight = (International) flight;
                    line.append("International,")
                        .append(internationalFlight.getTax()).append(",")
                        .append(internationalFlight.getRequiresVisa()).append(",")
                        .append(internationalFlight.getFlightNumber()).append(",")
                        .append(internationalFlight.getAirline()).append(",")
                        .append(internationalFlight.getOrigin()).append(",")
                        .append(internationalFlight.getDestination()).append(",")
                        .append(internationalFlight.getEconomySeatCount()).append(",")
                        .append(internationalFlight.getBusinessSeatCount()).append(",")
                        .append(internationalFlight.getFirstClassSeatCount()).append(",")
                        .append(internationalFlight.getDepartureTime()).append(",")
                        .append(internationalFlight.getArrivalTime()).append(",")
                        .append(internationalFlight.getEconomyPrice()).append(",")
                        .append(internationalFlight.getBusinessPrice()).append(",")
                        .append(internationalFlight.getFirstClassPrice());
                }
                
                writer.write(line.toString());
                writer.newLine();
            }
            // System.out.println("Flights saved successfully to " + FLIGHTS_FILE);
        } catch (IOException e) {
            System.err.println("Error saving users: " + e.getMessage());
        }
        writeSystemLog("Flights saved successfully to " + FLIGHTS_FILE);
    }
    public ArrayList<Flight> loadFlights(String path) {

    ArrayList<Flight> flights = new ArrayList<>();

    try (BufferedReader flightsReader = new BufferedReader(new FileReader(path))) {
        String line;

        while ((line = flightsReader.readLine()) != null) {
            String[] flightDetails = line.split(",");

            String flightType = flightDetails[0];
            double tax = Double.parseDouble(flightDetails[1]);
            boolean requiresVisa = Boolean.parseBoolean(flightDetails[2]);
            int flightNumber = Integer.parseInt(flightDetails[3]);
            String airline = flightDetails[4];
            String origin = flightDetails[5];
            String destination = flightDetails[6];
            int economySeats = Integer.parseInt(flightDetails[7]);
            int businessSeats = Integer.parseInt(flightDetails[8]);
            int firstClassSeats = Integer.parseInt(flightDetails[9]);

            String DepartureTime = flightDetails[10];
            String ArrivalTime = flightDetails[11];

            double economyPrice = Double.parseDouble(flightDetails[12]);
            double businessPrice = Double.parseDouble(flightDetails[13]);
            double firstClassPrice = Double.parseDouble(flightDetails[14]);

            if (flightType.equalsIgnoreCase("Domestic")) {
                Domestic domestic = new Domestic(flightNumber, airline, origin, destination, economySeats, businessSeats, firstClassSeats, DepartureTime, ArrivalTime, economyPrice, businessPrice, firstClassPrice);
                domestic.setRequiresVisa(requiresVisa);
                domestic.setTax(tax);
                
                flights.add(domestic);
            } else if (flightType.equalsIgnoreCase("International")) {
                International international = new International(flightNumber, airline, origin, destination, economySeats, businessSeats, firstClassSeats, DepartureTime, ArrivalTime, economyPrice, businessPrice, firstClassPrice);

                international.setRequiresVisa(requiresVisa);
                international.setTax(tax);

                flights.add(international);
            }
        }
    } catch (Exception e) {
        System.out.println("An Error Happened: " + e.getMessage());
    }

    return flights;
}

    public void savePassengers(ArrayList<Passenger> passengers) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(PASSENGERS_FILE))) {
            for (Passenger passenger : passengers) {
                StringBuilder line = new StringBuilder();
                line.append(passenger.getPassengerId()).append(",")
                    .append(passenger.getName()).append(",")
                    .append(passenger.getPassportNumber()).append(",")
                    .append(passenger.getDateOfBirth()).append(",")
                    .append(passenger.getSpecialRequests());
                
                writer.write(line.toString());
                writer.newLine();
            }
            // System.out.println("Passengers saved successfully to " + PASSENGERS_FILE);
        } catch (IOException e) {
            System.err.println("Error saving passengers: " + e.getMessage());
        }
        writeSystemLog("Passengers saved successfully to " + PASSENGERS_FILE);

    }
    public ArrayList<Passenger> loadPassengers(String path){
        ArrayList<Passenger> passengers = new ArrayList<>();
        
        try (BufferedReader passengersReader = new BufferedReader(new FileReader(path))) {
            String line;
            
            while ((line = passengersReader.readLine()) != null) {
                String[] passengerDetails = line.split(",");



                int passengerId = Integer.parseInt(passengerDetails[0]);
                String name = passengerDetails[1];
                String passportNumber = passengerDetails[2];
                String dateOfBirth = passengerDetails[3];
                String specialRequests = passengerDetails[4];
                
                Passenger passenger = new Passenger(passengerId, name, passportNumber, dateOfBirth, specialRequests);
                passengers.add(passenger);
            }
        } catch (Exception e) {
            System.out.println("An Error Happened: " + e.getMessage());
        }

        return passengers;

    }
    
    public void saveBookings(ArrayList<Booking> bookings) {
    try (BufferedWriter writer = new BufferedWriter(new FileWriter(BOOKINGS_FILE))) {
        for (Booking booking : bookings) {
            StringBuilder line = new StringBuilder();

            line.append(booking.getBookingReference()).append(",")
                .append(booking.getCustomer().getUserId()).append(",")
                .append(booking.getFlight().getFlightNumber()).append(",")
                .append(booking.getStatus()).append(",")
                .append(booking.getPaymentStatus()).append(",");

            StringBuilder pIds = new StringBuilder();
            List<Passenger> plist = booking.getPassengers();
            for (int i = 0; i < plist.size(); i++) {
                pIds.append(plist.get(i).getPassengerId());
                if (i < plist.size() - 1) {
                    pIds.append(';');
                }
            }
            line.append(pIds.toString()).append(",");

            StringBuilder seats = new StringBuilder();
            List<String> slist = booking.getSeatSelections();
            for (int i = 0; i < slist.size(); i++) {
                seats.append(slist.get(i));
                if (i < slist.size() - 1) {
                    seats.append(';');
                }
            }
            line.append(seats.toString());

            writer.write(line.toString());
            writer.newLine();
        }
        // System.out.println("Bookings saved successfully to " + BOOKINGS_FILE);
    } catch (IOException e) {
        System.err.println("Error saving bookings: " + e.getMessage());
    }
    writeSystemLog("Bookings saved successfully to " + BOOKINGS_FILE);
}
    public ArrayList<Booking> loadBookings(String path){
        ArrayList<Booking> bookings = new ArrayList<>();
        ArrayList<Passenger> allPassengers = loadPassengers("resources\\passengers.txt");


        try (BufferedReader reader = new BufferedReader(new FileReader(BOOKINGS_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                
                String[] f = line.split(",", -1);
    
                String ref = f[0];
                int custId = Integer.parseInt(f[1]);
                int flightNo = Integer.parseInt(f[2]);
                String status = f[3];
                String payStatus = f[4];
                String[] pIds = f[5].split(";");
                String[] seats = f[6].split(";");
    
                // look up Customer and Flight
                Customer cust   = findCustomerById(custId);
                Flight flight   = findFlightByNumber(flightNo);
    
                // create booking object
                Booking b = new Booking(ref, cust, flight, status, payStatus);

                for (int i = 0; i < pIds.length; i++) {
                    int pid = Integer.parseInt(pIds[i]);
                    Passenger p = null;
                    for (Passenger passenger : allPassengers) {
                        if (passenger.getPassengerId() == pid) {
                            p = passenger;
                            break;
                        }
                    }
                    
                    if (p != null) {
                        b.addPassenger(p, seats[i]);
                    }else {
                        System.err.println("Warning: Passenger with ID " + pid + " not found.");
                        return null;
                    }
                }
    
    
                bookings.add(b);
            }
        } catch (IOException e) {
            System.err.println("Error loading bookings: " + e.getMessage());
        }

        return bookings;

    }

    

    public Customer findCustomerById(int userId) {
        ArrayList<User> customers = loadUsers("resources\\users.txt");
        for (User u : customers) {
            if (u instanceof Customer) {
                Customer c = (Customer) u;              
                if (c.getUserId() == (userId)) {

                    return c;                            
                }
            }
        }
        writeSystemLog("Customer not found.");
        // no matching customer found
        return null;
    }

    public Flight findFlightByNumber(int flightNumber) {
        ArrayList<Flight> flights = loadFlights("resources\\flights.txt");

        for (Flight f : flights) {
            if (f instanceof Domestic || f instanceof International) {
                if (f.getFlightNumber() == (flightNumber)) {

                    return f;
                }
            }
        }
        writeSystemLog("Flight not found.");
        // no matching flight found
        return null;
    }

    
    public void writeSystemLog(String message) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String timestamp = LocalDateTime.now().format(formatter);
        String logEntry = "[" + timestamp + "] " + message;

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("resources\\system_logs.txt",true))) {
            writer.write(logEntry);
            writer.newLine();
        } catch (IOException e) {
            System.err.println("Error writing to system log: " + e.getMessage());
        }

    }

    public void writeMaintaninceMode(String isMaintanceMode){
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("resources\\isMaintainceMode.txt",false))) {
            writer.write(isMaintanceMode);
            writer.newLine();
        } catch (IOException e) {
            System.err.println("Error writing to system log: " + e.getMessage());
        }
        
    }

}