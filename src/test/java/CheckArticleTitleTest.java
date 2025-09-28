import lib.BaseTest;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class CheckArticleTitleTest extends BaseTest {

    @Test
    public void assertArticlePresent() {
        String searchQuery = "Java";
        String desiredArticleTitle = "Java (programming language)";

        // 1. Открыть поиск
        driver.findElement(By.id("org.wikipedia:id/search_container")).click();
        WebElement searchField = wait.until(ExpectedConditions.elementToBeClickable(
                By.id("org.wikipedia:id/search_src_text")));
        searchField.sendKeys(searchQuery);

        // 2. Ждём появления результатов поиска
        List<WebElement> results = new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.presenceOfAllElementsLocatedBy(
                        By.id("org.wikipedia:id/page_list_item_title")));

        // 3. Ищем нужную статью
        WebElement targetArticle = null;
        for (WebElement result : results) {
            String titleText = result.getText();
            if (titleText.equalsIgnoreCase(desiredArticleTitle)) {
                targetArticle = result;
                break;
            }
        }

        if (targetArticle == null) {
            throw new AssertionError("Статья с заголовком '" + desiredArticleTitle + "' не найдена в результатах поиска");
        }

        // 4. Клик по найденной статье
        System.out.println("Кликаем по статье: " + desiredArticleTitle);
        targetArticle.click();

        // 5. Делаем небольшую паузу для загрузки
        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        // 6. Если появилось окно Wikipedia Games
        try {
            WebElement gamesTitle = new WebDriverWait(driver, Duration.ofSeconds(5))
                    .until(ExpectedConditions.presenceOfElementLocated(
                            By.xpath("//android.widget.TextView[@text='Wikipedia games']")));
            System.out.println("Обнаружено окно Wikipedia Games");

            List<WebElement> closeButtons = driver.findElements(
                    By.xpath("//android.widget.ImageView[@resource-id='org.wikipedia:id/closeButton' and @content-desc='Close']"));

            if (!closeButtons.isEmpty()) {
                System.out.println("Нажимаем на крестик Wikipedia Games");
                closeButtons.get(0).click();
            } else {
                System.out.println("Кнопка закрытия Wikipedia Games не найдена");
            }
        } catch (Exception e) {
            System.out.println("ℹ Окно Wikipedia Games не обнаружено");
        }

// 7. Проверяем заголовок статьи на экране (без ожидания)
        try {
            By articleTitleLocator = By.xpath("//*[contains(@text, '" + desiredArticleTitle + "')]");
            WebElement titleOnPage = driver.findElement(articleTitleLocator);

            System.out.println("Заголовок статьи найден на экране: " + titleOnPage.getText());
        } catch (NoSuchElementException e) {
            throw new AssertionError("Заголовок статьи не найден на экране: " + desiredArticleTitle);
        }
    }
}