package com.arrival;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;

public class HttpClient {
    private final Logger LOGGER = LoggerFactory.getLogger(HttpClient.class);
    private static HttpClient instance = null;
    private final OkHttpClient client = new OkHttpClient();

    private HttpClient() {
    }

    public static HttpClient getInstance() {
        if (instance == null) {
            instance = new HttpClient();
        }
        return instance;
    }

    public void getByUrl(String url) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .build();

        try (Response response = client.newCall(request).execute()) {
            InputStream inputStream = response.body().byteStream();
            while (inputStream.available() > 0) {
                inputStream.read();
            }
        }
    }
}
