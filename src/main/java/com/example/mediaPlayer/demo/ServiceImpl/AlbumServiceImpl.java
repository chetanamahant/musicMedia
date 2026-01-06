package com.example.mediaPlayer.demo.ServiceImpl;

import com.example.mediaPlayer.demo.Entity.Album;
import com.example.mediaPlayer.demo.Entity.Artist;
import com.example.mediaPlayer.demo.Repo.AlbumRepository;
import com.example.mediaPlayer.demo.Repo.ArtistRepository;
import com.example.mediaPlayer.demo.ServiceI.AlbumServiceInterface;
import com.example.mediaPlayer.demo.dto.AlbumRequestDTO;
import com.example.mediaPlayer.demo.dto.response.AlbumResponseDTO;
import com.example.mediaPlayer.demo.exception.ResourceNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AlbumServiceImpl implements AlbumServiceInterface {

   @Autowired
    private  ArtistRepository artistRepository;

   @Autowired
   private AlbumRepository albumRepository;

   @Autowired
   private ModelMapper mapper;

    // ---------------- CREATE ----------------
    @Override
    public AlbumResponseDTO createAlbum(AlbumRequestDTO dto) {

        Artist artist = artistRepository.findById(dto.getArtistId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Artist not found"));

        Album album = new Album();
        album.setName(dto.getName());
        album.setArtist(artist);

        Album saved = albumRepository.save(album);

        return mapToDTO(saved);
    }

    // ---------------- GET ALL ----------------
    @Override
    public List<AlbumResponseDTO> getListOfAlbum() {

        List<Album> albums = albumRepository.findAll();

        if (albums.isEmpty()) {
            throw new ResourceNotFoundException("No albums found");
        }

        return albums.stream()
                .map(this::mapToDTO)
                .toList();
    }

    // ---------------- GET BY ARTIST ----------------
    @Override
    public List<AlbumResponseDTO> getAlbumsByArtist(Long artistId) {

        List<Album> albums = albumRepository.findByArtistId(artistId);

        if (albums.isEmpty()) {
            throw new ResourceNotFoundException("No albums found for artist");
        }

        return albums.stream()
                .map(this::mapToDTO)
                .toList();
    }

    // ---------------- GET BY ID ----------------
    @Override
    public AlbumResponseDTO getAlbumById(Long id) {

        Album album = albumRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Album not found with id: " + id));

        return mapToDTO(album);
    }

    // ---------------- DELETE ----------------
    @Override
    public void deleteAlbum(Long id) {

        Album album = albumRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Album not found with id: " + id));

        albumRepository.delete(album);
    }

    // ---------------- MAPPER ----------------
    private AlbumResponseDTO mapToDTO(Album album) {
        return new AlbumResponseDTO(
                album.getId(),
                album.getName(),
                album.getArtist().getId(),
                album.getArtist().getName()
        );
    }
}
