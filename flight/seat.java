package flight;

public class seat {

    private int seatNumber;
    private int flightNumber;
    private String seatType;
    private boolean booked;

    public seat(int seatNumber, int flightNumber, String seatType, boolean status) {
        this.seatNumber = seatNumber;
        this.flightNumber = flightNumber;
        this.seatType = seatType;
        this.booked = status;
    }

    public void setBooked(boolean booked){
        this.booked = booked;
    }

    public boolean getBooked(){
        return booked;
    }

    public int getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(int seatNumber) {
        this.seatNumber = seatNumber;
    }

    public int getFlightNumber() {
        return flightNumber;
    }

    public void setFlightNumber(int flightNumber) {
        this.flightNumber = flightNumber;
    }

    public String getSeatType() {
        return seatType;
    }

    public void setSeatType(String seatType) {
        this.seatType = seatType;
    }

}