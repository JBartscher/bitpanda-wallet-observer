package de.bartscher.client;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.quarkus.cache.CacheResult;
import io.quarkus.rest.client.reactive.ClientQueryParam;
import io.quarkus.rest.client.reactive.jackson.ClientObjectMapper;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.QueryParam;
import org.eclipse.microprofile.rest.client.annotation.ClientHeaderParam;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import org.jboss.resteasy.reactive.RestQuery;

@RegisterRestClient(configKey = "bitpanda-client")
public interface BitPandaApiClient {

    @ClientObjectMapper
    static ObjectMapper objectMapper(ObjectMapper defaultObjectMapper) {
        return defaultObjectMapper.copy()
                .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
                .disable(DeserializationFeature.UNWRAP_ROOT_VALUE);
    }


    @GET
    @Path("/ticker")
    @CacheResult(cacheName = "prices-cache")
    PriceResponse getPrices();

    @GET
    @Path("/wallets")
    @ClientHeaderParam(name = "X-Api-Key", value = "${bitpanda.api-key}")
    CryptoWalletsResponse getCryptoWallets();

    @GET
    @Path("/trades")
    @ClientQueryParam(name = "page_size", value = "50")
    @ClientHeaderParam(name = "X-Api-Key", value = "${bitpanda.api-key}")
    CryptoTradesResponse getTrades(@RestQuery("page_number") int pageNumber);
}
