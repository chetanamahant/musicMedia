package com.example.mediaPlayer.demo.ServiceI;

import com.example.mediaPlayer.demo.dto.response.SongResponseDTO;

import java.util.List;

public interface RecommendationServiceInterface {
    List<SongResponseDTO> recommendSongs();
    List<SongResponseDTO> recommendByGenre(Long genreId);
}
