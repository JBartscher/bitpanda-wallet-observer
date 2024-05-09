package de.bartscher.client;

import com.fasterxml.jackson.annotation.JsonProperty;

public record CryptoAttributesDTO(@JsonProperty("cryptocoin_id") int coinId,
                                  @JsonProperty("cryptocoin_symbol") String coinAbbreviation, float balance,
                                  String name) {
}
