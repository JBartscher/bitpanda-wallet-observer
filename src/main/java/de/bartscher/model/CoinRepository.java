package de.bartscher.model;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.Optional;

@ApplicationScoped
public class CoinRepository implements PanacheRepository<Coin> {
    /**
     * find by abbreviation.
     *
     * @param abbreviation abbreviation like "ADA", "BTC", LTC etc.
     * @return an Optional of a {@link Coin}
     */
    public Optional<Coin> findByAbbreviation(String abbreviation) {
        return find("abbreviation", abbreviation).firstResultOptional();
    }

    /**
     * find by name.
     *
     * @param name name like "BTC Wallet", "LTC Wallet" etc.
     * @return an Optional of a {@link Coin}
     */
    public Optional<Coin> findByName(String name) {
        return find("name", name).firstResultOptional();
    }

    /**
     * find by coin id.
     *
     * @param coinId name like 1 = BTC, 3 = LTC etc.
     * @return an Optional of a {@link Coin}
     */
    public Optional<Coin> findByCoinId(int coinId) {
        return find("coin_identifier", coinId).firstResultOptional();
    }
}
