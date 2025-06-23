package utils;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class BaseTest {
    protected WebDriver driver;
    protected WebDriverWait wait;

    @BeforeEach
    void setUp() {
        String browser = System.getProperty("browser", "chrome");
        ChromeOptions options = new ChromeOptions();

        if (browser.equalsIgnoreCase("yandex")) {
            WebDriverManager.chromedriver().driverVersion("134.0.0").setup(); // ставим нужную версию
            options.setBinary(Config.get("yandex.browser.path")); // путь к бинарнику Яндекс.Браузера
        } else {
            WebDriverManager.chromedriver().setup(); // обычный Chrome, последняя доступная версия
        }

        driver = new ChromeDriver(options);
        driver.manage().window().setSize(new Dimension(1280, 800));
        wait = new WebDriverWait(driver, Duration.ofSeconds(10)); // <-- вот здесь создаём объект ожидания
        driver.get(Config.get("base.url"));
    }

    @AfterEach
    void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}


