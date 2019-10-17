package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.NewContact;

public class ContactModificationTests extends TestBase {
    @Test
    public void testContactModification() {
        app.getContactHelper().selectContact();
        app.getContactHelper().editContact();
        app.getNavigationHelper().fillNewContactData(new NewContact("test1", "test2", "123456789", "test@test.com"));
        app.getContactHelper().submitContactModification();
    }
}
