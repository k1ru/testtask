package com.arrival;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

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

    }


}
