package locators;

import org.openqa.selenium.By;

public class LoginLocators {
    //Login Page element locators
    public static final By PASSWORD_FIELD_LOCATOR = By.xpath("//*[@id='__next']/main/div/div[2]/div/div/div/div/div[5]/div[1]/input");
    public static final By PHONE_FIELD_LOCATOR = By.xpath("//*[@id='__next']/main/div/div[2]/div/div/div/div/div[3]/div[1]/input");
    public static final By LOGIN_BUTTON_LOCATOR = By.xpath("//*[@id='__next']/main/div/div[2]/div/div/div/div/button");
    public static final By ERROR_MESSAGE_PASSWORD_LOCATOR = By.xpath("//*[contains(text(),'كلمة المرور غير صحيحة')]");
    public static final By ERROR_MESSAGE_PHONE_LOCATOR = By.xpath("//*[contains(text(),'مطلوب رقم جوال سعودي')]");
    public static final By ERROR_MESSAGE_EMPTY_PASSWORD_LOCATOR = By.xpath("//*[contains(text(),'مطلوب كلمة المرور')]");
    public static final By PASSWORD_HIDE_ICON_LOCATOR = By.xpath("//*[@id='__next']/main/div/div[2]/div/div/div/div/div[5]/div[1]/button");
    public static final By PROFILE_ICON_LOCATOR = By.xpath("//*[@id='__next']/main/div/div[1]/div[2]/button/span/img");
}