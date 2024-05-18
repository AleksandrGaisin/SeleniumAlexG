import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import pages.HeaderComponent;
import pages.MainPage;
import pages.WebFormPage;

import static org.assertj.core.api.Assertions.assertThat;

public class PageObjectTests {
    WebDriver driver;

    @BeforeEach
    void start() throws InterruptedException {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        Thread.sleep(2000);
    }

    @AfterEach
    void tearDown() {
        driver.quit();
    }

    @Test
    void simpleWebPageTest() {
        MainPage mainPage = new MainPage(driver);
        mainPage.open();
        WebFormPage webFormPage = mainPage.openWebForm();
        WebElement pageTitle = webFormPage.getPageTitle();
        assertThat(pageTitle.getText()).isEqualTo("Web form");
    }

    @Test
    void simpleWebPageTest2() {
        MainPage mainPage = new MainPage(driver);
        mainPage.open();
        assertThat(mainPage.checkButton()).isTrue();
    }

    @Test
    void simpleWebPageTest3() {
        MainPage mainPage = new MainPage(driver);
        mainPage.open();
        HeaderComponent header = mainPage.header();
        WebElement title = header.getTitle();
        assertThat(title.isDisplayed()).isTrue();
        assertThat(title.getText()).contains("Selenium WebDriver");
    }
}
