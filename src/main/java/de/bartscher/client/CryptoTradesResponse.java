package de.bartscher.client;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record CryptoTradesResponse(@JsonProperty("data") List<CryptoTrade> trades) {
}
