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
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.time.Duration;

public class PasswordResetExecution {

    private WebDriver driver;
    private String otp;

    // Web Elements
    private WebElement phoneField;
    private WebElement sendOTPButton;
    private WebElement otpField;
    private WebElement newPasswordField;
    private WebElement repeatPasswordField;
    private WebElement resetPasswordButton;
    private WebElement passwordErrorMessage;
    private WebElement otpErrorMessage;

    @Before
    public void setUp() {
        System.setProperty("webdriver.chrome.driver", "C:/Users/PC/Downloads/chromedriver-win32/chromedriver.exe");
        driver = new ChromeDriver();
        driver.get("https://next.aqar.fm/login");

    }

    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit(); // Close the browser after tests
        }
    }


    @Given("User on Password Reset page")
    public void userOnPasswordResetPage() {

        WebElement forgotPassButton = driver.findElement(By.xpath("//*[@id=\"__next\"]/main/div/div[2]/div/div/div/div/div[7]/a"));
        forgotPassButton.click();

    }

    @When("User enters an unregistered phone number {string}")
    public void userEntersAnUnRegisteredPhoneNumber(String phoneNumber) {
        phoneField = driver.findElement(By.xpath("//*[@id=\"__next\"]/main/div/div[2]/div/div/div/div/div[2]/div/input"));
        phoneField.sendKeys(phoneNumber);
        sendOTPButton = driver.findElement(By.xpath("//*[@id=\"__next\"]/main/div/div[2]/div/div/div/div/button"));
        sendOTPButton.click();
        WebElement unregisteredPhone = driver.findElement(By.xpath("//*[@id=\"__next\"]/main/div/div[2]/div/div/div/div/div[2]/div[2]/div"));
        Assert.assertTrue(unregisteredPhone.isDisplayed());
    }
    @Then("An error message for wrong phone appears")
    public void phone_error(){}

    @When("User enters a registered phone number {string}")
    public void userEntersARegisteredPhoneNumber(String phoneNumber) {
        phoneField = driver.findElement(By.xpath("//*[@id=\"__next\"]/main/div/div[2]/div/div/div/div/div[2]/div/input"));
        phoneField.sendKeys(phoneNumber);
        sendOTPButton = driver.findElement(By.xpath("//*[@id=\"__next\"]/main/div/div[2]/div/div/div/div/button"));
        sendOTPButton.click();

        // Wait for OTP input and password fields to become visible
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"__next\"]/main/div/div[2]/div/div/div/div/div[3]/div[2]/div[1]/input")));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"__next\"]/main/div/div[2]/div/div/div/div/div[3]/div[4]/div/input")));
    }
    @Then("User should receive an OTP")
    public void user_receive(){}


    public void getOtpFromTheAPI(String userId) {
        RestAssured.baseURI = "https://dev-v3.aqar.fm/dev";
        Response response = RestAssured.given().when().get("/user/" + userId);
        otp = response.jsonPath().getString("find {it.id == " + userId + "}.vcode");
    }

    @When("User enters wrong OTP {string} and a invalid password")
    public void userEntersWrongOtpAndShortPassword(String wrongOtp) {
        userEntersARegisteredPhoneNumber("521000400");
        otpField = driver.findElement(By.xpath("//*[@id=\"__next\"]/main/div/div[2]/div/div/div/div/div[3]/div[2]/div[1]/input"));
        otpField.sendKeys(wrongOtp);
        newPasswordField = driver.findElement(By.xpath("//*[@id=\"__next\"]/main/div/div[2]/div/div/div/div/div[3]/div[4]/div/input"));
        newPasswordField.sendKeys("9876");
        repeatPasswordField = driver.findElement(By.xpath("//*[@id=\"__next\"]/main/div/div[2]/div/div/div/div/div[3]/div[6]/div/input"));
        repeatPasswordField.sendKeys("9876");
        resetPasswordButton = driver.findElement(By.xpath("//*[@id=\"__next\"]/main/div/div[2]/div/div/div/div/button"));
        resetPasswordButton.click();

        // Verify error messages
        passwordErrorMessage = driver.findElement(By.xpath("//*[@id=\"__next\"]/main/div/div[2]/div/div/div/div/div[3]/div[6]/ul/li/div/div"));

        Assert.assertTrue(passwordErrorMessage.isDisplayed(), "Expected password error message not displayed.");
        newPasswordField.clear();
        repeatPasswordField.clear();
        newPasswordField.sendKeys("thismypassword");
        repeatPasswordField.sendKeys("thismypasswor");
        Assert.assertTrue(passwordErrorMessage.isDisplayed(), "Expected password error message not displayed.");

    }
    @Then("User should see error messages for invalid OTP and invalid password")
    public void error_message(){}


    @When("User enters valid phone and correct OTP and valid password {string}")
    public void userEntersValidPasswordandCorrectOTP(String password) {
        driver.get(" https://next.aqar.fm/forgot-password");
        userEntersARegisteredPhoneNumber("521000400");
        getOtpFromTheAPI("163165");
        otpField = driver.findElement(By.xpath("//*[@id=\"__next\"]/main/div/div[2]/div/div/div/div/div[3]/div[2]/div[1]/input"));
        otpField.sendKeys(otp);
        newPasswordField = driver.findElement(By.xpath("//*[@id=\"__next\"]/main/div/div[2]/div/div/div/div/div[3]/div[4]/div/input"));
        newPasswordField.sendKeys(password);
        repeatPasswordField = driver.findElement(By.xpath("//*[@id=\"__next\"]/main/div/div[2]/div/div/div/div/div[3]/div[6]/div/input"));
        repeatPasswordField.sendKeys(password);
        resetPasswordButton = driver.findElement(By.xpath("//*[@id=\"__next\"]/main/div/div[2]/div/div/div/div/button"));
        resetPasswordButton.click();
    }

    @Then("User should be able to reset the password successfully")
    public void userShouldBeAbleToResetPasswordSuccessfully() {
        Assert.assertFalse(otpErrorMessage.isDisplayed(), "OTP error message should not be displayed.");
        Assert.assertFalse(passwordErrorMessage.isDisplayed(), "Password error message should not be displayed.");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement profileIcon = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"__next\"]/main/div/div[1]/div[2]/button/span/img")));
        Assert.assertTrue(profileIcon != null);
    }



}