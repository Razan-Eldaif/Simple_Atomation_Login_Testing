package steps;
import io.cucumber.java.BeforeAll;
import io.cucumber.java.AfterAll;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
public class LoginUiExecution {

    private static WebDriver driver;
    private static final String LOGIN_URL = "https://next.aqar.fm/login"; // Test page URL
    WebElement titlel;
    WebElement pwFeild;
    WebElement phoneFeild;
    WebElement loginButton;
    WebElement phoneErrorMessage;
    WebElement pwErrorMessage;
    @BeforeAll
    public void setUp() {
        System.setProperty("webdriver.chrome.driver", "C:/Users/PC/Downloads/chromedriver-win32/chromedriver.exe");//Add path for chromedriver
        driver = new ChromeDriver();
        driver.get(LOGIN_URL); // Navigate to the Login Page
        titlel = driver.findElement(By.xpath("//*[@id=\"__next\"]/main/div/div[2]/div/div/h1"));
        pwFeild = driver.findElement(By.name("password"));
        phoneFeild = driver.findElement(By.name("phone"));
        loginButton = driver.findElement(By.xpath("//*[@id=\"__next\"]/main/div/div[2]/div/div/div/div/button"));
        String phonePlaceholder = phoneFeild.getAttribute("placeholder");
        String pwPlaceholder = pwFeild.getAttribute("placeholder");
        String loginButtonText = loginButton.getText();
        WebElement phoneFeildTitle = driver.findElement(By.xpath("//*[@id=\"__next\"]/main/div/div[2]/div/div/div/div/div[2]"));
        WebElement pwFeildTitle = driver.findElement(By.xpath("//*[@id=\"__next\"]/main/div/div[2]/div/div/div/div/div[4]"));

        Assert.assertEquals("انضم إلى عقار", titlel.getText());
        Assert.assertTrue(pwFeild.isDisplayed());
        Assert.assertTrue(phoneFeild.isDisplayed());
        Assert.assertTrue(loginButton.isDisplayed());
        Assert.assertTrue(phoneFeildTitle.isDisplayed());
        Assert.assertTrue(pwFeildTitle.isDisplayed());

        Assert.assertEquals(phonePlaceholder, "الرجاء كتابة رقم الجوال...");
        Assert.assertEquals(pwPlaceholder, "الرجاء كتابة كلمة المرور...");

        Assert.assertEquals(phoneFeildTitle.getText(), "رقم الجوال");
        Assert.assertEquals(pwFeildTitle, "كلمة المرور");
        Assert.assertEquals(loginButtonText, "دخول");

    }

    @AfterAll
    public static void tearDown() {
        if (driver != null) {
            driver.quit(); // Close the browser after tests
        }
    }

    @Then("The login form with all its feilds and button appear")
    public void login_form_appearance() {

    }
    @When("User enter login buttoun with empty feilds")
    public void blank_feilds(){
         phoneFeild.clear();
         pwFeild.clear();
         loginButton.click();

         phoneErrorMessage = driver.findElement(By.xpath("//*[@id=\"__next\"]/main/div/div[2]/div/div/div/div/div[3]/div[2]/div"));
         pwErrorMessage = driver.findElement(By.xpath("//*[@id=\"__next\"]/main/div/div[2]/div/div/div/div/div[5]/div[2]/div"));
         Assert.assertTrue(phoneErrorMessage.isDisplayed());
         Assert.assertTrue(pwErrorMessage.isDisplayed());
         Assert.assertEquals(phoneErrorMessage.getText(), "مطلوب رقم جوال سعودي");
        Assert.assertEquals(pwErrorMessage.getText(), "مطلوب كلمة المرور");
    }
@When("User enters valid phone and leave password feild empty")
        public void valid_phone_blank_password(){
    phoneFeild.sendKeys("521000400");
    pwFeild.clear();
    loginButton.click();

    Assert.assertTrue(pwErrorMessage.isDisplayed());

}

    @When("User enters valid password and leave phone feild empty")
    public void valid_password_blank_phone(){
        phoneFeild.clear();
        pwFeild.sendKeys("Pass@test123");
        loginButton.click();

        Assert.assertTrue(phoneErrorMessage.isDisplayed());

    }
    @When("User enters valid password and incorrect phone number")
    public void valid_password_incorrect_phone(){
        phoneFeild.sendKeys("521000401");
        pwFeild.sendKeys("Pass@test123");
        loginButton.click();
        WebElement authErrorMessage = driver.findElement(By.xpath("//*[@id=\"__next\"]/main/div/div[2]/div/div/div/div/div[6]/div/div"));
        Assert.assertTrue(authErrorMessage.isDisplayed());
        Assert.assertEquals(authErrorMessage.getText(), "البيانات التي تمادخالها غير صحيحة او المستخدم غير موجود");
    }
@When("User enters valid Phone and incorrect password")
    public void valid_phone_incorrect_password(){
    phoneFeild.sendKeys("521000400");
    pwFeild.sendKeys("Pass@test023");
    loginButton.click();
    Assert.assertTrue(pwErrorMessage.isDisplayed());
    Assert.assertEquals(pwErrorMessage.getText(), "كلمة المرور غير صحيحة");

}
@When("User enters chracters on phone feild")
    public void enter_char_instead_of_number_on_phoneFeild(){
    phoneFeild.sendKeys("afg?/66@ :;.");
    String enteredText = phoneFeild.getAttribute("value");
    Assert.assertTrue(enteredText.matches("^[0-9]*$"));
    Assert.assertFalse(enteredText.matches(".*[a-zA-Z].*"));
}
@When("User enters chracters on password feild and the content is hiden")
   public void hide_and_unhide_password(){
        pwFeild.sendKeys("Pass@test023");
        String passwordType = pwFeild.getAttribute("type");
        Assert.assertEquals(passwordType,"password");
        WebElement passwordHideIcon = driver.findElement(By.xpath("//*[@id=\"__next\"]/main/div/div[2]/div/div/div/div/div[5]/div[1]/button/img"));
        passwordHideIcon.click();
        //passwordType = pwFeild.getAttribute("type");
        Assert.assertEquals(passwordType,"text");
    }
@When("User enters valid Phone and password")
    public void valid_phone_valid_password(){
        phoneFeild.sendKeys("521000400");
        pwFeild.sendKeys("Pass@test123");
        loginButton.click();
        String currentUrl = driver.getCurrentUrl();
        Assert.assertEquals(currentUrl, "https://next.aqar.fm/");
}
}
