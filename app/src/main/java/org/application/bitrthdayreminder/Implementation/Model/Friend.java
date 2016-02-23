package org.application.bitrthdayreminder.Implementation.Model;

/**
 * Created by sachin on 2/16/2016.
 */
public class Friend {

    private int id;
    private String name;
    private String dob;
    private String contact;
    private String facebookId;
    private String location;

    public Friend(int id, String name, String dob, String contact, String facebookId,String location) {
        this.id = id;
        this.name = name;
        this.dob = dob;
        this.contact = contact;
        this.facebookId = facebookId;
        this.location = location;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFacebookId() {
        return facebookId;
    }

    public void setFacebookId(String facebookId) {
        this.facebookId = facebookId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }
}
