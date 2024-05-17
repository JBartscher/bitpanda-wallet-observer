package de.bartscher.service;

import de.bartscher.client.CryptoWalletDTO;
import de.bartscher.client.PriceResponse;
import de.bartscher.exceptions.PriceNotFoundException;
import de.bartscher.model.*;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class CryptoWalletService {

    private final CryptoWalletRepository cryptoWalletRepository;

    private final CoinRepository coinRepository;

    public CryptoWalletService(CryptoWalletRepository cryptoWalletRepository, CoinRepository coinRepository) {
        this.cryptoWalletRepository = cryptoWalletRepository;
        this.coinRepository = coinRepository;
    }

    public CryptoWallet getById(Long id) {
        return cryptoWalletRepository.findById(id);
    }

    public CryptoWallet getByWalletByWalletIdentifier(String identifier) {
        return cryptoWalletRepository.findByWalletIdentifier(identifier);
    }

    @Transactional
    public CryptoWallet createWalletFromDTO(CryptoWalletDTO walletDTO, PriceResponse prices) throws PriceNotFoundException {
        CryptoWallet cryptoWallet = new CryptoWallet();
        cryptoWallet.setWalletId(walletDTO.id());

        // coin
        Optional<Coin> coinOptional = coinRepository.findByAbbreviation(walletDTO.attributes().coinAbbreviation());
        Coin coin = null;
        if (coinOptional.isPresent()) {
            coin = coinOptional.get();
        } else {
            coin = new Coin();
            coin.setAbbreviation(walletDTO.attributes().coinAbbreviation());
            coin.setName(walletDTO.attributes().name());
            coin.setCoinId(walletDTO.attributes().coinId());
        }
        cryptoWallet.setCoin(coin);

        WalletBalance walletBalance = new WalletBalance();

        walletBalance.setAmount(walletDTO.attributes().balance());
        walletBalance.setWorth(walletDTO.attributes().balance() * prices.getPrice(coin.getAbbreviation()));
        walletBalance.setType(WalletBalanceType.DATAPOINT_FROM_INIT);
        cryptoWallet.setBalanceHistory(List.of(walletBalance));

        cryptoWallet.setAmount(walletBalance.getAmount());
        cryptoWallet.setValue(walletBalance.getAmount());


        cryptoWalletRepository.persist(cryptoWallet);
        return cryptoWallet;
    }

    public Collection<CryptoWallet> getAllWallets() {
        return cryptoWalletRepository.listAll();
    }
}
