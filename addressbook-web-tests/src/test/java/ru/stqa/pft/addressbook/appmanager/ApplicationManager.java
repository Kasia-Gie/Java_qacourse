package ru.stqa.pft.addressbook.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.util.concurrent.TimeUnit;

public class ApplicationManager {

    private final GroupHelper groupHelper = new GroupHelper();

    public void init() {
        groupHelper.wd = new FirefoxDriver();
        groupHelper.wd.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        groupHelper.wd.get("http://localhost:8090/addressbook");
        login("admin", "secret");
    }

    private void login(String username, String password) {
        groupHelper.wd.findElement(By.name("user")).click();
        groupHelper.wd.findElement(By.name("user")).clear();
        groupHelper.wd.findElement(By.name("user")).sendKeys(username);
        groupHelper.wd.findElement(By.name("pass")).click();
        groupHelper.wd.findElement(By.name("pass")).clear();
        groupHelper.wd.findElement(By.name("pass")).sendKeys(password);
        groupHelper.wd.findElement(By.xpath("//input[@value='Login']")).click();
    }

    public void logout() {
        groupHelper.wd.findElement(By.linkText("Logout")).click();
    }

    public void goToGroupPage() {
        groupHelper.wd.findElement(By.linkText("groups")).click();
    }

    public void stop() {
        groupHelper.wd.quit();
    }

    private boolean isElementPresent(By by) {
        try {
            groupHelper.wd.findElement(by);
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    private boolean isAlertPresent() {
        try {
            groupHelper.wd.switchTo().alert();
            return true;
        } catch (NoAlertPresentException e) {
            return false;
        }
    }

    public GroupHelper getGroupHelper() {
        return groupHelper;
    }
}
