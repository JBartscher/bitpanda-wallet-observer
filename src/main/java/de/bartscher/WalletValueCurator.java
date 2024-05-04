package de.bartscher;

import de.bartscher.client.BitPandaApiClient;
import de.bartscher.client.CryptoWalletsResponse;
import de.bartscher.client.PriceResponse;
import io.quarkus.runtime.StartupEvent;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import static io.quarkus.arc.impl.UncaughtExceptions.LOGGER;

@ApplicationScoped
public class WalletValueCurator {

    @RestClient
    BitPandaApiClient bitPandaApiClient;

    void onStart(@Observes StartupEvent ev) {
        LOGGER.info("The application is starting...");
        PriceResponse priceResponse = bitPandaApiClient.getPrices();
        CryptoWalletsResponse wallets = bitPandaApiClient.getCryptoWallets();
        LOGGER.info(priceResponse.toString());
    }

    private void getWalletValue(){}
}
