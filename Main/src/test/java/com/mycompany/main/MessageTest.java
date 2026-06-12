package com.mycompany.main;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;

public class MessageTest {
    
    private Message message;
    
    @BeforeEach
    void setUp() {
        message = new Message();
    }
    
    // ========== MESSAGE LENGTH TESTS ==========
    
    @Test
    void testMessageNotExceed250Characters_Success() {
        String testMessage = "Hi Mike, can you join us for dinner tonight?";
        String result = message.checkMessageLength(testMessage);
        assertEquals("Message ready to send.", result);
    }
    
    @Test
    void testMessageExceeds250Characters_Failure() {
        // Create a message longer than 250 characters
        StringBuilder longMessage = new StringBuilder();
        for (int i = 0; i < 30; i++) {
            longMessage.append("This is a very long message that will exceed the limit. ");
        }
        String result = message.checkMessageLength(longMessage.toString());
        assertTrue(result.contains("exceeds 250 characters by"));
        assertTrue(result.contains("please reduce the size"));
    }
    
    // ========== RECIPIENT CELL NUMBER TESTS ==========
    
    @Test
    void testRecipientNumberCorrectlyFormatted() {
        String result = message.checkRecipientCell("+27718693002");
        assertEquals("Cell phone number successfully captured.", result);
    }
    
    @Test
    void testRecipientNumberIncorrectlyFormatted() {
        String result = message.checkRecipientCell("08575975889");
        assertTrue(result.contains("incorrectly formatted") || result.contains("international code"));
    }
    
    // ========== MESSAGE HASH TESTS ==========
    
    @Test
    void testMessageHashCorrect() {
        // Test Data from Part 2: MessageID first two digits = 00, messageNumber = 0, first word = "HI", last word = "TONIGHT"
        // Expected: "00:0:HITONIGHT"
        String messageID = "0012345678";
        int messageNumber = 0;
        String messageText = "HI Mike can you join us for dinner TONIGHT";
        
        String hash = message.createMessageHash(messageID, messageNumber, messageText);
        
        // The hash should contain the first two digits, colon, number, colon, and combined words
        assertTrue(hash.startsWith("00:0:"));
        assertTrue(hash.contains("HI") || hash.contains("HITONIGHT"));
    }
    
    @Test
    void testMessageHashLoop() {
        // Test multiple message hashes in a loop
        String[] testMessages = {
            "Hello World",
            "Good Morning Everyone",
            "See you later",
            "Thank you for your help",
            "Meeting at 5pm"
        };
        
        String messageID = "9912345678";
        for (int i = 0; i < testMessages.length; i++) {
            String hash = message.createMessageHash(messageID, i, testMessages[i]);
            assertNotNull(hash);
            assertTrue(hash.contains(":"));
            System.out.println("Message " + (i+1) + " Hash: " + hash);
        }
    }
    
    // ========== MESSAGE ID TESTS ==========
    
    @Test
    void testMessageIDCreated() {
        String messageID = message.generateMessageID();
        assertNotNull(messageID);
        assertEquals(10, messageID.length(), "Message ID should be exactly 10 digits");
        System.out.println("Message ID generated: " + messageID);
    }
    
    @Test
    void testMessageIDNotMoreThanTenCharacters() {
        String messageID = message.generateMessageID();
        boolean result = message.checkMessageID(messageID);
        assertTrue(result, "Message ID should be 10 characters or less");
    }
    
    // ========== MESSAGE SEND TESTS ==========
    
    @Test
    void testUserSelectedSendMessage() {
        String result = message.sendMessage(1);
        assertEquals("Message successfully sent.", result);
    }
    
    @Test
    void testUserSelectedDisregardMessage() {
        String result = message.sendMessage(2);
        assertEquals("Press 0 to delete the message.", result);
    }
    
    @Test
    void testUserSelectedStoreMessage() {
        String result = message.sendMessage(3);
        assertEquals("Message successfully stored.", result);
    }
    
    // ========== ADDITIONAL TESTS ==========
    
    @Test
    void testPrintMessages() {
        ArrayList<String> testMessages = new ArrayList<>();
        testMessages.add("First message");
        testMessages.add("Second message");
        
        String result = message.printMessages(testMessages);
        assertTrue(result.contains("First message"));
        assertTrue(result.contains("Second message"));
    }
    
    @Test
    void testReturnTotalMessages() {
        int total = message.returnTotalMessages(5);
        assertEquals(5, total);
    }
}