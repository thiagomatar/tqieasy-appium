package br.com.tqi.appium;


import br.com.tqi.appium.config.AppiumDriverConfig;
import br.com.tqi.appium.pageobjects.LoginPageObject;
import br.com.tqi.appium.pageobjects.LogoutPageObject;
import br.com.tqi.appium.pageobjects.TaskPageObject;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.remote.MobilePlatform;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;


public class FeatureTQIEasy {

    @Test
    public void teste() throws NoSuchElementException, InterruptedException {
        AppiumDriver driver = AppiumDriverConfig.Instance().driver;

        LoginPageObject loginPageObject = new LoginPageObject(driver);
        loginPageObject.execute("coloque_o_email_aqui", "senha");

        TaskPageObject taskPageObject = new TaskPageObject(driver);
        taskPageObject.execute();

        LogoutPageObject logoutPageObject = new LogoutPageObject(driver);
        logoutPageObject.execute();

        driver.quit();
    }

}