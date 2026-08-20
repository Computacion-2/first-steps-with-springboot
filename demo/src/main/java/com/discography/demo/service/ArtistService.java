package com.discography.demo.service;

import org.springframework.stereotype.Service;

import com.discography.demo.repository.ArtistRepository;

@Service
public class ArtistService {

    private final ArtistRepository artistRepository;

    public ArtistService(ArtistRepository artistRepository) {
        this.artistRepository = artistRepository;
    }

    //Here'll be all the logic related with an artist
}
