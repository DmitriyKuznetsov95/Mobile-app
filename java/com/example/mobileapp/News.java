package com.example.mobileapp;

public class News {

    private String Header;
    private String Text;
    private String Date;
    private String Image;

    public News() {
    }

    public News(String header, String text, String date, String image) {
        Header = header;
        Text = text;
        Date = date;
        Image = image;
    }

    public String getHeader() {
        return Header;
    }

    public void setHeader(String header) {
        Header = header;
    }

    public String getText() {
        return Text;
    }

    public void setText(String text) {
        Text = text;
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
