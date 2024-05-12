package de.bartscher.client;

/**
 *
 * @param next url to the next item, if not present the current payload is the last payload
 * @param self url to current payload
 * @param last url of the last payload endpoint
 */
public record PaginationLinks(String next, String self, String last) {
}
