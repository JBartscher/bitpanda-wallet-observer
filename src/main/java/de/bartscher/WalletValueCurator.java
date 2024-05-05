package de.bartscher;

import de.bartscher.client.*;
import de.bartscher.exceptions.PriceNotFoundException;
import de.bartscher.model.CryptoWallet;
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

    void onStart(@Observes StartupEvent ev) {
        LOGGER.info("The application is starting...");
      //  PriceResponse priceResponse = bitPandaApiClient.getPrices(); LOGGER.info(priceResponse.toString());
        CryptoTradesResponse tradesResponse = bitPandaApiClient.getTrades();
































































        LOGGER.info(tradesResponse);
    }

    private void queryCryptoWallets() {
        CryptoWalletsResponse walletDtos = bitPandaApiClient.getCryptoWallets();
        List<CryptoWallet> cryptoWallets = walletDtos.cryptoWallets().stream().map(w -> toCryptoWallet(w)).toList();
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
