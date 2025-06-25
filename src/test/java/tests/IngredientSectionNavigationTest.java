package tests;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pages.MainPage;
import utils.BaseTest;

import static org.junit.jupiter.api.Assertions.assertTrue;

@Epic("Конструктор")
@Feature("Навигация по разделам ингредиентов")
public class IngredientSectionNavigationTest extends BaseTest {

    // Для этого теста не нужна авторизация, поэтому нет @BeforeEach с созданием юзера

    @BeforeEach
    void openMainPage() {
        driver.get(utils.Config.get("base.url"));
    }

    @Test
    @DisplayName("Переход к разделу 'Соусы'")
    void navigationToSaucesSectionIsSuccessful() {
        MainPage mainPage = new MainPage(driver, wait);
        mainPage.selectSaucesTab();
        assertTrue(mainPage.isTabActive("Соусы"), "Вкладка 'Соусы' не стала активной.");
    }

    @Test
    @DisplayName("Переход к разделу 'Начинки'")
    void navigationToFillingsSectionIsSuccessful() {
        MainPage mainPage = new MainPage(driver, wait);
        mainPage.selectFillingsTab();
        assertTrue(mainPage.isTabActive("Начинки"), "Вкладка 'Начинки' не стала активной.");
    }

    @Test
    @DisplayName("Переход к разделу 'Булки' после выбора другого раздела")
    void navigationToBunsSectionIsSuccessful() {
        MainPage mainPage = new MainPage(driver, wait);
        // Сначала переходим на другую вкладку
        mainPage.selectSaucesTab();
        // Затем возвращаемся на булки
        mainPage.selectBunsTab();
        assertTrue(mainPage.isTabActive("Булки"), "Вкладка 'Булки' не стала активной.");
    }
}
