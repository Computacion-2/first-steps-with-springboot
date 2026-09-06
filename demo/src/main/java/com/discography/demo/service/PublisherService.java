package com.discography.demo.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.discography.demo.model.Artist;
import com.discography.demo.model.Publisher;
import com.discography.demo.model.Track;
import com.discography.demo.repository.PublisherRepository;

@Service
public class PublisherService {
    
    @Autowired 
    private final PublisherRepository publisherRepository;

    public PublisherService(PublisherRepository publisherRepository) {
        this.publisherRepository = publisherRepository;
    }

    public List<Publisher> findTracksByNameArtist(String nameArtist) {
        return publisherRepository.findByArtistName(nameArtist);
    }

    public void addTrackToArtist(Artist artist, Track song) {
        Publisher publisherToAdd = new Publisher(idGeneration(), artist, song, LocalDate.now());
        publisherRepository.save(publisherToAdd);
    }

    private String idGeneration() {

        Optional<Publisher> lastPublisher = publisherRepository.findTopByOrderByIdDesc();

        if (lastPublisher.isEmpty()) return "P00001";

        String idPublisher = lastPublisher.get().getIdPublisher();

        int updateId = Integer.parseInt(idPublisher.substring(1));
        updateId++;

        return String.format("P%04d", updateId);
    }
}
