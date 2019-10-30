package ru.stqa.pft.addressbook.model;

import java.util.Objects;

public class ContactData {
    private int id;
    private final String firstName;
    private final String lastName;
    private final String mobile;
    private final String email;
    private static String group;

    public ContactData(String firstName, String lastName, String mobile, String email, String group) {
        this.id = 0;
        this.firstName = firstName;
        this.lastName = lastName;
        this.mobile = mobile;
        this.email = email;
        this.group = group;
    }

    public ContactData(int id, String firstName, String lastName, String mobile, String email, String group) {
            this.id = id;
            this.firstName = firstName;
            this.lastName = lastName;
            this.mobile = mobile;
            this.email = email;
            this.group = group;
        }

        public int getId () {
            return id;
        }

        public String getFirstName () {

            return firstName;
        }

        public String getLastName () {

            return lastName;
        }

        public String getMobile () {

            return mobile;
        }

        public String getEmail () {
            return email;
        }

        public static String getGroup () {

            return group;
        }

    public void setId(int id) {
        this.id = id;
    }

    @Override
        public String toString () {
            return "ContactData{" +
                    "id='" + id + '\'' +
                    ", firstName='" + firstName + '\'' +
                    ", lastName='" + lastName + '\'' +
                    '}';
        }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ContactData that = (ContactData) o;
        return id == that.id &&
                Objects.equals(firstName, that.firstName) &&
                Objects.equals(lastName, that.lastName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName);
    }
}
