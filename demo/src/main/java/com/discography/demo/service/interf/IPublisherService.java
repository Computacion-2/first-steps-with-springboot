package com.discography.demo.service.interf;

import java.util.List;

import com.discography.demo.model.Artist;
import com.discography.demo.model.Publisher;
import com.discography.demo.model.Track;

public interface IPublisherService {

    public List<Publisher> findTracksByNameArtist(String nameArtist);

    public void addTrackToArtist(Artist artist, Track song);

}
