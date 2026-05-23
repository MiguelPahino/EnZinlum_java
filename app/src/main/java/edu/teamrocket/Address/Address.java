package edu.teamrocket.Address;

import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Optional; 

public class Address {

    public Optional<PublicKey> publicKey = Optional.empty();
    private Optional<PrivateKey> privateKey = Optional.empty();
    double balance = 0d;
    private final String symbol = "EZI";
    
    public Address(){}

    public void generateKeyPair(){
        KeyPair pair = GenSig.generateKeyPair();
        this.setPrivateKey(pair.getPrivate());
        this.setPublicKey(pair.getPublic());
    }

    private void setPrivateKey(PrivateKey privateKey){
        this.privateKey=Optional.of(privateKey);
    }

    private void setPublicKey(PublicKey publicKey){
        this.publicKey=Optional.of(publicKey);
    }

    double getBalance(){
        return this.balance;
    }

    boolean isSKpresent(){
        return privateKey.isPresent();
    }

    void transferEZI(double EZI){
        this.balance+=EZI;
    }

    public PublicKey getPublicKey(){
        return this.publicKey.get();
    }

    @Override
    public String toString() {
        return new StringBuilder()
                .append("\nPK = ")
                .append(this.getPublicKey().hashCode())
                .append("\nBalance = ")
                .append(this.getBalance())
                .append("\s")
                .append(this.symbol).toString();
    }
    
}
