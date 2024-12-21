package steps;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.BeforeAll;
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

    // Page elements
    private WebElement title;
    private WebElement passwordField;
    private WebElement phoneField;
    private WebElement loginButton;



public void set() {
    //relpace the path with your Chrome Driver path
    System.setProperty("webdriver.chrome.driver", "C:/Users/PC/Downloads/chromedriver-win32/chromedriver.exe");
}
    private void initializeElements() {
        title = driver.findElement(By.xpath("//*[@id='__next']/main/div/div[2]/div/div/h1"));
        passwordField = driver.findElement(By.xpath("//*[@id='__next']/main/div/div[2]/div/div/div/div/div[5]/div[1]/input"));
        phoneField = driver.findElement(By.xpath("//*[@id='__next']/main/div/div[2]/div/div/div/div/div[3]/div[1]/input"));
        loginButton = driver.findElement(By.xpath("//*[@id='__next']/main/div/div[2]/div/div/div/div/button"));
    }


    public void tearDown() {
        if (driver != null) {
            driver.quit(); // Close the browser after tests
        }
    }
    @When("User enters correct Phone and correct password")
    @Given("User is on the login page")
    public void user_on_the_login_page() {

    }

    @Then("The login form with all its fields and button should appear")
    public void login_form_appearance() {
        Assert.assertTrue(passwordField.isDisplayed(), "Password field is not displayed");
        Assert.assertTrue(phoneField.isDisplayed(), "Phone field is not displayed");
        Assert.assertTrue(loginButton.isDisplayed(), "Login button is not displayed");
        tearDown();
    }

    @When("User enters valid Phone and incorrect password")
    public void user_enters_valid_phone_and_incorrect_password() {

    }

    @Then("An error message should appear on the screen")
    public void an_error_message_should_appear_on_the_screen() {
        set();
        driver = new ChromeDriver();
        driver.manage().window().maximize(); // Maximize the browser window
        driver.get(LOGIN_URL);
        initializeElements();
        phoneField.sendKeys("521000400");
        passwordField.sendKeys("incorrectPassword");
        loginButton.click();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement errorMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(text(),'كلمة المرور غير صحيحة')]")));
        Assert.assertTrue(errorMessage.isDisplayed(), "Error message for incorrect password is not displayed");
        tearDown();
    }

    @When("User enters valid password and incorrect phone number")
    public void valid_password_incorrect_phone() {

    }

    @Then("Invalid phone number error message should appear")
    public void invalid_phone_number_error_message() {
        set();
        driver = new ChromeDriver();
        driver.manage().window().maximize(); // Maximize the browser window
        driver.get(LOGIN_URL);
        initializeElements();
        phoneField.sendKeys("123456789");
        passwordField.sendKeys("Pass@test123");
        loginButton.click();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement phoneErrorMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(text(),'مطلوب رقم جوال سعودي')]")));
        Assert.assertTrue(phoneErrorMessage.isDisplayed(), "Error message for invalid phone number is not displayed");
        tearDown();
    }

    @When("User clicks login button with empty fields")
    public void blank_fields() {

    }

    @Then("Two error messages should appear on the screen")
    public void two_error_messages_should_appear() {
        set();
        driver = new ChromeDriver();
        driver.manage().window().maximize(); // Maximize the browser window
        driver.get(LOGIN_URL);
        initializeElements();
        phoneField.clear();
        passwordField.clear();
        loginButton.click();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement phoneErrorMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(text(),'مطلوب رقم جوال سعودي')]")));
        WebElement pwErrorMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(text(),'مطلوب كلمة المرور')]")));
        Assert.assertTrue(phoneErrorMessage.isDisplayed(), "Phone error message is not displayed");
        Assert.assertTrue(pwErrorMessage.isDisplayed(), "Password error message is not displayed");
        tearDown();
    }

    @When("User enters valid phone and leaves password field empty")
    @Then("An error message for empty password should appear on the screen")
    public void valid_phone_blank_password() {
        set();
        driver = new ChromeDriver();
        driver.manage().window().maximize(); // Maximize the browser window
        driver.get(LOGIN_URL);
        initializeElements();
        phoneField.clear();
        phoneField.sendKeys("521000400");
        passwordField.clear();
        loginButton.click();

        WebElement pwErrorMessage = driver.findElement(By.xpath("//*[contains(text(),'مطلوب كلمة المرور')]"));
        Assert.assertTrue(pwErrorMessage.isDisplayed(), "Error message for empty password is not displayed");
        tearDown();
    }

    @When("User enters valid password and leaves phone field empty")
    @Then("An error message for empty phone should appear on the screen")
    public void valid_password_blank_phone() {
        set();
        driver = new ChromeDriver();
        driver.manage().window().maximize(); // Maximize the browser window
        driver.get(LOGIN_URL);
        initializeElements();
        passwordField.clear();
        passwordField.sendKeys("Pass@test123");
        phoneField.clear();
        loginButton.click();

        WebElement phoneErrorMessage = driver.findElement(By.xpath("//*[contains(text(),'مطلوب رقم جوال سعودي')]"));
        Assert.assertTrue(phoneErrorMessage.isDisplayed(), "Error message for empty phone is not displayed");
        tearDown();
    }

    @When("User enters characters in phone field")
    @Then("Any type of characters should be blocked")
    public void enter_char_instead_of_number_on_phoneField() {
        set();
        driver = new ChromeDriver();
        driver.manage().window().maximize(); // Maximize the browser window
        driver.get(LOGIN_URL);
        initializeElements();
        phoneField.sendKeys("fghjks1234");
        String enteredText = phoneField.getAttribute("value");
        Assert.assertFalse(enteredText.matches("^[0-9]*$"), "Phone field should not accept non-numeric characters");
   tearDown();
    }

    @When("User enters characters in password field and the content is hidden")
    @And("click on unhide password icon")
    public void hide(){}
    @Then("The text in the password field is unhidden")
    public void hide_and_unhide_password() {
       set();
        driver = new ChromeDriver();
        driver.manage().window().maximize(); // Maximize the browser window
        driver.get(LOGIN_URL);
        initializeElements();
        String passwordType = passwordField.getAttribute("type");
        Assert.assertEquals(passwordType, "password", "Password field is not hidden");

        WebElement passwordHideIcon = driver.findElement(By.xpath("//*[@id='__next']/main/div/div[2]/div/div/div/div/div[5]/div[1]/button"));
        Actions actions = new Actions(driver);
        actions.moveToElement(passwordHideIcon).click().perform();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        wait.until(ExpectedConditions.attributeToBe(passwordField, "type", "text"));

        passwordType = passwordField.getAttribute("type");
        Assert.assertEquals(passwordType, "text", "Password field is not visible after clicking unhide");

        // Hide again
        passwordHideIcon.click();
        wait.until(ExpectedConditions.attributeToBe(passwordField, "type", "password"));
        passwordType = passwordField.getAttribute("type");
        Assert.assertEquals(passwordType, "password", "Password field is not hidden again");
        tearDown();
    }



    @Then("User should be redirected to the dashboard")
    public void correct_phone_correct_password() {
        set();
        driver = new ChromeDriver();
        driver.manage().window().maximize(); // Maximize the browser window
        driver.get(LOGIN_URL);
        initializeElements();
        phoneField.sendKeys("521000400");
        passwordField.sendKeys("Pass@test123");
        loginButton.click();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        WebElement profileIcon = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='__next']/main/div/div[1]/div[2]/button/span/img")));
        Assert.assertNotNull(profileIcon, "User was not redirected to the dashboard");
        tearDown();
    }


}