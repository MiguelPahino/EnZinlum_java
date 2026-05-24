package edu.teamrocket.Address;

import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Optional;

import edu.teamrocket.Contract.TokenContract;  

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

    public double getBalance(){
        return this.balance;
    }

    boolean isSKpresent(){
        return privateKey.isPresent();
    }

    public void transferEZI(double EZI){
        this.balance+=EZI;
    }

    public void send(TokenContract contrato,double enziniums){
        if (getBalance()>=enziniums){
            this.balance -= enziniums;
            contrato.payable(getPK(), enziniums);
        }
    }

    public PublicKey getPK(){
        return this.publicKey.get();
    }

    @Override
    public String toString() {
        return new StringBuilder()
                .append("\nPK = ")
                .append(this.getPK().hashCode())
                .append("\nBalance = ")
                .append(this.getBalance())
                .append("\s")
                .append(this.symbol).toString();
    }
    
}
