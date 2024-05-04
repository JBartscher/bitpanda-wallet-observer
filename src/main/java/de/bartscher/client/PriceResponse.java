package de.bartscher.client;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

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
}
