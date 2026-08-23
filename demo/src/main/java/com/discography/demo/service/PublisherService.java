package com.discography.demo.service;

import org.springframework.stereotype.Service;

import com.discography.demo.repository.PublisherRepository;

@Service
public class PublisherService {
    
    private final PublisherRepository publisherRepository;

    public PublisherService(PublisherRepository publisherRepository) {
        this.publisherRepository = publisherRepository;
    }

    //Here'll be all the logic related with a publisher
}
