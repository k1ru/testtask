package com.arrival;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpClient {
    private final Logger LOGGER = LoggerFactory.getLogger(HttpClient.class);

    private static HttpClient instance = null;

    private HttpClient() {
    }

    public static HttpClient getInstance() {
        if (instance == null) {
            instance = new HttpClient();
        }
        return instance;
    }

}
