package de.bartscher.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.*;

@Entity
public class CryptoWallet {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false, unique = true)
    @JsonProperty("wallet_id")
    private String walletId;

    @OneToOne(cascade = CascadeType.DETACH)
    private Coin coin;

    private Float amount;
    /**
     * the value the crypto amount is worth.
     */
    private Float worth;

    public Float getAmount() {
        return amount;
    }

    public void setAmount(Float amount) {
        this.amount = amount;
    }

    public Float getWorth() {
        return worth;
    }

    public void setWorth(Float worth) {
        this.worth = worth;
    }

}
