import io.appium.java_client.AppiumDriver;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class SearchFieldTest extends BaseTest {

    @Test
    public void testSearchFieldPlaceholderText() {
        // Локатор контейнера поля поиска (нажимаем на него, чтобы открылось поле ввода)
        By searchContainerLocator = By.id("org.wikipedia:id/search_container");
        WebElement searchField = wait.until(ExpectedConditions.elementToBeClickable(searchContainerLocator));
        searchField.click();

        // Локатор поля ввода, которое появляется после клика
        By searchInputLocator = By.id("org.wikipedia:id/search_src_text");

        // Ожидаемый текст в поле ввода
        String expectedText = "Search Wikipedia";
        String errorMessage = "Текст в поле поиска не соответствует ожидаемому.";

        // Используем метод из BaseTest для проверки текста
        assertElementHasText(searchInputLocator, expectedText, errorMessage);
    }
}
