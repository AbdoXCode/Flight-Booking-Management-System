package booking;

public class Passenger {
    private int passengerId;
    private String name;
    private String passportNumber;
    private String dateOfBirth;
    private String specialRequests;

    public Passenger(int passengerId, String name, String passportNumber, String dateOfBirth, String specialRequests) {
        this.passengerId = passengerId;
        this.name = name;
        this.passportNumber = passportNumber;
        this.dateOfBirth = dateOfBirth;
        this.specialRequests = specialRequests;
    }

    public void updateInfo(String name, String passportNumber, String dateOfBirth, String specialRequests) {
        this.name = name;
        this.passportNumber = passportNumber;
        this.dateOfBirth = dateOfBirth;
        this.specialRequests = specialRequests;
        System.out.println("Passenger information updated successfully.");
    }
    
    public String getPassengerDetails() {
        return "Passenger ID: " + passengerId + 
               "\nName: " + name + 
               "\nPassport Number: " + passportNumber + 
               "\nDate of Birth: " + dateOfBirth + 
               "\nSpecial Requests: " + specialRequests;
    }
    
    // Add getters
    
    public void setName(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }
    public int getPassengerId() {
        return passengerId;
    }
    
    public String getPassportNumber() {
        return passportNumber;
    }
    
    public String getDateOfBirth() {
        return dateOfBirth;
    }
    
    public String getSpecialRequests() {
        return specialRequests;
    }
}