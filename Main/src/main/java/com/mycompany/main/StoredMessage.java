package com.mycompany.main;

import java.util.*;
import java.nio.file.*;
import java.io.*;
import org.json.JSONArray;
import org.json.JSONObject;

public class StoredMessage {
    
    private ArrayList<String> sentMessages = new ArrayList<>();
    private ArrayList<String> disregardedMessages = new ArrayList<>();
    private ArrayList<String> storedMessagesList = new ArrayList<>();
    private ArrayList<String> messageHashes = new ArrayList<>();
    private ArrayList<String> messageIDs = new ArrayList<>();
    
    private ArrayList<StoredMessage> allStoredMessages = new ArrayList<>();
    
    private static final String STORAGE_FILE = "stored_messages.json";
    private String message;
    private String messageHash;
    private String messageID;
    private String recipient;
    private String flag;

    public StoredMessage(String messageID, String messageHash, String recipient, String message, String flag) {
        this.messageID = messageID;
        this.messageHash = messageHash;
        this.recipient = recipient;
        this.message = message;
        this.flag = flag;
    }
    
    public StoredMessage() {
       loadStoredMessagesFromFile();
       allStoredMessages = new ArrayList<>();  
    }
    
    public void addMessage(String messageID, String messageHash, String recipient, String message, String flag) {
        StoredMessage msg = new StoredMessage(messageID, messageHash, recipient, message, flag);
        allStoredMessages.add(msg);
        messageIDs.add(messageID);
        messageHashes.add(messageHash);
        
        switch (flag.toLowerCase()) {
            case "sent" -> {
                sentMessages.add(message);
                saveMessageToJSON(msg);
            }
            case "disregard" -> {
                disregardedMessages.add(message);
                saveMessageToJSON(msg);
            }
            case "stored" -> {
                storedMessagesList.add(message);
                saveMessageToJSON(msg);
            }
        }
    }
    
    public void populateWithTestData(Message msgSystem) {
        String msgID1 = msgSystem.generateMessageID();
        String msgHash1 = msgSystem.createMessageHash(msgID1, 1, "Did you get the cake?");
        addMessage(msgID1, msgHash1, "+27834557896", "Did you get the cake?", "Sent");
        
        String msgID2 = msgSystem.generateMessageID();
        String msgHash2 = msgSystem.createMessageHash(msgID2, 2, "Where are you? You are late! I have asked you to be on time.");
        addMessage(msgID2, msgHash2, "+27838884567", "Where are you? You are late! I have asked you to be on time.", "Stored");
        
        String msgID3 = msgSystem.generateMessageID();
        String msgHash3 = msgSystem.createMessageHash(msgID3, 3, "Yohoooo, I am at your gate.");
        addMessage(msgID3, msgHash3, "+27834484567", "Yohoooo, I am at your gate.", "Disregard");
        
        String msgID4 = msgSystem.generateMessageID();
        String msgHash4 = msgSystem.createMessageHash(msgID4, 4, "It is dinner time !");
        addMessage(msgID4, msgHash4, "0838884567", "It is dinner time !", "Sent");
        
        String msgID5 = msgSystem.generateMessageID();
        String msgHash5 = msgSystem.createMessageHash(msgID5, 5, "Ok, I am leaving without you.");
        addMessage(msgID5, msgHash5, "+27838884567", "Ok, I am leaving without you.", "Stored");
    }
    
    // ==================== STORED MESSAGES MENU OPTIONS ====================
    
    public void displaySenderAndRecipient() {
        System.out.println("\n=== SENDER AND RECIPIENT OF ALL STORED MESSAGES ===");
        boolean found = false;
        for (StoredMessage msg : allStoredMessages) {
            if (msg.flag.equalsIgnoreCase("stored")) {
                System.out.println("Recipient: " + msg.recipient);
                found = true;
            }
        }
        if (!found) {
            System.out.println("No stored messages found.");
        }
    }
    
    public void displayLongestStoredMessage() {
        System.out.println("\n=== LONGEST STORED MESSAGE ===");
        StoredMessage longest = null;
        for (StoredMessage msg : allStoredMessages) {
            if (msg.flag.equalsIgnoreCase("stored")) {
                if (longest == null || msg.message.length() > longest.message.length()) {
                    longest = msg;
                }
            }
        }
        if (longest != null) {
            System.out.println("Message: " + longest.message);
            System.out.println("Length: " + longest.message.length() + " characters");
        } else {
            System.out.println("No stored messages found.");
        }
    }
    
    public void searchByMessageID(Scanner scanner) {
        System.out.print("\nEnter Message ID to search: ");
        String id = scanner.nextLine();
        boolean found = false;
        
        for (StoredMessage msg : allStoredMessages) {
            if (msg.messageID.equals(id)) {
                System.out.println("Recipient: " + msg.recipient);
                System.out.println("Message: " + msg.message);
                found = true;
                break;
            }
        }
        
        if (!found) {
            System.out.println("Message ID not found.");
        }
    }
    
    public void searchByRecipient(Scanner scanner) {
        System.out.print("\nEnter recipient to search: ");
        String person = scanner.nextLine();
        boolean found = false;
        
        System.out.println("\n=== MESSAGES FOR RECIPIENT: " + person + " ===");
        for (StoredMessage msg : allStoredMessages) {
            if (msg.recipient.equals(person)) {
                System.out.println("- " + msg.message);
                found = true;
            }
        }
        
        if (!found) {
            System.out.println("No messages found for this recipient.");
        }
    }
    
    public void deleteByMessageHash(Scanner scanner) {
        System.out.print("\nEnter Message Hash to delete: ");
        String hash = scanner.nextLine();
        boolean found = false;
        
        for (int i = 0; i < messageHashes.size(); i++) {
            if (messageHashes.get(i).equals(hash)) {
                StoredMessage msg = allStoredMessages.get(i);
                System.out.println("Message: \"" + msg.message + "\" successfully deleted.");
                allStoredMessages.remove(i);
                messageHashes.remove(i);
                messageIDs.remove(i);
                
                // Also remove from respective arrays
                if (msg.flag.equalsIgnoreCase("sent")) {
                    sentMessages.remove(msg.message);
                } else if (msg.flag.equalsIgnoreCase("disregard")) {
                    disregardedMessages.remove(msg.message);
                } else if (msg.flag.equalsIgnoreCase("stored")) {
                    storedMessagesList.remove(msg.message);
                }
                
                found = true;
                break;
            }
        }
        
        if (!found) {
            System.out.println("Message hash not found.");
        }
    }
    
    // 2f: Display full report of all stored messages
    public void displayFullReport() {
        System.out.println("\n=== FULL REPORT OF ALL STORED MESSAGES ===");
        System.out.printf("%-20s %-20s %-30s %-15s\n", "Message Hash", "Recipient", "Message", "Flag");
        System.out.println(String.join("", Collections.nCopies(85, "-")));
        
        for (StoredMessage msg : allStoredMessages) {
            String shortHash = msg.messageHash.length() > 18 ? 
                              msg.messageHash.substring(0, 18) : msg.messageHash;
            String shortMsg = msg.message.length() > 28 ? 
                             msg.message.substring(0, 27) + "..." : msg.message;
            System.out.printf("%-20s %-20s %-30s %-15s\n", 
                            shortHash, msg.recipient, shortMsg, msg.flag);
        }
    }
    
    // Display all sent messages (for test requirement)
    public ArrayList<String> getSentMessages() {
        return sentMessages;
    }
    
    // Get all stored messages (for testing)
    public ArrayList<StoredMessage> getAllStoredMessages() {
        return allStoredMessages;
    }
    
    // Get message hashes (for testing)
    public ArrayList<String> getMessageHashes() {
        return messageHashes;
    }
    
    // ==================== JSON STORAGE METHODS ====================
    
    private void saveMessageToJSON(StoredMessage msg) {
        JSONArray jsonArray = new JSONArray();
        
        try {
            String content = new String(Files.readAllBytes(Paths.get(STORAGE_FILE)));
            jsonArray = new JSONArray(content);
        } catch (IOException e) {
            // File doesn't exist, create new array
        }
        
        JSONObject jsonMsg = new JSONObject();
        jsonMsg.put("messageID", msg.messageID);
        jsonMsg.put("messageHash", msg.messageHash);
        jsonMsg.put("recipient", msg.recipient);
        jsonMsg.put("message", msg.message);
        jsonMsg.put("flag", msg.flag);
        
        jsonArray.put(jsonMsg);
        
        try {
            Files.write(Paths.get(STORAGE_FILE), jsonArray.toString(2).getBytes(java.nio.charset.StandardCharsets.UTF_8));
        } catch (IOException ex) {
            System.getLogger(StoredMessage.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
    }
    
    private void loadStoredMessagesFromFile() {
        try {
            String content = new String(Files.readAllBytes(Paths.get(STORAGE_FILE)));
            JSONArray jsonArray = new JSONArray(content);
            
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonMsg = jsonArray.getJSONObject(i);
                StoredMessage msg = new StoredMessage(
                        jsonMsg.getString("messageID"),
                        jsonMsg.getString("messageHash"),
                        jsonMsg.getString("recipient"),
                        jsonMsg.getString("message"),
                        jsonMsg.getString("flag")
                );
                allStoredMessages.add(msg);
                messageIDs.add(msg.messageID);
                messageHashes.add(msg.messageHash);
                
                if (msg.flag.equalsIgnoreCase("sent")) {
                    sentMessages.add(msg.message);
                } else if (msg.flag.equalsIgnoreCase("disregard")) {
                    disregardedMessages.add(msg.message);
                } else if (msg.flag.equalsIgnoreCase("stored")) {
                    storedMessagesList.add(msg.message);
                }
            }
        } catch (IOException e) {
            // No existing file
        }
    }
    
    // Display stored messages menu
    public void showMenu(Scanner scanner) {
        int choice;
        do {
            System.out.println("\n=== STORED MESSAGES MENU ===");
            System.out.println("a. Display the sender and recipient of all stored messages");
            System.out.println("b. Display the longest stored message");
            System.out.println("c. Search for a message ID and display recipient and message");
            System.out.println("d. Search for all messages stored for a particular recipient");
            System.out.println("e. Delete a message using the message hash");
            System.out.println("f. Display a report that lists full details of all stored messages");
            System.out.println("g. Return to main menu");
            System.out.print("Enter choice: ");
            
            String input = scanner.nextLine();
            if (input.length() == 0) {
                choice = 'g';
            } else {
                choice = input.charAt(0);
            }
            
            switch (choice) {
                case 'a' -> displaySenderAndRecipient();
                case 'b' -> displayLongestStoredMessage();
                case 'c' -> searchByMessageID(scanner);
                case 'd' -> searchByRecipient(scanner);
                case 'e' -> deleteByMessageHash(scanner);
                case 'f' -> displayFullReport();
                case 'g' -> System.out.println("Returning to main menu...");
                default -> System.out.println("Invalid choice.");
            }
        } while (choice != 'g');
    }
}