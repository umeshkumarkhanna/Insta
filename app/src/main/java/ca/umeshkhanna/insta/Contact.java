package ca.umeshkhanna.insta;

import java.io.Serializable;

public class Contact implements Serializable {

    String name;
    boolean checked = false;
    String phoneNumber;

    public Contact(String name, String phoneNumber) {
        this.name = name;
        this.phoneNumber = phoneNumber;
    }

    public Contact(String name, String phoneNumber, boolean checked) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.checked = checked;
    }

    public String getName() {
        return name;
    }

    public String getNumber() {
        return phoneNumber;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public String toString() {
        return name;
    }

    public void toggleChecked() {
        checked = !checked;
    }

}

