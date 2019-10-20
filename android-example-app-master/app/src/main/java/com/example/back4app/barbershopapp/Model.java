package com.example.back4app.barbershopapp;


import java.io.Serializable;

public class Model implements Serializable {
    private String title, category , event, description, members, activities;

    public Model() {
    }

    public Model(String title, String category, String description, String members, String activities) {
        this.title = title;
        this.category = category;
        this.description = description;
        this.members = members;
        this.activities = activities;
    }

    public Model(String title, String category) {
        this.title=title;
        this.category=category;

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String desccription) {
        this.description = desccription;
    }

    public String getMembers() {
        return members;
    }

    public void setMembers(String members) {
        this.members = members;
    }

    public String getActivities() {
        return activities;
    }

    public void setActivities(String activities) {
        this.activities = activities;
    }
}
