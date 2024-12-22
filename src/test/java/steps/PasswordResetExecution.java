package steps;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import java.time.Duration;
import locators.ResetPasswordLocators; // Import page elements locators

public class PasswordResetExecution {

    private WebDriver driver;
    private WebDriverWait wait;
    private WebElement phoneField;
    private WebElement sendOtpButton;
    private WebElement otpField;
    private WebElement newPasswordField;
    private WebElement repeatPasswordField;
    private WebElement resetPasswordButton;
    private String otp;
    private static final String FORGOT_PASSWORD_URL = "https://next.aqar.fm/forgot-password";

    @Before("@ResetPassword")
    public void setUp() {
        System.setProperty("webdriver.chrome.driver", "C:/Users/PC/Downloads/chromedriver-win32/chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        wait = new WebDriverWait(driver, Duration.ofSeconds(30));
    }

    @After("@ResetPassword")
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

    @Given("User on Password Reset page")
    public void userOnPasswordResetPage() {
        driver.get(FORGOT_PASSWORD_URL);
    }

    @When("User enters an unregistered phone number {string}")
    public void userEntersAnUnregisteredPhoneNumber(String phoneNumber) {
        phoneField = driver.findElement(ResetPasswordLocators.PHONE_FIELD_LOCATOR);
        phoneField.sendKeys(phoneNumber);
        sendOtpButton = driver.findElement(ResetPasswordLocators.SEND_OTP_BUTTON_LOCATOR);
        sendOtpButton.click();

        WebElement unregisteredPhone = wait.until(ExpectedConditions.visibilityOfElementLocated(ResetPasswordLocators.UNREGISTERED_PHONE_LOCATOR));
        Assert.assertTrue(unregisteredPhone.isDisplayed(), "Unregistered phone message is not displayed.");
    }

    @Then("An error message for wrong phone appears")
    public void phoneError() {
        // No need for implementation
    }

    @When("User enters a registered phone number {string}")
    public void userEntersARegisteredPhoneNumber(String phoneNumber) {
        phoneField = driver.findElement(ResetPasswordLocators.PHONE_FIELD_LOCATOR);
        phoneField.sendKeys(phoneNumber);
        sendOtpButton = driver.findElement(ResetPasswordLocators.SEND_OTP_BUTTON_LOCATOR);
        sendOtpButton.click();

        // Wait for OTP input and password fields to become visible
        wait.until(ExpectedConditions.visibilityOfElementLocated(ResetPasswordLocators.OTP_FIELD_LOCATOR));
        wait.until(ExpectedConditions.visibilityOfElementLocated(ResetPasswordLocators.NEW_PASSWORD_FIELD_LOCATOR));
    }

    @Then("User should receive an OTP")
    public void userReceive() {
        // No need for implementation
    }

    public void getOtpFromTheApi(String userId) {
        RestAssured.baseURI = "https://dev-v3.aqar.fm/dev";
        Response response = RestAssured.given().when().get("/user/" + userId);
        if (response.getStatusCode() == 200) {
            otp = response.jsonPath().getString("vcode");
            System.out.println("OTP: " + otp);
        } else {
            System.out.println("Failed to fetch OTP. Status code: " + response.getStatusCode());
        }
    }

    public String getOtp() {
        return otp;
    }

    @When("User enters the OTP and an invalid password")
    public void userEntersWrongOtpAndShortPassword() {
        // Wait to get OTP
        wait.until(ExpectedConditions.visibilityOfElementLocated(ResetPasswordLocators.OTP_FIELD_LOCATOR));

        getOtpFromTheApi("163165");
        otpField = driver.findElement(ResetPasswordLocators.OTP_FIELD_LOCATOR);
        otpField.sendKeys(otp);

        newPasswordField = driver.findElement(ResetPasswordLocators.NEW_PASSWORD_FIELD_LOCATOR);
        newPasswordField.sendKeys("9876");

        repeatPasswordField = driver.findElement(ResetPasswordLocators.REPEAT_PASSWORD_FIELD_LOCATOR);
        repeatPasswordField.sendKeys("9876");

        resetPasswordButton = driver.findElement(ResetPasswordLocators.RESET_PASSWORD_BUTTON_LOCATOR);
        resetPasswordButton.click();
    }

    @Then("User should see error messages for invalid password")
    public void errorMessage() {
        WebElement passwordErrorMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(ResetPasswordLocators.PASSWORD_ERROR_MESSAGE_LOCATOR));
        Assert.assertTrue(passwordErrorMessage.isDisplayed(), "Expected password error message is not displayed.");
        newPasswordField.clear();
        repeatPasswordField.clear();
    }

    @When("User enters valid password {string}")
    public void userEntersValidPasswordAndCorrectOtp(String password) {
        getOtpFromTheApi("163165");

        newPasswordField = driver.findElement(ResetPasswordLocators.NEW_PASSWORD_FIELD_LOCATOR);
        newPasswordField.sendKeys(password);

        repeatPasswordField = driver.findElement(ResetPasswordLocators.REPEAT_PASSWORD_FIELD_LOCATOR);
        repeatPasswordField.sendKeys(password);

        resetPasswordButton = driver.findElement(ResetPasswordLocators.RESET_PASSWORD_BUTTON_LOCATOR);
        resetPasswordButton.click();
    }

    @Then("User should be able to reset the password successfully")
    public void userShouldBeAbleToResetPasswordSuccessfully() {
        // Wait for profile icon to be visible
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(40));
        WebElement profileIcon = wait.until(ExpectedConditions.visibilityOfElementLocated(ResetPasswordLocators.PROFILE_ICON_LOCATOR));
        Assert.assertNotNull(profileIcon, "Profile icon should be visible after password reset.");
    }
}
