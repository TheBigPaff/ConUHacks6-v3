package com.example.tempconuhacks6;

public class Playlist {
    private String id;
    private String name;
    private Tracks tracks;
    private Image[] images;
    private Song[] songs; // this will be instantiated manually
    private Artist[] artists; // same
    private String[] genres;

    public Playlist(String id){

    }
    // TODO: deep copy of arrays
    public Playlist(String id, String name, Tracks tracks, Image[] images){
        this.id = id;
        this.name = name;
        this.tracks = tracks;
        this.images = images;
    }

    public void setImages(Image[]  images) {
        this.images = images;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setTracks(Tracks tracks) {
        this.tracks = tracks;
    }

    public void setArtists(Artist[] artists) {
        this.artists = artists;
    }

    public void setGenres(String[] genres) {
        this.genres = genres;
    }

    public void setSongs(Song[] songs) {
        this.songs = songs;
    }

    public String getName() {
        return name;
    }

    public Image[] getImages() {
        return images;
    }

    public Tracks getTracks() {
        return tracks;
    }

    public Artist[] getArtists() {
        return artists;
    }

    public Song[] getSongs() {
        return songs;
    }

    public String[] getGenres() {
        return genres;
    }

    public String getId() {
        return id;
    }
}
