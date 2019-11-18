package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;

import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsCollectionContaining.hasItem;

public class ContactAndGroupTests extends TestBase {
    private ContactData directContact;
    private GroupData directGroup;

    @BeforeClass
    private void ensurePreconditions() {
        Contacts contacts = app.db().contacts();
        Groups groups = app.db().groups();

        for (ContactData contact : contacts) {
            for (GroupData group : groups) {
                if (groups.stream()
                        .filter(g -> g.getName().equals(group.getName()))
                        .collect(Collectors.toList()).size() > 1) {
                    continue;
                }
                if (!contact.getGroups().contains(group)) {
                    directContact = contact;
                    directGroup = group;
                    return;
                }
            }
        }
        directContact = new ContactData().withFirstName("firstname").withLastName("lastname");
        directGroup = new GroupData().withName(String.format("Test Group (%s)", System.currentTimeMillis()).replaceAll("-", ""));

        if (app.db().contacts().size() == 0) {
            app.contact().homePage();
            app.contact().goToAddNewContact();
            app.contact().create(directContact);
            app.goTo().groupPage();
            app.group().create(directGroup);

            contacts = app.db().contacts();
            groups = app.db().groups();

            directContact.withId(contacts.stream().mapToInt((c) -> c.getId()).max().getAsInt());
            directGroup.withId(groups.stream().mapToInt((g) -> g.getId()).max().getAsInt());
        }
    }

    @Test
    public void ContactAddedToGroup() {
        if (app.db().contacts().size() == 0) {
            app.contact().homePage();
            Groups before = directContact.getGroups();
            app.contact().addToGroup(directContact, directGroup);

            ContactData contactFromDb = app.db().getContact(directContact.getId());
            assertThat(contactFromDb.getGroups(), hasItem(directGroup));
            assertThat(contactFromDb.getGroups(), equalTo(before.withAdded(directGroup)));

        }
    }

    @Test(dependsOnMethods = "ContactAddedToGroup")
    public void ContactRemovedFromGroup() {
        if (app.db().contacts().size() == 0) {
            app.contact().homePage();
            Groups before = directContact.getGroups();
            app.contact().removeFromGroup(directContact, directGroup);

            ContactData contactFromDb = app.db().getContact(directContact.getId());
            assertThat(contactFromDb.getGroups(), not(hasItem(directGroup)));
            assertThat(contactFromDb.getGroups(), equalTo(before.without(directGroup)));
        }
    }
}
