package com.example.back4app.barbershopapp;


import java.io.Serializable;

public class Model implements Serializable {
    private String title, category , event;

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




}
