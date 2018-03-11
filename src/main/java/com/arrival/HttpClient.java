package com.arrival;

import com.arrival.models.YandexLoginPage;
import com.arrival.models.YandexResults;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class HttpClient {
    private static HttpClient instance;
    private Map<String, String> cookies = new HashMap<>();

    private HttpClient() {
    }

    public static HttpClient getInstance() {
        log.debug("Initiating HttpClient...");
        if (instance == null) {
            instance = new HttpClient();
        }
        return instance;
    }

    @SneakyThrows
    public YandexResults getYandexPageContent(String url, String searchPattern) {
        Map<String, String> query = new HashMap<>();
        query.put("text", searchPattern);
        query.put("lr", "2");

        Connection.Response response = getRequest(url, query);
        if (response == null) return new YandexResults(404, null, null, null);

        Document doc = response.parse();
        Elements title = doc.getElementsByTag("title");
        Elements results = doc.getElementsByAttributeValueMatching("class", "serp-item");
        Elements href = new Elements();
        for (Element result : results) {
            href.add(result.getElementsByAttribute("href").first());
        }

        return new YandexResults(response.statusCode(), title, results, href);
    }

    @SneakyThrows
    public int getPageContent(String url) {
        int statusCode = 404;
        Connection.Response response = getRequest(url);
        if (response != null) {
            cookies = response.cookies();
            statusCode = response.statusCode();
        }

        return statusCode;
    }

    @SneakyThrows
    public YandexLoginPage doYandexLogin(Map<String, String> dataMap) {
        Connection.Response response = postRequest("https://passport.yandex.ru/passport?mode=auth", dataMap);
        int statusCode = response.statusCode();

        Document doc = response.parse();
        Element title = doc.getElementsByTag("title").first();
        Element userName = doc.getElementsByAttributeValue("class", "usermenu__user-name").first();
        Element userEmail = doc.getElementsByAttributeValue("class", "usermenu__user-email").first();

        Element hrefExit = null;
        Elements href = doc.getElementsByAttribute("href");
        for (Element element : href) {
            if (element.text().contains("Выйти") || element.text().contains("Exit"))
                hrefExit = element;
        }

        return new YandexLoginPage(statusCode, title, userName, userEmail, hrefExit);
    }

    @SneakyThrows
    private Connection.Response getRequest(String url, Map<String, String> query) {
        Connection.Response response = null;
        try {
            response = Jsoup.connect(url)
                    .data(query)
                    .ignoreContentType(true)
                    .userAgent("Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:25.0) Gecko/20100101 Firefox/25.0")
                    .referrer("http://www.google.com")
                    .timeout(12000)
                    .followRedirects(true)
                    .method(Connection.Method.GET)
                    .cookies(cookies)
                    .execute();
        } catch (UnknownHostException e) {
            log.error(e.toString());
        }

        if (response != null) {
            log.debug(response.url().toString());
            cookies = response.cookies();
        }

        return response;
    }

    @SneakyThrows
    private Connection.Response getRequest(String url) {
        Connection.Response response = null;
        try {
            response = Jsoup.connect(url)
                    .ignoreContentType(true)
                    .userAgent("Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:25.0) Gecko/20100101 Firefox/25.0")
                    .referrer("http://www.google.com")
                    .timeout(12000)
                    .followRedirects(true)
                    .method(Connection.Method.GET)
                    .cookies(cookies)
                    .execute();
        } catch (UnknownHostException e) {
            log.error(e.toString());
        }

        if (response != null) {
            log.debug(response.url().toString());
            cookies = response.cookies();
        }

        return response;
    }

    @SneakyThrows
    private Connection.Response postRequest(String url, Map<String, String> dataMap) {
        Connection.Response response = null;
        try {
            response = Jsoup.connect(url)
                    .ignoreContentType(true)
                    .userAgent("Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:25.0) Gecko/20100101 Firefox/25.0")
                    .followRedirects(true)
                    .timeout(12000)
                    .referrer("yandex.ru")
                    .data(dataMap)
                    .cookies(cookies)
                    .method(Connection.Method.POST)
                    .execute();
        } catch (UnknownHostException e) {
            log.error(e.toString());
        }

        if (response != null) {
            log.debug(response.url().toString());
            cookies = response.cookies();
        }

        return response;
    }
}