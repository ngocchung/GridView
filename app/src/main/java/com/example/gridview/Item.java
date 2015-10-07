package com.example.gridview;

public class Item {
    String imageUrl;
    String title;
    public Item(String imageUrl, String title) {
        super();
        this.imageUrl = imageUrl;
        this.title = title;
    }
    public String getImageUrl() {
        return imageUrl;
    }
    public String getTitle() {
        return title;
    }
}
