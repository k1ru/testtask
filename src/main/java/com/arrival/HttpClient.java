package com.arrival;

import com.arrival.models.YandexLoginPage;
import com.arrival.models.YandexResults;
import lombok.SneakyThrows;
import lombok.Synchronized;
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
    private final String USER_AGENT = "Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:25.0) Gecko/20100101 Firefox/25.0";
    private final String REFERRER = "http://www.yandex.ru";
    private final String YANDEX_AUTH_URL = "https://passport.yandex.ru/passport?mode=auth";
    private final int REQUEST_TIMEOUT = 12000;

    private HttpClient() {
    }

    @Synchronized
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

        Connection.Response response = sendRequest(url, query, Connection.Method.GET);
        if (response == null) {
            return new YandexResults(404, null, null, null);
        }

        return parseYandexPageContent(response);
    }

    @SneakyThrows
    private YandexResults parseYandexPageContent(Connection.Response response) {
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
        Connection.Response response = sendRequest(url, new HashMap<>(), Connection.Method.GET);
        if (response != null) {
            return response.statusCode();
        } else return 404;
    }

    @SneakyThrows
    public YandexLoginPage doYandexLogin(Map<String, String> dataMap) {
        Connection.Response response = sendRequest(YANDEX_AUTH_URL, dataMap, Connection.Method.POST);

        return parseYandexLoginPage(response);
    }

    @SneakyThrows
    private YandexLoginPage parseYandexLoginPage(Connection.Response response) {
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
    private Connection.Response sendRequest(String url, Map<String, String> dataMap, Connection.Method method) {
        Connection.Response response = null;
        try {
            Connection connection = Jsoup.connect(url)
                    .ignoreContentType(true)
                    .userAgent(USER_AGENT)
                    .followRedirects(true)
                    .timeout(REQUEST_TIMEOUT)
                    .referrer(REFERRER)
                    .cookies(cookies)
                    .method(method);
            if (!dataMap.isEmpty()) {
                connection.data(dataMap);
            }
            response = connection.execute();
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