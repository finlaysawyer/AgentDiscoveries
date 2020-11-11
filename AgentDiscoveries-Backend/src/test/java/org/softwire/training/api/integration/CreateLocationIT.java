package org.softwire.training.api.integration;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.softwire.training.api.integration.helper.LoginHelper;
import org.softwire.training.api.integration.helper.WebDriverHelper;

import static org.junit.jupiter.api.Assertions.*;

public class CreateLocationIT {
    public static final String TARGET_ADDRESS = System.getProperty("target.address");

    private static WebDriver driver;
    private static WebDriverWait wait;

    @BeforeAll
    public static void setUp() {
        driver = WebDriverHelper.getSharedDriver();
        wait = new WebDriverWait(driver, 10);
    }

    @Test
    public void testCanSubmitLocationAndAppearsOnLanding() {
        driver.get(TARGET_ADDRESS);
        LoginHelper.ensureLoggedInAsAdmin(driver);
        driver.get(TARGET_ADDRESS + "/#/admin/locations/add");

        WebElement siteNameInput = driver.findElement(By.id("site-name"));
        siteNameInput.sendKeys("MI5");
        WebElement locationNameInput = driver.findElement(By.id("location-name"));
        locationNameInput.sendKeys("London, UK");
        WebElement timeZoneInput = driver.findElement(By.id("time-zone"));
        timeZoneInput.sendKeys("Europe/London");
        WebElement longitudeInput = driver.findElement(By.id("longitude"));
        longitudeInput.sendKeys("106.69142");
        WebElement latitudeInput = driver.findElement(By.id("latitude"));
        latitudeInput.sendKeys("19.48631");
        WebElement submitButton = driver.findElement(By.id("submit-location"));
        submitButton.click();

        wait.until(ExpectedConditions.urlToBe(TARGET_ADDRESS + "/#/admin/locations"));
        assertEquals(TARGET_ADDRESS + "/#/admin/locations", driver.getCurrentUrl());

        driver.get(TARGET_ADDRESS + "/#/Landing");

        WebElement locationName = driver.findElement(By.xpath("//*[text()='MI5']"));
        assertTrue(locationName.isDisplayed());
    }
}
