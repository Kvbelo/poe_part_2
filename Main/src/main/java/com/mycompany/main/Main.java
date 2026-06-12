/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.main;

import java.util.Scanner;
import java.util.regex.Pattern;

class Login {
    String username;
    String password;
    String cellPhoneNumber;
    String firstName;
    String lastName;
    private boolean isLoggedIn; 
    
    public Login() {
        this.username = "";
        this.password = "";
        this.cellPhoneNumber = "";
        this.firstName = "";
        this.lastName = "";
        this.isLoggedIn = false;  
    }
    
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    
    public String getFirstName() {
        return firstName;
    }
    
    public String getLastName() {
        return lastName;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
    
    public void setCellPhoneNumber(String cellPhoneNumber) {
        this.cellPhoneNumber = cellPhoneNumber;
    }
    
    public String getUsername() {
        return username;
    }
    
    public String getPassword() {
        return password;
    }
    
    public String getCellPhoneNumber() {
        return cellPhoneNumber;
    }
    
    public boolean isLoggedIn() {
        return isLoggedIn;
    }
    
    public void setLoggedIn(boolean loggedIn) {
        this.isLoggedIn = loggedIn;
    }
    
    public boolean checkUserName(String kyle) {
        return username.contains("_") && username.length() <= 5;
    }
    
    public boolean checkPasswordComplexity(String password1) {
        boolean hasMinLength = password.length() >= 8;
        boolean hasCapital = !password.equals(password.toLowerCase());
        boolean hasNumber = password.matches(".*\\d.*");
        boolean hasSpecial = !password.matches("[A-Za-z0-9]*");
        
        return hasMinLength && hasCapital && hasNumber && hasSpecial;
    }
    
    public boolean checkCellPhoneNumber(String string) {
        String regex = "^\\+27[0-9]{1,10}$";
        return Pattern.matches(regex, cellPhoneNumber);
    }
    
    public String registerUser(String john_1, String pass1234, String string, String john, String doe) {
        if (!checkUserName("kyle!!!!!!!")) {
            return "Username is not correctly formatted; please ensure that your username contains an underscore and is no more than five characters in length.";
        }
        
        if (!checkPasswordComplexity("password")) {
            return "Password is not correctly formatted; please ensure that the password contains at least eight characters, a capital letter, a number, and a special character.";
        }
        
        if (!checkCellPhoneNumber("08966553")) {
            return "Cell phone number incorrectly formatted or does not contain international code.";
        }
        
        return "User successfully registered!";
    }
    
    public boolean loginUser(String enteredUsername, String enteredPassword) {
        return this.username.equals(enteredUsername) && this.password.equals(enteredPassword);
    }
    
    public String returnLoginStatus(String enteredUsername, String enteredPassword) {
        if (loginUser(enteredUsername, enteredPassword)) {
            return "Welcome " + firstName + ", " + lastName + " it is great to see you again.";
        } else {
            return "Username or password incorrect, please try again.";
        }
    }

    String returnLoginStatus() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Login loginSystem = new Login();
        
        System.out.println("=".repeat(50));
        System.out.println("Sign Up AND Login");
        
        System.out.println(" Sign Up ");
        
        System.out.print("Enter First Name: ");
        String firstName = scanner.nextLine();
        loginSystem.setFirstName(firstName);
        
        System.out.print("Enter Last Name: ");
        String lastName = scanner.nextLine();
        loginSystem.setLastName(lastName);
        
        boolean validUsername = false;
        while (!validUsername) {
            System.out.print("Create Username (must contain _ and be ≤5 chars): ");
            String username = scanner.nextLine();
            loginSystem.setUsername(username);
            
            if (loginSystem.checkUserName("kyle!!!!!!!")) {
                System.out.println("Username successfully captured.");
                validUsername = true;
            } else {
                System.out.println("Username is not correctly formatted; please ensure that your username contains an underscore and is no more than five characters in length.");
            }
        }
        
        boolean validPassword = false;
        while (!validPassword) {
            System.out.print("Create Password (8+ chars, 1 capital, 1 number, 1 special): ");
            String password = scanner.nextLine();
            loginSystem.setPassword(password);
            
            if (loginSystem.checkPasswordComplexity("password")) {
                System.out.println("Password successfully captured.");
                validPassword = true;
            } else {
                System.out.println("Password is not correctly formatted; please ensure that the password contains at least eight characters, a capital letter, a number, and a special character.");
            }
        }
        
        boolean validPhone = false;
        while (!validPhone) {
            System.out.print("Enter Cell Phone Number (e.g., +27123456789): ");
            String phone = scanner.nextLine();
            loginSystem.setCellPhoneNumber(phone);
            
            if (loginSystem.checkCellPhoneNumber("08966553")) {
                System.out.println("Cell phone number successfully added.");
                validPhone = true;
            } else {
                System.out.println("Cell phone number incorrectly formatted or does not contain international code.");
            }
        }
        
        System.out.println("\n" + loginSystem.registerUser("john_1", "Pass@1234", "+27718693002", "John", "Doe"));
        
        if (loginSystem.registerUser("john_1", "Pass@1234", "+27718693002", "John", "Doe").equals("User successfully registered!")) {
            System.out.println(" Login ");
            
            boolean loggedIn = false;
            int attempts = 0;
            while (!loggedIn && attempts < 3) {
                System.out.print("Enter Username: ");
                String loginUsername = scanner.nextLine();
                
                System.out.print("Enter Password: ");
                String loginPassword = scanner.nextLine();
                
                String loginStatus = loginSystem.returnLoginStatus(loginUsername, loginPassword);
                System.out.println(loginStatus);
                
                if (loginSystem.loginUser(loginUsername, loginPassword)) {
                    loggedIn = true;
                    loginSystem.setLoggedIn(true);
                } else {
                    attempts++;
                    if (attempts < 3) {
                        System.out.println("Please try again. (" + (3 - attempts) + " attempts remaining)");
                    }
                }
            }
            
            if (loggedIn) {
                Quickchat quickChat = new Quickchat(loginSystem);
                quickChat.start(); 
                StoredMessage stoerdMessage = new StoredMessage ();
                //StoredMessage.start();
            } else {
                System.out.println("Account locked. Too many failed attempts.");
            }
        }
        
        scanner.close();
    }
}
