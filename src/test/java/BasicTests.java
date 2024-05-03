import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotInteractableException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BasicTests {
    WebDriver driver;
    private final static String BASE_URL = "https://bonigarcia.dev/selenium-webdriver-java/";
    private final static String WEB_FORM = "https://bonigarcia.dev/selenium-webdriver-java/web-form.html";

    @BeforeEach
    void start() throws InterruptedException {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get(WEB_FORM);
        Thread.sleep(2000);
    }

    @AfterEach
    void end() {
        driver.quit();
    }

    @Test
    void firstTest()  {
        assertEquals(BASE_URL, driver.getCurrentUrl());
    }

    @Test
    void secondTest() throws InterruptedException {
        driver.get("https://bonigarcia.dev/selenium-webdriver-java/mouse-over.html");
        List<WebElement> elements = driver.findElements(By.xpath("//div[contains(@class, 'figure')]"));
        for (WebElement element : elements) {
            new Actions(driver)
                    .moveToElement(element)
                    .perform();
            Thread.sleep(1000);
        }
    }

    @Test
    void negativeTest() throws InterruptedException {
        driver.get("https://bonigarcia.dev/selenium-webdriver-java/mouse-over.html");
        List<WebElement> elements = driver.findElements(By.xpath("//div[contains(@class, 'figure')]"));
        for (WebElement element : elements) {
            new Actions(driver)
                    .moveToElement(element)
                    .perform();
            Thread.sleep(1000);
        }
        assertEquals(4, elements.size());
    }

    @Test
    void inputTest() {
        WebElement textInput = driver.findElement(By.xpath("//input[@id = 'my-text-id']"));
        textInput.sendKeys("Random first text");

        WebElement passwordInput = driver.findElement(By.xpath("//input[@name = 'my-password']"));
        passwordInput.sendKeys("Random second text");

        WebElement textArea = driver.findElement(By.xpath("//textarea[@name = 'my-textarea']"));
        textArea.sendKeys("Textarea text text text");

        String enteredText = textInput.getAttribute("value");
        assertEquals("Random first text", enteredText, "The text does not match the expected value");
        String enteredPassword = passwordInput.getAttribute("value");
        assertEquals("Random second text", enteredPassword, "The password does not match the expected value");
        String enteredTextarea = textArea.getAttribute("value");
        assertEquals("Textarea text text text", enteredTextarea, "The textarea text does not match the expected value");
    }

    @Test
    void disabledInput() {
        WebElement disabledForm = driver.findElement(By.cssSelector("input.form-control[disabled]"));
        try {
            disabledForm.sendKeys("Random text");
            Assertions.fail("Expected ElementNotIntractableException was not thrown");
        } catch (ElementNotInteractableException e) {
        }
    }

    @Test
    void readonlyField() {
        WebElement readonlyField = driver.findElement(By.xpath("//input[@name = 'my-readonly']"));
        assertNotNull(readonlyField.getAttribute("readonly"), "The field state isn't readonly");
    }

    @Test
    void dropDownTests() {
        List <WebElement> dropdownSelect = driver.findElements(By.xpath("//select[@name='my-select']/option"));
        WebElement dropdownOption = driver.findElement(By.xpath("//select[@name='my-select']/option[3]"));
        dropdownOption.click();
        assertTrue(dropdownOption.isSelected(), "The selected item is not selected from the list");

        //Доделать с Геннадием
        WebElement datalistDrop = driver.findElement(By.xpath("//input[@list = 'my-options']"));
        datalistDrop.sendKeys("New");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(2));
        List<WebElement> options = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.cssSelector("datalist#my-options option")));
        Select select = new Select(datalistDrop);

    }

    @Test
    void checkBoxesTests() {
        WebElement checkedCheckbox1 = driver.findElement(By.xpath("//input[@id = 'my-check-1']"));
        boolean isChecked = checkedCheckbox1.isSelected();
        assertTrue(isChecked, "Checkbox 1 should be chosen");

        WebElement checkedCheckbox2 = driver.findElement(By.xpath("//input[@id = 'my-check-2']"));
        checkedCheckbox2.click();
        boolean isChecked2 = checkedCheckbox1.isSelected();
        assertTrue(isChecked2, "Checkbox 2 should be chosen");
        assertTrue(checkedCheckbox1.isSelected() && checkedCheckbox2.isSelected(), "Both buttons are chosen");

        WebElement checkedRadioButton1 = driver.findElement(By.xpath("//input[@id = 'my-radio-1']"));
        WebElement checkedRadioButton2 = driver.findElement(By.xpath("//input[@id = 'my-radio-2']"));
        assertTrue(checkedRadioButton1.isSelected() ^ checkedRadioButton2.isSelected(), "Only one radio button should be chosen");
        checkedRadioButton2.click();
        assertTrue(checkedRadioButton1.isSelected() ^ checkedRadioButton2.isSelected(), "Only one radio button should be chosen");
    }

    @Test
    void calendarTest() {
        WebElement calendarField = driver.findElement(By.xpath("//input[@name = 'my-date']"));
        calendarField.click();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(2));
        WebElement calendar = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[contains(@class, 'datepicker-dropdown')]//td[text()='22']")));
        calendar.click();
        String dateText = calendarField.getAttribute("value");
        String[] dateComponents = dateText.split("/");
        assertEquals("22", dateComponents[1], "Date is incorrect");
    }

    @Test
    void returnToIndex() {
        WebElement returnButton = driver.findElement(By.xpath("//a[contains(text(), 'Return to index')]"));
        returnButton.click();
        String currentUrl = driver.getCurrentUrl();
        assertEquals("https://bonigarcia.dev/selenium-webdriver-java/index.html", currentUrl, "Current url does not match the expected one");
    }

    @Test
    void uploadFile() {
        WebElement uploadForm = driver.findElement(By.xpath("//input[@type='file']"));
        uploadForm.sendKeys("/Users/aleksandrgaisin/Downloads/images.png");
        WebElement submit = driver.findElement(By.xpath("//button[text()='Submit']"));
        submit.click();
    }
}
