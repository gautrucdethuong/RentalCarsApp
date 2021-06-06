package com.example.rentalcarsapp.model;

/**
 * Author by HUYNH NHAT MINH (ミン).
 * Email: minhhnce140197@fpt.edu.vn.
 * Date on 6/6/2021.
 * Company: FPT大学.
 */

public class User {
    String Displayname;


    String Email;
    long createdAt;

    public User(){};
    public User(String displayname, String email, long createdAt){
        this.Displayname=displayname;
        this.Email=email;
        this.createdAt=createdAt;
    }


    public String getDisplayname() {
        return Displayname;
    }

    public String getEmail() {
        return Email;
    }

    public long getCreatedAt() {
        return createdAt;
    }

}
