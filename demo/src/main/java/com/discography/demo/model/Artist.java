package com.discography.demo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Artist {
    
    @Id
    private String idArtist;
    private String name;
    private String nationality;
    
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
    
}
