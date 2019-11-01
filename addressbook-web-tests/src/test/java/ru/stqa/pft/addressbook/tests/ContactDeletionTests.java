package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

import java.util.List;

public class ContactDeletionTests extends TestBase {

    @BeforeMethod
    public void ensurePreconditions() {
        if (app.contact().list().size() == 0) {
            app.contact().create(new ContactData().withFirstName("test1").withLastName("test2").withMobile("123456789").withEmail("test@test.com").withGroup("test1"), true);
            app.contact().promptGoToHomePage();
        }
    }

    @Test
    public void testContactDeletion() throws InterruptedException {
        List<ContactData> before = app.contact().list();
        int index = before.size() - 1;
        app.contact().delete(index);
        List<ContactData> after = app.contact().list();
        Assert.assertEquals(after.size(), before.size() - 1);

        before.remove(index);
        Assert.assertEquals(before, after);
    }

}



