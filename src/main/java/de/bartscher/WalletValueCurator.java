package de.bartscher;

import de.bartscher.client.*;
import de.bartscher.exceptions.PriceNotFoundException;
import de.bartscher.model.CryptoWallet;
import de.bartscher.service.CryptoWalletService;
import de.bartscher.service.WalletBalanceService;
import io.quarkus.runtime.StartupEvent;
import io.quarkus.scheduler.Scheduled;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.jboss.logging.Logger;

import java.util.ArrayList;
import java.util.List;

import static java.lang.String.format;

@ApplicationScoped
public class WalletValueCurator {

    private static final Logger LOG = Logger.getLogger(WalletValueCurator.class);

    @RestClient
    BitPandaApiClient bitPandaApiClient;

    private final CryptoWalletService cryptoWalletService;
    private final WalletBalanceService walletBalanceService;

    public WalletValueCurator(CryptoWalletService cryptoWalletService, WalletBalanceService walletBalanceService) {
        this.cryptoWalletService = cryptoWalletService;
        this.walletBalanceService = walletBalanceService;
    }

    void onStart(@Observes StartupEvent ev) {
        LOG.info("the application is starting...");
        LOG.info(format("found %d wallets.", cryptoWalletService.getAllWallets().size()));
        LOG.info("initializing crypto wallets...");
        initWallets();
        LOG.info(format("after initializing %d wallets are present.", cryptoWalletService.getAllWallets().size()));

    }

    @Scheduled(every = "30m")
    void archiveWalletValues() {
        var prices = bitPandaApiClient.getPrices();

        // gets all wallets, does not need pagination
        CryptoWalletsResponse walletsResponse = bitPandaApiClient.getCryptoWallets();
        LOG.info(format("save value of following wallets: %s", walletsResponse.cryptoWallets().stream().map(it -> it.attributes().name()).toList()));
        for (CryptoWalletDTO w : walletsResponse.cryptoWallets()) {
            CryptoWallet wallet = cryptoWalletService.getByWalletByWalletIdentifier(w.id());
            if (wallet == null) {
                LOG.warn(format("cannot find wallet with id: %s. Wallet is probably not created yet", w.id()));
                continue;
            }
            try {
                walletBalanceService.createBalanceDatapoint(wallet, prices);
                LOG.info(format("created new balance point for %s wallet", wallet.getCoin().getName()));
            } catch (PriceNotFoundException e) {
                LOG.error(e);
            }
        }
    }

    void initWallets() {
        var prices = bitPandaApiClient.getPrices();
        // gets all wallets, does not need pagination
        CryptoWalletsResponse walletsResponse = bitPandaApiClient.getCryptoWallets();
        LOG.info(walletsResponse);
        for (CryptoWalletDTO walletDTO : walletsResponse.cryptoWallets()) {
            CryptoWallet wallet = cryptoWalletService.getByWalletByWalletIdentifier(walletDTO.id());
            if (wallet == null) {
                try {
                    wallet = cryptoWalletService.createWalletFromDTO(walletDTO, prices);
                } catch (PriceNotFoundException e) {
                    LOG.error("could not create wallet. Error occurred: " + e.getMessage(), e);
                }
            }
        }
    }

    void initTrades() {
        List<CryptoTrade> allTrades = queryAllCryptoTrades();
        for (CryptoTrade trade : allTrades) {

        }

    }

    private List<CryptoTrade> queryAllCryptoTrades() {
        List<CryptoTrade> allTrades = new ArrayList<>();
        int pageNumber = 1;
        CryptoTradesResponse tradesResponse = null;
        do {
            tradesResponse = bitPandaApiClient.getTrades(pageNumber);
            allTrades.addAll(tradesResponse.trades());
            pageNumber++;
        } while (tradesResponse.paginationLinks().next() != null);
        return allTrades;
    }

    private List<CryptoWallet> queryCryptoWallets() {
        CryptoWalletsResponse walletDtos = bitPandaApiClient.getCryptoWallets();
        return walletDtos.cryptoWallets().stream().map(this::toCryptoWallet).toList();
    }

    private CryptoWallet toCryptoWallet(CryptoWalletDTO cryptoWalletDTO) {
        CryptoWallet wallet = null;
        try {
            wallet = WalletHelper.mapCryptoWalletDTOtoEntity(cryptoWalletDTO, bitPandaApiClient.getPrices());
        } catch (PriceNotFoundException e) {
            LOG.error("Could not get WalletValue " + e.getMessage());
        }
        return wallet;
    }
}
