package steps;

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

    // Web Elements
    private WebElement phoneField;
    private WebElement sendOTPButton;
    private WebElement otpField;
    private WebElement newPasswordField;
    private WebElement repeatPasswordField;
    private WebElement resetPasswordButton;
    private WebElement passwordErrorMessage;
    private WebElement otpErrorMessage;

    @Given("User on Password Reset page")
    public void userOnPasswordResetPage() {
        System.setProperty("webdriver.chrome.driver", "C:/Users/PC/Downloads/chromedriver-win32/chromedriver.exe"); // Add path for chromedriver
        driver = new ChromeDriver();
        driver.get("https://next.aqar.fm/forgot-password"); // Directly open the password reset page
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

    @When("User enters a registered phone number {string}")
    public void userEntersARegisteredPhoneNumber(String phoneNumber) {
        phoneField = driver.findElement(By.xpath("//*[@id=\"__next\"]/main/div/div[2]/div/div/div/div/div[2]/div/input"));
        phoneField.sendKeys(phoneNumber);
        sendOTPButton = driver.findElement(By.xpath("//*[@id=\"__next\"]/main/div/div[2]/div/div/div/div/button"));
        sendOTPButton.click();
        getOtpFromTheAPI("163165");

        // Wait for OTP input and password fields to become visible
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"__next\"]/main/div/div[2]/div/div/div/div/div[3]/div[2]/div[1]/input")));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"__next\"]/main/div/div[2]/div/div/div/div/div[3]/div[4]/div/input")));
    }


    public void getOtpFromTheAPI(String userId) {
        RestAssured.baseURI = "https://dev-v3.aqar.fm/dev";
        Response response = RestAssured.given().when().get("/user/" + userId);
        otp = response.jsonPath().getString("find {it.id == " + userId + "}.vcode");
    }

    @When("User enters wrong OTP {string} and a short password")
    public void userEntersWrongOtpAndShortPassword(String wrongOtp) {
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
        otpErrorMessage = driver.findElement(By.xpath("//*[@id=\"__next\"]/main/div/div[2]/div/div/div/div/div[3]/div[2]/div[2]/div"));
        Assert.assertTrue(passwordErrorMessage.isDisplayed(), "Expected password error message not displayed.");
        Assert.assertTrue(otpErrorMessage.isDisplayed(), "Expected OTP error message not displayed.");
    }

    @When("User enters an invalid password {string}")
    public void userEntersInvalidPassword(String password) {
        newPasswordField = driver.findElement(By.xpath("//*[@id=\"__next\"]/main/div/div[2]/div/div/div/div/div[3]/div[4]/div/input"));
        newPasswordField.sendKeys(password);
        repeatPasswordField = driver.findElement(By.xpath("//*[@id=\"__next\"]/main/div/div[2]/div/div/div/div/div[3]/div[6]/div/input"));
        repeatPasswordField.sendKeys(password);
        resetPasswordButton.click();
        Assert.assertTrue(passwordErrorMessage.isDisplayed(), "Expected password error message not displayed.");
    }

    @When("User enters a valid OTP")
    public void userEntersValidOtp() {
        otpField = driver.findElement(By.xpath("//*[@id=\"__next\"]/main/div/div[2]/div/div/div/div/div[3]/div[2]/div[1]/input"));
        otpField.sendKeys(otp);
    }

    @When("User enters a valid password {string}")
    public void userEntersValidPassword(String password) {
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
    }

    // Cleanup method to close the driver
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}