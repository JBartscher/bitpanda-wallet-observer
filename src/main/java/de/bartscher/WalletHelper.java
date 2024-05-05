package de.bartscher;

import de.bartscher.client.CryptoWalletDTO;
import de.bartscher.client.PriceResponse;
import de.bartscher.exceptions.PriceNotFoundException;
import de.bartscher.model.CryptoWallet;

import java.text.MessageFormat;

public class WalletHelper {
    /**
     * map an CryptoWalletDTO into an CryptoWallet POJO
     *
     * @param walletDTO DTO of a CryptoWallet
     * @param prices list of asset prices
     * @return a CryptoWallet POJO
     * @throws PriceNotFoundException
     */
    public static CryptoWallet mapCryptoWalletDTOtoEntity(CryptoWalletDTO walletDTO, PriceResponse prices) throws PriceNotFoundException {
        String abbreviation = walletDTO.attributes().coinAbbreviation();
        // check if abbreviation is in prices list
        if (!prices.getCurrencyPrices().containsKey(abbreviation)) {
            throw new PriceNotFoundException(MessageFormat.format("prices list {0} does not contain abbreviation {1}", prices.getCurrencyPrices(), abbreviation));
        }
        // check if abbreviation has EUR value
        if (!prices.getCurrencyPrices().get(abbreviation).containsKey("EUR")) {
            throw new PriceNotFoundException(MessageFormat.format("price for abbreviation {0} does not contain price in EUR", abbreviation));
        }
        // get the worth in €
        float worth = prices.getCurrencyPrices().get(abbreviation).get("EUR");

        float amount = walletDTO.attributes().balance() * worth;
        return new CryptoWallet(walletDTO.id(), walletDTO.attributes().coinAbbreviation(), walletDTO.attributes().balance(), amount);
    }
}
