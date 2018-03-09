package com.arrival.search;

import com.arrival.TestBase;
import org.testng.annotations.Test;

import java.io.IOException;

public class SearchTest extends TestBase {

    @Test
    public void test() throws IOException {
        LOGGER.info("Search test");
        getUrl("http://www.yandex.ru");
    }
}
