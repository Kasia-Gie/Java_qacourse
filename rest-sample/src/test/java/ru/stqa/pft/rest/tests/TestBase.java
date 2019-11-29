package ru.stqa.pft.rest.tests;

import org.testng.SkipException;
import ru.stqa.pft.rest.appmanager.ApplicationManager;
import ru.stqa.pft.rest.model.Issue;

public class TestBase {

    protected static final ApplicationManager app = new ApplicationManager();

    public boolean isIssueOpen(int issueId) {
        Issue issue = app.rest().getIssue(issueId);
        String status = issue.getState_name();
        return !status.equals("Closed");
    }

    public void skipIfNotFixed(int issueId) {
        if (isIssueOpen(issueId)) {
            throw new SkipException("Ignored because of issue: " + issueId);
        }
    }
}