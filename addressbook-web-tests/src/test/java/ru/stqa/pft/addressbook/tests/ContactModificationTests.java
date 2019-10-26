package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

public class ContactModificationTests extends TestBase {
    @Test
    public void testContactModification() throws InterruptedException {
        int before = app.getContactHelper().getContactCount();
        if (!app.getContactHelper().isThereAContact()) {
            app.getContactHelper().createContact(new ContactData("test1", "test2", "123456789", "test@test.com", "test1"), true);
            app.getNavigationHelper().promptGoToHomePage();
        }
        app.getContactHelper().selectContact(0);
        app.getContactHelper().editContact();
        app.getContactHelper().fillContactForm(new ContactData("test1", "test2", "123456789", "test@test.com", null), false);
        app.getContactHelper().submitContactModification();
        app.getNavigationHelper().promptGoToHomePage();
        int after = app.getContactHelper().getContactCount();
        Assert.assertEquals(after, before);
    }
}
