package br.com.tqi.appium.pageobjects;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.NoSuchElementException;

public class LogoutPageObject extends PageObjectBase{

    private final AppiumDriver driver;
    private final WebDriverWait wait;

    private static final By NAVIGATION_PROFILE = By.id("br.com.tqi.ponto:id/navigation_profile");
    private static final By EXIT = By.id("br.com.tqi.ponto:id/tvMenuName");
    private List<MobileElement> exit;
    private MobileElement profile;


    public LogoutPageObject(AppiumDriver driver) {
        super(driver);
        this.driver = driver;
        this.wait = new WebDriverWait(driver, 10);
    }

    @Override
    public void load() {
        wait.until(ExpectedConditions.presenceOfElementLocated(NAVIGATION_PROFILE));
        profile = (MobileElement) driver.findElement(NAVIGATION_PROFILE);
        profile.click();
    }

    public void execute(){
        this.load();
        wait.until(ExpectedConditions.presenceOfElementLocated(EXIT));
        exit = (List<MobileElement>) driver.findElements(EXIT);
        MobileElement exit = this.exit.stream().filter(f -> f.getText().equals("Sair")).findFirst()
                .orElseThrow(() -> new NoSuchElementException(ERROR_WHILE_FETCH_ELEMENT));
        exit.click();
    }
}
