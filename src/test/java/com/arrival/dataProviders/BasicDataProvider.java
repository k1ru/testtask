package com.arrival.dataProviders;

import com.arrival.models.AuthTestData;
import com.arrival.models.SearchTestData;
import org.testng.annotations.DataProvider;

public class BasicDataProvider {

    @DataProvider(name = "searchTestDataProvider")
    public static Object[][] getSearchTestData() {
        return new Object[][]{
                {new SearchTestData("Description: basic search test - valid url. Positive",
                        "www.yandex.ru", "arrival",
                        200, "яндекс", 10)},
                {new SearchTestData("Description: basic search test - wrong url. Negative",
                        "www.yan!dex.ru", "arrival",
                        404, "", 0)},
                {new SearchTestData("Description: basic search test - valid url, but not yandex.ru. Negative",
                        "www.google.ru", "arrival",
                        200, "google", 0)}
        };
    }

    @DataProvider(name = "authorizationTestDataProvider")
    public static Object[][] getAuthorizationTestData() {
        return new Object[][]{
                {new AuthTestData("Description: yandex authorization test - valid user and password. Positive",
                        "www.yandex.ru", "testTask2018", "z1a2q3testTask2018",
                        200, "яндекс", "testTask2018",
                        "testTask2018@yandex.ru", true)},
                {new AuthTestData("Description: yandex authorization test - valid user but invalid password. Negative",
                        "www.yandex.ru", "testTask2018", "wrongPassword",
                        200, "Авторизация", null,
                        null, false)},
                {new AuthTestData("Description: yandex authorization test - invalid user and password. Negative",
                        "www.yandex.ru", "wrongName", "wrongPassword",
                        200, "Авторизация", null,
                        null, false)},
                {new AuthTestData("Description: yandex authorization test - invalid url. Negative",
                        "www.yan!dex.ru", "wrongName", "wrongPassword",
                        404, null, null,
                        null, false)}
        };
    }
}
