package com.example.rentalmanager.db;

public class Categories {
    String category;
    int image;

    public Categories(String category, int image) {
        this.category = category;
        this.image = image;
    }
    public Categories(String category) {
        this.category = category;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }
}
