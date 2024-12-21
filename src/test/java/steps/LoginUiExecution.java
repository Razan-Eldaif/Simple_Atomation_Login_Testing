package steps;

import io.cucumber.java.en.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.time.Duration;

public class LoginUiExecution {

    private WebDriver driver;
    private static final String LOGIN_URL = "https://next.aqar.fm/login"; // Test page URL

    // Page element locators
    private static final By PASSWORD_FIELD_LOCATOR = By.xpath("//*[@id='__next']/main/div/div[2]/div/div/div/div/div[5]/div[1]/input");
    private static final By PHONE_FIELD_LOCATOR = By.xpath("//*[@id='__next']/main/div/div[2]/div/div/div/div/div[3]/div[1]/input");
    private static final By LOGIN_BUTTON_LOCATOR = By.xpath("//*[@id='__next']/main/div/div[2]/div/div/div/div/button");
    private static final By ERROR_MESSAGE_PASSWORD_LOCATOR = By.xpath("//*[contains(text(),'كلمة المرور غير صحيحة')]");
    private static final By ERROR_MESSAGE_PHONE_LOCATOR = By.xpath("//*[contains(text(),'مطلوب رقم جوال سعودي')]");
    private static final By ERROR_MESSAGE_EMPTY_PASSWORD_LOCATOR = By.xpath("//*[contains(text(),'مطلوب كلمة المرور')]");
    private static final By PASSWORD_HIDE_ICON_LOCATOR = By.xpath("//*[@id='__next']/main/div/div[2]/div/div/div/div/div[5]/div[1]/button");
    private static final By PROFILE_ICON_LOCATOR = By.xpath("//*[@id='__next']/main/div/div[1]/div[2]/button/span/img");

    public void setUp() {
        System.setProperty("webdriver.chrome.driver", "C:/Users/PC/Downloads/chromedriver-win32/chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize(); // Maximize the browser window
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10)); // Implicit wait
    }

    public void tearDown() {
        if (driver != null) {
            driver.quit(); // Close the browser after tests
        }
    }

    private void openLoginPage() {
        driver.get(LOGIN_URL);
    }

    @Given("User is on the login page")
    public void userOnTheLoginPage() {
        setUp();
        openLoginPage();
    }

    @Then("The login form with all its fields and button should appear")
    public void loginFormAppearance() {
        Assert.assertTrue(driver.findElement(PASSWORD_FIELD_LOCATOR).isDisplayed(), "Password field is not displayed");
        Assert.assertTrue(driver.findElement(PHONE_FIELD_LOCATOR).isDisplayed(), "Phone field is not displayed");
        Assert.assertTrue(driver.findElement(LOGIN_BUTTON_LOCATOR).isDisplayed(), "Login button is not displayed");
        tearDown();
    }

    @When("User enters valid Phone and incorrect password")
    public void userEntersValidPhoneAndIncorrectPassword() {
        driver.findElement(PHONE_FIELD_LOCATOR).sendKeys("521000400");
        driver.findElement(PASSWORD_FIELD_LOCATOR).sendKeys("incorrectPassword");
        driver.findElement(LOGIN_BUTTON_LOCATOR).click();
    }

    @Then("An error message should appear on the screen")
    public void anErrorMessageShouldAppearOnTheScreen() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(40));
        WebElement errorMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(ERROR_MESSAGE_PASSWORD_LOCATOR));
        Assert.assertTrue(errorMessage.isDisplayed(), "Error message for incorrect password is not displayed");
        tearDown();
    }

    @When("User enters valid password and incorrect phone number")
    public void userEntersValidPasswordAndIncorrectPhoneNumber() {
        driver.findElement(PHONE_FIELD_LOCATOR).sendKeys("123456789");
        driver.findElement(PASSWORD_FIELD_LOCATOR).sendKeys("Pass@test123");
        driver.findElement(LOGIN_BUTTON_LOCATOR).click();
    }

    @Then("Invalid phone number error message should appear")
    public void invalidPhoneNumberErrorMessage() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(40));
        WebElement phoneErrorMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(ERROR_MESSAGE_PHONE_LOCATOR));
        Assert.assertTrue(phoneErrorMessage.isDisplayed(), "Error message for invalid phone number is not displayed");
        tearDown();
    }
    @When("User clicks login button with empty fields")
    public void blankFields() {
        driver.findElement(PHONE_FIELD_LOCATOR).clear();
        driver.findElement(PASSWORD_FIELD_LOCATOR).clear();
        driver.findElement(LOGIN_BUTTON_LOCATOR).click();
    }

    @Then("Two error messages should appear on the screen")
    public void twoErrorMessagesShouldAppear() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(40));
        WebElement phoneErrorMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(ERROR_MESSAGE_PHONE_LOCATOR));
        WebElement pwErrorMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(ERROR_MESSAGE_EMPTY_PASSWORD_LOCATOR));
        Assert.assertTrue(phoneErrorMessage.isDisplayed(), "Phone error message is not displayed");
        Assert.assertTrue(pwErrorMessage.isDisplayed(), "Password error message is not displayed");
        tearDown();
    }

    @When("User enters valid phone and leaves password field empty")
    public void userEntersValidPhoneAndLeavesPasswordFieldEmpty() {
        driver.findElement(PHONE_FIELD_LOCATOR).sendKeys("521000400");
        driver.findElement(PASSWORD_FIELD_LOCATOR).clear();
        driver.findElement(LOGIN_BUTTON_LOCATOR).click();
    }

    @Then("An error message for empty password should appear on the screen")
    public void emptyPasswordErrorMessage() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(40));
        WebElement pwErrorMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(ERROR_MESSAGE_EMPTY_PASSWORD_LOCATOR));
        Assert.assertTrue(pwErrorMessage.isDisplayed(), "Error message for empty password is not displayed");
        tearDown();
    }

    @When("User enters valid password and leaves phone field empty")
    public void userEntersValidPasswordAndLeavesPhoneFieldEmpty() {
        driver.findElement(PASSWORD_FIELD_LOCATOR).sendKeys("Pass@test123");
        driver.findElement(PHONE_FIELD_LOCATOR).clear();
        driver.findElement(LOGIN_BUTTON_LOCATOR).click();
    }

    @Then("An error message for empty phone should appear on the screen")
    public void emptyPhoneErrorMessage() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(40));
        WebElement phoneErrorMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(ERROR_MESSAGE_PHONE_LOCATOR));
        Assert.assertTrue(phoneErrorMessage.isDisplayed(), "Error message for empty phone is not displayed");
        tearDown();
    }

    @When("User enters characters in phone field")
    public void enterCharactersInsteadOfNumberInPhoneField() {
        driver.findElement(PHONE_FIELD_LOCATOR).sendKeys("fghjks1234");
    }

    @Then("Any type of characters should be blocked")
    public void validatePhoneFieldAcceptance() {
        String enteredText = driver.findElement(PHONE_FIELD_LOCATOR).getAttribute("value");
        Assert.assertFalse(enteredText.matches("^[0-9]*$"), "Phone field should not accept non-numeric characters");
        tearDown();
    }

    @When("User enters characters in password field and the content is hidden")
    public void inputHiddenPasswordCharacters() {
        driver.findElement(PASSWORD_FIELD_LOCATOR).sendKeys("hiddenPassword");
    }

    @And("click on unhide password icon")
    public void clickUnhidePasswordIcon() {
        WebElement passwordHideIcon = driver.findElement(PASSWORD_HIDE_ICON_LOCATOR);
        Actions actions = new Actions(driver);
        actions.moveToElement(passwordHideIcon).click().perform();
    }

    @Then("The text in the password field is unhidden")
    public void hideAndUnhidePassword() {
        String initialPasswordType = driver.findElement(PASSWORD_FIELD_LOCATOR).getAttribute("type");
        Assert.assertEquals(initialPasswordType, "password", "Password field is not hidden initially");

        clickUnhidePasswordIcon();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(40));
        wait.until(ExpectedConditions.attributeToBe(driver.findElement(PASSWORD_FIELD_LOCATOR), "type", "text"));

        String passwordType = driver.findElement(PASSWORD_FIELD_LOCATOR).getAttribute("type");
        Assert.assertEquals(passwordType, "text", "Password field is not visible after clicking unhide");

        // Hide the password again
        WebElement passwordHideIcon = driver.findElement(PASSWORD_HIDE_ICON_LOCATOR);
        passwordHideIcon.click();
        wait.until(ExpectedConditions.attributeToBe(driver.findElement(PASSWORD_FIELD_LOCATOR), "type", "password"));

        passwordType = driver.findElement(PASSWORD_FIELD_LOCATOR).getAttribute("type");
        Assert.assertEquals(passwordType, "password", "Password field is not hidden again");

        tearDown();
    }

    @When("User enters correct Phone and correct password")
    public void correctPhoneCorrectPassword() {
        driver.findElement(PHONE_FIELD_LOCATOR).sendKeys("521000400");
        driver.findElement(PASSWORD_FIELD_LOCATOR).sendKeys("Pass@test123");
        driver.findElement(LOGIN_BUTTON_LOCATOR).click();
    }

    @Then("User should be redirected to the dashboard")
    public void userRedirectedToDashboard() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(40));
        WebElement profileIcon = wait.until(ExpectedConditions.visibilityOfElementLocated(PROFILE_ICON_LOCATOR));
        Assert.assertNotNull(profileIcon, "User was not redirected to the dashboard");
        tearDown();
    }
}