package com.discography.demo.service.interf;

import java.util.List;

import com.discography.demo.model.Artist;

public interface IArtistService {
    
    public List<Artist> getAllTheArtist();

    public void addArtist(String nameArtist, String nationalityArtist);

    public boolean removeArtistById(String idArtist);

}
