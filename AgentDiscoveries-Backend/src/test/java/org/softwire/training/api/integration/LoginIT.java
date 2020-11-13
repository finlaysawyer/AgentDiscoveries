package org.softwire.training.api.integration;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.softwire.training.api.integration.helper.LoginHelper;
import org.softwire.training.api.integration.helper.WebDriverHelper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class LoginIT {
    public static final String TARGET_ADDRESS = System.getProperty("target.address");

    private static WebDriver driver;

    @BeforeAll
    public static void setUp() {
        driver = WebDriverHelper.getSharedDriver();
    }

    @Test
    public void testTitle() {
        driver.get(TARGET_ADDRESS);
        String title = driver.getTitle();
        assertEquals("Agent Discoveries", title);
    }

    @Test
    public void testCanLogIn() {
        driver.get(TARGET_ADDRESS);
        LoginHelper.ensureLoggedOut(driver);
        LoginHelper.login(driver, LoginHelper.TEST_AGENT);

        WebElement navBarRight = driver.findElement(By.className("navbar-right"));
        assertTrue(navBarRight.getText().contains("Log Out"));
    }

    @Test
    public void testCanLogOut() {
        driver.get(TARGET_ADDRESS);
        LoginHelper.ensureLoggedIn(driver);
        LoginHelper.logOut(driver);

        WebElement navBarRight = driver.findElement(By.className("navbar-right"));
        assertFalse(navBarRight.getText().contains("Log Out"));
    }
}
