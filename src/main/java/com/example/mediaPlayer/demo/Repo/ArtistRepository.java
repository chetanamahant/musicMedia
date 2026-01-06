package com.example.mediaPlayer.demo.Repo;

import com.example.mediaPlayer.demo.Entity.Artist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArtistRepository extends JpaRepository<Artist, Long> {
}

