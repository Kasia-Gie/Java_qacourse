package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;
import ru.stqa.pft.addressbook.model.Groups;

import java.io.File;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactModificationTests extends TestBase {


    @BeforeMethod
    public void ensurePreconditions() {
        if (app.db().contacts().size() == 0) {
            Contacts before = app.db().contacts();
            Groups groups = app.db().groups();
            app.contact().homePage();
            app.contact().goToAddNewContact();
            app.contact().create(new ContactData().withFirstName("test1").withLastName("test2")
                    .withHomePhone("111").withMobilePhone("222").withWorkPhone("333")
                    .withEmail("test@test.com").withEmail2("test2@test.com").withEmail3("test3@test.com").withAddress("test1").inGroup(groups.iterator().next()));
            app.contact().promptGoToHomePage();
        }
    }

    @Test
    public void testContactModification() {
        Contacts before = app.db().contacts();
        Groups groups = app.db().groups();
        ContactData modifiedContact = before.iterator().next();
        app.contact().homePage();
        ContactData contactData = new ContactData()
                .withId(modifiedContact.getId()).withFirstName("test1").withLastName("test2")
                .withHomePhone("111").withMobilePhone("222").withWorkPhone("333")
                .withEmail("test@test.com").withEmail2("test2@test.com").withEmail3("test3@test.com").withAddress("test1").inGroup(groups.iterator().next());
        app.contact().modify(contactData);
        app.contact().homePage();
        assertThat(app.contact().count(), equalTo((before.size())));
        Contacts after = app.db().contacts();
        assertThat(after, equalTo(before.without(modifiedContact).withAdded(contactData)));
        verifyContactListInUI();
    }
}
