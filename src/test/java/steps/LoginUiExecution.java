package steps;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import java.time.Duration;
import locators.LoginLocators; // Import page elements locators

public class LoginUiExecution {

    private WebDriver driver;
    private static final String LOGIN_URL = "https://next.aqar.fm/login"; // Test page URL

    @Before("@Login") // This will run once before all scenarios
    public void setUp() {
        System.setProperty("webdriver.chrome.driver", "C:/Users/PC/Downloads/chromedriver-win32/chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize(); // Maximize the browser window
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10)); // Implicit wait
        driver.get(LOGIN_URL); // Open the login page once
    }

    @After("@Login")
    public void tearDown() {
        try {
            Thread.sleep(20000); // Wait for 20 seconds before closing the browser
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if (driver != null) {
            driver.quit(); // Close the browser after tests
        }
    }

    @Given("User is on the login page")
    public void userOnTheLoginPage() {
        // No need to implement
    }

    @Then("The login form with all its fields and button should appear")
    public void loginFormAppearance() {
        Assert.assertTrue(driver.findElement(LoginLocators.PASSWORD_FIELD_LOCATOR).isDisplayed(), "Password field is not displayed");
        Assert.assertTrue(driver.findElement(LoginLocators.PHONE_FIELD_LOCATOR).isDisplayed(), "Phone field is not displayed");
        Assert.assertTrue(driver.findElement(LoginLocators.LOGIN_BUTTON_LOCATOR).isDisplayed(), "Login button is not displayed");
    }

    @When("User enters valid Phone and incorrect password")
    public void userEntersValidPhoneAndIncorrectPassword() {
        driver.findElement(LoginLocators.PHONE_FIELD_LOCATOR).sendKeys("521000400");
        driver.findElement(LoginLocators.PASSWORD_FIELD_LOCATOR).sendKeys("incorrectPassword");
        driver.findElement(LoginLocators.LOGIN_BUTTON_LOCATOR).click();
    }

    @Then("An error message should appear on the screen")
    public void anErrorMessageShouldAppearOnTheScreen() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(40));
        WebElement errorMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(LoginLocators.ERROR_MESSAGE_PASSWORD_LOCATOR));
        Assert.assertTrue(errorMessage.isDisplayed(), "Error message for incorrect password is not displayed");
    }

    @When("User enters valid password and incorrect phone number")
    public void userEntersValidPasswordAndIncorrectPhoneNumber() {
        driver.findElement(LoginLocators.PHONE_FIELD_LOCATOR).sendKeys("123456789");
        driver.findElement(LoginLocators.PASSWORD_FIELD_LOCATOR).sendKeys("Pass@test123");
        driver.findElement(LoginLocators.LOGIN_BUTTON_LOCATOR).click();
    }

    @Then("Invalid phone number error message should appear")
    public void invalidPhoneNumberErrorMessage() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(40));
        WebElement phoneErrorMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(LoginLocators.ERROR_MESSAGE_PHONE_LOCATOR));
        Assert.assertTrue(phoneErrorMessage.isDisplayed(), "Error message for invalid phone number is not displayed");
    }

    @When("User clicks login button with empty fields")
    public void blankFields() {
        driver.findElement(LoginLocators.PHONE_FIELD_LOCATOR).clear();
        driver.findElement(LoginLocators.PASSWORD_FIELD_LOCATOR).clear();
        driver.findElement(LoginLocators.LOGIN_BUTTON_LOCATOR).click();
    }

    @Then("Two error messages should appear on the screen")
    public void twoErrorMessagesShouldAppear() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(40));
        WebElement phoneErrorMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(LoginLocators.ERROR_MESSAGE_PHONE_LOCATOR));
        WebElement pwErrorMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(LoginLocators.ERROR_MESSAGE_EMPTY_PASSWORD_LOCATOR));
        Assert.assertTrue(phoneErrorMessage.isDisplayed(), "Phone error message is not displayed");
        Assert.assertTrue(pwErrorMessage.isDisplayed(), "Password error message is not displayed");
    }

    @When("User enters valid phone and leaves password field empty")
    public void userEntersValidPhoneAndLeavesPasswordFieldEmpty() {
        driver.findElement(LoginLocators.PHONE_FIELD_LOCATOR).sendKeys("521000400");
        driver.findElement(LoginLocators.PASSWORD_FIELD_LOCATOR).clear();
        driver.findElement(LoginLocators.LOGIN_BUTTON_LOCATOR).click();
    }

    @Then("An error message for empty password should appear on the screen")
    public void emptyPasswordErrorMessage() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(40));
        WebElement pwErrorMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(LoginLocators.ERROR_MESSAGE_EMPTY_PASSWORD_LOCATOR));
        Assert.assertTrue(pwErrorMessage.isDisplayed(), "Error message for empty password is not displayed");
    }

    @When("User enters valid password and leaves phone field empty")
    public void userEntersValidPasswordAndLeavesPhoneFieldEmpty() {
        driver.findElement(LoginLocators.PASSWORD_FIELD_LOCATOR).sendKeys("Pass@test123");
        driver.findElement(LoginLocators.PHONE_FIELD_LOCATOR).clear();
        driver.findElement(LoginLocators.LOGIN_BUTTON_LOCATOR).click();
    }

    @Then("An error message for empty phone should appear on the screen")
    public void emptyPhoneErrorMessage() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(40));
        WebElement phoneErrorMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(LoginLocators.ERROR_MESSAGE_PHONE_LOCATOR));
        Assert.assertTrue(phoneErrorMessage.isDisplayed(), "Error message for empty phone is not displayed");
    }

    @When("User enters characters in phone field")
    public void enterCharactersInsteadOfNumberInPhoneField() {
        driver.findElement(LoginLocators.PHONE_FIELD_LOCATOR).sendKeys("fghjks1234");
    }

    @Then("Any type of characters should be blocked")
    public void validatePhoneFieldAcceptance() {
        String enteredText = driver.findElement(LoginLocators.PHONE_FIELD_LOCATOR).getAttribute("value");
        Assert.assertTrue(enteredText.matches("^[0-9]*$"), "Phone field should not accept non-numeric characters");
    }

    @When("User enters characters in password field and the content is hidden")
    public void inputHiddenPasswordCharacters() {
        driver.findElement(LoginLocators.PASSWORD_FIELD_LOCATOR).sendKeys("hiddenPassword");
    }

    @And("click on unhidden password icon")
    public void clickUnhidePasswordIcon() {
        // Implement the logic to click on the unhidden button
        WebElement passwordHideIcon = driver.findElement(LoginLocators.PASSWORD_HIDE_ICON_LOCATOR);
        passwordHideIcon.click();
    }

    @Then("The text in the password field is unhidden")
    public void hideAndUnhidePassword() {
        // Check the type of the password field
        String passwordType = driver.findElement(LoginLocators.PASSWORD_FIELD_LOCATOR).getAttribute("type");
        Assert.assertEquals(passwordType, "text", "Password field is not visible after clicking unhide");
    }

    @When("User enters correct Phone and correct password")
    public void correctPhoneCorrectPassword() {
        driver.findElement(LoginLocators.PHONE_FIELD_LOCATOR).sendKeys("521000400");
        driver.findElement(LoginLocators.PASSWORD_FIELD_LOCATOR).sendKeys("Pass@test123");
        driver.findElement(LoginLocators.LOGIN_BUTTON_LOCATOR).click();
    }

    @Then("User should be redirected to the dashboard")
    public void userRedirectedToDashboard() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(40));
        WebElement profileIcon = wait.until(ExpectedConditions.visibilityOfElementLocated(LoginLocators.PROFILE_ICON_LOCATOR));
        Assert.assertNotNull(profileIcon, "User was not redirected to the dashboard");

    }
}