package de.bartscher.client;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record CryptoWalletsResponse(@JsonProperty("data") List<CryptoWalletDTO> cryptoWallets) {
}
