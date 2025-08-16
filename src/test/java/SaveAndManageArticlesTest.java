import io.appium.java_client.AppiumBy;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Pause;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SaveAndManageArticlesTest extends BaseTest {

    @Test
    public void testSaveArticleToNewList() {
        String searchQuery = "Java";
        String folderName = "TestFolder";

        // 1. Открыть поиск
        driver.findElement(By.id("org.wikipedia:id/search_container")).click();
        WebElement searchField = wait.until(ExpectedConditions.elementToBeClickable(
                By.id("org.wikipedia:id/search_src_text")));
        searchField.sendKeys(searchQuery);

        // 2. Найти первую статью
        WebElement firstResult = wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//android.widget.TextView[contains(@text,'" + searchQuery + "')]")));

        // 3. Получить заголовок
        String articleTitle = firstResult.getText();

        // 4. Долгий тап (long press) с помощью W3C Actions
        int x = firstResult.getLocation().getX() + 10;
        int y = firstResult.getLocation().getY() + 10;

        PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
        Sequence longPress = new Sequence(finger, 1);
        longPress.addAction(finger.createPointerMove(Duration.ofMillis(0), PointerInput.Origin.viewport(), x, y));
        longPress.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
        longPress.addAction(new Pause(finger, Duration.ofSeconds(1)));
        longPress.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));
        driver.perform(Arrays.asList(longPress));

        // 5. Нажать Save
        WebElement saveButton = wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//android.widget.TextView[@resource-id='org.wikipedia:id/title' and @text='Save']")));
        saveButton.click();

        // 6. Снэкбар "Saved ..."
        wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//*[contains(@text,'Saved " + articleTitle + "')]")));

        // 7. Нажать "Add to a list"
        driver.findElement(By.id("org.wikipedia:id/snackbar_action")).click();

        // 8. Ввести имя списка
        WebElement nameInput = wait.until(ExpectedConditions.presenceOfElementLocated(
                By.id("org.wikipedia:id/text_input")));
        nameInput.sendKeys(folderName);

        // 9. Нажать "ОК"
        driver.findElement(By.id("android:id/button1")).click();

        // 10. Снэкбар "Moved ..."
        wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//*[contains(@text,'Moved " + articleTitle + "')]")));

        // 11. Ожидаем снэкбар "Moved ... to ..." и нажимаем "View list"
        wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//*[contains(@text,'Moved " + articleTitle + "')]")));
        driver.findElement(By.id("org.wikipedia:id/snackbar_action")).click();

        // 11.1 Проверка: если на экране появилось окно с кнопкой "Got it", нажимаем на неё
        try {
            // Делаем небольшую паузу, чтобы окно успело появиться
            Thread.sleep(500);

            List<WebElement> gotItButtons = driver.findElements(
                    By.xpath("//android.widget.Button[@resource-id='org.wikipedia:id/buttonView']"));
            if (!gotItButtons.isEmpty()) {
                gotItButtons.get(0).click();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // 11.2 Ждём появления статьи в списке сохранённого
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("org.wikipedia:id/item_title")));

        // 12. Проверить название списка
        WebElement folderTitle = wait.until(ExpectedConditions.presenceOfElementLocated(
                By.id("org.wikipedia:id/item_title")));
        assertTrue(folderTitle.getText().equals(folderName), "Неверное название папки");

        // 13. Проверить наличие статьи
        WebElement articleInList = wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//*[@text='" + articleTitle + "']")));
        assertTrue(articleInList.isDisplayed(), "Статья не найдена в списке");

        // 14. Вернуться назад по стрелке «Назад» и оказаться на экране с поисковой строкой
        driver.findElement(By.xpath("//android.widget.ImageButton[@content-desc='Navigate up']")).click();

        // 15. Нажать на крестик в строке поиска, чтобы стереть слово, введенное в поиск
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("org.wikipedia:id/search_close_btn"))).click();

        // 17. Ввести новое слово в поиск
        String searchQuery2 = "Appium";
        driver.findElement(By.id("org.wikipedia:id/search_container")).click();
        searchField = wait.until(ExpectedConditions.elementToBeClickable(
                By.id("org.wikipedia:id/search_src_text")));
        searchField.sendKeys(searchQuery2);

        // 18. Найти вторую статью
        WebElement secondResult = wait.until(ExpectedConditions.presenceOfElementLocated(
        By.xpath("//android.widget.TextView[contains(@text,'" + searchQuery2 + "')]")));

        // 19. Получить заголовок второй статьи
        String secondArticleTitle = secondResult.getText();

        // 20. Долгий тап (long press) с помощью W3C Actions
        int x2 = secondResult.getLocation().getX() + 10;
        int y2 = secondResult.getLocation().getY() + 10;

        PointerInput finger2 = new PointerInput(PointerInput.Kind.TOUCH, "finger");
        Sequence longPress2 = new Sequence(finger2, 1);
        longPress2.addAction(finger2.createPointerMove(Duration.ofMillis(0), PointerInput.Origin.viewport(), x2, y2));
        longPress2.addAction(finger2.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
        longPress2.addAction(new Pause(finger2, Duration.ofSeconds(1)));
        longPress2.addAction(finger2.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));
        driver.perform(Arrays.asList(longPress2));

        // 21. Нажать Save
        WebElement saveButton2 = wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//android.widget.TextView[@resource-id='org.wikipedia:id/title' and @text='Save']")));
        saveButton2.click();

        // 22. Снэкбар "Saved ..."
        wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//*[contains(@text,'Saved " + secondArticleTitle + "')]")));

        // 23. Нажать "Add to a list"
        driver.findElement(By.id("org.wikipedia:id/snackbar_action")).click();

        // 24. Дождаться появления bottom sheet
        wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//android.widget.TextView[@resource-id='org.wikipedia:id/dialog_title' and @text='Move to reading list']")));

        // 25. Убедиться, что в нем есть созданный лист для чтения с названием «TestFolder»
        wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//android.widget.TextView[@resource-id='org.wikipedia:id/item_title' and @text='" + folderName + "']")));

        // 26. Тап на блок ViewGroup, внутри которого этот текст, чтобы сохранить статью в лист для чтения
        WebElement folderBlock = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//androidx.recyclerview.widget.RecyclerView[@resource-id='org.wikipedia:id/list_of_lists']/android.view.ViewGroup[.//android.widget.TextView[@text='" + folderName + "']]")));
        folderBlock.click();

        // 27. Дождаться появления снекбара
        wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//*[contains(@text,'Moved " + secondArticleTitle + " to TestFolder')]")));

        // 28. Нажать «View list»
        driver.findElement(By.id("org.wikipedia:id/snackbar_action")).click();

        // 29. Дождаться открытия экрана листа для чтения
        // 29.1 Проверка: если на экране появилось окно с кнопкой "Got it", нажимаем на неё
        try {
            // Делаем небольшую паузу, чтобы окно успело появиться
            Thread.sleep(500);

            List<WebElement> gotItButtons = driver.findElements(
                    By.xpath("//android.widget.Button[@resource-id='org.wikipedia:id/buttonView']"));
            if (!gotItButtons.isEmpty()) {
                gotItButtons.get(0).click();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // 30. Убедиться, что в списке есть обе статьи
        wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//android.widget.TextView[@text='" + articleTitle + "']")));
        wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//android.widget.TextView[@text='" + secondArticleTitle + "']")));

        // 31. Найти статью для удаления по заголовку и получить её координаты
        WebElement articleToDelete = driver.findElement(
                By.xpath("//android.widget.TextView[@text='" + articleTitle + "']"));

        int deleteX = articleToDelete.getLocation().getX();
        int deleteY = articleToDelete.getLocation().getY();
        int swipeStartX = deleteX + 300;
        int swipeEndX = deleteX + 10;

        // 32. Свайп влево для удаления статьи
        PointerInput swipeFinger = new PointerInput(PointerInput.Kind.TOUCH, "swipeFinger");
        Sequence swipe = new Sequence(swipeFinger, 1);
        swipe.addAction(swipeFinger.createPointerMove(Duration.ofMillis(0), PointerInput.Origin.viewport(), swipeStartX, deleteY));
        swipe.addAction(swipeFinger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
        swipe.addAction(swipeFinger.createPointerMove(Duration.ofMillis(500), PointerInput.Origin.viewport(), swipeEndX, deleteY));
        swipe.addAction(swipeFinger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));
        driver.perform(Collections.singletonList(swipe));

        // 33. Проверить, что статья исчезла
        wait.until(ExpectedConditions.invisibilityOfElementLocated(
                By.xpath("//android.widget.TextView[@text='" + articleTitle + "']")));

    }
}
