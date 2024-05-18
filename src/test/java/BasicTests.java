import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.assertj.core.api.SoftAssertions;
import java.time.Duration;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
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
        assertEquals(WEB_FORM, driver.getCurrentUrl());
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
    void mouseOverTest() throws InterruptedException {
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

        String firstText = "Random first text";
        String secondText = "Random second text";
        String textAreaText = "Textarea text text text";

        WebElement textInput = driver.findElement(By.xpath("//input[@id = 'my-text-id']"));
        textInput.sendKeys(firstText);

        WebElement passwordInput = driver.findElement(By.xpath("//input[@name = 'my-password']"));
        passwordInput.sendKeys(secondText);

        WebElement textArea = driver.findElement(By.xpath("//textarea[@name = 'my-textarea']"));
        textArea.sendKeys(textAreaText);

        String enteredText = textInput.getAttribute("value");
        String enteredPassword = passwordInput.getAttribute("value");
        String enteredTextarea = textArea.getAttribute("value");

        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(enteredText).isEqualTo(firstText);
            softly.assertThat(enteredPassword).isEqualTo(secondText);
            softly.assertThat(enteredTextarea).isEqualTo(textAreaText);
        });
    }

    @Test
    void disabledInput() {
        WebElement disabledForm = driver.findElement(By.cssSelector("input.form-control[disabled]"));
        assertEquals("",disabledForm.getText());
        Exception thrown = assertThrows(ElementNotInteractableException.class, () -> disabledForm.sendKeys("Test"));
        assertThat(thrown.getMessage()).contains("element not intractable");
    }

    @Test
    void readonlyField() {
        WebElement readonly = driver.findElement(By.name("my-readonly"));
        //readonly.click();
        boolean isReadOnly = readonly.getAttribute("readonly") != null;
        System.out.println(isReadOnly);
        System.out.println(readonly.getAttribute("readonly"));

        assertThat(Boolean.parseBoolean(readonly.getAttribute("readonly")))
                .as("Should be %s", isReadOnly)
                .isFalse();
        Assertions.assertEquals(readonly.getAttribute("readonly"), "true");
    }

    @Test
    void dropDownTests() {
        List <WebElement> dropdownSelect = driver.findElements(By.xpath("//select[@name='my-select']/option"));
        WebElement dropdownOption = driver.findElement(By.xpath("//select[@name='my-select']/option[3]"));
        dropdownOption.click();
        assertTrue(dropdownOption.isSelected(), "The selected item is not selected from the list");
    }

    @Test
    void selectFromDataListTest() throws InterruptedException {
        WebElement dataList = driver.findElement(By.name("my-datalist"));
        List<WebElement> dataListOptions = driver.findElements(By.xpath("//datalist/option"));
        for (WebElement option : dataListOptions) {
            dataList.clear();
            String optionValue = option.getAttribute("value");
            dataList.sendKeys(optionValue);
            System.out.println("Selected: " + optionValue);
            Thread.sleep(2000);
        }
        dataList.clear();
        dataList.sendKeys("CustomValue");
        Thread.sleep(3000);
    }

    @Test
    void checkBoxesTests() {
        WebElement checkedCheckbox1 = driver.findElement(By.xpath("//input[@id = 'my-check-1']"));
        assertTrue(checkedCheckbox1.isSelected(), "Checkbox 1 should be chosen selected");

        WebElement checkedCheckbox2 = driver.findElement(By.xpath("//input[@id = 'my-check-2']"));
        checkedCheckbox2.click();
        assertTrue(checkedCheckbox1.isSelected(), "Checkbox 1 should be chosen after selecting Checkbox 2");
        assertTrue(checkedCheckbox2.isSelected(), "Checkbox 2 should be chosen after clicking on it");

        driver.navigate().refresh();
        WebElement refreshedCheckbox1 = driver.findElement(By.xpath("//input[@id = 'my-check-1']"));
        WebElement refreshedCheckbox2 = driver.findElement(By.xpath("//input[@id = 'my-check-2']"));

        assertTrue(refreshedCheckbox1.isSelected(), "Checkbox 1 should be chosen after refreshing");
        assertFalse(refreshedCheckbox2.isSelected(), "Checkbox 2 should not be chosen refreshing");
    }
    @Test
    void radioButtonsTest() {
        WebElement checkedRadioButton1 = driver.findElement(By.xpath("//input[@id = 'my-radio-1']"));
        assertTrue(checkedRadioButton1.isSelected(), "Only the first radio button should be chosen");
        WebElement checkedRadioButton2 = driver.findElement(By.xpath("//input[@id = 'my-radio-2']"));
        assertFalse(checkedRadioButton2.isSelected(), "The second radio button should not be chosen");

        checkedRadioButton2.click();
        assertTrue(checkedRadioButton2.isSelected(), "Only first radio button should be chosen");
        assertFalse(checkedRadioButton1.isSelected(), "First radio button should not be chosen after click on the second radio button");

        driver.navigate().refresh();
        WebElement refreshedRadioButton1 = driver.findElement(By.xpath("//input[@id = 'my-radio-1']"));
        WebElement refreshedRadioButton2 = driver.findElement(By.xpath("//input[@id = 'my-radio-2']"));
        assertTrue(refreshedRadioButton1.isSelected(), "Radiobutton 1 should be chosen after refreshing");
        assertFalse(refreshedRadioButton2.isSelected(), "Radiobutton 2 should not be chosen refreshing");
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
        assertEquals(BASE_URL + "/index.html", currentUrl, "Current url does not match the expected one");
    }

    @Test
    void uploadFile() {
        WebElement uploadForm = driver.findElement(By.xpath("//input[@type='file']"));
        uploadForm.sendKeys("/Users/aleksandrgaisin/Downloads/images.png");
        WebElement submit = driver.findElement(By.xpath("//button[text()='Submit']"));
        submit.click();

       WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(2));
       wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h1[@class='display-6' and text()='Form submitted']")));
       wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//p[@class='lead' and text()='Received!']")));

    }

    @Test
    void dragAndDrop() {
        driver.get("https://bonigarcia.dev/selenium-webdriver-java/drag-and-drop.html");
        WebElement draggable = driver.findElement(By.xpath("//div[@id='draggable']"));
        WebElement target = driver.findElement(By.xpath("//div[@id='target']"));

        new Actions(driver)
                .dragAndDrop(draggable, target)
                .perform();
    }

    @Test
    void checkSlowCalc() {
        driver.get("https://bonigarcia.dev/selenium-webdriver-java/slow-calculator.html");

        By calcButton = By.xpath("//h1[@class = 'display-6' and text()='Slow calculator']");
        By oneButton = By.xpath("//div[@class='keys']/span[text()='1']");
        By threeButton = By.xpath("//div[@class='keys']/span[text()='3']");
        By plusButton = By.xpath("//div[@class='keys']/span[text()='+']");
        By equalButton = By.xpath("//div[@class='keys']/span[text()='=']");
        By resultField = By.xpath("//div[@class='screen']");

        driver.findElement(calcButton).click();
        driver.findElement(oneButton).click();
        driver.findElement(plusButton).click();
        driver.findElement(threeButton).click();
        driver.findElement(equalButton).click();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(7));
        wait.until(ExpectedConditions.textToBe(resultField, "4"));
    }

    @Test
    void dialogBoxesTest() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));


        driver.get("https://bonigarcia.dev/selenium-webdriver-java/dialog-boxes.html");
        driver.findElement(By.id("my-alert")).click();
        Alert launchAlert = driver.switchTo().alert();
        wait.until(ExpectedConditions.alertIsPresent());
        assertThat(launchAlert.getText()).isEqualTo("Hello world!");
        launchAlert.accept();

        driver.findElement(By.id("my-confirm")).click();
        driver.switchTo().alert().accept();
        assertThat(driver.findElement(By.id("confirm-text")).getText()).isEqualTo("You chose: true");
        driver.findElement(By.id("my-confirm")).click();
        driver.switchTo().alert().dismiss();

        assertThat(driver.findElement(By.id("confirm-text")).getText()).isEqualTo("You chose: false");
        driver.findElement(By.id("my-prompt")).click();
        driver.switchTo().alert().sendKeys("TestTest");
        driver.switchTo().alert().accept();
        assertThat(driver.findElement(By.id("prompt-text")).getText()).isEqualTo("You typed: TestTest");

        driver.findElement(By.id("my-modal")).click();
        WebElement saveChanges = driver.findElement(By.xpath("//button[normalize-space() = 'Save changes']"));
        wait.until(ExpectedConditions.elementToBeClickable(saveChanges));
        saveChanges.click();
        assertThat(driver.findElement(By.id("modal-text")).getText()).isEqualTo("You chose: Save changes");

        driver.findElement(By.id("my-modal")).click();
        WebElement close = driver.findElement(By.xpath("//button[normalize-space() = 'Close']"));
        wait.until(ExpectedConditions.elementToBeClickable(close));
        close.click();
        assertThat(driver.findElement(By.id("modal-text")).getText()).isEqualTo("You chose: Close");
    }
}
