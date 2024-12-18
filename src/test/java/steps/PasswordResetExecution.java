package steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import static org.testng.Assert.assertEquals;
public class PasswordResetExecution {



    private WebDriver driver;

    @Given("I navigate to the reset password page")
    public void i_navigate_to_the_reset_password_page() {
        System.setProperty("webdriver.chrome.driver", "path/to/chromedriver");
        driver = new ChromeDriver();
        driver.get("https://next.aqar.fm/forgot-password");
    }

    @When("I enter a registered phone number {string}")
    public void i_enter_a_registered_phone_number(String phoneNumber) {
        driver.findElement(By.id("phone-number-input")).sendKeys(phoneNumber); // Replace with actual selector
        driver.findElement(By.id("send-otp-button")).click(); // Replace with actual selector
    }

    @When("I enter an unregistered phone number {string}")
    public void i_enter_an_unregistered_phone_number(String phoneNumber) {
        driver.findElement(By.id("phone-number-input")).sendKeys(phoneNumber); // Replace with actual selector
        driver.findElement(By.id("send-otp-button")).click(); // Replace with actual selector
    }

    @When("I receive an OTP")
    public void i_receive_an_otp() {
        // Here you'd ideally have a way to programmatically check for the OTP
        // For simplicity, we'll simulate it
        // In an actual setup, you might want to use a mock service or API call to get this OTP

        // Simulating the OTP received
        driver.findElement(By.id("otp-input")).sendKeys("123456"); // Replace with actual selector
        driver.findElement(By.id("submit-otp-button")).click(); // Replace with actual selector
    }

    @When("I enter the OTP {string}")
    public void i_enter_the_otp(String otp) {
        driver.findElement(By.id("otp-input")).sendKeys(otp); // Replace with actual selector
        driver.findElement(By.id("submit-otp-button")).click(); // Replace with actual selector
    }

    @When("I enter a valid new password {string}")
    public void i_enter_a_valid_new_password(String newPassword) {
        driver.findElement(By.id("new-password-input")).sendKeys(newPassword); // Replace with actual selector
        driver.findElement(By.id("submit-password-button")).click(); // Replace with actual selector
    }

    @When("I enter a new password {string}")
    public void i_enter_a_new_password(String newPassword) {
        driver.findElement(By.id("new-password-input")).sendKeys(newPassword); // Replace with actual selector
        driver.findElement(By.id("submit-password-button")).click(); // Replace with actual selector
    }

    @Then("I should receive an OTP for password reset")
    public void i_should_receive_an_otp_for_password_reset() {
        // Verify OTP messaging logic depending on how the application handles it
        // Could add a check for a successful OTP sent message or update logic as needed
    }

    @Then("I should see an error message {string}")
    public void i_should_see_an_error_message(String expectedErrorMessage) {
        String actualErrorMessage = driver.findElement(By.id("error-message")).getText(); // Use actual selector for error messages
        assertEquals(actualErrorMessage, expectedErrorMessage);
        driver.quit();
    }

    @Then("I should see a success message {string}")
    public void i_should_see_a_success_message(String expectedSuccessMessage) {
        String actualSuccessMessage = driver.findElement(By.id("success-message")).getText(); // Use actual selector for success messages
        assertEquals(actualSuccessMessage, expectedSuccessMessage);
        driver.quit();
    }
}