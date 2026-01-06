package com.example.mediaPlayer.demo.Repo;

import com.example.mediaPlayer.demo.Entity.SongLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LikeRepository extends JpaRepository<SongLike, Long> {

    boolean existsByUserIdAndSongId(Long userId, Long songId);

    Optional<SongLike> findByUserIdAndSongId(Long userId, Long songId);

    @Query("SELECT sl FROM SongLike sl JOIN FETCH sl.song WHERE sl.user.id = :userId")
    List<SongLike> findByUserIdWithSong(@Param("userId") Long userId);


}
