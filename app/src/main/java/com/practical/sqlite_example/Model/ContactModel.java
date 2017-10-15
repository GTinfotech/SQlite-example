package com.practical.sqlite_example.Model;

/**
 * Created by Jay on 10/15/2017.
 */

public class ContactModel {

    int id;
    String contact_name;
    String contact_number;
    String contact_age;
    String contact_category;
    int contact_category_id;
    String contact_email;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContact_name() {
        return contact_name;
    }

    public void setContact_name(String contact_name) {
        this.contact_name = contact_name;
    }

    public String getContact_number() {
        return contact_number;
    }

    public void setContact_number(String contact_number) {
        this.contact_number = contact_number;
    }

    public String getContact_age() {
        return contact_age;
    }

    public void setContact_age(String contact_age) {
        this.contact_age = contact_age;
    }

    public String getContact_category() {
        return contact_category;
    }

    public void setContact_category(String contact_category) {
        this.contact_category = contact_category;
    }

    public int getContact_category_id() {
        return contact_category_id;
    }

    public void setContact_category_id(int contact_category_id) {
        this.contact_category_id = contact_category_id;
    }

    public String getContact_email() {
        return contact_email;
    }

    public void setContact_email(String contact_email) {
        this.contact_email = contact_email;
    }
}
