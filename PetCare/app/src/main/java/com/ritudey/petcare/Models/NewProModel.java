package com.ritudey.petcare.Models;

public class NewProModel {

    int amount;
    String description;
    String img_url;
    String name;
    String rating;

    public NewProModel() {
    }

    public NewProModel(int amount, String description, String img_url, String name, String rating) {
        this.amount = amount;
        this.description = description;
        this.img_url = img_url;
        this.name = name;
        this.rating = rating;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }
}
