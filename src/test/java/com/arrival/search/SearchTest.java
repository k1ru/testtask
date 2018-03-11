package com.arrival.search;

import com.arrival.TestBase;
import com.arrival.dataProviders.BasicDataProvider;
import com.arrival.models.YandexResults;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.select.Elements;
import org.testng.Assert;
import org.testng.annotations.Test;

@Slf4j
public class SearchTest extends TestBase {


    @SneakyThrows
    @Test(dataProvider = "searchTestDataProvider", dataProviderClass = BasicDataProvider.class)
    public void testYandexSearch(String description,
                                 String url,
                                 String searchPattern,
                                 int expectedStatusCode,
                                 String expectedTitle,
                                 int expectedResultSize) {
        log.info(description);
        log.info("1. Open url " + url + " and search " + searchPattern);
        YandexResults yandexResults = getYandexResults(url, searchPattern);

        log.info("2. Check status code. Expected: " + expectedStatusCode);
        Assert.assertTrue(yandexResults.getStatusCode() == expectedStatusCode);

        if (expectedStatusCode == 200) {
            log.info("3. Check title. Expected: " + expectedTitle);
            Elements title = yandexResults.getTitle();
            Assert.assertTrue(checkElements(title, expectedTitle) > 0,
                    "Expected title presents\n" + title);

            log.info("4. Check results. Results should contain '" + searchPattern + "' " + expectedResultSize + " times");
            Elements results = yandexResults.getResults();
            Assert.assertTrue(results.size() >= expectedResultSize,
                    "Result entries contain searchPattern\n" + results);

            log.info("5. Check result href. Results href should contain '" + searchPattern + "' " + expectedResultSize + " times");
            Elements href = yandexResults.getHref();
            Assert.assertTrue(href.size() >= expectedResultSize,
                    "Result entries links contain searchPattern\n" + href);
        }
    }
}
