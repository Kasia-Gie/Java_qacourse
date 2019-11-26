package ru.stqa.pft.mantis.tests;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.mantis.appmanager.HttpSession;
import ru.stqa.pft.mantis.model.MailMessage;
import ru.stqa.pft.mantis.model.UserData;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.List;

import static org.testng.Assert.assertTrue;

public class ResetPasswordTests extends TestBase {

    @BeforeMethod
    public void startMailServer() {
        app.mail().start();
    }

    @Test
    public void testResetPassword() throws IOException, MessagingException {
        String adminUser = app.getProperty(("web.adminLogin"));
        String adminPass = app.getProperty("web.adminPassword");

        UserData user = app.db().users().iterator().next();
        String username = user.getUsername();
        String newPassword = "newPassword";
        String email = user.getEmail();

        app.mantis().logIn(adminUser, adminPass);
        app.mantis().resetUserPassword(username);

        List<MailMessage> mailMessages = app.mail().waitForMail(1, 10000);
        String resetPassLink = app.mail().GetURL(mailMessages, email);

        app.mantis().setNewPassword(resetPassLink, newPassword);

        HttpSession session = app.newSession();
        assertTrue(session.login(username, newPassword));
        assertTrue(session.isLoggedInAs(username));
    }

    @AfterMethod(alwaysRun = true)
    public void stopMailServer() {
        app.mail().stop();
    }
}