package ru.stqa.pft.rest.appmanager;


public class ApplicationManager {
    private RestHelper restHelper;

    public RestHelper rest() {
        if (restHelper == null) {
            restHelper = new RestHelper(this);
        }
        return restHelper;
    }
}