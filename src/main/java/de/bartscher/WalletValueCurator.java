package de.bartscher;

import de.bartscher.client.*;
import de.bartscher.exceptions.PriceNotFoundException;
import de.bartscher.model.CryptoWallet;
import de.bartscher.service.CryptoWalletService;
import io.quarkus.runtime.StartupEvent;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import java.util.List;

import static io.quarkus.arc.impl.UncaughtExceptions.LOGGER;

@ApplicationScoped
public class WalletValueCurator {

    @RestClient
    BitPandaApiClient bitPandaApiClient;

    private final CryptoWalletService cryptoWalletService;

    public WalletValueCurator(CryptoWalletService cryptoWalletService) {
        this.cryptoWalletService = cryptoWalletService;
    }

    void onStart(@Observes StartupEvent ev) {
        LOGGER.info("The application is starting...");
        //  PriceResponse priceResponse = bitPandaApiClient.getPrices(); LOGGER.info(priceResponse.toString());


    }

    void initWallets() {
        // gets all wallets, does not need pagination
        CryptoWalletsResponse walletsResponse = bitPandaApiClient.getCryptoWallets();
        LOGGER.info(walletsResponse);
        for (CryptoWalletDTO walletDTO : walletsResponse.cryptoWallets()) {
            CryptoWallet wallet = cryptoWalletService.getByWalletByWalletIdentifier(walletDTO.id());
            if (wallet == null) {
                wallet = cryptoWalletService.createWalletFromDTO(walletDTO);
            }
        }
    }

    void initTrades() {
        CryptoTradesResponse tradesResponse = bitPandaApiClient.getTrades();
        LOGGER.info(tradesResponse);
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
            LOGGER.error("Could not get WalletValue " + e.getMessage());
        }
        return wallet;
    }
}
