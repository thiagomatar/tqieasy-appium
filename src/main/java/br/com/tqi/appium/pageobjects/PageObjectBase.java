package br.com.tqi.appium.pageobjects;

import io.appium.java_client.AppiumDriver;

public abstract class PageObjectBase {
    public static final String ERROR_WHILE_FETCH_ELEMENT = "Error while fetch element";

    private final AppiumDriver driver;

    protected PageObjectBase(AppiumDriver driver) {
        this.driver = driver;
    }

    public abstract void load();

}
