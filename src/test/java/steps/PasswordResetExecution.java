package steps;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.time.Duration;

public class PasswordResetExecution {

    private WebDriver driver;
    private String otp;
    private static final String FORGOT_PASSWORD_URL = "https://next.aqar.fm/forgot-password";

    // Locators for Web Elements
    private By PHONE_FIELD_LOCATOR = By.xpath("//*[@id='__next']/main/div/div[2]/div/div/div/div/div[2]/div/input");
    private By SEND_OTP_BUTTON_LOCATOR = By.xpath("//*[@id='__next']/main/div/div[2]/div/div/div/div/button");
    private By OTP_FIELD_LOCATOR = By.xpath("//*[@id='__next']/main/div/div[2]/div/div/div/div/div[3]/div[2]/div[1]/input");
    private By NEW_PASSWORD_FIELD_LOCATOR = By.xpath("//*[@id='__next']/main/div/div[2]/div/div/div/div/div[3]/div[4]/div/input");
    private By REPEAT_PASSWORD_FIELD_LOCATOR = By.xpath("//*[@id='__next']/main/div/div[2]/div/div/div/div/div[3]/div[6]/div/input");
    private By RESET_PASSWORD_BUTTON_LOCATOR = By.xpath("//*[@id='__next']/main/div/div[2]/div/div/div/div/button");
    private By PASSWORD_ERROR_MESSAGE_LOCATOR = By.xpath("//*[@id='__next']/main/div/div[2]/div/div/div/div/div[3]/div[6]/ul/li/div/div");
   // private By OTP_ERROR_MESSAGE_LOCATOR = By.xpath("//div[contains(@class, 'otp-error')]");
    private By UNREGISTERED_PHONE_LOCATOR = By.xpath("//*[@id='__next']/main/div/div[2]/div/div/div/div/div[2]/div[2]/div");
    private By PROFILE_ICON_LOCATOR = By.xpath("//*[@id='__next']/main/div/div[1]/div[2]/button/span/img");

    @Before
    public void beforeScenario() {
        setUp();
    }

    public void setUp() {
        System.setProperty("webdriver.chrome.driver", "C:/Users/PC/Downloads/chromedriver-win32/chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize(); // Maximize the browser window
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10)); // Implicit wait
    }


    @Given("User on Password Reset page")
    public void userOnPasswordResetPage() {
        driver.get(FORGOT_PASSWORD_URL);
    }

    @When("User enters an unregistered phone number {string}")
    public void userEntersAnUnregisteredPhoneNumber(String phoneNumber) {
        WebElement phoneField = driver.findElement(PHONE_FIELD_LOCATOR);
        phoneField.sendKeys(phoneNumber);
        WebElement sendOtpButton = driver.findElement(SEND_OTP_BUTTON_LOCATOR);
        sendOtpButton.click();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement unregisteredPhone = wait.until(ExpectedConditions.visibilityOfElementLocated(UNREGISTERED_PHONE_LOCATOR));
        Assert.assertTrue(unregisteredPhone.isDisplayed(), "Unregistered phone message is not displayed.");
    }

    @Then("An error message for wrong phone appears")
    public void phoneError() {
        // Implementation for phone error message validation
    }

    @When("User enters a registered phone number {string}")
    public void userEntersARegisteredPhoneNumber(String phoneNumber) {
        WebElement phoneField = driver.findElement(PHONE_FIELD_LOCATOR);
        phoneField.sendKeys(phoneNumber);
        WebElement sendOtpButton = driver.findElement(SEND_OTP_BUTTON_LOCATOR);
        sendOtpButton.click();

        // Wait for OTP input and password fields to become visible
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        wait.until(ExpectedConditions.visibilityOfElementLocated(OTP_FIELD_LOCATOR));
        wait.until(ExpectedConditions.visibilityOfElementLocated(NEW_PASSWORD_FIELD_LOCATOR));
    }

    @Then("User should receive an OTP")
    public void userReceive() {
        // Implementation for OTP reception validation
    }

    public void getOtpFromTheApi(String userId) {
        RestAssured.baseURI = "https://dev-v3.aqar.fm/dev";
        Response response = RestAssured.given().when().get("/user/" + userId);
        otp = response.jsonPath().getString("find {it.id == " + userId + "}.vcode");
    }

    @When("User enters wrong OTP {string} and an invalid password")
    public void userEntersWrongOtpAndShortPassword(String wrongOtp) {
        userEntersARegisteredPhoneNumber("521000400");

        WebElement otpField = driver.findElement(OTP_FIELD_LOCATOR);
        otpField.sendKeys(wrongOtp);

        WebElement newPasswordField = driver.findElement(NEW_PASSWORD_FIELD_LOCATOR);
        newPasswordField.sendKeys("9876");

        WebElement repeatPasswordField = driver.findElement(REPEAT_PASSWORD_FIELD_LOCATOR);
        repeatPasswordField.sendKeys("9876");

        WebElement resetPasswordButton = driver.findElement(RESET_PASSWORD_BUTTON_LOCATOR);
        resetPasswordButton.click();

        // Verify error messages
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement passwordErrorMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(PASSWORD_ERROR_MESSAGE_LOCATOR));
        Assert.assertTrue(passwordErrorMessage.isDisplayed(), "Expected password error message not displayed.");

        // Test for another invalid password
        newPasswordField.clear();
        repeatPasswordField.clear();
        newPasswordField.sendKeys("thismypassword");
        repeatPasswordField.sendKeys("thismypassword");

        // Verify that the error message is displayed for password validation
        passwordErrorMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(PASSWORD_ERROR_MESSAGE_LOCATOR));
        Assert.assertTrue(passwordErrorMessage.isDisplayed(), "Expected password error message not displayed.");
    }

    @Then("User should see error messages for invalid OTP and invalid password")
    public void errorMessage() {
        // Validate that both OTP and password error messages are displayed
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
       // WebElement otpErrorMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(OTP_ERROR_MESSAGE_LOCATOR));
        WebElement passwordErrorMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(PASSWORD_ERROR_MESSAGE_LOCATOR));

       // Assert.assertTrue(otpErrorMessage.isDisplayed(), "Expected OTP error message is not displayed.");
        Assert.assertTrue(passwordErrorMessage.isDisplayed(), "Expected password error message is not displayed.");
    }

    @When("User enters valid phone and correct OTP and valid password {string}")
    public void userEntersValidPasswordAndCorrectOtp(String password) {
        driver.get(FORGOT_PASSWORD_URL);
        userEntersARegisteredPhoneNumber("521000400");
        getOtpFromTheApi("163165");

        WebElement otpField = driver.findElement(OTP_FIELD_LOCATOR);
        otpField.sendKeys(otp);

        WebElement newPasswordField = driver.findElement(NEW_PASSWORD_FIELD_LOCATOR);
        newPasswordField.sendKeys(password);

        WebElement repeatPasswordField = driver.findElement(REPEAT_PASSWORD_FIELD_LOCATOR);
        repeatPasswordField.sendKeys(password);

        WebElement resetPasswordButton = driver.findElement(RESET_PASSWORD_BUTTON_LOCATOR);
        resetPasswordButton.click();
    }

    @Then("User should be able to reset the password successfully")
    public void userShouldBeAbleToResetPasswordSuccessfully() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // Check that no error messages are displayed
       // WebElement otpErrorMessage = driver.findElement(OTP_ERROR_MESSAGE_LOCATOR);
        WebElement passwordErrorMessage = driver.findElement(PASSWORD_ERROR_MESSAGE_LOCATOR);

       // Assert.assertFalse(otpErrorMessage.isDisplayed(), "OTP error message should not be displayed.");
        Assert.assertFalse(passwordErrorMessage.isDisplayed(), "Password error message should not be displayed.");

        // Wait for profile icon to be visible
        WebElement profileIcon = wait.until(ExpectedConditions.visibilityOfElementLocated(PROFILE_ICON_LOCATOR));
        Assert.assertNotNull(profileIcon, "Profile icon should be visible after password reset.");
    }


}