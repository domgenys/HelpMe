package com.example.helpme;




import java.util.Date;
import java.util.List;

public class AskingHelp {



    //ASking Help implementing as table, all var setting as columns
    private String name;
    private String number;
    private String email;
    private String comments;
    private String location;
    private Date created;
    private Date updated;
    private String objectId;


// objects are = null by default values
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getUpdated() {
        return updated;
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public String getLocation() {
        return location;
    }


    public void setLocation(String location) {
        this.location = location;




    }
}
