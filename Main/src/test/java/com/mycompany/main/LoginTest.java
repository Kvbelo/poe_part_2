package com.mycompany.main;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

public class LoginTest {
    
    private Login login;
    
    @BeforeEach
    void setUp() {
        login = new Login();
    }
    
    // ========== assertEquals TESTS ==========
    
    @Test
    void testUsernameCorrectlyFormatted() {
        // Test Data: "kyl_1"
        boolean result = login.checkUserName("kyl_1");
        assertTrue(result, "Username with underscore and 5 chars should be valid");
    }
    
    @Test
    void testUsernameIncorrectlyFormatted() {
        // Test Data: "kyle!!!!!!!"
        boolean result = login.checkUserName("kyle!!!!!!!");
        assertFalse(result, "Username without underscore and too long should be invalid");
    }
    
    @Test
    void testPasswordMeetsComplexity() {
        // Test Data: "Ch&&sec@ke99!"
        boolean result = login.checkPasswordComplexity("Ch&&sec@ke99!");
        assertTrue(result, "Password with capital, number, special char, 8+ chars should be valid");
    }
    
    @Test
    void testPasswordDoesNotMeetComplexity() {
        // Test Data: "password"
        boolean result = login.checkPasswordComplexity("password");
        assertFalse(result, "Password without capital, number, or special char should be invalid");
    }
    
    @Test
    void testCellPhoneCorrectlyFormatted() {
        // Test Data: +27838968976
        boolean result = login.checkCellPhoneNumber("+27838968976");
        assertTrue(result, "Cell number with international code should be valid");
    }
    
    @Test
    void testCellPhoneIncorrectlyFormatted() {
        // Test Data: 08966553
        boolean result = login.checkCellPhoneNumber("08966553");
        assertFalse(result, "Cell number without international code should be invalid");
    }
    
    // ========== assertTrue/False TESTS ==========
    
    @Test
    void testLoginSuccessful() {
        // First register a user
        login.registerUser("john_1", "Pass@1234", "+27718693002", "John", "Doe");
        
        // Then test login
        boolean result = login.loginUser("john_1", "Pass@1234");
        assertTrue(result, "Login with correct credentials should succeed");
    }
    
    @Test
    void testLoginFailed() {
        // First register a user
        login.registerUser("john_1", "Pass@1234", "+27718693002", "John", "Doe");
        
        // Then test login with wrong password
        boolean result = login.loginUser("john_1", "WrongPassword");
        assertFalse(result, "Login with incorrect password should fail");
    }
    
    @Test
    void testReturnLoginStatus() {
        login.registerUser("jane_1", "Pass@1234", "+27718693002", "Jane", "Smith");
        login.loginUser("jane_1", "Pass@1234");
        String result = login.returnLoginStatus();
        assertEquals("Welcome Jane, Smith it is great to see you again.", result);
    }
}