package edu.teamrocket.Address;

import java.nio.charset.Charset;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Signature;
import java.util.Optional;



import java.security.NoSuchProviderException;

public class GenSig {
    
    private GenSig(){}

    public static KeyPair generateKeyPair(){
        Optional<KeyPair> pair = Optional.empty();
        if (pair.isEmpty()){
            try{
                KeyPairGenerator generator = KeyPairGenerator.getInstance("DSA", "SUN");
                SecureRandom random = SecureRandom.getInstance("SHA1PRNG", "SUN");
                generator.initialize(1024,random);
                pair = Optional.of(generator.generateKeyPair());
            }
            catch(NoSuchProviderException|NoSuchAlgorithmException e){
                throw new RuntimeException(e);
            }
        }
        return pair.get();
    }
    
    public static byte[] sign(PrivateKey sKey,String message){
        try{
            Signature signDSA = Signature.getInstance("SHA1withDSA", "SUN");
            signDSA.initSign(sKey);
            signDSA.update(message.getBytes(Charset.defaultCharset()));
            byte[] realSign = signDSA.sign();
            return realSign;
        }catch(Exception e){
            throw new RuntimeException("Error al firmar: " + e.getMessage(), e);
        }
    }

    public static boolean verify(PublicKey pk, String message,byte[] signedMessage){
        try{
            Signature sign = Signature.getInstance("SHA1withDSA", "SUN");
            sign.initVerify(pk);

            sign.update(message.getBytes(Charset.defaultCharset()));
            
            return sign.verify(signedMessage);
        }
        catch(Exception e){
            throw new RuntimeException("Error al confirmar la firma: " + e.getMessage(), e);
        }
    }
}
