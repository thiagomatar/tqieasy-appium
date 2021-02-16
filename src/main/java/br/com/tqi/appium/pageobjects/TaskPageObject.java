package br.com.tqi.appium.pageobjects;

import br.com.tqi.appium.util.ScreenUtil;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class TaskPageObject extends PageObjectBase {

    private final AppiumDriver driver;
    private final WebDriverWait wait;
    private final DateTimeFormatter defaultDateFormatter;

    private static final By HOME_GRID_ID = By.id("br.com.tqi.ponto:id/tv_home_button");
    private static final By HISTORY_GRID_ID = By.id("br.com.tqi.ponto:id/tvDate");
    private static final By TASK_DATE_ID = By.id("br.com.tqi.ponto:id/etActivityDate");
    private static final By TASK_WORKED_HOURS = By.id("br.com.tqi.ponto:id/etWorkedHours");
    private static final By TASK_DESCRIPTION = By.id("br.com.tqi.ponto:id/etDescription");
    private static final By TASK_SAVE_BUTTON = By.id("br.com.tqi.ponto:id/btSave");



    private MobileElement taskDate;
    private MobileElement taskWorkedHours;
    private MobileElement taskDescription;
    private MobileElement taskButton;

    private LocalDate lastDate;
    private long diffBetweenDates;


    public TaskPageObject(AppiumDriver driver) {
        super(driver);
        this.driver = driver;
        this.wait = new WebDriverWait(driver, 10);
        this.defaultDateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    }


    @Override
    public void load() {
        wait.until(ExpectedConditions.presenceOfElementLocated(HOME_GRID_ID));
        MobileElement history = findHistory(findHomeGrid());
        history.click();
        wait.until(ExpectedConditions.presenceOfElementLocated(HISTORY_GRID_ID));
        String date = verifyLastDateRegistered();
        calculateDatesToPerformProcess(date);
        driver.navigate().back();
        wait.until(ExpectedConditions.presenceOfElementLocated(HOME_GRID_ID));
        MobileElement tasks = findTasks(findHomeGrid());
        tasks.click();
    }

    public void execute() {
        this.load();
        wait.until(ExpectedConditions.presenceOfElementLocated(TASK_DATE_ID));
        this.loadTaskComponents();
        this.registerTask();
        this.driver.navigate().back();
    }

    private MobileElement findHistory(List<MobileElement> homeGrid) {
        return homeGrid.stream().filter(f -> f.getText().equals("Histórico")).findFirst()
                .orElseThrow(() -> new NoSuchElementException(ERROR_WHILE_FETCH_ELEMENT));
    }

    private MobileElement findTasks(List<MobileElement> homeGrid) {
        return homeGrid.stream().filter(f -> f.getText().equals("Lançar\natividade")).findFirst()
                .orElseThrow(() -> new NoSuchElementException(ERROR_WHILE_FETCH_ELEMENT));
    }

    private List<MobileElement> findHomeGrid() {
        return (List<MobileElement>) driver.findElements(HOME_GRID_ID);
    }

    private String verifyLastDateRegistered() {
        ScreenUtil screen = new ScreenUtil(driver);
        screen.swipe(ScreenUtil.Direction.UP);
        screen.swipe(ScreenUtil.Direction.UP);
        List<MobileElement> record = (List<MobileElement>) driver.findElements(HISTORY_GRID_ID);
        return record.stream().map(MobileElement::getText).reduce((first, last) -> last)
                .orElseThrow(() -> new NoSuchElementException(ERROR_WHILE_FETCH_ELEMENT));
    }

    private void calculateDatesToPerformProcess(String date) {
        lastDate = LocalDate.parse(date, this.defaultDateFormatter);
        LocalDate today = LocalDate.now();
        diffBetweenDates = ChronoUnit.DAYS.between(lastDate, today);
    }


    private void loadTaskComponents() {
        taskDate = (MobileElement) driver.findElement(TASK_DATE_ID);
        taskWorkedHours = (MobileElement) driver.findElement(TASK_WORKED_HOURS);
        taskDescription = (MobileElement) driver.findElement(TASK_DESCRIPTION);
        taskButton = (MobileElement) driver.findElement(TASK_SAVE_BUTTON);
    }

    private void registerTask() {
        for (int i = 0; i < diffBetweenDates; i++) {
            if (lastDate.getDayOfWeek() == DayOfWeek.SATURDAY || lastDate.getDayOfWeek() == DayOfWeek.SUNDAY) {
                diffBetweenDates++;
                lastDate = lastDate.plusDays(1);
            } else {
                this.taskDate.setValue(defaultDateFormatter.format(lastDate.plusDays(1)));
                this.taskWorkedHours.setValue("8:00");
                this.taskDescription.setValue("Análise e desenvolvimento de sistemas");
                this.taskButton.click();
            }
        }
    }
}
