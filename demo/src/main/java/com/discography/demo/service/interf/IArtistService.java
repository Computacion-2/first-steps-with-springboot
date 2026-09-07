package com.discography.demo.service.interf;

import java.util.List;

import com.discography.demo.model.Artist;

public interface IArtistService {
    
    List<Artist> getAllTheArtist();
    void addArtist(String nameArtist, String nationalityArtist);
    Artist findByName(String name);
    Artist findById(String id);
    boolean removeArtistById(String idArtist);

}
