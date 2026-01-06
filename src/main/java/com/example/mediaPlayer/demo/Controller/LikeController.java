package com.example.mediaPlayer.demo.Controller;

import com.example.mediaPlayer.demo.Entity.Song;
import com.example.mediaPlayer.demo.Entity.SongLike;
import com.example.mediaPlayer.demo.ServiceI.LikeServiceInterface;
import com.example.mediaPlayer.demo.configuration.CustomUserDetailsService;
import com.example.mediaPlayer.demo.dto.response.ApiSuccessResponse;
import com.example.mediaPlayer.demo.dto.response.SongResponseDTO;
import com.example.mediaPlayer.demo.util.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;



@RestController
@RequestMapping("/likes")
public class LikeController {

    @Autowired
    private LikeServiceInterface likeServiceInterface;

    // LIKE A SONG
    @PreAuthorize("hasRole('USER')")
    @PostMapping("/likeSong/{songId}")
    public ResponseEntity<ApiSuccessResponse<String>> likeSong(
            @PathVariable Long songId,
            @AuthenticationPrincipal CustomUserDetails userDetails) {

        String data = likeServiceInterface.likeSong(userDetails.getId(), songId);

        return ResponseEntity.ok(
                new ApiSuccessResponse<>(
                        true,
                        "Song liked successfully",
                        data,
                        LocalDateTime.now()
                )
        );
    }

    // GET ALL LIKED SONGS
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/getLikedSongs")
    public ResponseEntity<ApiSuccessResponse<List<SongResponseDTO>>> getLikedSongs(
            @AuthenticationPrincipal CustomUserDetails userDetails) {

        List<SongResponseDTO> songs = likeServiceInterface.getLikedSongs(userDetails.getId());

        return ResponseEntity.ok(
                new ApiSuccessResponse<>(
                        true,
                        "Liked songs fetched successfully",
                        songs,
                        LocalDateTime.now()
                )
        );
    }

    // UNLIKE A SONG
    @PreAuthorize("hasRole('USER')")
    @DeleteMapping("/unlike/{songId}")
    public ResponseEntity<ApiSuccessResponse<String>> unlikeSong(
            @PathVariable Long songId,
            @AuthenticationPrincipal CustomUserDetails userDetails) {

        likeServiceInterface.unlikeSong(userDetails.getId(), songId);

        return ResponseEntity.ok(
                new ApiSuccessResponse<>(
                        true,
                        "Song unliked successfully",
                        null,
                        LocalDateTime.now()
                )
        );
    }
}