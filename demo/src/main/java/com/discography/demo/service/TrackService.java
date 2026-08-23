package com.discography.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.discography.demo.model.Track;
import com.discography.demo.repository.TrackRepository;

@Service
public class TrackService {
    
    private final TrackRepository trackRepository;

    public TrackService(TrackRepository trackRepository) {
        this.trackRepository = trackRepository;
    }

    public List<Track> getAllTracks() {
        return trackRepository.findAll();
    }
}
