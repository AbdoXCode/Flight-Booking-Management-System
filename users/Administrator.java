package users;

import java.util.ArrayList;
import java.util.Scanner;

import Files.FileManager;

public class Administrator extends User{
    private int adminId;
    private int securityLevel;
    private boolean maintenanceMode;

    
    FileManager fm = new FileManager();

    Scanner scanner = new Scanner(System.in);

    // the maintenanceMode is needed to be implemented in the booking system --> to be done in future

    public Administrator(int userId, String username, String password, String name, String email, String contactInfo,int adminId,int securityLevel) {
        super(userId, username, password, name, email, contactInfo);
        this.adminId = adminId;
        this.securityLevel = securityLevel;
        this.maintenanceMode = false;
    }



    public void createUser(){
        System.out.println("\n===== CREATE USER =====");
        System.out.println("1. Create Customer");
        System.out.println("2. Create Agent");
        System.out.println("3. Create Administrator");
        System.out.println("4. Back to Main Menu");
        System.out.print("Enter your choice: ");

        //👽 make sure the user choose a valid option before continuing
        try {
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    createCustomer();
                    break;
                case 2:
                    createAgent();
                    break;
                case 3:
                    createAdministrator();
                    break;
                case 4:
                    return;
                default:
                    System.out.println("Invalid choice. Please enter a number between 1-4.");
                    break;
            }
        } catch (java.util.InputMismatchException e) {
            System.out.println("Invalid input. Please enter a number.");
            scanner.nextLine(); // Clear invalid input
        }
    }

    private void createCustomer(){
            ArrayList<User> users = fm.loadUsers("resources\\users.txt");

        System.out.println("\n===== CREATE CUSTOMER =====");

        System.out.print("Enter Username: ");
        scanner.nextLine();
        String username = scanner.nextLine();
        
        for(User someone : users){
            if (someone.getUsername().equals(username)) {
                System.out.println("Username already exists. Please try again.");
                return;
            }
        }
        System.out.print("Enter password: ");
        String password = setPassword(scanner.nextLine());
        
        while (password == null) {
            System.out.print("Password must be at least 6 characters with letters and numbers! Please try again: ");
            password = setPassword(scanner.nextLine());
            
        }
        

        System.out.print("Enter name: ");
        String name = scanner.nextLine();
       
        System.out.print("Enter email: ");
        String email = scanner.nextLine();
        
        System.out.print("Enter contact info: ");
        String contactInfo = scanner.nextLine();
        
        System.out.print("Enter address: ");
        String address = scanner.nextLine();

        int nextUserId = users.size() + 1;
        int nextCustomerId = 1;

        for (User user : users) {
            if (user instanceof Customer) {
                Customer customer = (Customer) user;
                if (customer.getCustomerId() >= nextCustomerId) {
                    nextCustomerId = customer.getCustomerId() + 1;
                }
            }
        }

        Customer customer = new Customer(nextUserId, username, password, name, email, contactInfo, nextCustomerId, address, contactInfo, address);

        users.add(customer);
        fm.saveUsers(users);

        System.out.println("Customer created successfully!");


    }

    private void createAgent(){ 
            ArrayList<User> users = fm.loadUsers("resources\\users.txt");

        System.out.println("\n===== CREATE AGENT =====");

        System.out.print("Enter Username: ");
        scanner.nextLine();
        String username = scanner.nextLine();
        
        for(User someone : users){
            if (someone.getUsername().equals(username)) {
                System.out.println("Username already exists. Please try again.");
                return;
            }
        }
        System.out.print("Enter password: ");
        String password = setPassword(scanner.nextLine());
        
        while (password == null) {
            System.out.print("Password must be at least 6 characters with letters and numbers! Please try again: ");
            password = setPassword(scanner.nextLine());
            
        }

        System.out.print("Enter name: ");
        String name = scanner.nextLine();
        
        
        System.out.print("Enter email: ");
        String email = scanner.nextLine();
        
        System.out.print("Enter contact info: ");
        String contactInfo = scanner.nextLine();
        

        System.out.print("Enter department: ");
        String department = scanner.nextLine();
        
        System.out.print("Enter commission rate (0.00-1.00): ");
        double commission = scanner.nextDouble();

        int nextUserId = users.size() + 1;
        int nextAgentId = 1;

        for (User user : users) {
            if (user instanceof Agent) {
                Agent agent = (Agent) user;
                if (agent.getAgentId() >= nextAgentId) {
                    nextAgentId = agent.getAgentId() + 1;
                }
            }
        }

        Agent agent = new Agent(nextUserId, username, password, name, email, contactInfo, nextAgentId, department, commission);

        users.add(agent);
        fm.saveUsers(users);

        System.out.println("Agent created successfully!");
    }

    private void createAdministrator(){ 
            ArrayList<User> users = fm.loadUsers("resources\\users.txt");

        System.out.println("\n===== CREATE ADMINISTRATOR =====");

        System.out.print("Enter Username: ");
        scanner.nextLine();
        String username = scanner.nextLine();
        
        for(User someone : users){
            if (someone.getUsername().equals(username)) {
                System.out.println("Username already exists. Please try again.");
                return;
            }
        }
        System.out.print("Enter password: ");
        String password = setPassword(scanner.nextLine());
        
        while (password == null) {
            System.out.print("Password must be at least 6 characters with letters and numbers! Please try again: ");
            password = setPassword(scanner.nextLine());
            
        }

        System.out.print("Enter name: ");
        String name = scanner.nextLine();
        
        
        System.out.print("Enter email: ");
        String email = scanner.nextLine();
        
        System.out.print("Enter contact info: ");
        String contactInfo = scanner.nextLine();
        System.out.print("Enter security level (1-3): ");
        int securityLevel;
        //👽 make sure the user choose a valid option before continuing
        while (true) {
            try {
                securityLevel = scanner.nextInt();
                if (securityLevel >= 1 && securityLevel <= 3) {
                    break;
                } else {
                    System.out.print("Security level must be between 1-3. Try again: ");
                }
            } catch (java.util.InputMismatchException e) {
                System.out.print("Invalid input. Please enter a number between 1-3: ");
                scanner.nextLine(); // Clear invalid input
            }
        }
        
    
        int nextUserId = users.size() + 1;
        int nextAdminId = 1;

        for (User user : users) {
            if (user instanceof Administrator) {
                Administrator admin = (Administrator) user;
                if (admin.getAdminId() >= nextAdminId) {
                    nextAdminId = admin.getAdminId() + 1;
                }
            }
        }

        Administrator admin = new Administrator(nextUserId, username, password, name, email, contactInfo, nextAdminId, securityLevel);

        users.add(admin);
        fm.saveUsers(users);

        System.out.println("Administrator created successfully!");
    }
    
    public void deleteUser(String username){
            ArrayList<User> users = fm.loadUsers("resources\\users.txt");

        for (User user : users) {
            if (user.getUsername().equals(username)) {
                fm.writeSystemLog("username "+username+" has been deleted by admin "+this.getUsername());

                users.remove(user);
                fm.saveUsers(users);
                System.out.println("User deleted successfully!");
                return;
            }
        }
        System.out.println("User not found.");

    }
    
    public void modifySystemSettings(){
        System.out.println("\n===== MODIFY SYSTEM SETTINGS =====");

        System.out.println("Current Maintenance Mode: " + (maintenanceMode ? "ON" : "OFF"));
        System.out.println("Do you want to toggle it? (yes/no)");

        java.util.Scanner scanner = new java.util.Scanner(System.in);
        String input = scanner.nextLine().trim().toLowerCase();

        if (input.equals("yes")) {
            maintenanceMode = !maintenanceMode;
            System.out.println("Maintenance Mode is now: " + (maintenanceMode ? "ON" : "OFF"));
        } else {
            System.out.println("No changes made to system settings.");
        }
        if (maintenanceMode == true) {
            fm.writeMaintaninceMode("true");
        }else if (maintenanceMode == false) {
            fm.writeMaintaninceMode("false");
        }
    }

    public void viewSystemLogs(){
        System.out.println("\n===== VIEW SYSTEM LOGS =====");
        
        try(
            Scanner logScanner = new Scanner(new java.io.File("resources\\system_logs.txt"))
        ) {
            while (logScanner.hasNextLine()) {
                String logEntry = logScanner.nextLine();
                System.out.println(logEntry);
            }
        } catch (Exception e) {
            System.out.println("err "+e);
        }

    }
    
    public void manageUserAccess() {
        ArrayList<User> users = fm.loadUsers("resources\\users.txt");

        System.out.println("\n===== MANAGE USER ACCESS =====");

        for (User user : users) {
            System.out.println(user.getUsername() + " (" + user.getClass().getSimpleName() + ")");
        }
        System.out.print("Enter the username of the user you want to manage: ");
        String username = scanner.nextLine();

        User userToManage = null;
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                userToManage = user;
                break;
            }
        }
        if (userToManage == null) {
            System.out.println("User not found.");
            return;
        }
        System.out.println("1. Delete User");
        System.out.println("2. Modify User");
        System.out.print("Enter your choice: ");
        int choice;
        //👽 make sure the user choose a valid option before continuing
        try {
            choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline
        } catch (java.util.InputMismatchException e) {
            System.out.println("Invalid input. Please enter a number.");
            scanner.nextLine(); // Clear invalid input 
            return;
        }
        switch (choice) {
            case 1:
            fm.writeSystemLog("Admin Has deleted this user "+ userToManage.getUsername()); 
            users.remove(userToManage);
            fm.saveUsers(users);
            break;
        
            case 2:
            System.out.print("Enter new username (leave blank to keep current): ");
            String newUsername = scanner.nextLine();
            if (!newUsername.isEmpty()) {
                userToManage.setUsername(newUsername);
            }

            System.out.print("Enter new password (leave blank to keep current): ");
            String newPassword = scanner.nextLine();
            if (!newPassword.isEmpty()) {
                // Use setPassword method to properly validate and process the password
                //👽 if there an invaild input the system will ask for a new password until it is valid
                String processedPassword;
                while (true) {
                    processedPassword = setPassword(newPassword);
                    if (processedPassword != null) {
                        userToManage.setPassword(processedPassword);
                        break;
                    } else {
                        System.out.print("Password must be at least 6 characters with letters and numbers,Try again:");
                        newPassword = scanner.nextLine();
                    }
                }
            }
            System.out.print("Enter new name (leave blank to keep current): ");
            String newName = scanner.nextLine();
            if (!newName.isEmpty()) {
                userToManage.setName(newName); 
            }

            System.out.print("Enter new email (leave blank to keep current): ");
            String newEmail = scanner.nextLine();
            if (!newEmail.isEmpty()) {
                userToManage.setEmail(newEmail);
            }

            System.out.print("Enter new contact info (leave blank to keep current): ");
            String newContactInfo = scanner.nextLine();
            if (!newContactInfo.isEmpty()) {
                userToManage.setContactInfo(newContactInfo);
            }


            fm.writeSystemLog("Admin Has did some edits for this user: "+userToManage.getUsername());
            fm.saveUsers(users);
            break;

            default:
                break;
        }

    }

    public int getAdminId(){
        return adminId;
    }
    public int getSecurityLevel(){
        return securityLevel;
    }

    public boolean isMaintenanceMode() {
        return maintenanceMode;
    }
    
}