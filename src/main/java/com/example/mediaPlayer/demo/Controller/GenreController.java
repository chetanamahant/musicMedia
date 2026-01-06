package com.example.mediaPlayer.demo.Controller;


import com.example.mediaPlayer.demo.Entity.Genre;
import com.example.mediaPlayer.demo.Entity.Song;
import com.example.mediaPlayer.demo.ServiceI.GenreServiceInterface;
import com.example.mediaPlayer.demo.ServiceI.RecommendationServiceInterface;
import com.example.mediaPlayer.demo.ServiceImpl.RecommendationServiceImpl;
import com.example.mediaPlayer.demo.dto.response.ApiSuccessResponse;
import com.example.mediaPlayer.demo.dto.response.GenreResponse;
import com.example.mediaPlayer.demo.dto.response.SongResponseDTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/genres")
public class GenreController {

    @Autowired
    private GenreServiceInterface genreService;

    @Autowired
    private RecommendationServiceInterface recommendationService;


    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/createGenre")
    public ResponseEntity<ApiSuccessResponse<GenreResponse>> createGenre(
            @Valid @RequestBody Genre request) {

        return ResponseEntity.ok(
                new ApiSuccessResponse<>(
                        true,
                        "Genre created successfully",
                        genreService.createGenre(request),
                        LocalDateTime.now()
                )
        );
    }

    @GetMapping("/getAllGenres")
    public ResponseEntity<ApiSuccessResponse<List<GenreResponse>>> getAllGenres() {

        return ResponseEntity.ok(
                new ApiSuccessResponse<>(
                        true,
                        "Genres fetched successfully",
                        genreService.getAllGenres(),
                        LocalDateTime.now()
                )
        );
    }

    @GetMapping("/getGenreById/{id}")
    public ResponseEntity<ApiSuccessResponse<GenreResponse>> getGenreById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                new ApiSuccessResponse<>(
                        true,
                        "Genre details fetched successfully",
                        genreService.getGenreById(id),
                        LocalDateTime.now()
                )
        );
    }

    @GetMapping("/recommendByGenre/{genreId}/recommendations")
    public ResponseEntity<ApiSuccessResponse<List<SongResponseDTO>>> recommendByGenre(
            @PathVariable Long genreId) {

        return ResponseEntity.ok(
                new ApiSuccessResponse<>(
                        true,
                        "Genre recommendations",
                        recommendationService.recommendByGenre(genreId),
                        LocalDateTime.now()
                )
        );
    }
}
