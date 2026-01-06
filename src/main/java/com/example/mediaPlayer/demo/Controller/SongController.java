package com.example.mediaPlayer.demo.Controller;

import com.example.mediaPlayer.demo.ServiceI.SongServiceInterface;
import com.example.mediaPlayer.demo.dto.SongRequestDTO;
import com.example.mediaPlayer.demo.dto.response.SongResponseDTO;
import com.example.mediaPlayer.demo.dto.response.ApiSuccessResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/songs")
public class SongController {

    @Autowired
    private SongServiceInterface songService;

    @GetMapping("/getSongsByGenreId")
    public ResponseEntity<ApiSuccessResponse<List<SongResponseDTO>>> getSongs(
            @RequestParam(required = false) Long genreId) {

        return ResponseEntity.ok(new ApiSuccessResponse<>(
                true,
                "Songs fetched successfully",
                songService.getSongs(genreId),
                LocalDateTime.now()
        ));
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/uploadSong")
    public ResponseEntity<ApiSuccessResponse<SongResponseDTO>> uploadSong(
            @Valid @RequestBody SongRequestDTO dto) {

        return ResponseEntity.ok(new ApiSuccessResponse<>(
                true,
                "Song uploaded successfully",
                songService.uploadSong(dto),
                LocalDateTime.now()
        ));
    }

    @GetMapping("/getSongById/{id}")
    public ResponseEntity<ApiSuccessResponse<SongResponseDTO>> getSongById(
            @PathVariable Long id) {

        return ResponseEntity.ok(new ApiSuccessResponse<>(
                true,
                "Song details fetched",
                songService.getSongById(id),
                LocalDateTime.now()
        ));
    }

    @GetMapping("/search")
    public ResponseEntity<ApiSuccessResponse<List<SongResponseDTO>>> searchSongs(
            @RequestParam String keyword) {

        return ResponseEntity.ok(new ApiSuccessResponse<>(
                true,
                "Search results",
                songService.searchSongs(keyword),
                LocalDateTime.now()
        ));
    }

    @GetMapping("streamSong/{id}/stream")
    public ResponseEntity<Resource> streamSong(@PathVariable Long id) {
        return songService.streamSong(id);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/deleteSong/{id}")
    public ResponseEntity<ApiSuccessResponse<String>> deleteSong(
            @PathVariable Long id) {

        songService.deleteSong(id);

        return ResponseEntity.ok(new ApiSuccessResponse<>(
                true,
                "Song deleted successfully",
                "Deleted",
                LocalDateTime.now()
        ));
    }
}
