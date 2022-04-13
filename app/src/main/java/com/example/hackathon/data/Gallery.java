package com.example.hackathon.data;

public class Gallery {

    String image, date;

    public String getImage() {
        return image;
    }

    public Gallery setImage(String image) {
        this.image = image;
        return this;
    }

    public String getDate() {
        return date;
    }

    public Gallery setDate(String date) {
        this.date = date;
        return this;
    }

    public Gallery(String image, String date) {
        this.image = image;
        this.date = date;
    }
}
