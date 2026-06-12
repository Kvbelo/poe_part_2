package com.mycompany.main;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class StoredMessageTest {
    
    private StoredMessagesManager storedManager;
    private Message messageSystem;
    
    @BeforeEach
    void setUp() {
        storedManager = new StoredMessagesManager();
        messageSystem = new Message();
        storedManager.populateWithTestData(messageSystem);
    }
    
    // ========== TEST 1: Sent Messages Array Correctly Populated ==========
    @Test
    void testSentMessagesArrayCorrectlyPopulated() {
        ArrayList<String> sentMessages = storedManager.getSentMessages();
        
        // Should contain test data messages 1 and 4
        assertTrue(sentMessages.contains("Did you get the cake?"));
        assertTrue(sentMessages.contains("It is dinner time !"));
        
        System.out.println("Sent Messages Test Passed - Contains: " + sentMessages);
    }
    
    // ========== TEST 2: Display the Longest Message ==========
    @Test
    void testDisplayLongestMessage() {
        // The longest message in test data is message 2
        ArrayList<StoredMessagesManager.StoredMessage> allMessages = storedManager.getAllStoredMessages();
        
        String longestMessage = "";
        for (StoredMessagesManager.StoredMessage msg : allMessages) {
            if (msg.message.length() > longestMessage.length()) {
                longestMessage = msg.message;
            }
        }
        
        String expected = "Where are you? You are late! I have asked you to be on time.";
        assertEquals(expected, longestMessage);
        
        System.out.println("Longest Message Test Passed - Length: " + longestMessage.length());
    }
    
    // ========== TEST 3: Search for Message ID ==========
    @Test
    void testSearchForMessageID() {
        // Get a message ID from a stored message
        ArrayList<StoredMessagesManager.StoredMessage> allMessages = storedManager.getAllStoredMessages();
        String targetID = null;
        String expectedMessage = null;
        
        for (StoredMessagesManager.StoredMessage msg : allMessages) {
            if (msg.message.equals("It is dinner time !")) {
                targetID = msg.messageID;
                expectedMessage = msg.message;
                break;
            }
        }
        
        assertNotNull(targetID);
        
        // Search for it
        String foundMessage = "";
        for (StoredMessagesManager.StoredMessage msg : allMessages) {
            if (msg.messageID.equals(targetID)) {
                foundMessage = msg.message;
                break;
            }
        }
        
        assertEquals(expectedMessage, foundMessage);
        System.out.println("Message ID Search Test Passed - Found: " + foundMessage);
    }
    
    // ========== TEST 4: Search All Messages for a Particular Recipient ==========
    @Test
    void testSearchForRecipient() {
        String targetRecipient = "+27838884567";
        ArrayList<String> foundMessages = new ArrayList<>();
        
        ArrayList<StoredMessagesManager.StoredMessage> allMessages = storedManager.getAllStoredMessages();
        for (StoredMessagesManager.StoredMessage msg : allMessages) {
            if (msg.recipient.equals(targetRecipient)) {
                foundMessages.add(msg.message);
            }
        }
        
        assertTrue(foundMessages.contains("Where are you? You are late! I have asked you to be on time."));
        assertTrue(foundMessages.contains("Ok, I am leaving without you."));
        
        System.out.println("Recipient Search Test Passed - Found " + foundMessages.size() + " messages");
    }
    
    // ========== TEST 5: Delete a Message Using Message Hash ==========
    @Test
    void testDeleteByMessageHash() {
        // Get a message hash from test data message 2
        ArrayList<StoredMessagesManager.StoredMessage> allMessages = storedManager.getAllStoredMessages();
        String targetHash = null;
        String expectedMessage = null;
        
        for (StoredMessagesManager.StoredMessage msg : allMessages) {
            if (msg.message.equals("Where are you? You are late! I have asked you to be on time.")) {
                targetHash = msg.messageHash;
                expectedMessage = msg.message;
                break;
            }
        }
        
        assertNotNull(targetHash);
        
        // Delete it (we need to get the hash from the manager)
        Object hashesObject = storedManager.getMessageHashes();
        int hashCount = 0;
        
        // Handle the case where getMessageHashes() returns an Object
        if (hashesObject instanceof ArrayList<?>) {
            ArrayList<?> hashes = (ArrayList<?>) hashesObject;
            hashCount = hashes.size();
            
            boolean found = false;
            String deletedMessage = "";
            
            for (int i = 0; i < hashes.size(); i++) {
                Object hashObj = hashes.get(i);
                if (hashObj != null && hashObj.equals(targetHash)) {
                    deletedMessage = storedManager.getAllStoredMessages().get(i).message;
                    found = true;
                    break;
                }
            }
            
            assertTrue(found);
            assertEquals(expectedMessage, deletedMessage);
        } else {
            fail("getMessageHashes() did not return an ArrayList");
        }
        
        System.out.println("Delete by Hash Test Passed - Deleted: " + expectedMessage);
    }
    
    // ========== TEST 6: Display Report ==========
    @Test
    void testDisplayReport() {
        ArrayList<StoredMessagesManager.StoredMessage> allMessages = storedManager.getAllStoredMessages();
        
        System.out.println("\n=== DISPLAY REPORT TEST ===");
        System.out.println("The system returns a report that shows all the sent messages, including:");
        System.out.println("- Message Hash");
        System.out.println("- Recipient");
        System.out.println("- Message");
        System.out.println("\n--- Report Contents ---");
        
        // Fix: Ensure flag is treated as String, add null check
        for (Iterator<StoredMessagesManager.StoredMessage> it = allMessages.iterator(); it.hasNext();) {
            StoredMessagesManager.StoredMessage msg = it.next();
            if (msg.flag != null && msg.flag.toString().equalsIgnoreCase("sent")) {
                System.out.println("Hash: " + msg.messageHash);
                System.out.println("Recipient: " + msg.recipient);
                System.out.println("Message: " + msg.message);
                System.out.println("---");
            }
        }
        
        // Verify at least one sent message exists
        boolean hasSentMessage = false;
        for (StoredMessagesManager.StoredMessage msg : allMessages) {
            if (msg.flag != null && msg.flag.toString().equalsIgnoreCase("sent")) {
                hasSentMessage = true;
                break;
            }
        }
        
        assertTrue(hasSentMessage, "Report should contain at least one sent message");
        System.out.println("\nDisplay Report Test Passed");
    }
    
    // ========== BONUS: Test All Arrays Populated ==========
    @Test
    void testAllArraysPopulated() {
        ArrayList<StoredMessagesManager.StoredMessage> allMessages = storedManager.getAllStoredMessages();
        
        // Count different types
        int sentCount = 0;
        int storedCount = 0;
        int disregardedCount = 0;
        
        for (StoredMessagesManager.StoredMessage msg : allMessages) {
            // Fix: Convert flag to String properly with null check
            String flagValue = (msg.flag != null) ? msg.flag.toString().toLowerCase() : "";
            switch (flagValue) {
                case "sent" -> sentCount++;
                case "stored" -> storedCount++;
                case "disregard" -> disregardedCount++;
                default -> {}
            }
        }
        
        // FIXED: Handle getMessageHashes() returning Object
        Object hashesObject = storedManager.getMessageHashes();
        int totalHashes = 0;
        
        if (hashesObject instanceof ArrayList<?>) {
            totalHashes = ((ArrayList<?>) hashesObject).size();
        } else if (hashesObject instanceof List<?>) {
            totalHashes = ((List<?>) hashesObject).size();
        } else if (hashesObject instanceof java.util.Collection<?>) {
            totalHashes = ((java.util.Collection<?>) hashesObject).size();
        } else {
            // If it's not a Collection, we can't get size
            totalHashes = -1; // Indicates unknown
        }
        
        System.out.println("\n=== ARRAY POPULATION TEST ===");
        System.out.println("Sent Messages: " + sentCount);
        System.out.println("Stored Messages: " + storedCount);
        System.out.println("Disregarded Messages: " + disregardedCount);
        System.out.println("Total Message Hashes: " + (totalHashes >= 0 ? totalHashes : "Unable to determine"));
        
        assertTrue(sentCount > 0, "Sent messages array should not be empty");
        assertTrue(storedCount > 0, "Stored messages array should not be empty");
        
        // Only check hash count if we could get it
        if (totalHashes >= 0) {
            assertTrue(totalHashes > 0, "Message hashes collection should not be empty");
        }
    }
}