package com.example.tempconuhacks6;

public class Artist {
    private String href;
    private String name;
    private String[] genres;

    public Artist(String href, String name, String[] genres){
        this.href = href;
        this.name = name;
        this.genres = genres;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public String getName() {
        return name;
    }

    public String getHref() {
        return href;
    }

    public String[] getGenres() {
        return genres;
    }
}
