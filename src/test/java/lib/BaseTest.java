package lib;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertFalse;

public class BaseTest {
    protected AppiumDriver driver;
    protected WebDriverWait wait;

    @BeforeEach
    public void setUp() throws MalformedURLException {
        UiAutomator2Options options = new UiAutomator2Options()
                .setPlatformName("Android")
                .setDeviceName("AndroidTestDevice")
                .setPlatformVersion("16.0")
                .setAppPackage("org.wikipedia")
                .setAppActivity("org.wikipedia.main.MainActivity")
                .setApp("/Users/zapevalovatatiana/Desktop/JavaAppiumAutomation3/JavaAppiumAutomation4/apks/org.wikipedia.apk");

        String appiumUrl = "http://127.0.0.1:4723";
        driver = new AndroidDriver(new URL(appiumUrl), options);
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        handleWelcomeScreens();
    }

    protected void handleWelcomeScreens() {
        try {
            WebElement skipButton = wait.until(ExpectedConditions.presenceOfElementLocated(
                    By.id("org.wikipedia:id/fragment_onboarding_skip_button")));
            skipButton.click();
        } catch (Exception e) {
            System.out.println("Экран приветствия не обнаружен");
        }

        try {
            WebElement popupCloseButton = wait.until(ExpectedConditions.presenceOfElementLocated(
                    By.id("org.wikipedia:id/view_announcement_action_negative")));
            popupCloseButton.click();
        } catch (Exception e) {
            System.out.println("Всплывающее окно не обнаружено");
        }
    }




    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

}