package com.example.mediaPlayer.demo.Repo;

import com.example.mediaPlayer.demo.Entity.Genre;
import com.example.mediaPlayer.demo.Entity.Song;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface SongRepository extends JpaRepository<Song, Long> {

    List<Song> findByTitleContainingIgnoreCase(String title);

    @Query(value = "SELECT s FROM Song s " +
                  " WHERE LOWER(s.title) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
                  "     OR LOWER(s.artist) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
                  "     OR LOWER(s.album) LIKE LOWER(CONCAT('%', :keyword, '%'))" , nativeQuery = true)
    List<Song> search(@Param("keyword") String keyword);

    @Query("SELECT s FROM Song s WHERE s.genre.id = :genreId")
    List<Song> findByGenre_Id(@Param("genreId") Long genreId);

    List<Song> findByGenre(Genre genre);

    List<Song> findTop10ByOrderByIdDesc();
}
