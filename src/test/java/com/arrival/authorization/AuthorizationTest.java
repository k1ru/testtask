package com.arrival.authorization;

import com.arrival.TestBase;
import com.arrival.dataProviders.BasicDataProvider;
import com.arrival.models.AuthTestData;
import com.arrival.models.YandexLoginPage;
import lombok.extern.slf4j.Slf4j;
import org.testng.Assert;
import org.testng.annotations.Test;

@Slf4j
public class AuthorizationTest extends TestBase {

    @Test(dataProvider = "authorizationTestDataProvider", dataProviderClass = BasicDataProvider.class)
    public void authorizationTest(AuthTestData data) {
        log.info(data.getDescription());
        log.info("1. Open url " + data.getUrl());
        int statusCode = getPage(data.getUrl());

        log.info("2. Check status code. Expected: " + data.getExpectedStatusCode());
        Assert.assertTrue(statusCode == data.getExpectedStatusCode());

        if (data.getExpectedStatusCode() == 200) {
            log.info("3. Do yandex login");
            YandexLoginPage yandexLoginPage = doYandexLogin(data.getUserName(), data.getPassword());

            log.info("4. Check status code. Expected: " + data.getExpectedStatusCode());
            Assert.assertTrue(yandexLoginPage.getStatusCode() == data.getExpectedStatusCode());

            log.info("5. Check title. Expected: " + data.getExpectedTitle());
            String title = yandexLoginPage.getTitle().text();
            Assert.assertTrue(title.equalsIgnoreCase(data.getExpectedTitle()),
                    "Check title. Actual: " + title);

            log.info("6. Check user name. Expected: " + data.getExpectedUserName());
            if (data.getExpectedUserName() == null) {
                Assert.assertNull(yandexLoginPage.getUserName());
            } else {
                String actualUserName = yandexLoginPage.getUserName().text();
                Assert.assertTrue(actualUserName.equalsIgnoreCase(data.getExpectedUserName()),
                        "Check user name. Actual: " + actualUserName);
            }

            log.info("7. Check user email. Expected: " + data.getExpectedUserEmail());
            if (data.getExpectedUserEmail() == null) {
                Assert.assertNull(yandexLoginPage.getUserEmail());
            } else {
                String actualUserEmail = yandexLoginPage.getUserEmail().text();
                Assert.assertTrue(actualUserEmail.equalsIgnoreCase(data.getExpectedUserEmail()),
                        "Check user email. Actual: " + actualUserEmail);
            }

            log.info("8. Check that Exit button displayed: " + data.isExitButtonDisplayed());
            if (data.isExitButtonDisplayed()) {
                Assert.assertNotNull(yandexLoginPage.getHrefExit(),
                        "Check that Exit button is displayed. Actual: " + yandexLoginPage.getHrefExit());
            } else Assert.assertNull(yandexLoginPage.getHrefExit(),
                    "Check that Exit button is NOT displayed. Actual: " + yandexLoginPage.getHrefExit());
        }
    }
}
