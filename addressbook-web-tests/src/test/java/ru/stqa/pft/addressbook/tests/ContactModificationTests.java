package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

import java.util.Comparator;
import java.util.List;

public class ContactModificationTests extends TestBase {

    @BeforeMethod
    public void ensurePreconditions() {
        if (app.contact().list().size() == 0) {
            app.contact().create(new ContactData().withFirstName("test1").withLastName("test2").withMobile("123456789").withEmail("test@test.com").withGroup("test1"), true);
            app.contact().promptGoToHomePage();
        }
    }

    @Test
    public void testContactModification() throws InterruptedException {
        List<ContactData> before = app.contact().list();
        int index = before.size() - 1;
        ContactData contact = new ContactData()
        .withId(before.get(index).getId()).withFirstName("test1").withLastName("test2").withMobile("123456789").withEmail("test@test.com");
        app.contact().modify(before, contact);
        List<ContactData> after = app.contact().list();
        Assert.assertEquals(after.size(), before.size());

        before.remove(index);
        before.add(contact);
        Comparator<? super ContactData> byId = (c1, c2) -> Integer.compare(c1.getId(), c2.getId());
        before.sort(byId);
        after.sort(byId);
        Assert.assertEquals(before, after);
    }
}
