package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;

import java.util.Arrays;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactAddressTests extends TestBase {

    @BeforeMethod
    public void ensurePreconditions() {
        if (app.contact().all().size() == 0) {
            app.contact().create(new ContactData().withFirstName("test1").withLastName("test2")
                    .withHomePhone("111").withMobilePhone("222").withWorkPhone("333")
                    .withEmail("test@test.com").withEmail2("test2@test.com").withEmail3("test3@test.com").withAddress("test1"));
            app.contact().promptGoToHomePage();
        }
    }

    @Test
    public void addressTest(){
        app.goTo().homePage();
        Contacts before = app.contact().all();
        ContactData contact = before.iterator().next();
        ContactData addressInfoFromEditForm = app.contact().infoFromEditForm(contact);

        assertThat(contact.getAddress(), equalTo(mergeAddress(addressInfoFromEditForm)));
    }

    private String mergeAddress(ContactData contact) {
        return Arrays.asList(contact.getAddress())
                .stream()
                .collect(Collectors.joining(""));
    }

    public static String cleaned(String phone) {
        return phone.replaceAll("\\s", "").replaceAll("[-()]", "");
    }
}