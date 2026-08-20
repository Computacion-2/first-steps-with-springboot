package com.discography.demo.service;

import com.discography.demo.repository.PublisherRepository;

public class PublisherService {
    
    private final PublisherRepository publisherRepository;

    public PublisherService(PublisherRepository publisherRepository) {
        this.publisherRepository = publisherRepository;
    }

    //Here'll be all the logic related with a publisher
}
