package com.discography.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.discography.demo.model.Artist;

@Repository
public interface ArtistRepository extends JpaRepository<Artist, String> {
    
}
