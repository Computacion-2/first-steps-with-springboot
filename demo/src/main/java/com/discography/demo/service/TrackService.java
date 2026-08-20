package com.discography.demo.service;

import com.discography.demo.repository.TrackRepository;

public class TrackService {
    
    private final TrackRepository trackRepository;

    public TrackService(TrackRepository trackRepository) {
        this.trackRepository = trackRepository;
    }

    //Here'll be all the logic related with a Track
}
