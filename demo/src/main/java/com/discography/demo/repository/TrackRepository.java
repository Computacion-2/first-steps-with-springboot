package com.discography.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.discography.demo.model.Track;

@Repository
public interface TrackRepository extends JpaRepository<Track, String> {
    
}
