package com.example.mediaPlayer.demo.Controller;

import com.example.mediaPlayer.demo.ServiceI.PlaylistServiceInterface;
import com.example.mediaPlayer.demo.dto.request.PlaylistRequest;
import com.example.mediaPlayer.demo.dto.response.ApiSuccessResponse;
import com.example.mediaPlayer.demo.dto.response.PlaylistResponse;
import com.example.mediaPlayer.demo.util.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
@RestController
@RequestMapping("/playlists")
public class PlaylistController {

    @Autowired
    private PlaylistServiceInterface playlistService;

    // CREATE
    @PostMapping("/createPlaylist")
    public ResponseEntity<ApiSuccessResponse<PlaylistResponse>> createPlaylist(
            @RequestBody PlaylistRequest request ,@AuthenticationPrincipal CustomUserDetails userDetails ) {

        return ResponseEntity.ok(new ApiSuccessResponse<>(
                true,
                "Playlist created",
                playlistService.createPlaylist(request, userDetails.getId()), // JWT later
                LocalDateTime.now()
        ));
    }

    // ADD SONG
    @PostMapping("/{playlistId}/songs/{songId}")
    public ResponseEntity<ApiSuccessResponse<String>> addSong(
            @PathVariable Long playlistId,
            @PathVariable Long songId ,  @AuthenticationPrincipal CustomUserDetails userDetails) {

        playlistService.addSongToPlaylist(playlistId, songId, userDetails.getId());

        return ResponseEntity.ok(new ApiSuccessResponse<>(
                true, "Song added to playlist", null, LocalDateTime.now()
        ));
    }

    // REMOVE SONG
    @DeleteMapping("/{playlistId}/songs/{songId}")
    public ResponseEntity<ApiSuccessResponse<String>> removeSong(
            @PathVariable Long playlistId,
            @PathVariable Long songId , @AuthenticationPrincipal CustomUserDetails userDetails) {

        playlistService.removeSongFromPlaylist(playlistId, songId, userDetails.getId());

        return ResponseEntity.ok(new ApiSuccessResponse<>(
                true, "Song removed from playlist", null, LocalDateTime.now()
        ));
    }

    // GET PLAYLIST BY ID
    @GetMapping("/getPlaylist/{playlistId}")
    public ResponseEntity<ApiSuccessResponse<PlaylistResponse>> getPlaylist(
            @PathVariable Long playlistId  , @AuthenticationPrincipal CustomUserDetails userDetails) {

        return ResponseEntity.ok(new ApiSuccessResponse<>(
                true,
                "Playlist fetched",
                playlistService.getPlaylistById(playlistId, userDetails.getId()),
                LocalDateTime.now()
        ));
    }

    // GET USER PLAYLISTS
    @GetMapping
    public ResponseEntity<ApiSuccessResponse<List<PlaylistResponse>>> getMyPlaylists
    (@AuthenticationPrincipal CustomUserDetails userDetails) {

        return ResponseEntity.ok(new ApiSuccessResponse<>(
                true,
                "My playlists fetched",
                playlistService.getUserPlaylists(userDetails.getId()),
                LocalDateTime.now()
        ));
    }

    // UPDATE
    @PutMapping("/{playlistId}")
    public ResponseEntity<ApiSuccessResponse<PlaylistResponse>> updatePlaylist(
            @PathVariable Long playlistId,
            @RequestBody PlaylistRequest request,
            @AuthenticationPrincipal CustomUserDetails userDetails) {

        return ResponseEntity.ok(new ApiSuccessResponse<>(
                true,
                "Playlist updated",
                playlistService.updatePlaylist(playlistId, request, userDetails.getId()),
                LocalDateTime.now()
        ));
    }

    // DELETE
    @DeleteMapping("/{playlistId}")
    public ResponseEntity<ApiSuccessResponse<String>> deletePlaylist(
            @PathVariable Long playlistId,@AuthenticationPrincipal CustomUserDetails userDetails) {

        playlistService.deletePlaylist(playlistId, userDetails.getId());

        return ResponseEntity.ok(new ApiSuccessResponse<>(
                true,
                "Playlist deleted",
                null,
                LocalDateTime.now()
        ));
    }
}
