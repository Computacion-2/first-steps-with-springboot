package com.discography.demo.service.impl;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.discography.demo.model.Artist;
import com.discography.demo.model.Publisher;
import com.discography.demo.model.Track;
import com.discography.demo.repository.PublisherRepository;
import com.discography.demo.service.interf.IPublisherService;

@Service("publisherService")
public class PublisherService implements IPublisherService {
    
    @Autowired 
    private final PublisherRepository publisherRepository;

    public PublisherService(PublisherRepository publisherRepository) {
        this.publisherRepository = publisherRepository;
    }

    @Override 
    public List<Publisher> findTracksByNameArtist(String nameArtist) {
        return publisherRepository.findByArtistName(nameArtist);
    }

    @Override 
    public void addTrackToArtist(Artist artist, Track song) {
        Publisher publisherToAdd = new Publisher(idGeneration(), artist, song, LocalDate.now());
        publisherRepository.save(publisherToAdd);
    }

    private String idGeneration() {

        Optional<Publisher> lastPublisher = publisherRepository.findTopByOrderByIdPublisherDesc();

        if (lastPublisher.isEmpty()) return "P00001";

        String idPublisher = lastPublisher.get().getIdPublisher();

        int updateId = Integer.parseInt(idPublisher.substring(1));
        updateId++;

        return String.format("P%04d", updateId);
    }
}
