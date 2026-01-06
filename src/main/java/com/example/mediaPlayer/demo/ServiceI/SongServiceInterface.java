package com.example.mediaPlayer.demo.ServiceI;

import com.example.mediaPlayer.demo.dto.SongRequestDTO;
import com.example.mediaPlayer.demo.dto.response.SongResponseDTO;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface SongServiceInterface {

    SongResponseDTO uploadSong(SongRequestDTO dto);

    List<SongResponseDTO> getSongs(Long genreId);

    SongResponseDTO getSongById(Long id);

    List<SongResponseDTO> searchSongs(String keyword);

    ResponseEntity<Resource> streamSong(Long id);

    void deleteSong(Long id);
}
