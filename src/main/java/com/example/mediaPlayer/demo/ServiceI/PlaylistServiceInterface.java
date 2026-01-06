package com.example.mediaPlayer.demo.ServiceI;

import com.example.mediaPlayer.demo.Entity.Playlist;
import com.example.mediaPlayer.demo.dto.request.PlaylistRequest;
import com.example.mediaPlayer.demo.dto.response.PlaylistResponse;

import java.util.List;

public interface PlaylistServiceInterface {

    // CREATE
    PlaylistResponse createPlaylist(PlaylistRequest request, Long userId);

    // ADD SONG
    void addSongToPlaylist(Long playlistId, Long songId, Long userId);

    // REMOVE SONG
    void removeSongFromPlaylist(Long playlistId, Long songId, Long userId);

    // GET BY ID
    PlaylistResponse getPlaylistById(Long playlistId, Long userId);

    // GET USER PLAYLISTS
    List<PlaylistResponse> getUserPlaylists(Long userId);

    // UPDATE
    PlaylistResponse updatePlaylist(Long playlistId, PlaylistRequest request, Long userId);

    // DELETE
    void deletePlaylist(Long playlistId, Long userId);
}
