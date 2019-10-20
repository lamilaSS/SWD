package com.example.back4app.barbershopapp;


import java.io.Serializable;

public class Model implements Serializable {
    private String title;
    private String category;
    private String event;

    public Model() {
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


    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }
}
