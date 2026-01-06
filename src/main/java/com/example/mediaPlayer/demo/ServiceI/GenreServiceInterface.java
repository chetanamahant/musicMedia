package com.example.mediaPlayer.demo.ServiceI;

import com.example.mediaPlayer.demo.Entity.Genre;
import com.example.mediaPlayer.demo.dto.response.GenreResponse;

import java.util.List;

public interface GenreServiceInterface {

    List<GenreResponse> getAllGenres();

    GenreResponse getGenreById(Long id);

    GenreResponse createGenre(Genre name);
}
