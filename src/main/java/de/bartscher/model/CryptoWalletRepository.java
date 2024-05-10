package de.bartscher.model;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.Optional;

@ApplicationScoped
public class CryptoWalletRepository implements PanacheRepository<CryptoWallet> {

    /**
     * find by wallet identifier.
     *
     * @param walletIdentifier unique wallet identifier "de098d7d-59e4-11e9-8c75-0a0e6623f374"
     * @return an {@link CryptoWallet}
     */
    public CryptoWallet findByWalletIdentifier(String walletIdentifier) {
        return find("wallet_identifier", walletIdentifier).firstResult();
    }
}
