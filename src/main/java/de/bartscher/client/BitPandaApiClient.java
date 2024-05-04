package de.bartscher.client;

import io.quarkus.cache.CacheResult;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import org.eclipse.microprofile.rest.client.annotation.ClientHeaderParam;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@RegisterRestClient(configKey = "bitpanda-client")
public interface BitPandaApiClient {

    @GET
    @Path("/ticker")
    @CacheResult(cacheName = "prices-cache")
    PriceResponse getPrices();

    @GET
    @Path("/wallets")
    @ClientHeaderParam(name = "X-Api-Key", value = "${bitpanda.api-key}")
    CryptoWalletsResponse getCryptoWallets();
}
