package com.arrival.search;

import com.arrival.TestBase;
import com.arrival.dataProviders.BasicDataProvider;
import com.arrival.models.SearchTestData;
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
    public void testYandexSearch(SearchTestData data) {
        log.info(data.getDescription());
        log.info("1. Open url " + data.getUrl() + " and search " + data.getSearchPattern());
        YandexResults yandexResults = getYandexResults(data.getUrl(), data.getSearchPattern());

        log.info("2. Check status code. Expected: " + data.getExpectedStatusCode());
        Assert.assertTrue(yandexResults.getStatusCode() == data.getExpectedStatusCode());

        if (data.getExpectedStatusCode() == 200) {
            log.info("3. Check title. Expected: " + data.getExpectedTitle());
            Elements title = yandexResults.getTitle();
            Assert.assertTrue(checkElements(title, data.getExpectedTitle()) > 0,
                    "Expected title presents\n" + title);

            log.info("4. Check results. Results should contain '"
                    + data.getSearchPattern() + "' " + data.getExpectedResultSize() + " times");
            Elements results = yandexResults.getResults();
            Assert.assertTrue(results.size() >= data.getExpectedResultSize(),
                    "Result entries contain searchPattern\n" + results);

            log.info("5. Check result href. Results href should contain '"
                    + data.getSearchPattern() + "' " + data.getExpectedResultSize() + " times");
            Elements href = yandexResults.getHref();
            Assert.assertTrue(href.size() >= data.getExpectedResultSize(),
                    "Result entries links contain searchPattern\n" + href);
        }
    }
}
