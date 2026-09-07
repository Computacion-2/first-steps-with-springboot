package com.discography.demo.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;

@Entity
public class Artist {
    
    @Id
    private String idArtist;
    private String name;
    private String nationality;

    @ManyToMany(mappedBy = "artists")
    private List<Track> tracks = new ArrayList<>();
    
    public Artist(String idArtist, String name, String nationality) {
        this.idArtist = idArtist;
        this.name = name;
        this.nationality = nationality;
    }

    //Empty Constructor (We don't want a Runtime Exception)
    public Artist() {
    }

    public String getIdArtist() {
        return idArtist;
    }

    public void setIdArtist(String idArtist) {
        this.idArtist = idArtist;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public List<Track> getTracks() {
        return tracks;
    }

    public void setTracks(List<Track> tracks) {
        this.tracks = tracks;
    }
}
