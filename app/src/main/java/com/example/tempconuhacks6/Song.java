package com.example.tempconuhacks6;

public class Song {
    private String id;
    private String name;
    private Artist[] artists;
    private String preview_url;
    private Album album; // contains the image we need for the song


    public Song(String id, String name) {
        this.name = name;
        this.id = id;
    }
    public Song(String id, String name, Artist[] artist) {
        this.name = name;
        this.id = id;
        this.artists = artist;
    }
    public Song(String id, String name, Artist[] artist, String preview_url, Album album){
        this.name = name;
        this.id = id;
        this.artists = artist;
        this.preview_url = preview_url;
        this.album = album;
    }
    public String getId() {
        return id;
    }

    public Artist[] getArtists() {
        return artists;
    }

    public Album getAlbum() {
        return album;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setArtists(Artist[] artist) {
        this.artists = artist;
    }

    @Override
    public String toString() {
        return "Song{" +
                "name='" + name + '\'' +
                '}';
    }
}
