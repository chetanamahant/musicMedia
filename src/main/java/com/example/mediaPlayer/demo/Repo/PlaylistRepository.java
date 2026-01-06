package com.example.mediaPlayer.demo.Repo;

import com.example.mediaPlayer.demo.Entity.Playlist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface PlaylistRepository extends JpaRepository<Playlist , Long> {

    List<Playlist> findByUser_Id(Long userId);
}
