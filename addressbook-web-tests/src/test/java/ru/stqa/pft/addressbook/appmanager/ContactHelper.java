package ru.stqa.pft.addressbook.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import ru.stqa.pft.addressbook.model.ContactData;

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

    public void selectContact() {

        click(By.name("selected[]"));
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
}
