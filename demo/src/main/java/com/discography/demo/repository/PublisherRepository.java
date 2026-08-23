package com.discography.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.discography.demo.model.Publisher;

public interface PublisherRepository extends JpaRepository<Publisher, String> {
    
}
