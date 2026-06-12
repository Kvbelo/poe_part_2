/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.main;

/**
 *
 * @author kabel
 */
import java.util.ArrayList;
import java.util.Random;
import java.util.regex.Pattern;

public class Message {
    private String messageID;
    private int numMessagesSent;
    private String recipient;
    private String message;
    private String messageHash;
    private static int messageCounter = 0;
    
    public Message() {
        this.messageID = generateMessageID();
        this.numMessagesSent = ++messageCounter;
        this.recipient = "";
        this.message = "";
        this.messageHash = "";
    }
    
    String generateMessageID() {
        Random rand = new Random();
        long tenDigitNumber = 1000000000L + (long)(rand.nextDouble() * 9000000000L);
        return String.valueOf(tenDigitNumber);
    }
    
    public boolean checkMessageID(String messageID1) {
        return messageID.length() <= 10;
    }
    
    public String checkRecipientCell(String string) {
        String regex = "^\\+27[0-9]{1,10}$";
        if (Pattern.matches(regex, recipient)) {
            return "Cell phone number successfully captured.";
        } else {
            return "Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.";
        }
    }
    
    public String checkMessageLength(String toString) {
        if (message.length() <= 250) {
            return "Message ready to send.";
        } else {
            int excess = message.length() - 250;
            return "Message exceeds 250 characters by " + excess + "; please reduce the size.";
        }
    }
    
    public String createMessageHash() {
        String firstTwoDigits = messageID.substring(0, 2);
        String firstWord = "";
        String lastWord = "";
        
        String[] words = message.trim().split("\\s+");
        if (words.length > 0) {
            firstWord = words[0].toUpperCase();
            lastWord = words[words.length - 1].toUpperCase();
        }
        
        messageHash = firstTwoDigits + ":" + numMessagesSent + ":" + firstWord + lastWord;
        return messageHash;
    }
    
    public String sendMessage(int choice) {
        if (choice == 1) {
            return "Message successfully sent.";
        } else if (choice == 2) {
            return "Press 0 to delete the message.";
        } else if (choice == 3) {
            return "Message successfully stored.";
        }
        return "Invalid option.";
    }
    
    public String printMessage() {
        return "Message ID: " + messageID + "\n" +
               "Message Hash: " + messageHash + "\n" +
               "Recipient: " + recipient + "\n" +
               "Message: " + message;
    }
    
    public int returnTotalMessages(int par) {
        return messageCounter;
    }
    
    public String getMessageID() {
        return messageID;
    }
    
    public int getNumMessagesSent() {
        return numMessagesSent;
    }
    
    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }
    
    public String getRecipient() {
        return recipient;
    }
    
    public void setMessage(String message) {
        this.message = message;
    }
    
    public String getMessage() {
        return message;
    }
    
    public String getMessageHash() {
        return messageHash;
    }
    
    public static void resetCounter() {
        messageCounter = 0;
    }
    
    public void storeMessage() {
    try {
        java.io.FileWriter writer = new java.io.FileWriter("messages.json", true);
        writer.write("{\n");
        writer.write(" \"Message ID\": \"" + messageID + "\",\n");
        writer.write(" \"Message Hash\": \"" + messageHash + "\",\n");
        writer.write(" \"Recipient\": \"" + recipient + "\",\n");
        writer.write(" \"Message\": \"" + message + "\"\n");
        writer.write("},\n");
        writer.close();
    } catch (Exception error) {
        System.out.println("Could not save message.");
    }
}

    String createMessageHash(String msgID2, int i, String where_are_you_You_are_late_I_have_asked_y) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    String printMessages(ArrayList<String> testMessages) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
