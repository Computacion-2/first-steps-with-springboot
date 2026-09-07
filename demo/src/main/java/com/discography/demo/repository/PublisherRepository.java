package com.discography.demo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.discography.demo.model.Publisher;


public interface PublisherRepository extends JpaRepository<Publisher, String> {

    List<Publisher> findByArtistName(String name);

    Optional<Publisher> findTopByOrderByIdPublisherDesc();
    
}
