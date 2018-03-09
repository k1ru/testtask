package com.arrival;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import java.io.IOException;

public class TestBase {
    protected final Logger LOGGER = LoggerFactory.getLogger(TestBase.class);
    private HttpClient httpClient;


    @BeforeClass
    public void beforeClass() {
        LOGGER.info("beforeClass");
        httpClient = HttpClient.getInstance();
    }

    @AfterClass
    public void afterClass() {
        LOGGER.info("afterClass");
    }

    protected void getUrl(String url) throws IOException {
        httpClient.getByUrl(url);
//        LOGGER.debug(s);
    }
}
