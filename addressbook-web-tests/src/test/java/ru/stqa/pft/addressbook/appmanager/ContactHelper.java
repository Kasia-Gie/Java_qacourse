package ru.stqa.pft.addressbook.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import ru.stqa.pft.addressbook.model.ContactData;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;

public class ContactHelper extends HelperBase {

    public ContactHelper(WebDriver wd) {
        super(wd);
    }

    public void fillContactForm(ContactData newContact, boolean creation) {
        type(By.name("firstname"), newContact.getFirstName());
        type(By.name("lastname"), newContact.getLastName());
        type(By.name("mobile"), newContact.getMobile());
        type(By.name("email"), newContact.getEmail());

        if (creation) {
            new Select(wd.findElement(By.name("new_group"))).selectByVisibleText(ContactData.getGroup());
        } else {
            Assert.assertFalse(isElementPresent(By.name("new_group")));
        }
    }

    public void selectContact(int index) {
        wd.findElements(By.name("selected[]")).get(index).click();
    }

    public void goToAddNewContact() {
        click(By.linkText("add new"));
    }

    public void editContact() {

        click(By.xpath("//*[@id=\"maintable\"]/tbody/tr[2]/td[8]/a/img"));
    }

    public void submitContactModification() {

        click(By.xpath("//*[@id=\"content\"]/form[1]/input[22]"));
    }

    public void submitNewContact() {

        wd.findElement(By.name("submit")).click();
    }

    public void deleteSelectedContact() {

        click(By.xpath("//*[@id=\"content\"]/form[2]/div[2]/input"));
    }

    public void acceptContactDeletion() {

        wd.switchTo().alert().accept();
    }

    public boolean isThereAContact() {
        return isElementPresent(By.xpath("//*[@id=\"maintable\"]/tbody/tr[2]/td[8]/a/img"));
    }

    public void createContact(ContactData contact, boolean creation) {
        goToAddNewContact();
        fillContactForm(contact, creation);
        submitNewContact();
    }

    public int getContactCount() {
        return wd.findElements(By.name("selected[]")).size();
    }

    public List<ContactData> getContactList() {
        WebElement mainTable = wd.findElement(By.id("maintable"));
        List<ContactData> contacts = new ArrayList<ContactData>();
        List<WebElement> rows = mainTable.findElements(By.tagName("tr"));

        for (int i = 1; i < rows.size(); i++) {
            List<WebElement> cells = rows.get(i).findElements(By.tagName("td"));
            String firstName = cells.get(2).getText();
            String lastName = cells.get(1).getText();
            String id = rows.get(i).findElement(By.tagName("input")).getAttribute("value");
            ContactData contact = new ContactData(id, firstName, lastName, null, null, null);
            contacts.add(contact);
        }

        return contacts;
    }
}