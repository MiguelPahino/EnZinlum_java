package edu.teamrocket.Contract;

import java.security.PublicKey;
import java.util.Map;
import java.util.Optional;
import java.util.HashMap;
import edu.teamrocket.Address.Address;

public class TokenContract {

private Optional<Address> owner = Optional.empty();
private Optional<PublicKey> ownerPK = Optional.empty();
private String name = new String();
private String symbol = new String();
private double totalSupply = 0d;
private Double totalTokensSold = 0d;
public Double tokenPrice = 0d;
private Map<PublicKey,Double> balances = new HashMap<PublicKey,Double>();

public TokenContract(Address address){
this.owner = Optional.of(address);
this.ownerPK = Optional.of(address.getPK());
}

public void setName(String name){
    this.name= name;
}

public void setSymbol(String symbol){
    this.symbol=symbol;
}

public void setTotalSupply(double totalSuply){
    this.totalSupply = totalSuply;
}

public Address owner(){
    return this.owner.get();
}

public void setTokenPrice(Double tokenPrice){
    this.tokenPrice =  tokenPrice;
}

public Double getTokenPrice(){
    return this.tokenPrice;
}

public String owners(){
    StringBuilder builder = new StringBuilder();

    this.balances
    .entrySet().stream().filter(x-> x.getKey() != ownerPK.get())
    .forEach(x-> builder.append(x.getKey() + ": " + x.getValue() + "\n"));

    return builder.toString();
}

public String name(){
    return this.name;
}

public String symbol(){
    return this.symbol;
}

public double totalSupply(){
    return this.totalSupply;
}

public Map<PublicKey,Double> getBalances(){
    return this.balances;
}

public int numOwners(){
   return balances.size();
}

void require(Boolean holds) throws InsufficientTokensException {
    if (! holds) {
        throw new InsufficientTokensException(
            "No dispones de tokens suficientes para completar la transaccion.");
    }
}

public int totalTokensSold(){
    getBalances().entrySet().stream()
            .filter(x-> x.getKey().equals(this.ownerPK.get()))
            .forEach(x-> this.totalTokensSold += x.getValue());
    return this.totalTokensSold.intValue();
}

public void addOwner(PublicKey pk,Double totalSupply){
    this.balances.putIfAbsent(pk, totalSupply);
}

public Double balanceOf(PublicKey ownerPK){
    return this.balances.containsKey(ownerPK) ?this.balances.get(ownerPK):0d;
}

public void transfer(PublicKey recipient,Double quantity){
    try {
            require(balanceOf(ownerPK.get()) >= quantity);
            this.getBalances().compute(ownerPK.get(), (pk, tokens) -> tokens - quantity);
            this.getBalances().put(recipient, balanceOf(recipient) + quantity);
        } catch (InsufficientTokensException e) {
        }      
}

public void transfer(PublicKey sender,PublicKey recipient, Double quantity){
try {
    require(balanceOf(sender) >= quantity);
    this.getBalances().compute(sender, (pk, tokens) -> tokens - quantity);
    this.getBalances().put(recipient, balanceOf(recipient) + quantity);
} catch (InsufficientTokensException e) {
} 
}

public void payable(PublicKey recipient, Double enziniums) {
        try {
            require(enziniums >= this.getTokenPrice());
            Double units = Math.floor(enziniums / tokenPrice);
            transfer(recipient, units);
            this.owner.get().transferEZI(units * tokenPrice);
        } catch (InsufficientTokensException e) {
        }
    }
 
@Override
    public String toString() {
        return new StringBuilder()
                .append("\nname = ")
                .append(this.name())
                .append("\nsymbol = ")
                .append(this.symbol())
                .append("\ntotalSupply = ")
                .append(this.totalSupply())
                .append("\nowner PK = ")
                .append(this.ownerPK.hashCode()).toString();
    }


}
