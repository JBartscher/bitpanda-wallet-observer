package de.bartscher.client;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.Instant;
import java.time.ZonedDateTime;

public record TradeTime(@JsonProperty("date_iso8601") ZonedDateTime dateTime, @JsonProperty("unix") Instant ts) {
}
