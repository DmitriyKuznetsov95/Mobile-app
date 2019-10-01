package com.example.mobileapp;

public class List_chemp {

    private String Header;
    private String Date;
    private String Image;

    public List_chemp() {
    }

    public List_chemp(String header, String date, String image) {
        Header = header;
        Date = date;
        Image = image;
    }

    public String getHeader() {
        return Header;
    }

    public void setHeader(String header) {
        Header = header;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }
}
