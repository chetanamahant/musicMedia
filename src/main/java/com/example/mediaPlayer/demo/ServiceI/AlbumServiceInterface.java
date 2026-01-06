package com.example.mediaPlayer.demo.ServiceI;

import com.example.mediaPlayer.demo.Entity.Album;
import com.example.mediaPlayer.demo.dto.AlbumRequestDTO;
import com.example.mediaPlayer.demo.dto.response.AlbumResponseDTO;

import java.util.List;

public interface AlbumServiceInterface {
    AlbumResponseDTO createAlbum(AlbumRequestDTO dto);

    List<AlbumResponseDTO> getListOfAlbum();

    List<AlbumResponseDTO> getAlbumsByArtist(Long artistId);

    AlbumResponseDTO getAlbumById(Long id);

    void deleteAlbum(Long id);
}
