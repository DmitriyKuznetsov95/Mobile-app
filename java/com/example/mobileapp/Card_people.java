package com.example.mobileapp;

public class Card_people {
    private String Name;
    private String SurName;
    private String Image;

    public Card_people() {
    }

    public Card_people(String name, String surName, String image) {
        Name = name;
        SurName = surName;
        Image = image;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getSurName() {
        return SurName;
    }

    public void setSurName(String surName) {
        SurName = surName;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }
}
