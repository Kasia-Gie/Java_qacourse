package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;

import java.util.Arrays;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;


public class ContactDetailedInfo extends TestBase {

    @BeforeMethod
    public void ensurePreconditions() {
        if (app.contact().all().size() == 0) {
            app.contact().create(new ContactData().withFirstName("test1").withLastName("test2")
                    .withHomePhone("111").withMobilePhone("222").withWorkPhone("333")
                    .withEmail("test@test.com").withEmail2("test2@test.com").withEmail3("test3@test.com").withGroup("test1").withAddress("test1"), true);
            app.contact().promptGoToHomePage();
        }
    }

    @Test
    public void testContactDetailedInfo() {
        app.goTo().homePage();
        Contacts before = app.contact().all();
        ContactData contact = before.iterator().next();
        ContactData contactInfoFromContent = app.contact().infoFromContent(contact);
        ContactData contactInfoFromEditForm = app.contact().infoFromEditForm(contact);
        assertThat(cleaned(contactInfoFromContent.getAllData()), equalTo((merged(contactInfoFromEditForm))));
    }

    public String merged(ContactData contact) {
        return Arrays.asList(contact.getFirstName(), contact.getLastName(), contact.getHomePhone(), contact.getMobilePhone(), contact.getWorkPhone(),
                contact.getEmail(), contact.getEmail2(), contact.getEmail3(), contact.getAddress())
                .stream()
                .map(ContactDetailedInfo::cleaned)
                .collect(Collectors.joining(""));
    }

    public static String cleaned(String contactData) {
        return contactData
                .replaceAll("H:", "").replaceAll("M:", "").replaceAll("W:", "")
                .replaceAll("Member of:(.*)", "").replaceAll("[()]", "")
                .replaceAll("\n", "").replaceAll("\\s", "");
    }
}
