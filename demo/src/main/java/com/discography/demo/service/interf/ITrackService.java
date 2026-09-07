package com.discography.demo.service.interf;

import java.util.List;

import com.discography.demo.model.Track;

public interface ITrackService {
    
    public List<Track> getAllTracks();
    Track save(Track track);
    public boolean removeTrackById(String idTrack);
    
}
