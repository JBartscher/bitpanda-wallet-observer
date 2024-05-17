package de.bartscher.service;

import de.bartscher.client.PriceResponse;
import de.bartscher.exceptions.PriceNotFoundException;
import de.bartscher.model.CryptoWallet;
import de.bartscher.model.WalletBalance;
import de.bartscher.model.WalletBalanceRepository;
import de.bartscher.model.WalletBalanceType;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class WalletBalanceService {

    private final WalletBalanceRepository walletBalanceRepository;

    public WalletBalanceService(WalletBalanceRepository walletBalanceRepository) {
        this.walletBalanceRepository = walletBalanceRepository;
    }

    /**
     * Create a datapoint in time of the balance and worth of a crpto wallet
     *
     * @param wallet the crypto wallet for which a datapoint is created
     * @param prices a list of prices
     * @throws PriceNotFoundException if the abbreviation or coin could not be found
     */
    @Transactional
    public void createBalanceDatapoint(CryptoWallet wallet, PriceResponse prices) throws PriceNotFoundException {
        String abbreviation = wallet.getCoin().getAbbreviation();
        float price = prices.getPrice(abbreviation);
        WalletBalance balance = new WalletBalance();
        balance.setType(WalletBalanceType.DATAPOINT_FROM_SCHEDULER);
        balance.setAmount(wallet.getAmount());
        balance.setWorth(price * wallet.getAmount());
        walletBalanceRepository.persist(balance);
    }
}
