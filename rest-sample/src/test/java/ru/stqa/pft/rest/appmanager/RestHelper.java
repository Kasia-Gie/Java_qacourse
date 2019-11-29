package ru.stqa.pft.rest.appmanager;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import io.restassured.RestAssured;
import ru.stqa.pft.rest.model.Issue;

import java.io.IOException;
import java.util.Set;

public class RestHelper {
    private final String URL = "http://demo.bugify.com/api";
    ApplicationManager app;

    public RestHelper(ApplicationManager app) {
        this.app = app;
        RestAssured.authentication = RestAssured.basic("384810e2a2c89ef4be5e19823af9bb25", "");
    }

    public int createIssue(Issue newIssue) throws IOException {
        String json = RestAssured.given()
                .param("subject", newIssue.getSubject())
                .param("description", newIssue.getDescription())
                .post(URL + "/issues.json").asString();

        JsonElement parsed = new JsonParser().parse(json);
        int issue_id = parsed.getAsJsonObject().get("issue_id").getAsInt();

        return issue_id;
    }

    public Set<Issue> getIssues() throws IOException {
        String json = RestAssured.get(URL + "/issues.json").asString();

        JsonElement parsed = new JsonParser().parse(json);
        JsonElement issues = parsed.getAsJsonObject().get("issues");

        return new Gson().fromJson(issues, new TypeToken<Set<Issue>>() {
        }.getType());
    }

    public Issue getIssue(int issueId) {
        String json = RestAssured.get(URL + String.format("/issues/%s.json", issueId)).asString();

        JsonElement parsed = new JsonParser().parse(json);
        JsonElement issue = parsed.getAsJsonObject().get("issues").getAsJsonArray().get(0);

        Gson gson = new Gson();
        return new Issue().
                withId(gson.fromJson(issue, Issue.class).getId())
                .withStatus(gson.fromJson(issue, Issue.class).getState_name());
    }
}