package ru.stqa.pft.addressbook.appmanager;


import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
//import org.hibernate.sql.Select;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;
import ru.stqa.pft.addressbook.model.GroupData;

import java.util.List;

public class ContactHelper extends HelperBase {

    public ContactHelper(WebDriver wd) {
        super(wd);
    }

    public void fillContactForm(ContactData contactData, boolean creation) {
        type(By.name("firstname"), contactData.getFirstName());
        type(By.name("lastname"), contactData.getLastName());
        type(By.name("address"), contactData.getAddress());
        type(By.name("home"), contactData.getHomePhone());
        type(By.name("work"), contactData.getWorkPhone());
        type(By.name("mobile"), contactData.getMobilePhone());
        type(By.name("email"), contactData.getEmail());
        type(By.name("email2"), contactData.getEmail2());
        type(By.name("email3"), contactData.getEmail3());

        if (creation) {
            if (contactData.getGroups().size() > 0)  {
                Assert.assertTrue(contactData.getGroups().size() == 1);
                new Select(wd.findElement(By.name("new_group"))).selectByVisibleText(contactData.getGroups().iterator().next().getName());
            }
        } else {
            Assert.assertFalse(isElementPresent(By.name("new_group")));
        }
    }

    private void selectContactById(int id) {
        wd.findElement(By.cssSelector("input[value='" + id + "']")).click();
    }

    public void goToAddNewContact() {
        click(By.linkText("add new"));
    }

    public void editContactById(int id) {
        wd.findElement(By.xpath("//a[contains(@href, 'edit.php?id=" + id + "')]")).click();
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

    public void promptGoToHomePage() {
        click(By.linkText("home page"));
    }

    public void homePage() {
        if (isElementPresent(By.id("maintable"))) {
            return;
        }
        click(By.linkText("home"));
    }

    public boolean isThereAContact() {
        return isElementPresent(By.xpath("//*[@id=\"maintable\"]/tbody/tr[2]/td[8]/a/img"));
    }

    public int count() {
        return wd.findElements(By.name("selected[]")).size();
    }

    public void create(ContactData contact) {
        goToAddNewContact();
        fillContactForm(contact, true);
        submitNewContact();
        contactCache = null;
        promptGoToHomePage();
    }

    public void modify(ContactData contact) {
        editContactById(contact.getId());
        fillContactForm(contact, false);
        submitContactModification();
        contactCache = null;
        promptGoToHomePage();
    }

    public void delete(ContactData contact) {
        selectContactById(contact.getId());
        deleteSelectedContact();
        acceptContactDeletion();
        contactCache = null;
        homePage();
    }

    private Contacts contactCache = null;

    public Contacts all() {
        if (contactCache != null) {
            return new Contacts(contactCache);
        }
        WebElement mainTable = wd.findElement(By.id("maintable"));
        contactCache = new Contacts();
        List<WebElement> rows = mainTable.findElements(By.tagName("tr"));

        for (int i = 1; i < rows.size(); i++) {
            List<WebElement> cells = rows.get(i).findElements(By.tagName("td"));
            String firstName = cells.get(2).getText();
            String lastName = cells.get(1).getText();
            String allPhones = cells.get(5).getText();
            String allEmails = cells.get(4).getText();
            String address = cells.get(3).getText();
            int id = Integer.parseInt(rows.get(i).findElement(By.tagName("input")).getAttribute("value"));
            contactCache.add(new ContactData().withId(id).withFirstName(firstName).withLastName(lastName)
                    .withAllPhones(allPhones).withAllEmails(allEmails).withAddress(address));
        }
        return new Contacts(contactCache);
    }

    public ContactData infoFromEditForm(ContactData contact) {
        initContactModificationById(contact.getId());
        String firstName = wd.findElement(By.name("firstname")).getAttribute("value");
        String lastName = wd.findElement(By.name("lastname")).getAttribute("value");
        String home = wd.findElement(By.name("home")).getAttribute("value");
        String mobile = wd.findElement(By.name("mobile")).getAttribute("value");
        String work = wd.findElement(By.name("work")).getAttribute("value");
        String email = wd.findElement(By.name("email")).getAttribute("value");
        String email2 = wd.findElement(By.name("email2")).getAttribute("value");
        String email3 = wd.findElement(By.name("email3")).getAttribute("value");
        String address = wd.findElement(By.name("address")).getAttribute("value");
        wd.navigate().back();
        return new ContactData().withId(contact.getId()).withFirstName(firstName).withLastName(lastName).
                withHomePhone(home).withMobilePhone(mobile).withWorkPhone(work)
                .withEmail(email).withEmail2(email2).withEmail3(email3).withAddress(address);
    }

    public ContactData infoFromContent(ContactData contact) {
        openContactContentById(contact.getId());
        String allData = wd.findElement(By.id("content")).getText();
        wd.navigate().back();
        return new ContactData().withAllData(allData);
    }

    private void openContactContentById(int id) {
        wd.findElement(By.cssSelector(String.format("a[href= 'view.php?id=%s']", id))).click();
    }

    private void initContactModificationById(int id) {
        wd.findElement(By.cssSelector(String.format("a[href='edit.php?id=%s']", id))).click();
    }

    public void addToGroup(ContactData contact, GroupData group) {
        selectContactById(contact.getId());
        selectGroupForAdding(group.getName());
        click(By.name("add"));
        click(By.xpath(".//*[@id='content']/div/i/a"));
    }

    public void removeFromGroup(ContactData contact, GroupData group) {
        selectGroupForRemoval(group.getName());
        selectContactById(contact.getId());
        click(By.name("remove"));
        click(By.xpath(".//*[@id='content']/div/i/a"));
    }

    private void selectGroupForAdding(String groupName) {
        new Select(wd.findElement(By.name("to_group"))).selectByVisibleText(groupName);

    }

    private void selectGroupForRemoval(String groupName) {
        new Select(wd.findElement(By.name("group"))).selectByVisibleText(groupName);

    }
}