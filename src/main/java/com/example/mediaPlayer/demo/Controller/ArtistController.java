package com.example.mediaPlayer.demo.Controller;

import com.example.mediaPlayer.demo.Entity.Artist;
import com.example.mediaPlayer.demo.ServiceI.ArtistServiceInterface;
import com.example.mediaPlayer.demo.dto.ArtistDTO;
import com.example.mediaPlayer.demo.dto.response.ApiSuccessResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/artists")
public class ArtistController {

    @Autowired
    private ArtistServiceInterface artistService;

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/createArtist")
    public ResponseEntity<ApiSuccessResponse<Artist>> createArtist(
            @Valid @RequestBody ArtistDTO dto) {

        return ResponseEntity.ok(new ApiSuccessResponse<>(
                true, "Artist created",
                artistService.createArtist(dto),
                LocalDateTime.now()
        ));
    }

    @GetMapping("/getAllArtists")
    public ResponseEntity<ApiSuccessResponse<List<Artist>>> getAllArtists() {

        return ResponseEntity.ok(new ApiSuccessResponse<>(
                true,
                "Artists fetched successfully",
                artistService.getAllArtists(),
                LocalDateTime.now()
        ));
    }

    @GetMapping("/getArtistById/{id}")
    public ResponseEntity<ApiSuccessResponse<Artist>> getArtistById(@PathVariable Long id) {

        return ResponseEntity.ok(new ApiSuccessResponse<>(
                true,
                "Artist details fetched successfully",
                artistService.getArtistById(id),
                LocalDateTime.now()
        ));
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/deleteArtistById/{id}")
    public ResponseEntity<ApiSuccessResponse<String>> deleteArtistById(@PathVariable Long id) {

        artistService.deleteArtistById(id);

        return ResponseEntity.ok(new ApiSuccessResponse<>(
                true,
                "Artist deleted",
                "Artist removed",
                LocalDateTime.now()
        ));
    }
}

