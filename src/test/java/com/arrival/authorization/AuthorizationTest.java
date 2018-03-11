package com.arrival.authorization;

import com.arrival.TestBase;
import com.arrival.dataProviders.BasicDataProvider;
import com.arrival.models.YandexLoginPage;
import lombok.extern.slf4j.Slf4j;
import org.testng.Assert;
import org.testng.annotations.Test;

@Slf4j
public class AuthorizationTest extends TestBase {

    @Test(dataProvider = "authorizationTestDataProvider", dataProviderClass = BasicDataProvider.class)
    public void authorizationTest(String description,
                                  String url,
                                  String userName,
                                  String password,
                                  int expectedStatusCode,
                                  String expectedTitle,
                                  String expectedUserName,
                                  String expectedUserEmail,
                                  boolean exitButtonDisplayed) {
        log.info(description);
        log.info("1. Open url " + url);
        int statusCode = getPage(url);

        log.info("2. Check status code. Expected: " + expectedStatusCode);
        Assert.assertTrue(statusCode == expectedStatusCode);

        if (expectedStatusCode == 200) {
            log.info("3. Do yandex login");
            YandexLoginPage yandexLoginPage = doYandexLogin(userName, password);

            log.info("4. Check status code. Expected: " + expectedStatusCode);
            Assert.assertTrue(yandexLoginPage.getStatusCode() == expectedStatusCode);

            log.info("5. Check title. Expected: " + expectedTitle);
            String title = yandexLoginPage.getTitle().text();
            Assert.assertTrue(title.equalsIgnoreCase(expectedTitle),
                    "Check title. Actual: " + title);

            log.info("6. Check user name. Expected: " + expectedUserName);
            if (expectedUserName == null) Assert.assertNull(yandexLoginPage.getUserName());
            else {
                String actualUserName = yandexLoginPage.getUserName().text();
                Assert.assertTrue(actualUserName.equalsIgnoreCase(expectedUserName),
                        "Check user name. Actual: " + actualUserName);
            }

            log.info("7. Check user email. Expected: " + expectedUserEmail);
            if (expectedUserEmail == null) Assert.assertNull(yandexLoginPage.getUserEmail());
            else {
                String actualUserEmail = yandexLoginPage.getUserEmail().text();
                Assert.assertTrue(actualUserEmail.equalsIgnoreCase(expectedUserEmail),
                        "Check user email. Actual: " + actualUserEmail);
            }

            log.info("8. Check that Exit button displayed: " + exitButtonDisplayed);
            if (exitButtonDisplayed) {
                Assert.assertNotNull(yandexLoginPage.getHrefExit(),
                        "Check that Exit button is displayed. Actual: " + yandexLoginPage.getHrefExit());
            } else Assert.assertNull(yandexLoginPage.getHrefExit(),
                    "Check that Exit button is NOT displayed. Actual: " + yandexLoginPage.getHrefExit());
        }
    }
}
