import io.appium.java_client.android.AndroidDriver;
import lib.BaseTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Pause;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SearchFieldTest extends BaseTest {

    @Test
    public void testSearchFieldPlaceholderText() {

        waitForElementAndClick(By.xpath("//*[@text[contains(.,'Search Wikipedia')]]"), "Cannot find search container", 5);

        waitForElementPresent(By.xpath("//*[@text[contains(.,'Search Wikipedia')]]"), "Cannot find search input", 5);

        // Метод перенесла из lib.BaseTest в к другим тестам
        assertElementHasText("//*[@text[contains(.,'Search Wikipedia')]]", "Search Wikipedia", "The text is different");
    }

    @Test
    public void cancelSearchTest() {

        waitForElementAndClick(
                By.id("org.wikipedia:id/search_container"), "Cannot find Search container", 5);

        waitForElementAndSendKeys(
                By.xpath("//*[@text[contains(.,'Search Wikipedia')]]"), "Java", "Cannot find search input", 5);

        assertElementsCountMoreThanOneById("org.wikipedia:id/page_list_item_title", "Less than one article was found", 5);

        waitForElementAndClick(
                By.id("org.wikipedia:id/search_close_btn"), "Cannot find X to cancel search", 5);

        waitForElementNotPresent(By.id("org.wikipedia:id/page_list_item_title"), "the results did not disappear", 5);


    }

@Test
public void EachSearchResultContainsQueryTest () {

    waitForElementAndClick(
            By.id("org.wikipedia:id/search_container"), "Cannot find Search container", 5);

    waitForElementAndSendKeys(
            By.xpath("//*[@text[contains(.,'Search Wikipedia')]]"), "Java", "Cannot find search input", 5);

    assertEachElementContainsQuery(By.id("org.wikipedia:id/page_list_item_title"), "Java", "Not each search result contains query", 5);

}

    @Test
    public void testSaveArticleToNewList() {

        waitForElementAndClick(
                By.id("org.wikipedia:id/search_container"), "Cannot find Search container", 5);

        waitForElementAndSendKeys(
                By.xpath("//*[@text[contains(.,'Search Wikipedia')]]"), "Java", "Cannot find search input", 5);

        waitForElementPresent(By.id("org.wikipedia:id/page_list_item_title"), "The results are not found", 5);

        longTapOnArticle(By.id("org.wikipedia:id/page_list_item_title"),"Java (programming language)", "Expected article is not on the page", 5);

        waitForElementAndClick(
                By.xpath("//*[@resource-id='org.wikipedia:id/title' and @text='Save']"), "Cannot find Save button", 5);

        waitForElementAndClick(
                By.xpath("//*[@resource-id='org.wikipedia:id/snackbar_action' and @text='Add to list']"), "Cannot find option to add article to the list", 5);

        waitForElementAndSendKeys(By.id("org.wikipedia:id/text_input"), "TestFolder", "Cannot put text into articles folder input", 5);

        waitForElementAndClick(
                By.id("android:id/button1"), "Cannot press OK", 5);

        waitForElementAndClick(
                By.xpath("//*[@resource-id='org.wikipedia:id/snackbar_action' and @text='View list']"), "Cannot view the list", 5);

        waitForElementAndClick(
                By.xpath("//*[@resource-id='org.wikipedia:id/buttonView' and @text='Got it']"), "Cannot find Got it tip overlay", 5);

        waitForElementPresent
                (By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_title' and @text='Java (programming language)']"), "The article is not in the list", 5);

        waitForElementAndClick(
                By.xpath("//android.widget.ImageButton[@content-desc='Navigate up']"), "Cannot go back to the previous page", 5);

        waitForElementAndClear(
                By.id("org.wikipedia:id/search_src_text"), "Cannot find Search field", 5);

        waitForElementAndSendKeys(
                By.xpath("//*[@text[contains(.,'Search Wikipedia')]]"), "Appium", "Cannot find search input", 5);

        waitForElementPresent(By.id("org.wikipedia:id/page_list_item_title"), "The results are not found", 5);

        longTapOnArticle(By.id("org.wikipedia:id/page_list_item_title"), "Appium", "Expected article is not on the page", 5);

        waitForElementAndClick(
                By.xpath("//*[@resource-id='org.wikipedia:id/title' and @text='Save']"), "Cannot find Save button", 5);

        waitForElementAndClick(
                By.xpath("//*[@resource-id='org.wikipedia:id/snackbar_action' and @text='Add to list']"), "Cannot find option to add article to the list", 5);

        waitForElementAndClick(
                By.xpath("//*[@resource-id='org.wikipedia:id/item_title' and @text='TestFolder']"), "Cannot find the folder", 5);

        waitForElementAndClick(
                By.xpath("//*[@resource-id='org.wikipedia:id/snackbar_action' and @text='View list']"), "Cannot view the list", 5);

        waitForElementPresent
                (By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_title' and @text='Java (programming language)']"), "The article is not in the list", 5);

        waitForElementPresent
                (By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_title' and @text='Appium']"), "The article is not in the list", 5);

        swipeElementToLeft
                (By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_title' and @text='Java (programming language)']"), "The article is not in the list");

        waitForElementAndClick(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_title' and @text='Appium']"), "Cannot find the article", 5);

        waitForElementAndClick(
                By.id("org.wikipedia:id/closeButton"),"Cannot find the X button", 5);

        waitForElementAndClick(
                By.id("org.wikipedia:id/page_web_view"), "Cannot find Got it message", 5);

        WebElement title_element = waitForElementPresent(By.xpath("//android.widget.TextView"), "Cannot find article title", 10);

        String article_title = title_element.getAttribute ("text");

        Assertions.assertEquals("Appium", article_title, "Unexpected title");

    }

@Test
public void ArticleHasTitleTest() {
    waitForElementAndClick(
            By.id("org.wikipedia:id/search_container"), "Cannot find Search container", 5);

    waitForElementAndSendKeys(
            By.xpath("//*[@text[contains(.,'Search Wikipedia')]]"), "Java", "Cannot find search input", 5);

    waitForElementPresent(By.id("org.wikipedia:id/page_list_item_title"), "The results are not found", 5);

    waitForElementAndClick(By.xpath( "//*[@resource-id='org.wikipedia:id/page_list_item_title' and @text='Java (programming language)']"),"Expected article is not on the page", 5);

    // waitForElementAndClick(By.id("org.wikipedia:id/closeButton"),"Cannot find the X button", 5);

    // waitForElementAndClick(By.id("org.wikipedia:id/page_web_view"), "Cannot find Got it message", 5);

    assertElementHasText(By.xpath("//android.widget.TextView[@text='Java (programming language)']"), "Article title does not contain title");

}

@Test

public void ArticleTitleAfterRotateTest () {

    waitForElementAndClick(
            By.id("org.wikipedia:id/search_container"), "Cannot find Search container", 5);

    waitForElementAndSendKeys(
            By.xpath("//*[@text[contains(.,'Search Wikipedia')]]"), "Java", "Cannot find search input", 5);

    waitForElementPresent(By.id("org.wikipedia:id/page_list_item_title"), "The results are not found", 5);

    waitForElementAndClick(By.xpath( "//*[@resource-id='org.wikipedia:id/page_list_item_title' and @text='Java (programming language)']"),"Expected article is not on the page", 5);

    waitForElementAndClick(By.id("org.wikipedia:id/closeButton"),"Cannot find the X button", 5);

    waitForElementAndClick(By.id("org.wikipedia:id/page_web_view"), "Cannot find Got it message", 5);

    String titleBeforeRotation = getElementAttribute(By.xpath("//android.widget.TextView[@text='Java (programming language)']"), "text","Cannot find title of article", 5);

    // Поворачиваем экран в landscape
    ((AndroidDriver) driver).rotate(ScreenOrientation.LANDSCAPE);

    String titleAfterRotation = getElementAttribute(By.xpath("//android.widget.TextView[@text='Java (programming language)']"), "text","Cannot find title of article", 5);

    // Возвращаем обратно в portrait
    ((AndroidDriver) driver).rotate(ScreenOrientation.PORTRAIT);

    Assertions.assertEquals (titleBeforeRotation, titleAfterRotation, "Article title has been changed after rotation");

}

    // METODY

    private WebElement waitForElementPresent (By by, String error_message, long timeoutInSeconds) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds));
        wait.withMessage(error_message + "\n");
        return wait.until(ExpectedConditions.presenceOfElementLocated(by)
        );
    }


    private WebElement waitForElementPresent (By by, String error_message)
    {
        return waitForElementPresent(by, error_message, 5);
    }


    private WebElement waitForElementAndClick(By by, String errorMessage, long timeoutInSeconds)
    {
        WebElement element = waitForElementPresent(by, errorMessage, timeoutInSeconds);
        element.click();
        return element;
    }

    private WebElement waitForElementAndSendKeys (By by, String value, String errorMessage, long timeoutInSeconds)
    {
        WebElement element = waitForElementPresent(by, errorMessage, timeoutInSeconds);
        element.sendKeys(value);
        return element;
    }

    private boolean waitForElementNotPresent (By by, String errorMessage, long timeoutInSeconds) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds));
        wait.withMessage(errorMessage + "\n");
        return wait.until(ExpectedConditions.invisibilityOfElementLocated(by)
        );
    }

    private void assertElementsCountMoreThanOneById(String id, String errorMessage, long timeoutInSeconds) {
        By locator = By.id(id);
        List<WebElement> elements = new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds))
                .withMessage(errorMessage + "\n")
                .until(ExpectedConditions.presenceOfAllElementsLocatedBy(locator));

        Assertions.assertTrue(elements.size() > 1, errorMessage + " Expected more than one element, but found " + elements.size());
    }

    private void assertEachElementContainsQuery(By by, String word, String errorMessage, long timeoutInSeconds) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds));
        wait.withMessage(errorMessage + "\n");

        List<WebElement> searchResults = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(by));

        for (WebElement result : searchResults) {
            String title = result.getText();
            assertTrue(title.contains(word), errorMessage);
        }
    }

    private void longTapOnArticle(By by, String expectedArticle, String errorMessage, long timeoutInSeconds) {

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds));
        wait.withMessage(errorMessage + "\n");

        WebElement articleElement = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_title' and contains(@text, '" + expectedArticle + "')]")
        ));

        Point location = articleElement.getLocation();
        Dimension size = articleElement.getSize();

        int x = location.getX() + size.getWidth() / 2;
        int y = location.getY() + size.getHeight() / 2;

        PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
        Sequence longPress = new Sequence(finger, 1);

        //создаем движение пальца
        longPress.addAction(finger.createPointerMove(Duration.ofMillis(0),
                PointerInput.Origin.viewport(), x, y));
        //палец нажимает экран
        longPress.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.ordinal()));
        //пауза
        longPress.addAction(new Pause(finger, Duration.ofSeconds(2)));
        //палец отпускает экран
        longPress.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.ordinal()));

        driver.perform(Arrays.asList(longPress));
    }

    protected void swipeElementToLeft(By by, String error_message) {
        WebElement element = waitForElementPresent(
                by,
                error_message,
                10
        );

        int left_x = element.getLocation().getX();
        int right_x = left_x + element.getSize().getWidth();
        int upper_y = element.getLocation().getY();
        int lower_y = upper_y + element.getSize().getHeight();
        int middle_y = (upper_y + lower_y) / 2;

        // Создаём "палец"
        PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
        Sequence swipe = new Sequence(finger, 1);

        // Нажатие в начале (правый край элемента)
        swipe.addAction(finger.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(), right_x, middle_y));
        swipe.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));

        // Задержка (имитация удержания)
        swipe.addAction(new Pause(finger, Duration.ofMillis(200)));

        // Перемещение к левому краю
        swipe.addAction(finger.createPointerMove(Duration.ofMillis(500), PointerInput.Origin.viewport(), left_x, middle_y));

        // Отпускаем палец
        swipe.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));

        // Выполняем жест
        driver.perform(Arrays.asList(swipe));
    }

private WebElement waitForElementAndClear (By by, String errorMessage, long timeoutInSeconds) {
        WebElement element = waitForElementPresent (by, errorMessage, timeoutInSeconds);
        element.clear();
        return element;
}

    protected void assertElementHasText(String xpath, String expectedText, String errorMessage) {
        By by = By.xpath(xpath);
        WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(by));
        String actualText = element.getText();
        if (!actualText.equals(expectedText)) {
            throw new AssertionError(errorMessage + " Expected: '" + expectedText +
                    "', but was: '" + actualText + "'");
        }
    }

protected void assertElementHasText (By by, String errorMessage) {
        WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(by));
        String actualText = element.getText();

        Assertions.assertTrue(
                actualText != null && !actualText.trim().isEmpty(),
                errorMessage + " Element has no text."
        );
    }

    private String getElementAttribute (By by, String attribute, String errorMessage, long timeoutInSeconds) {
        WebElement element = waitForElementPresent(by, errorMessage, timeoutInSeconds);
        return element.getAttribute(attribute);

    }


}
    
    
