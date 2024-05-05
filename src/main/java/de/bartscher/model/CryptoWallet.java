package de.bartscher.model;

public class CryptoWallet {
    private String id;
    private String abbreviation;
    private Float amount;
    /**
     * the value the crypto amount is worth.
     */
    private Float worth;

    public CryptoWallet(String id, String abbreviation, Float amount, Float worth) {
        this.id = id;
        this.abbreviation = abbreviation;
        this.amount = amount;
        this.worth = worth;
    }

    public String getAbbreviation() {
        return abbreviation;
    }

    public void setAbbreviation(String abbreviation) {
        this.abbreviation = abbreviation;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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
