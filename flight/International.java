package flight;

public class International extends Flight {
    private boolean requiresVisa;
    private double tax;

    public International(int FlightNumber, String airline, String origin, String destination, int EconomySeats,
                         int BusinessSeats, int FirstClassSeats,String departureTime, String arrivalTime,
                         double EconomyPrice, double BusinessPrice, double FirstClassPrice) {
        super(FlightNumber, airline, origin, destination, EconomySeats,
                BusinessSeats, FirstClassSeats,departureTime,arrivalTime, EconomyPrice, BusinessPrice, FirstClassPrice);
        this.requiresVisa = true;  // Default value
        this.tax = 1.15;  // Default value
    }

    public double totalprice(String seat){
        if (seat == null) {
            return -1;
        }

        if (seat.equalsIgnoreCase("economy")) {
            return tax*EconomyPrice;
        }
        else if (seat.equalsIgnoreCase("business")) {
            return tax*BusinessPrice;
        }
        else if (seat.equalsIgnoreCase("first class")) {
            return tax*FirstClassPrice;
        }
        else {
            return -1;
        }
    }

    public void setRequiresVisa(boolean requiresVisa){
        this.requiresVisa = requiresVisa;
    }
    public boolean getRequiresVisa(){
        return requiresVisa;
    }
    public void setTax(double tax){
        this.tax = tax;
    }
    public double getTax() {
        return tax;
    }
    @Override
    public double getEconomyPrice() {
        return EconomyPrice *tax;
    }
    @Override
    public double getBusinessPrice() {
        return BusinessPrice * tax;
    }
    @Override
    public double getFirstClassPrice() {
        return FirstClassPrice * tax;
    }
}