package com.example.mediaPlayer.demo.Repo;

import com.example.mediaPlayer.demo.Entity.Album;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AlbumRepository extends JpaRepository<Album, Long> {
    List<Album> findByArtistId(Long artistId);

    List<Album> findByArtist_Id(Long artistId);
}
