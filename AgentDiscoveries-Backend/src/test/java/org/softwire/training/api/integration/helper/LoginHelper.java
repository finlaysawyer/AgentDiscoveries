package org.softwire.training.api.integration.helper;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Helper for logging in to the application
 */
public class LoginHelper {
    public static final String TEST_AGENT = "test_agent";
    public static final String TEST_ADMIN = "test_admin";
    private static final String TEST_PASSWORD = "password";


    public static void ensureLoggedIn(WebDriver driver) {
        try {
            driver.findElement(By.id("logout-link"));
        } catch (NoSuchElementException e) {
            login(driver, TEST_AGENT);
        }
    }

    public static void ensureLoggedInAsAdmin(WebDriver driver) {
        try {
            driver.findElement(By.id("logout-link"));
        } catch (NoSuchElementException e) {
            login(driver, TEST_ADMIN);
        }
    }

    public static void login(WebDriver driver, String userType) {
        WebElement userNameInput = driver.findElement(By.id("user-name-input"));
        userNameInput.sendKeys(userType);

        WebElement passwordInput = driver.findElement(By.id("password-input"));
        passwordInput.sendKeys(TEST_PASSWORD);

        WebElement submitButton = driver.findElement(By.id("login-submit"));
        submitButton.submit();

        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.stalenessOf(submitButton));
    }

    public static void ensureLoggedOut(WebDriver driver) {
        try {
            logOut(driver);
        } catch (NoSuchElementException e) {
            // Fine
        }
    }

    public static void logOut(WebDriver driver) {
        WebElement logoutLink = driver.findElement(By.id("logout-link"));
        logoutLink.click();

        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.stalenessOf(logoutLink));
    }
}
