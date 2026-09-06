package com.discography.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.discography.demo.model.Track;
import com.discography.demo.repository.TrackRepository;

@Service
public class TrackService {
    
    @Autowired 
    private final TrackRepository trackRepository;

    public TrackService(TrackRepository trackRepository) {
        this.trackRepository = trackRepository;
    }

    public List<Track> getAllTracks() {
        return trackRepository.findAll();
    }

    private boolean removeTrackById(String idTrack) {
        if (idTrack == null) return false;

        trackRepository.deleteById(idTrack);
        return true;
    }
}
