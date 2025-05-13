package users;

import java.util.ArrayList;
import java.util.Scanner;

import Files.FileManager;

abstract public class User{
    private int userId;
    protected String username;
    protected String password;
    private String name;
    private String email;
    private String contactInfo;

    FileManager fm = new FileManager();

    public User(int userId, String username, String password, String name, String email, String contactInfo) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.name = name;
        this.email = email;
        this.contactInfo = contactInfo;
        

    }

    public void login(String username,String password){};
    public void logout(){
        fm.writeSystemLog("User logged out: " + username + " (ID: " + getUserId() + ")");
    };
    public void updateProfile(){
            ArrayList<User> users = fm.loadUsers("resources\\users.txt");

        User userToManage = null;
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                userToManage = user;
                break;
            }
        }
        if (userToManage == null) {
            System.out.println("User not found.");
            fm.writeSystemLog("Failed to update profile: User '" + username + "' not found");
            return;
        }
        Scanner scanner = new Scanner(System.in);
         System.out.print("Enter new username (leave blank to keep current): ");
            String newUsername = scanner.nextLine();
            if (!newUsername.isEmpty()) {
                userToManage.setUsername(newUsername);
            }

            System.out.print("Enter new password (leave blank to keep current): ");
        String password = setPassword(scanner.nextLine());

        while (password == null) {
            System.out.print("Password must be at least 6 characters with letters and numbers! Please try again: ");
            password = setPassword(scanner.nextLine());


        }
        userToManage.setPassword(password);

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
        fm.saveUsers(users);
            fm.writeSystemLog("Updated profile for user: " + username);

    }

    public void setUsername(String username) {
        String oldUsername = this.username;
        this.username = username;
        fm.writeSystemLog("Username changed from '" + oldUsername + "' to '" + username + "'");
    }
    public void setName(String name) {
        String oldName = this.name;
        this.name = name;
        fm.writeSystemLog("Name changed from '" + oldName + "' to '" + name + "'");

    }
    public void setEmail(String email) {
        String oldEmail = this.email;
        this.email = email;
        fm.writeSystemLog("Email changed from '" + oldEmail + "' to '" + email + "'");
    }
    public void setContactInfo(String contactInfo) {
        String oldContactInfo = this.contactInfo;
        this.contactInfo = contactInfo;
        fm.writeSystemLog("Contact info changed from '" + oldContactInfo + "' to '" + contactInfo + "'");
    }
    public String setPassword(String password) {
        boolean hasLetter = false;
        boolean hasNumber = false;
        
        for (char c : password.toCharArray()) {
            if (Character.isLetter(c)) {
                hasLetter = true;
            }
            if (Character.isDigit(c)) {
                hasNumber = true;
            }
        }
        
        if (password.length() >= 6 && hasLetter && hasNumber) {
            String oldPassword = this.password;
            this.password = password;
            fm.writeSystemLog("+ password set to "+password);


            return password;
        } else {
            fm.writeSystemLog("Password validation failed for user '" + username + "': Password must be at least 6 characters with letters and numbers");

            return null;
        }
    }
    public int getUserId(){
        return userId;
    }
    public String getUsername(){
        return username; 
    }
    public String getName(){
        return name; 
    }
    public String getEmail(){
        return email;
    }
    public String getContactInfo(){
        return contactInfo;
    }
    public String getPassword(){
        return password;
    }
}