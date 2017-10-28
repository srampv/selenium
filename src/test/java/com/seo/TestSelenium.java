/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author sramp
 */
package com.seo;

import static junit.framework.Assert.assertEquals;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

public class TestSelenium {

    private String baseUrl;
    private WebDriver driver;
    private ScreenshotHelper screenshotHelper;

    @Before
    public void openBrowser() {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
        System.setProperty("webdriver.chrome.driver", "/Users/shashank/Downloads/chromedriver");
        baseUrl = System.getProperty("webdriver.base.url");
        baseUrl = "http://demo.guru99.com/selenium/newtours/";
        driver = new ChromeDriver();
        driver.get(baseUrl);
        screenshotHelper = new ScreenshotHelper();
    }

    @After
    public void saveScreenshotAndCloseBrowser() throws IOException {
        screenshotHelper.saveScreenshot("screenshot.png");
        driver.quit();
    }
    @Test
    public void pageTitleAfterSearchShouldBeginWithDrupal() throws IOException, InterruptedException {
        assertEquals("Welcome: Mercury Tours", "Welcome: Mercury Tours", driver.getTitle());
        WebElement searchField = driver.findElement(By.name("userName"));
        searchField.sendKeys("Drupal!");
        WebElement searchField1 = driver.findElement(By.name("password"));
        searchField1.sendKeys("Drupal!");
        WebElement searchField3 = driver.findElement(By.name("submit"));
        searchField3.click();
        (new WebDriverWait(driver, 10)).until(new ExpectedCondition() {
            public Boolean apply(WebDriver d) {
                return d.getTitle().toLowerCase().startsWith("drupal!");
            }

            @Override
            public Object apply(Object f) {
                return f;
            }
        });

        Thread.sleep(10 * 1000l);
    }

    private class ScreenshotHelper {

        public void saveScreenshot(String screenshotFileName) throws IOException {
            File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            FileUtils.copyFile(screenshot, new File(screenshotFileName));
        }
    }
}
