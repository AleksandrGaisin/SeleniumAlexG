package pages;

import com.google.errorprone.annotations.concurrent.LazyInit;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class MainPage {
    WebDriver driver;
    HeaderComponent header;
    public final static String BASE_URL = "https://bonigarcia.dev/selenium-webdriver-java/";

    //locators
    @FindBy(xpath = "//a[text()='Web form']")
    @CacheLookup
    WebElement webFormButton;

    //constructor
    public MainPage(WebDriver driver) {
        this.driver = driver;
        this.header = new HeaderComponent(driver);
        PageFactory.initElements(driver, this);
    }

    //actions
    public void open() {
        driver.get(BASE_URL);
    }

    public WebFormPage openWebForm() {
        driver.findElement(By.xpath("//a[text()='Web form']")).click();
        return new WebFormPage(driver);
    }

    public boolean checkButton() {
        return webFormButton.isDisplayed();
    }

    public HeaderComponent header() {
        return new HeaderComponent(driver);
    }

    //actions returns WebForm


}
