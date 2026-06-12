/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.main;

/**
 *
 * @author kabel
 */
import java.util.Scanner;
import java.util.ArrayList;

public class Quickchat {
    private Login loginSystem;
    private ArrayList<Message> messages;
    private int totalSent;
    //private StoredMessage storedMessageSystem;
    private StoredMessage storedManager;
    
    public Quickchat(Login loginSystem) {
        this.loginSystem = loginSystem;
        this.messages = new ArrayList<>();
        this.totalSent = 0;
        this.storedManager = new StoredMessage(); 
    }
    
    public void start() {
        if (!loginSystem.isLoggedIn()) {
            System.out.println("Please log in first to use QuickChat.");
            return;
        }
        
        System.out.println("\nWelcome to QuickChat.");
        
        Scanner scanner = new Scanner(System.in);
        
        System.out.print("How many messages do you wish to enter? ");
        int numMessages = scanner.nextInt();
        scanner.nextLine();
        
        for (int i = 0; i < numMessages; i++) {
            System.out.println("\n--- Message " + (i + 1) + " ---");
            Message msg = new Message();
            
            boolean validRecipient = false;
            while (!validRecipient) {
                System.out.print("Enter recipient cell number (+27XXXXXXXXX): ");
                String recipient = scanner.nextLine();
                msg.setRecipient(recipient);
                
                String recipientCheck = msg.checkRecipientCell("08575975889");
                System.out.println(recipientCheck);
                if (recipientCheck.equals("Cell phone number successfully captured.")) {
                    validRecipient = true;
                }
            }
            
            boolean validMessage = false;
            while (!validMessage) {
                System.out.print("Enter your message (max 250 characters): ");
                String messageText = scanner.nextLine();
                msg.setMessage(messageText);
                
                String lengthCheck = msg.checkMessageLength(messageText);
                System.out.println(lengthCheck);
                if (lengthCheck.equals("Message ready to send.")) {
                    validMessage = true;
                }
            }
            
            msg.createMessageHash();
            
            System.out.println("\nChoose an option:");
            System.out.println("1 - Send Message");
            System.out.println("2 - Disregard Message");
            System.out.println("3 - Store Message to send later");
            System.out.print("Your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();
            
            String sendResult = msg.sendMessage(choice);
            System.out.println(sendResult);
            
         
            if (choice == 1) {
                totalSent++;
                System.out.println("\n" + msg.printMessage());
                messages.add(msg);
                // Save to stored messages
                storedManager.addMessage(msg.getMessageID(), msg.getMessageHash(), msg.getRecipient(), msg.getMessage(), "sent");
            } 
            else if (choice == 3) {
                   messages.add(msg);
                    // Save to stored messages
                    storedManager.addMessage(msg.getMessageID(), msg.getMessageHash(), msg.getRecipient(), msg.getMessage(), "stored");
                }
        }
        
        System.out.println("\n" + "=".repeat(50));
        System.out.println("Total number of messages sent: " + totalSent);
        System.out.println("=".repeat(50));
        
        boolean running = true;
        while (running) {
            System.out.println("\n--- MENU ---");
            System.out.println("Option 1) Send Messages");
            System.out.println("Option 2) Show recently sent messages");
            System.out.println("Option 3) Quit");
            System.out.println("Option 4) Stored Message");
            System.out.print("Choose an option: ");
            int option = scanner.nextInt();
            scanner.nextLine();
            
            switch (option) {
                case 1:
                    sendMoreMessages(scanner);
                    break;
                case 2:
                    System.out.println("Coming Soon.");
                    break;
                case 3:
                    System.out.println("Goodbye!");
                    running = false;
                    break;
                case 4:
                    storedManager.showMenu(scanner);
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
        scanner.close();
    }
    
    private void sendMoreMessages(Scanner scanner) {
        System.out.print("How many messages do you wish to enter? ");
        int numMessages = scanner.nextInt();
        scanner.nextLine();
        
        for (int i = 0; i < numMessages; i++) {
            System.out.println("\n--- Message " + (i + 1) + " ---");
            Message msg = new Message();
            
            boolean validRecipient = false;
            while (!validRecipient) {
                System.out.print("Enter recipient cell number (+27XXXXXXXXX): ");
                String recipient = scanner.nextLine();
                msg.setRecipient(recipient);
                
                String recipientCheck = msg.checkRecipientCell("08575975889");
                System.out.println(recipientCheck);
                if (recipientCheck.equals("Cell phone number successfully captured.")) {
                    validRecipient = true;
                }
            }
            
            boolean validMessage = false;
            while (!validMessage) {
                System.out.print("Enter your message (max 250 characters): ");
                String messageText = scanner.nextLine();
                msg.setMessage(messageText);
                
                String lengthCheck = msg.checkMessageLength(messageText);
                System.out.println(lengthCheck);
                if (lengthCheck.equals("Message ready to send.")) {
                    validMessage = true;
                }
            }
            
            msg.createMessageHash();
            
            System.out.println("\nChoose an option:");
            System.out.println("1 - Send Message");
            System.out.println("2 - Disregard Message");
            System.out.println("3 - Store Message to send later");
            System.out.print("Your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();
            
            String sendResult = msg.sendMessage(choice);
            System.out.println(sendResult);

            String flag = "";
        if (choice == 1) {
           flag = "sent";
           totalSent++;
           System.out.println("\n" + msg.printMessage());
           messages.add(msg);
           storedManager.addMessage(msg.getMessageID(), msg.getMessageHash(), msg.getRecipient(), msg.getMessage(), "sent");
        }  
        else if (choice == 2) {
                flag = "disregard";
        } 
        else if (choice == 3) {
                flag = "stored";
                messages.add(msg);
                storedManager.addMessage(msg.getMessageID(), msg.getMessageHash(), msg.getRecipient(), msg.getMessage(), "stored");
       }
    // Save to StoredMessage system
    if (choice == 1 || choice == 3) {
        storedManager.addMessage(
        msg.getMessageID(),
        msg.getMessageHash(),
        msg.getRecipient(),
        msg.getMessage(),
        flag
      );
    }
        }
        
        System.out.println("\nTotal number of messages sent: " + totalSent);
    }
    
    public int getTotalSent() {
        return totalSent;
    }
    
    public ArrayList<Message> getMessages() {
        return messages;
    }
}