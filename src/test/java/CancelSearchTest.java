import lib.BaseTest;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CancelSearchTest extends BaseTest {

    @Test
    public void cancelSearchTest() {
        // 1. Нажимаем на поле поиска
        By searchContainerLocator = By.id("org.wikipedia:id/search_container");
        WebElement searchContainer = wait.until(ExpectedConditions.elementToBeClickable(searchContainerLocator));
        searchContainer.click();

        // 2. Локатор поля ввода, которое появляется после клика
        By searchInputLocator = By.id("org.wikipedia:id/search_src_text");

        //3. Ждем, пока элемент станет кликабельным
        WebElement searchInput = wait.until(ExpectedConditions.presenceOfElementLocated(searchInputLocator));
        searchInput.sendKeys("Appium");

        // 4. Проверяем, что найдено несколько статей
        By searchResultLocator = By.id("org.wikipedia:id/page_list_item_title");
        List<WebElement> searchResults = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(searchResultLocator));
        assertTrue(searchResults.size() > 1, "Ожидалось, что найдется больше одной статьи");

        // 5. Нажимаем кнопку отмены поиска
        By closeButtonLocator = By.id("org.wikipedia:id/search_close_btn");
        WebElement closeButton = wait.until(ExpectedConditions.elementToBeClickable(closeButtonLocator));
        closeButton.click();

        // 6. Проверяем, что результаты поиска исчезли (поиск отменён)
        List<WebElement> resultsAfterCancel = driver.findElements(searchResultLocator);
        assertEquals(0, resultsAfterCancel.size(), "Результаты поиска должны исчезнуть после отмены");
    }
}
