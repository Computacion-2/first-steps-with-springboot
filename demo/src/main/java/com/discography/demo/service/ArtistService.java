package com.discography.demo.service;

import java.util.List;

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
}
