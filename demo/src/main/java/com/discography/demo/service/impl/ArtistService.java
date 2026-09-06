package com.discography.demo.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.discography.demo.model.Artist;
import com.discography.demo.repository.ArtistRepository;
import com.discography.demo.service.interf.IArtistService;

@Service
public class ArtistService implements IArtistService {

    @Autowired 
    private final ArtistRepository artistRepository;

    public ArtistService(ArtistRepository artistRepository) {
        this.artistRepository = artistRepository;
    }

    @Override 
    public List<Artist> getAllTheArtist() {
        return artistRepository.findAll();
    }

    //I recommend use DTO to only only get useful data from user. But for practicality of the exercise we'll ignore this.
    @Override 
    public void addArtist(String nameArtist, String nationalityArtist) {

        Artist newArtist = new Artist(idGeneration(), nameArtist, nationalityArtist);

        artistRepository.save(newArtist);
    }

    //Asign a id to Artist with a defined format: "A0000x"
    private String idGeneration() {

        Optional<Artist> lastOptionalArtist = artistRepository.findTopByOrderByIdDesc();

        if (lastOptionalArtist.isEmpty()) return "A00001";

        String idArtist = lastOptionalArtist.get().getIdArtist();

        int updateId = Integer.parseInt(idArtist.substring(1));
        updateId++;

        return String.format("A%04d", updateId);
    }

    @Override
    public boolean removeArtistById(String idArtist) {
        if (idArtist == null) return false;

        artistRepository.deleteById(idArtist);
        return true;
    }
}
