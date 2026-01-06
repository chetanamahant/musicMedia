package com.example.mediaPlayer.demo.ServiceImpl;

import com.example.mediaPlayer.demo.Entity.Genre;
import com.example.mediaPlayer.demo.Entity.Song;
import com.example.mediaPlayer.demo.Repo.GenreRepository;
import com.example.mediaPlayer.demo.Repo.SongRepository;
import com.example.mediaPlayer.demo.ServiceI.RecommendationServiceInterface;
import com.example.mediaPlayer.demo.dto.response.SongResponseDTO;
import com.example.mediaPlayer.demo.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecommendationServiceImpl implements RecommendationServiceInterface {

    @Autowired
    private SongRepository songRepository;

    @Autowired
    private GenreRepository genreRepository;

    @Override
    public List<SongResponseDTO> recommendSongs() {

        return songRepository.findTop10ByOrderByIdDesc()
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    @Override
    public List<SongResponseDTO> recommendByGenre(Long genreId) {

        Genre genre = genreRepository.findById(genreId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Genre not found with id: " + genreId));

        return songRepository.findByGenre(genre)
                .stream()
                .limit(10)
                .map(this::mapToDTO)
                .toList();
    }

    private SongResponseDTO mapToDTO(Song song) {

        return new SongResponseDTO(
                song.getId(),
                song.getTitle(),
                song.getGenre().getName(),
                song.getArtist() != null ? song.getArtist().getName() : null,
                song.getAlbum() != null ? song.getAlbum().getName() : null
        );
    }
}
