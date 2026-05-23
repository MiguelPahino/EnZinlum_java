package edu.teamrocket.Address;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.security.KeyPair;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class GenSigTest {

    KeyPair pair = null;
    byte[] sign  = null;

    @BeforeEach
    void createSignMethod(){
       pair = GenSig.generateKeyPair();
       sign = GenSig.sign(pair.getPrivate(), "Contraseña");
    }

    @Test
    void testVerify() {
        boolean resultado = GenSig.verify(pair.getPublic(), "Contraseña", sign);
        assertTrue(resultado);
    }
    
}
