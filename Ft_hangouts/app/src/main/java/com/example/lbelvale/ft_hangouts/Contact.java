package com.example.lbelvale.ft_hangouts;


import java.io.Serializable;

/**
 * Created by lbelvale on 12/20/16.
 */

public class Contact implements Serializable{
    private int color;
    private int id;
    private String firstname;
    private String lastname;
    private String phone;
    private String mail;
    private String address;

    public Contact(int color, String firstname, String lastname, String phone, String mail, String address) {
        super();
        this.color = color;
        this.firstname = firstname;
        this.lastname = lastname;
        this.phone = phone;
        this.mail = mail;
        this.address = address;
    }

    public String getFirstname() {
        return this.firstname;
    }

    public void   setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return this.lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public int    getColor() {
        return this.color;
    }

    public void setColor (int color) {
        this.color = color;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int    getId() {
        return this.id;
    }

    public String getPhone() {
        return this.phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }


    public String getMail() {
        return this.mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getAddress() {
        return this.address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
