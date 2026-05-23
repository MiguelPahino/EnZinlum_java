package edu.teamrocket.Address;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class AddressTest {

    Address rick = new Address();
    
    @BeforeEach
    void createAddress(){
        rick.generateKeyPair();
    }

    @Test
    void testBalanceCero(){
        assertEquals(0d, rick.getBalance());
    }

    @Test
    void testTransferEZI(){
        rick.transferEZI(100d);
        assertEquals(100d, rick.getBalance());
    }

    @Test
    void testToString() {
        Address address = new Address();
        address.generateKeyPair();
    
        String result = address.toString();
    
        assertTrue(result.contains("PK = "));
        assertTrue(result.contains("Balance = "));
        assertTrue(result.contains("EZI"));
    }
}