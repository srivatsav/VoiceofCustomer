package com.sri.voiceofcustomer.database.models;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by SRiGorthi on 15-01-2017.
 */

@IgnoreExtraProperties
public class User {

    public User()
    {}
    public String email;
    public String role;

    public User(String email,String role) {
        this.email = email;
        this.role = role;
    }
}
