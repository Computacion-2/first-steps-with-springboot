package com.discography.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.discography.demo.model.Publisher;
import com.discography.demo.repository.PublisherRepository;

@Service
public class PublisherService {
    
    private final PublisherRepository publisherRepository;

    public PublisherService(PublisherRepository publisherRepository) {
        this.publisherRepository = publisherRepository;
    }

    public List<Publisher> findTracksByNameArtist(String nameArtist) {
        return publisherRepository.findByArtistName(nameArtist);
    }
}
