package steps;

import io.cucumber.java.Before;
import io.cucumber.java.After;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
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

    // WebElements
    private WebElement title;
    private WebElement passwordField;
    private WebElement phoneField;
    private WebElement loginButton;

    @Before
    public void setUp() {
        System.setProperty("webdriver.chrome.driver", "C:/Users/PC/Downloads/chromedriver-win32/chromedriver.exe");
        driver = new ChromeDriver();
        driver.get(LOGIN_URL); // Navigate to the Login Page
        initializeElements();
    }

    private void initializeElements() {
        title = driver.findElement(By.xpath("//*[@id=\"__next\"]/main/div/div[2]/div/div/h1"));
        passwordField = driver.findElement(By.xpath("//*[@id=\"__next\"]/main/div/div[2]/div/div/div/div/div[5]/div[1]/input"));
        phoneField = driver.findElement(By.xpath("//*[@id=\"__next\"]/main/div/div[2]/div/div/div/div/div[3]/div[1]/input"));
        loginButton = driver.findElement(By.xpath("//*[@id=\"__next\"]/main/div/div[2]/div/div/div/div/button"));
    }

    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit(); // Close the browser after tests
        }
    }

    @Given("User on the login page")
    public void user_on_the_login_page() {
        Assert.assertEquals(driver.getCurrentUrl(), LOGIN_URL);
        Assert.assertTrue(title.isDisplayed());
    }

    @Then("The login form with all its fields and button appear")
    public void login_form_appearance() {
        Assert.assertTrue(passwordField.isDisplayed());
        Assert.assertTrue(phoneField.isDisplayed());
        Assert.assertTrue(loginButton.isDisplayed());
    }

    @When("User enters valid Phone and incorrect password")
    public void user_enters_valid_phone_and_incorrect_password() {
        // This is for implementing any future update
    }

    @Then("An error message should appear on the screen")
    public void an_error_message_should_apear_on_the_screen() {
        phoneField.sendKeys("521000400");
        passwordField.sendKeys("padf567lkk");
        loginButton.click();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10)); // waits up to 10 seconds
        WebElement pwErrorMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(text(),'كلمة المرور غير صحيحة')]")));
        Assert.assertTrue(pwErrorMessage.isDisplayed());
    }

    @When("User enters valid password and incorrect phone number")
    public void valid_password_incorrect_phone1() {
        // Implementation for future updates
    }

    @Then("Invalid phone number error message should appear")
    public void valid_password_incorrect_phone() {
        phoneField.sendKeys("123456789");
        passwordField.sendKeys("Pass@test123");
        loginButton.click();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10)); // waits up to 10 seconds
        WebElement phoneErrorMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(text(),'مطلوب رقم جوال سعودي')]")));
        Assert.assertTrue(phoneErrorMessage.isDisplayed());
    }

    @When("User enters valid password and unregistered phone number")
    public void valid_password_unregisterd_phonee() {
        // Implementation for future updates
    }

    @Then("unregistered phone number error message should appear")
    public void valid_password_unregistered_phone() {
        phoneField.sendKeys("512345678");
        passwordField.sendKeys("Pass@test123");
        loginButton.click();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10)); // waits up to 10 seconds
        WebElement phoneUnregisteredeErrorMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(text(),'الحساب غير مفعل')]")));
        Assert.assertTrue(phoneUnregisteredeErrorMessage.isDisplayed());
    }

    @When("User enter login button with empty fields")
    public void blank_fields() {
        phoneField.clear();
        passwordField.clear();
        loginButton.click();

        WebElement phoneErrorMessage = driver.findElement(By.xpath("//*[contains(text(),'مطلوب رقم جوال سعودي')]"));
        WebElement pwErrorMessage = driver.findElement(By.xpath("//*[contains(text(),'مطلوب كلمة المرور')]"));
        Assert.assertTrue(phoneErrorMessage.isDisplayed());
        Assert.assertTrue(pwErrorMessage.isDisplayed());
    }

    @When("User enters valid phone and leaves password field empty")
    @Then("An error message for empty password should appear on the screen")
    public void valid_phone_blank_password() {
        phoneField.clear();
        phoneField.sendKeys("521000400");
        passwordField.clear();
        loginButton.click();

        WebElement pwErrorMessage = driver.findElement(By.xpath("//*[contains(text(),'مطلوب كلمة المرور')]"));
        Assert.assertTrue(pwErrorMessage.isDisplayed());
    }

    @When("User enters valid password and leaves phone field empty")
    @Then("An error message for empty phone should appear on the screen")
    public void valid_password_blank_phone() {
        passwordField.clear();
        passwordField.sendKeys("Pass@test123");
        phoneField.clear();
        loginButton.click();

        WebElement phoneErrorMessage = driver.findElement(By.xpath("//*[contains(text(),'مطلوب رقم جوال سعودي')]"));
        Assert.assertTrue(phoneErrorMessage.isDisplayed());
    }

    @When("User enters characters in phone field")
    @Then("Any type of characters should be blocked")
    public void enter_char_instead_of_number_on_phoneField() {
        phoneField.sendKeys("fghjks1234");
        String enteredText = phoneField.getAttribute("value");
        Assert.assertTrue(enteredText.matches("^[0-9]*$"));
    }

    @When("User enters characters in password field and the content is hidden")
    @And("click on unhide password icon")
    @Then("The text in the password field is unhidden")
    public void hide_and_unhide_password() {
        String passwordType = passwordField.getAttribute("type");
        Assert.assertEquals(passwordType, "password");

        WebElement passwordHideIcon = driver.findElement(By.xpath("//*[@id=\"__next\"]/main/div/div[2]/div/div/div/div/div[5]/div[1]/button"));
        Actions actions = new Actions(driver);
        actions.moveToElement(passwordHideIcon).click().perform();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.attributeToBe(passwordField, "type", "text"));

        passwordType = passwordField.getAttribute("type");
        Assert.assertEquals(passwordType, "text");

        passwordHideIcon.click(); // Click to hide again
        wait.until(ExpectedConditions.attributeToBe(passwordField, "type", "password"));
        passwordType = passwordField.getAttribute("type");
        Assert.assertEquals(passwordType, "password");
    }

    @When("User enters correct Phone and correct password")
    @Then("User should be redirected to the dashboard")
    public void correct_phone_correct_password() {
        phoneField.sendKeys("521000400");
        passwordField.sendKeys("Pass@test123");
        loginButton.click();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        WebElement profileIcon = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"__next\"]/main/div/div[1]/div[2]/button/span/img")));
        Assert.assertTrue(profileIcon != null);
    }
}