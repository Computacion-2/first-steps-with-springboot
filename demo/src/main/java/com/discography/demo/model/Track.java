package com.discography.demo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Track {
    
    @Id
    private String idTrack;
    private String title;
    private String genre;
    private int duration;
    private String albumTitle;
    
    public Track(String idTrack, String title, String genre, int duration, String albumTitle) {
        this.idTrack = idTrack;
        this.title = title;
        this.genre = genre;
        this.duration = duration;
        this.albumTitle = albumTitle;
    }

    //Empty Constructor (We don't want a Runtime Exception)
    public Track() {
    }

    public String getIdTrack() {
        return idTrack;
    }

    public void setIdTrack(String idTrack) {
        this.idTrack = idTrack;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getAlbumTitle() {
        return albumTitle;
    }

    public void setAlbumTitle(String albumTitle) {
        this.albumTitle = albumTitle;
    }

}
