package org.softwire.training.api.integration;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.softwire.training.api.integration.helper.LoginHelper;
import org.softwire.training.api.integration.helper.WebDriverHelper;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class SearchReportsIT {
    public static final String TARGET_ADDRESS = System.getProperty("target.address");

    private static WebDriver driver;
    private static WebDriverWait wait;

    @BeforeAll
    public static void setUp() {
        driver = WebDriverHelper.getSharedDriver();
        wait = new WebDriverWait(driver, 4);
    }

    @Test
    public void testCanSearchLocationReportsByTitle() {
        driver.get(TARGET_ADDRESS);
        LoginHelper.ensureLoggedIn(driver);
        driver.get(TARGET_ADDRESS + "/#/submit/location");

        WebElement locationSelect = driver.findElement(By.id("location-select"));
        new Select(locationSelect).selectByIndex(1);
        WebElement statusInput = driver.findElement(By.id("status-input"));
        statusInput.sendKeys("1");
        WebElement reportTitleInput = driver.findElement(By.id("title-input"));
        reportTitleInput.sendKeys("Test");
        WebElement reportInput = driver.findElement(By.id("report-input"));
        reportInput.sendKeys("Report");
        WebElement submitButton = driver.findElement(By.id("submit-report"));
        submitButton.click();

        LoginHelper.logOut(driver);
        LoginHelper.ensureLoggedInAsAdmin(driver);

        driver.get(TARGET_ADDRESS + "/#/search/location");

        WebElement reportTitle = driver.findElement(By.id("title-search"));
        reportTitle.sendKeys("Test");
        WebElement searchButton = driver.findElement(By.id("search-report"));
        searchButton.click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("results-box")));
        WebElement alert = driver.findElement(By.id("results-length"));
        assertTrue(alert.getText().contains("result"));
    }

    @Test
    public void testCanSearchLocationReportsByPartialTitle() {
        driver.get(TARGET_ADDRESS);
        LoginHelper.ensureLoggedIn(driver);
        driver.get(TARGET_ADDRESS + "/#/submit/location");

        WebElement locationSelect = driver.findElement(By.id("location-select"));
        new Select(locationSelect).selectByIndex(1);
        WebElement statusInput = driver.findElement(By.id("status-input"));
        statusInput.sendKeys("1");
        WebElement reportTitleInput = driver.findElement(By.id("title-input"));
        reportTitleInput.sendKeys("Test Report");
        WebElement reportInput = driver.findElement(By.id("report-input"));
        reportInput.sendKeys("Report");
        WebElement submitButton = driver.findElement(By.id("submit-report"));
        submitButton.click();

        LoginHelper.logOut(driver);
        LoginHelper.ensureLoggedInAsAdmin(driver);

        driver.get(TARGET_ADDRESS + "/#/search/location");

        WebElement reportTitle = driver.findElement(By.id("title-search"));
        reportTitle.sendKeys("Test");
        WebElement searchButton = driver.findElement(By.id("search-report"));
        searchButton.click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("results-box")));
        WebElement alert = driver.findElement(By.id("results-length"));
        assertTrue(alert.getText().contains("result"));
    }

    @Test
    public void testModalOpensAndClosesForLocation() {
        driver.get(TARGET_ADDRESS);
        LoginHelper.ensureLoggedIn(driver);
        driver.get(TARGET_ADDRESS + "/#/submit/location");

        WebElement locationSelect = driver.findElement(By.id("location-select"));
        new Select(locationSelect).selectByIndex(1);
        WebElement statusInput = driver.findElement(By.id("status-input"));
        statusInput.sendKeys("1");
        WebElement reportTitleInput = driver.findElement(By.id("title-input"));
        reportTitleInput.sendKeys("Modal Test");
        WebElement reportInput = driver.findElement(By.id("report-input"));
        reportInput.sendKeys("Modal Report");
        WebElement submitButton = driver.findElement(By.id("submit-report"));
        submitButton.click();

        LoginHelper.logOut(driver);
        LoginHelper.ensureLoggedInAsAdmin(driver);

        driver.get(TARGET_ADDRESS + "/#/search/location");

        WebElement reportTitle = driver.findElement(By.id("title-search"));
        reportTitle.sendKeys("Modal Test");
        WebElement searchButton = driver.findElement(By.id("search-report"));
        searchButton.click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("body-id")));
        WebElement searchModalOpen = driver.findElement(By.id("open-modal"));
        searchModalOpen.click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("modal-header")));
        WebElement ModalHeader = driver.findElement(By.id("modal-header"));
        assertTrue(ModalHeader.getText().contains("Modal Test"));

        WebElement searchModalClose = driver.findElement(By.id("close-modal"));
        searchModalClose.click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("results-box")));
        WebElement alert = driver.findElement(By.id("results-length"));
        assertTrue(alert.getText().contains("result"));
    }

    @Test
    public void testPaginationSplitsResults() {
        driver.get(TARGET_ADDRESS);
        LoginHelper.ensureLoggedIn(driver);
        driver.get(TARGET_ADDRESS + "/#/submit/location");

        for (int i=0; i<11; i++) {
            WebElement locationSelect = driver.findElement(By.id("location-select"));
            new Select(locationSelect).selectByIndex(1);
            WebElement statusInput = driver.findElement(By.id("status-input"));
            statusInput.sendKeys("1");
            WebElement reportTitleInput = driver.findElement(By.id("title-input"));
            reportTitleInput.sendKeys("Test");
            WebElement reportInput = driver.findElement(By.id("report-input"));
            reportInput.sendKeys("Report");
            WebElement submitButton = driver.findElement(By.id("submit-report"));
            submitButton.click();

            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("go-back-button")));
            WebElement goBackButton = driver.findElement(By.id("go-back-button"));
            goBackButton.click();

            wait.until(ExpectedConditions.urlToBe(TARGET_ADDRESS + "/#/Landing"));
            driver.get(TARGET_ADDRESS + "/#/submit/location");
        }

        LoginHelper.logOut(driver);
        wait.until(ExpectedConditions.urlToBe(TARGET_ADDRESS + "/#/login"));
        LoginHelper.ensureLoggedInAsAdmin(driver);

        wait.until(ExpectedConditions.urlToBe(TARGET_ADDRESS + "/#/Landing"));
        driver.get(TARGET_ADDRESS + "/#/search/location");

        WebElement resultsHeader = driver.findElement(By.id("results-length"));
        assertTrue(resultsHeader.getText().contains("results"));

        WebElement secondPage = driver.findElement(By.id("page2"));
        secondPage.click();

        WebElement page2ResultsHeader = driver.findElement(By.id("results-length"));
        assertTrue(page2ResultsHeader.getText().contains("results"));
    }
}