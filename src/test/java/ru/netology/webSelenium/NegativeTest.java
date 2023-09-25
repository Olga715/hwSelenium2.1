package ru.netology.webSelenium;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class NegativeTest {
    private WebDriver driver;

    @BeforeAll
    public static void setupAll() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    public void beforeEach() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--no-sandbox");
        options.addArguments("--headless");
        driver = new ChromeDriver(options);
        driver.get("http://localhost:9999");
    }

    @AfterEach
    public void afterEach() {
        driver.quit();
        driver = null;
    }

    @Test
    public void shouldFailedIncorrectName() {
        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Sasha");
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+79507435671");
        driver.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        driver.findElement(By.cssSelector("button.button")).click();
        assertEquals("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.",
                driver.findElement(By.cssSelector("[data-test-id='name'].input_invalid .input__sub")).getText().trim());
    }

    @Test
    public void shouldFailedIncorrectPhone() {
        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Петров Петр");
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+7950");
        driver.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        driver.findElement(By.cssSelector("button.button")).click();
        assertEquals("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.",
                driver.findElement(By.cssSelector("[data-test-id='phone'].input_invalid .input__sub")).getText().trim());
    }

    @Test
    public void shouldFailedEmptyName() {
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+79507435671");
        driver.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        driver.findElement(By.cssSelector("button.button")).click();
        assertEquals("Поле обязательно для заполнения",
                driver.findElement(By.cssSelector("[data-test-id='name'].input_invalid .input__sub")).getText().trim());
    }

    @Test
    public void shouldFailedEmptyPhone() {
        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Петров Петр");
        driver.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        driver.findElement(By.cssSelector("button.button")).click();
        assertEquals("Поле обязательно для заполнения",
                driver.findElement(By.cssSelector("[data-test-id='phone'].input_invalid .input__sub")).getText().trim());
    }

    @Test
    public void shouldFailedCheckBox() {
        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Петров Петр");
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+79507435671");
        driver.findElement(By.cssSelector("button.button")).click();
        assertTrue(driver.findElement(By.cssSelector("[data-test-id='agreement'].input_invalid")).isDisplayed());
    }

}