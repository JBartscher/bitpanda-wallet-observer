package de.bartscher.client;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import de.bartscher.exceptions.PriceNotFoundException;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

// nested maps don't work well with records as they rely on setters
@JsonIgnoreProperties(ignoreUnknown = true)
public class PriceResponse {
    @JsonProperty
    Map<String, Map<String, Float>> prices = new HashMap<String, Map<String, Float>>();

    @JsonAnyGetter
    public Map<String, Map<String, Float>> getCurrencyPrices() {
        return prices;
    }

    @JsonAnySetter
    public void setCurrencyPrice(String key, Map<String, Float> value) {
        prices.put(key, value);
    }

    public float getPrice(String coinAbbreviation) throws PriceNotFoundException {
        // check if abbreviation exists
        if (!prices.containsKey(coinAbbreviation)) {
            throw new PriceNotFoundException(MessageFormat.format("prices list {0} does not contain abbreviation {1}", prices, coinAbbreviation));
        }
        // check if abbreviation has EUR value
        if (!prices.get(coinAbbreviation).containsKey("EUR")) {
            throw new PriceNotFoundException(MessageFormat.format("price for abbreviation {0} does not contain price in EUR", coinAbbreviation));
        }
        return prices.get(coinAbbreviation).get("EUR");
    }
}
