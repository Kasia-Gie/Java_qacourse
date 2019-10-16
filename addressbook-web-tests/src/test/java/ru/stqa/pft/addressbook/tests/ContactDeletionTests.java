package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;

public class ContactDeletionTests extends TestBase {
    @Test
    public void testContactDeletion() throws InterruptedException {
        app.getGroupHelper().selectContact();
        app.getGroupHelper().deleteSelectedContact();
        app.getGroupHelper().acceptDeletion();

        Thread.sleep(3000);
    }
}



