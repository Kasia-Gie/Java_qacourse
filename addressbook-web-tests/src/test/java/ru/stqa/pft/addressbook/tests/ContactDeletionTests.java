package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactDeletionTests extends TestBase {

    @BeforeMethod
    public void ensurePreconditions() {
        if (app.contact().all().size() == 0) {
            app.contact().create(new ContactData().withFirstName("test1").withLastName("test2").withEmail("test@test.com")
                    .withHomePhone("111").withMobilePhone("222").withWorkPhone("333").withEmail("test@test.com").withGroup("test1"), true);
            app.contact().promptGoToHomePage();
        }
    }

    @Test
    public void testContactDeletion() throws InterruptedException {
        Contacts before = app.contact().all();
        ContactData deletedContact = before.iterator().next();
        app.contact().delete(deletedContact);
        assertThat(app.contact().count(), equalTo((before.size() - 1)));
        Contacts after = app.contact().all();
        assertThat(after, equalTo(before.without(deletedContact)));
    }

}



