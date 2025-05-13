package flight;

import java.util.ArrayList;

public class Flight {
    protected int FlightNumber;
    protected String airline;
    protected String origin;
    protected String destination;
    protected int TotalAvailableSeats;
    protected int AvailableEconomySeats;
    protected int maxEconomySeats;
    protected int AvailableBusinessSeats;
    protected int maxBusinessSeats;
    protected int AvailableFirstClassSeats;
    protected int maxFirstClassSeats;
    protected String departureTime;
    protected String arrivalTime;
    protected double EconomyPrice;
    protected double BusinessPrice;
    protected double FirstClassPrice;
    protected ArrayList<seat> economySeatsList;
    protected ArrayList<seat> businessSeatsList;
    protected ArrayList<seat> firstClassSeatsList;



    public Flight (int FlightNumber,String airline, String origin, String destination, int EconomySeats,
                   int BusinessSeats,int FirstClassSeats , String departureTime, String arrivalTime,
                   double EconomyPrice,double BusinessPrice,double FirstClassPrice){

        //Flight details
        this.FlightNumber = FlightNumber;
        this.airline = airline;
        this.origin = origin;
        this.destination = destination;

        //Flight Schedule
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;

        //Seats

        //economy
        this.AvailableEconomySeats = EconomySeats;
        this.maxEconomySeats = EconomySeats;
        this.economySeatsList = new ArrayList<seat>();
        //Business
        this.AvailableBusinessSeats = BusinessSeats;
        this.maxBusinessSeats = BusinessSeats;
        this.businessSeatsList = new ArrayList<seat>();
        //First class
        this.AvailableFirstClassSeats = FirstClassSeats;
        this.maxFirstClassSeats = FirstClassSeats;
        this.firstClassSeatsList = new ArrayList<seat>();
        //total seats
        this.TotalAvailableSeats = EconomySeats + BusinessSeats + FirstClassSeats;

        //Prices of each class
        this.EconomyPrice = EconomyPrice;
        this.BusinessPrice = BusinessPrice;
        this.FirstClassPrice = FirstClassPrice;
    }

    //checking if any seats are available or in a specific seat type
    public boolean checkAvailability(String seat){
        //make sure the input isn't null before checking because "equalsIgnoreCase" take null as a valid input
        if (seat == null) {
            System.out.println("Seat type cannot be null.");
            return false;

        }
        //checks if there empty economy seat
        if (seat.equalsIgnoreCase("Economy")){
            return AvailableEconomySeats >0;
        }
        //checks if there empty business seat
        else if (seat.equalsIgnoreCase("Business")){
            return AvailableBusinessSeats >0;
        }
        //checks if there empty first class seat
        else if (seat.equalsIgnoreCase("First Class")){
            return AvailableFirstClassSeats >0;
        }
        //checks if there an empty seat at all on the flight
        else if (seat.equalsIgnoreCase("All")){
            return true;
        }else
        //wrong input
        {
            System.out.println("Invalid Seat Type!");
        }

        return false;
    }

    //takes the input time to convert to user friendly format
    // private String DepartureTime(int year, int month, int day, int hour,int minute) {
    //     DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy, hh:mm a");
    //     return LocalDateTime.of(year, month, day, hour, minute).format(formatter);
    // }
    // private String ArrivalTime(int year, int month, int day, int hour,int minute) {
    //     DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy, hh:mm a");
    //     return LocalDateTime.of(year, month, day, hour, minute).format(formatter);

    // }

    //(not used)
    public void updateSchedule(String departureTime, String arrivalTime){
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
    }

    //getter for the price of the seat
    public double calculatePrice(String seat){
        try {
            if (seat == null) throw new Exception();

            if (seat.equalsIgnoreCase("Economy")) {
                return EconomyPrice;
            } else if (seat.equalsIgnoreCase("Business")) {
                return BusinessPrice;
            } else if (seat.equalsIgnoreCase("First Class")) {
                return FirstClassPrice;
            } else {
                throw new Exception();
            }
        } catch (Exception e){
            System.out.println("Invalid Seat Type!");
            return 0;
        }
    }

    //create a new seat for the coustomer to reserve if there an avaliable seat
    public void reserveSeat(String seat){
        //make sure the input isn't null before checking because "equalsIgnoreCase" take null as a valid input
        if (seat == null) {
            System.out.println("Seat type cannot be null.");
            return;
        }

        //checks for the seat type
        if (seat.equalsIgnoreCase("Economy") || seat.equalsIgnoreCase("Business") || seat.equalsIgnoreCase("First Class")) {
            //checks if there an avaliable seat
            if (checkAvailability(seat)) {
                //takes the number of the avaliable seat
                int tmp = findEmptySeat(seat);
                //creat a new seat (objeact) with its number and type
                seat newSeat = new seat(tmp+1,FlightNumber,seat,true);
                //adds the seat in the array list of the specific seat type
                if (seat.equalsIgnoreCase("Economy")) {
                    economySeatsList.add(newSeat);
                    AvailableEconomySeats--;
                } else if (seat.equalsIgnoreCase("Business")) {
                    businessSeatsList.add(newSeat);
                    AvailableBusinessSeats--;
                } else if (seat.equalsIgnoreCase("First Class")) {
                    firstClassSeatsList.add(newSeat);
                    AvailableFirstClassSeats--;
                }
                TotalAvailableSeats--;

            }else {
                //if the checkAvailability(seat) was false
                System.out.println("No available seats in "+seat+"!");
            }
        }else {
            //wrong input
            System.out.println("Invalid seat type!");

        }

    }

    //find the empty seat and the return its number
    public int findEmptySeat(String seat){
        try{
            //if its a economy seat
            if (seat.equalsIgnoreCase("Economy")) {
                //will loop on the array list to find the unbooked seat or empty seat
                for (int i = 0; i < economySeatsList.size(); i++) {
                    if (economySeatsList.get(i) == null || economySeatsList.get(i).getBooked() == false) {
                        return i;

                    }
                }
                //if it didn't find empty seats will send the the number of seats to creat a new seat which will be the size +1
                return economySeatsList.size();

            }

            else if (seat.equalsIgnoreCase("Business")) {
                for (int i = 0; i < businessSeatsList.size(); i++) {
                    if (businessSeatsList.get(i) == null) {
                        return i;
                    }
                }
                return businessSeatsList.size();
            }
            else if (seat.equalsIgnoreCase("First Class")) {
                for (int i = 0; i < firstClassSeatsList.size(); i++) {
                    if (firstClassSeatsList.get(i) == null) {
                        return i;
                    }
                }
                return firstClassSeatsList.size();
            }


        }catch (Exception e){
            System.out.println("Invalid Seat Type");
        }
        return -1;
    }

    //cancel the booked seat to make it avaliable again
    //boda modified it
    public void cancelReservation(int seatNumber,String seatType){
        if (seatType == null) {
            System.out.println("Seat type cannot be null.");
        }

        
        if (seatType.equalsIgnoreCase("economy") ) {
            //sets the seat status from ture to false
            economySeatsList.get(seatNumber-1).setBooked(false);
            //increase the total available seats
            AvailableEconomySeats++;
        }

        if (seatType.equalsIgnoreCase("business") ) {
            businessSeatsList.get(seatNumber-1).setBooked(false);
            AvailableBusinessSeats++;
        }
        if (seatType.equalsIgnoreCase("first class") ) {
            firstClassSeatsList.get(seatNumber-1).setBooked(false);
            AvailableFirstClassSeats++;
        }

    }

    //add more seats
    public void increaseNumberOfSeats(int numberOfNewAddSeats,String seatType){
        if (seatType == null) {
            System.out.println("Seat type cannot be null.");
            return;
        }

        if (seatType.equalsIgnoreCase("economy") ) {
            AvailableEconomySeats += numberOfNewAddSeats;
            maxEconomySeats += numberOfNewAddSeats;
            return;
        }else if (seatType.equalsIgnoreCase("business") ) {
            AvailableBusinessSeats += numberOfNewAddSeats;
            maxBusinessSeats += numberOfNewAddSeats;
            return;
        }
        else if (seatType.equalsIgnoreCase("first class") ) {
            AvailableFirstClassSeats += numberOfNewAddSeats;
            maxFirstClassSeats += numberOfNewAddSeats;
            return;
        }
        else {
            System.out.println("Invalid Seat Type!");
        }
    }

    //remove seats
    public void decreaseNumberOfSeats(int numberOfSeatsToRemove,String seatType){
        if (seatType == null) {
            System.out.println("Seat type cannot be null.");
            return;
        }
        if (seatType.equalsIgnoreCase("economy") && AvailableEconomySeats>=numberOfSeatsToRemove ) {
            AvailableEconomySeats -= numberOfSeatsToRemove;
            maxEconomySeats -= numberOfSeatsToRemove;
            return;
        }
        else if (seatType.equalsIgnoreCase("business") && AvailableBusinessSeats>=numberOfSeatsToRemove ) {
            AvailableBusinessSeats -= numberOfSeatsToRemove;
            maxBusinessSeats -= numberOfSeatsToRemove;
            return;
        }else if (seatType.equalsIgnoreCase("first class") && AvailableFirstClassSeats>=numberOfSeatsToRemove ) {
            AvailableFirstClassSeats -= numberOfSeatsToRemove;
            maxFirstClassSeats -= numberOfSeatsToRemove;
            return;
        }
        else {
            System.out.println("Invalid Seat Type! or Not enough seats to remove!");
        }
    }


    public int getFlightNumber() {
        return FlightNumber;
    }

    public void setFlightNumber(int flightNumber) {
        FlightNumber = flightNumber;
    }

    public String getAirline() {
        return airline;
    }

    public void setAirline(String airline) {
        this.airline = airline;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public int getTotalAvailableSeats() {
        return TotalAvailableSeats;
    }

    public void setTotalAvailableSeats(int totalAvailableSeats) {
        TotalAvailableSeats = totalAvailableSeats;
    }

    public int getEconomySeatCount() {
        return AvailableEconomySeats;
    }

    public void setEconomySeatCount(int economySeats) {
        AvailableEconomySeats = economySeats;
    }

    public int getMaxEconomySeats() {
        return maxEconomySeats;
    }

    public void setMaxEconomySeats(int maxEconomySeats) {
        this.maxEconomySeats = maxEconomySeats;
    }

    public int getBusinessSeatCount() {
        return AvailableBusinessSeats;
    }

    public void setBusinessSeatCount(int businessSeats) {
        AvailableBusinessSeats = businessSeats;
    }

    public int getMaxBusinessSeats() {
        return maxBusinessSeats;
    }

    public void setMaxBusinessSeats(int maxBusinessSeats) {
        this.maxBusinessSeats = maxBusinessSeats;
    }

    public int getFirstClassSeatCount() {
        return AvailableFirstClassSeats;
    }

    public void setAvailableFirstClassSeats(int availableFirstClassSeats) {
        AvailableFirstClassSeats = availableFirstClassSeats;
    }

    public int getMaxFirstClassSeats() {
        return maxFirstClassSeats;
    }

    public void setMaxFirstClassSeats(int maxFirstClassSeats) {
        this.maxFirstClassSeats = maxFirstClassSeats;
    }

    public String getDepartureTime() {
        return departureTime;
    }

    public String getArrivalTime() {
        return arrivalTime;
    }

    // public void EditDepartureTime(int year,int month,int day,int hour,int minute) {
    //     this.departureTime = DepartureTime(year, month, day, hour, minute);
    // }

    // public void EditArrivalTime(int year,int month,int day,int hour,int minute) {
    //     this.arrivalTime = ArrivalTime(year, month, day, hour, minute);
    // }

    public double getEconomyPrice() {
        return EconomyPrice;
    }

    public void setEconomyPrice(double economyPrice) {
        EconomyPrice = economyPrice;
    }

    public double getBusinessPrice() {
        return BusinessPrice;
    }

    public void setBusinessPrice(double businessPrice) {
        BusinessPrice = businessPrice;
    }

    public double getFirstClassPrice() {
        return FirstClassPrice;
    }

    public void setFirstClassPrice(double firstClassPrice) {
        FirstClassPrice = firstClassPrice;
    }

    public int getAvailableEconomySeats() {
        return AvailableEconomySeats;
    }

    public void setAvailableEconomySeats(int availableEconomySeats) {
        this.AvailableEconomySeats = availableEconomySeats;
    }

    public ArrayList<seat> geteconomySeats() {
        return economySeatsList;
    }

    public ArrayList<seat> getAvailableBusinessSeats() {
        return businessSeatsList;
    }

    public void setAvailableBusinessSeats(int availableBusinessSeats) {
        this.AvailableBusinessSeats = availableBusinessSeats;
    }

    public ArrayList<seat> getAvailableFirstClassSeats() {
        return firstClassSeatsList;
    }

    public void setFirstClassSeatsList(ArrayList<seat> firstClassSeatsList) {
        this.firstClassSeatsList = firstClassSeatsList;
    }
}