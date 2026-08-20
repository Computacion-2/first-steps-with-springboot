package com.discography.demo.model;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

//This is a middle entity, because relation between Artist and Tracker is many to many.
@Entity
public class Publisher {

    @Id
    private String idPublisher;
    private String idArtist;
    private String idTrack;
    private LocalDate releaseDate;

    public Publisher(String idPublisher, String idArtist, String idTrack, LocalDate releaseDate) {
        this.idPublisher = idPublisher;
        this.idArtist = idArtist;
        this.idTrack = idTrack;
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

    public String getIdArtist() {
        return idArtist;
    }

    public void setIdArtist(String idArtist) {
        this.idArtist = idArtist;
    }

    public String getIdTrack() {
        return idTrack;
    }

    public void setIdTrack(String idTrack) {
        this.idTrack = idTrack;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }
}
