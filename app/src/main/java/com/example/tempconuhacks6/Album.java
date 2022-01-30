package com.example.tempconuhacks6;

public class Album {
    private Image[] images;
    public Album(Image[] images){
        this.images = images;
    }

    public Image[] getImages() {
        return images;
    }

    public void setImages(Image[] images) {
        this.images = images;
    }
}
