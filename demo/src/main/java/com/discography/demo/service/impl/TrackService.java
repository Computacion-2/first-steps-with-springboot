package com.discography.demo.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.discography.demo.model.Track;
import com.discography.demo.repository.TrackRepository;
import com.discography.demo.service.interf.ITrackService;

@Service("trackService")
public class TrackService implements ITrackService {
    
    @Autowired 
    private final TrackRepository trackRepository;

    public TrackService(TrackRepository trackRepository) {
        this.trackRepository = trackRepository;
    }

    @Override 
    public List<Track> getAllTracks() {
        return trackRepository.findAll();
    }

    @Override
    public boolean removeTrackById(String idTrack) {
        if (idTrack == null) return false;

        trackRepository.deleteById(idTrack);
        return true;
    }

    @Override
    public Track save(Track track) {
        return trackRepository.save(track);
    }
}
