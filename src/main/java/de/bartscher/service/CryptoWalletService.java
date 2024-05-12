package de.bartscher.service;

import de.bartscher.client.CryptoWalletDTO;
import de.bartscher.model.Coin;
import de.bartscher.model.CoinRepository;
import de.bartscher.model.CryptoWallet;
import de.bartscher.model.CryptoWalletRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

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
    public CryptoWallet createWalletFromDTO(CryptoWalletDTO walletDTO) {
        CryptoWallet cryptoWallet = new CryptoWallet();
        cryptoWallet.setWalletId(walletDTO.id());

        // coin
        Optional<Coin> coinOptional = coinRepository.findByAbbreviation(walletDTO.attributes().coinAbbreviation());
        Coin coin = null;
        if(coinOptional.isPresent()){
            coin = coinOptional.get();
        } else {
            coin = new Coin();
            coin.setAbbreviation(walletDTO.attributes().coinAbbreviation());
            coin.setName(walletDTO.attributes().name());
            coin.setCoinId(walletDTO.attributes().coinId());
        }
        cryptoWallet.setCoin(coin);

       // cryptoWallet.setAmount(walletDTO.attributes().balance());



        cryptoWalletRepository.persist(cryptoWallet);
        return cryptoWallet;
    }
}
