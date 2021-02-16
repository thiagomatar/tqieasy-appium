package br.com.tqi.appium.pageobjects;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LoginPageObject extends PageObjectBase{

    private final AppiumDriver driver;
    private final WebDriverWait wait;

    private static final By USERNAME_ID = By.id("br.com.tqi.ponto:id/etDocument");
    private static final By PASSWORD_ID = By.id("br.com.tqi.ponto:id/etpassword");
    private static final By LOGIN_ID = By.id("br.com.tqi.ponto:id/btLogin");
    private static final By FINGERPRINT_ID = By.id("br.com.tqi.ponto:id/dlAlertBtNo");

    private MobileElement username;
    private MobileElement password;
    private MobileElement login;
    private MobileElement fingerprint;


    public LoginPageObject(AppiumDriver driver) {
        super(driver);
        this.driver = driver;
        this.wait = new WebDriverWait(driver, 10);
    }

    @Override
    public void load() {
        wait.until(ExpectedConditions.presenceOfElementLocated(USERNAME_ID));
        this.username = (MobileElement) driver.findElement(USERNAME_ID);
        this.password = (MobileElement) driver.findElement(PASSWORD_ID);
        this.login = (MobileElement) driver.findElement(LOGIN_ID);
    }

    public void execute(String email, String password){
        load();
        wait.until(ExpectedConditions.presenceOfElementLocated(USERNAME_ID));
        this.username.setValue(email);
        this.password.setValue(password);
        this.login.click();
        wait.until(ExpectedConditions.presenceOfElementLocated(FINGERPRINT_ID));
        this.fingerprint = (MobileElement) driver.findElement(FINGERPRINT_ID);
        this.fingerprint.click();
    }

}
