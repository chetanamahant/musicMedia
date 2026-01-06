package com.example.mediaPlayer.demo.ServiceI;

import com.example.mediaPlayer.demo.Entity.Artist;
import com.example.mediaPlayer.demo.dto.ArtistDTO;

import java.util.List;

public interface ArtistServiceInterface {
    List<Artist> getAllArtists();
    Artist getArtistById(Long id);

    Artist createArtist(ArtistDTO artist);

    void deleteArtistById(Long id);
}
