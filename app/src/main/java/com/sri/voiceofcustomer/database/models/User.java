package com.sri.voiceofcustomer.database.models;

import com.google.firebase.database.IgnoreExtraProperties;

import java.math.BigInteger;

/**
 * Created by SRiGorthi on 15-01-2017.
 */

@IgnoreExtraProperties
public class User {

    public User()
    {}
    public String email;
    public String role;
    public String firstName;
    public String lastName;
    public String contact;

    public User(String email,String role,String firstName,String lastName,String contact) {
        this.email = email;
        this.role = role;
        this.firstName = firstName;
        this.lastName = lastName;
        this.contact = contact;
    }
}
