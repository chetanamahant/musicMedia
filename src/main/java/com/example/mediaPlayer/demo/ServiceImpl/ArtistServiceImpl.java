package com.example.mediaPlayer.demo.ServiceImpl;

import com.example.mediaPlayer.demo.Entity.Artist;
import com.example.mediaPlayer.demo.Repo.ArtistRepository;
import com.example.mediaPlayer.demo.ServiceI.ArtistServiceInterface;
import com.example.mediaPlayer.demo.dto.ArtistDTO;
import com.example.mediaPlayer.demo.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArtistServiceImpl implements ArtistServiceInterface {

    @Autowired
    private ArtistRepository artistRepository;

    @Override
    public List<Artist> getAllArtists() {
        return artistRepository.findAll();
    }

    @Override
    public Artist getArtistById(Long id) {
        return artistRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Artist not found with id: " + id));
    }

    @Override
    public Artist createArtist(ArtistDTO dto) {

        Artist artist = new Artist();
        artist.setName(dto.getName());
        artist.setBio(dto.getBio());

        return artistRepository.save(artist);
    }

    @Override
    public void deleteArtistById(Long id) {

        Artist artist = getArtistById(id);
        artistRepository.delete(artist);
    }
}
