package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

public class ContactCreationTests extends TestBase {

    @Test
    public void testContactCreation() throws Exception {
        app.getNavigationHelper().goToAddNewContact();
        app.getContactHelper().fillContactForm(new ContactData("test1", "test2", "123456789", "test@test.com", "test1"), true);
        app.getContactHelper().submitNewContact();
    }

}
