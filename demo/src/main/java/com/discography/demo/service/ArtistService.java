package com.discography.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.discography.demo.model.Artist;
import com.discography.demo.repository.ArtistRepository;

@Service
public class ArtistService {

    private final ArtistRepository artistRepository;

    public ArtistService(ArtistRepository artistRepository) {
        this.artistRepository = artistRepository;
    }

    public List<Artist> getAllTheArtist() {
        return artistRepository.findAll();
    }

    //I recommend use DTO to only only get useful data from user. But for practicality of the exercise we'll ignore this.
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

    private boolean removeArtistById(String idArtist) {
        if (idArtist != null) return false;

        artistRepository.deleteById(idArtist);
        return true;
    }
}
