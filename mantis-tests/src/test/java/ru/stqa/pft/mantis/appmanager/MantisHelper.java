package ru.stqa.pft.mantis.appmanager;

import org.openqa.selenium.By;

public class MantisHelper extends HelperBase {
    public MantisHelper(ApplicationManager app) {
        super(app);
        wd = app.getDriver();
    }

    public void resetUserPassword(String username) {
        click(By.linkText("Manage Users"));
        click(By.linkText(username));
        click(By.cssSelector("input[value='Reset Password'"));
    }

    public void logIn(String username, String password) {
        wd.get(app.getProperty("web.baseUrl") + "/login_page.php");
        type(By.name("username"), username);
        type(By.name("password"), password);
        click(By.cssSelector("input[value='Login']"));
    }

    public void setNewPassword(String url, String password) {
        wd.get(url);
        type(By.name("password"), password);
        type(By.name("password_confirm"), password);
        click(By.cssSelector("input[value='Update User']"));
    }
}