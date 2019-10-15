package ru.stqa.pft.addressbook.model;

public class NewContact {
    private final String firstName;
    private final String lastName;
    private final String mobile;
    private final String email;

    public NewContact(String firstName, String lastName, String mobile, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.mobile = mobile;
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getMobile() {
        return mobile;
    }

    public String getEmail() {
        return email;
    }
}
