package com.example.mediaPlayer.demo.ServiceImpl;

import com.example.mediaPlayer.demo.Entity.Playlist;
import com.example.mediaPlayer.demo.Entity.Song;
import com.example.mediaPlayer.demo.Entity.User;
import com.example.mediaPlayer.demo.Repo.PlaylistRepository;
import com.example.mediaPlayer.demo.Repo.SongRepository;
import com.example.mediaPlayer.demo.Repo.UserRepo;
import com.example.mediaPlayer.demo.ServiceI.PlaylistServiceInterface;
import com.example.mediaPlayer.demo.dto.request.PlaylistRequest;
import com.example.mediaPlayer.demo.dto.response.PlaylistResponse;
import com.example.mediaPlayer.demo.dto.response.SongResponseDTO;
import com.example.mediaPlayer.demo.exception.BadRequestException;
import com.example.mediaPlayer.demo.exception.ResourceNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class PlaylistServiceImpl implements PlaylistServiceInterface {

    @Autowired
    private PlaylistRepository playlistRepository;

    @Autowired
    private SongRepository songRepository;

    @Autowired
    private UserRepo userRepo;

    @Override
    public PlaylistResponse createPlaylist(PlaylistRequest request, Long userId) {

        if (request.getName() == null || request.getName().trim().isEmpty()) {
            throw new BadRequestException("Playlist name cannot be empty");
        }

        User user = userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Playlist playlist = new Playlist();
        playlist.setName(request.getName());
        playlist.setDescription(request.getDescription());
        playlist.setUser(user);

        return mapToResponse(playlistRepository.save(playlist));
    }

    @Override
    public void addSongToPlaylist(Long playlistId, Long songId, Long userId) {

        Playlist playlist = getPlaylistEntity(playlistId, userId);

        Song song = songRepository.findById(songId)
                .orElseThrow(() -> new ResourceNotFoundException("Song not found"));

        if (!playlist.getSongs().add(song)) {
            throw new BadRequestException("Song already exists in playlist");
        }

        playlistRepository.save(playlist);
    }

    @Override
    public void removeSongFromPlaylist(Long playlistId, Long songId, Long userId) {

        Playlist playlist = getPlaylistEntity(playlistId, userId);

        boolean removed = playlist.getSongs()
                .removeIf(song -> song.getId().equals(songId));

        if (!removed) {
            throw new ResourceNotFoundException("Song not found in playlist");
        }

        playlistRepository.save(playlist);
    }

    @Override
    public PlaylistResponse getPlaylistById(Long playlistId, Long userId) {

        Playlist playlist = getPlaylistEntity(playlistId, userId);
        return mapToResponse(playlist);
    }

    @Override
    public List<PlaylistResponse> getUserPlaylists(Long userId) {

        return playlistRepository.findByUser_Id(userId)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public PlaylistResponse updatePlaylist(Long playlistId, PlaylistRequest request, Long userId) {

        Playlist playlist = getPlaylistEntity(playlistId, userId);

        if (request.getName() != null && !request.getName().trim().isEmpty()) {
            playlist.setName(request.getName());
        }

        if (request.getDescription() != null) {
            playlist.setDescription(request.getDescription());
        }

        return mapToResponse(playlistRepository.save(playlist));
    }

    @Override
    public void deletePlaylist(Long playlistId, Long userId) {

        Playlist playlist = getPlaylistEntity(playlistId, userId);
        playlistRepository.delete(playlist);
    }

    // ======================
    // COMMON METHODS
    // ======================

    private Playlist getPlaylistEntity(Long playlistId, Long userId) {

        Playlist playlist = playlistRepository.findById(playlistId)
                .orElseThrow(() -> new ResourceNotFoundException("Playlist not found"));

        if (!playlist.getUser().getId().equals(userId)) {
            throw new BadRequestException("Not allowed to access this playlist");
        }
        return playlist;
    }

    private PlaylistResponse mapToResponse(Playlist playlist) {

        List<SongResponseDTO> songs = playlist.getSongs().stream()
                .map(song -> new SongResponseDTO(
                        song.getId(),
                        song.getTitle(),
                        song.getArtist() != null ? song.getArtist().getName() : null,
                        song.getAlbum() != null ? song.getAlbum().getName() : null
                ))
                .toList();

        return new PlaylistResponse(
                playlist.getId(),
                playlist.getName(),
                playlist.getDescription(),
                songs
        );
    }
}
