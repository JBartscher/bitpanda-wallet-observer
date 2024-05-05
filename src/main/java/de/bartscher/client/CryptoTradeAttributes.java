package de.bartscher.client;

import com.fasterxml.jackson.annotation.JsonProperty;

public record CryptoTradeAttributes(TradeType type,
                                    @JsonProperty("cryptocoin_id") int coinId,
                                    @JsonProperty("cryptocoin_symbol") String coinAbbreviation,
                                    @JsonProperty("amount_fiat") float tradeVolume,
                                    @JsonProperty("fiat_to_eur_rate") float tradeConversionRateToEuro,
                                    @JsonProperty("price") float coinPrice,
                                    @JsonProperty("amount_cryptocoin") float coinAmount,
                                    @JsonProperty("time") TradeTime time) {
}