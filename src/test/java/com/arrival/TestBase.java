package com.arrival;

import com.arrival.models.YandexLoginPage;
import com.arrival.models.YandexResults;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.testng.annotations.BeforeClass;

import java.util.HashMap;
import java.util.Map;

@Slf4j
public abstract class TestBase {
    private HttpClient httpClient;


    @BeforeClass
    public void beforeClass() {
        log.debug("beforeClass");
        httpClient = HttpClient.getInstance();
    }

    protected YandexResults getYandexResults(String url, String searchPattern) {
        return httpClient.getYandexPageContent(prepareUrl(url), searchPattern);
    }

    protected int getPage(String url) {
        return httpClient.getPageContent("https://" + url);
    }

    protected YandexLoginPage doYandexLogin(String userName, String password) {
        Map<String, String> dataMap = new HashMap<>();
        dataMap.put("login", userName);
        dataMap.put("passwd", password);
        dataMap.put("retpath", "https://yandex.ru");

        return httpClient.doYandexLogin(dataMap);
    }

    protected int checkElements(Elements elements, String expectedString) {
        int count = 0;
        for (Element element : elements) {
            if (expectedString.toLowerCase().contains(element.text().toLowerCase())) {
                count++;
            }
        }

        return count;
    }

    private String prepareUrl(String url) {
        String result = "https://" + url;
        if (url.toLowerCase().contains("yandex")) {
            result = "http://" + url + "/search/";
        } else result += "/";

        return result;
    }
}
