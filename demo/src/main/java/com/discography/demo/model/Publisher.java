package com.discography.demo.model;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

//This is a middle entity, because relation between Artist and Tracker is many to many.
@Entity
public class Publisher {

    @Id
    private String idPublisher;
    
    @ManyToOne
    @JoinColumn(name = "artist_id")
    private Artist artist;
    
    @ManyToOne
    @JoinColumn(name = "track_id")
    private Track track;
    private LocalDate releaseDate;

    public Publisher(String idPublisher, Artist artist, Track track, LocalDate releaseDate) {
        this.idPublisher = idPublisher;
        this.artist = artist;
        this.track = track;
        this.releaseDate = releaseDate;
    }

    public Publisher() {
    }

    public String getIdPublisher() {
        return idPublisher;
    }

    public void setIdPublisher(String idPublisher) {
        this.idPublisher = idPublisher;
    }

    public Artist getArtist() {
        return artist;
    }

    public void setArtist(Artist artist) {
        this.artist = artist;
    }

    public Track getTrack() {
        return track;
    }

    public void setTrack(Track track) {
        this.track = track;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }
}
