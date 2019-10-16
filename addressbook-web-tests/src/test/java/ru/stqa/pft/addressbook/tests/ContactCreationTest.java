package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.NewContact;

public class ContactCreationTest extends TestBase {

    @Test
    public void testContactCreation() throws Exception {
        app.getNavigationHelper().goToAddNewContact();
        app.getNavigationHelper().fillNewContactData(new NewContact("test1", "test2", "123456789", "test@test.com"));
        app.getNavigationHelper().submitNewContact();
    }

}
