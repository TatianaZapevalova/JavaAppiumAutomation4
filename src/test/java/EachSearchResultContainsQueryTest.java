import org.openqa.selenium.WebElement;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class EachSearchResultContainsQueryTest extends BaseTest {

    @Test
    public void eachSearchResultContainsQueryTest() {
        // 1. Нажать на строку поиска
        By searchContainerLocator = By.id("org.wikipedia:id/search_container");
        WebElement searchField = wait.until(ExpectedConditions.elementToBeClickable(searchContainerLocator));
        searchField.click();

        // 2. Найти поле ввода и ввести слово "Java"
        By searchSrcTextLocator = By.id("org.wikipedia:id/search_src_text");
        WebElement searchInput = wait.until(ExpectedConditions.presenceOfElementLocated(searchSrcTextLocator));
        searchInput.sendKeys("Java");

        // 3. Найти все результаты поиска
        By searchResultLocator = By.id("org.wikipedia:id/page_list_item_title");
        List<WebElement> searchResults = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(searchResultLocator));

        // 4. Убедиться, что результаты не пустые
        assertTrue(searchResults.size() > 0, "Результаты поиска не найдены");

        // 5. Проверить, что каждый результат содержит слово "Java" (без учёта регистра)
        for (WebElement result : searchResults) {
            String title = result.getText();
            assertTrue(title.toLowerCase().contains("java"), "Результат не содержит слово 'Java': " + title);
        }
    }
}

