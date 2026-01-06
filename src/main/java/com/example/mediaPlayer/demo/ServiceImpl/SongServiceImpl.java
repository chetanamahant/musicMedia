package com.example.mediaPlayer.demo.ServiceImpl;

import com.example.mediaPlayer.demo.Entity.Genre;
import com.example.mediaPlayer.demo.Entity.Song;
import com.example.mediaPlayer.demo.Repo.AlbumRepository;
import com.example.mediaPlayer.demo.Repo.ArtistRepository;
import com.example.mediaPlayer.demo.Repo.GenreRepository;
import com.example.mediaPlayer.demo.Repo.SongRepository;
import com.example.mediaPlayer.demo.ServiceI.SongServiceInterface;
import com.example.mediaPlayer.demo.dto.SongRequestDTO;
import com.example.mediaPlayer.demo.dto.response.SongResponseDTO;
import com.example.mediaPlayer.demo.exception.BadRequestException;
import com.example.mediaPlayer.demo.exception.ResourceNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;
import org.springframework.core.io.FileSystemResource;

@Service
public class SongServiceImpl implements SongServiceInterface {

    @Autowired
    private SongRepository songRepository;

    @Autowired
    private GenreRepository genreRepository;

    @Autowired
    private ArtistRepository artistRepository;

    @Autowired
    private AlbumRepository albumRepository;

    @Override
    public SongResponseDTO uploadSong(SongRequestDTO dto) {

        File file = new File(dto.getFilePath());
        if (!file.exists() || !file.isFile()) {
            throw new ResourceNotFoundException("Audio file not found at path");
        }

        Genre genre = genreRepository.findById(dto.getGenreId())
                .orElseThrow(() -> new ResourceNotFoundException("Genre not found"));

        Song song = new Song();
        song.setTitle(dto.getTitle());
        song.setFilePath(dto.getFilePath());
        song.setGenre(genre);

        if (dto.getArtistId() != null) {
            song.setArtist(
                    artistRepository.findById(dto.getArtistId())
                            .orElseThrow(() -> new ResourceNotFoundException("Artist not found"))
            );
        }

        if (dto.getAlbumId() != null) {
            song.setAlbum(
                    albumRepository.findById(dto.getAlbumId())
                            .orElseThrow(() -> new ResourceNotFoundException("Album not found"))
            );
        }

        return mapToDTO(songRepository.save(song));
    }

    @Override
    public List<SongResponseDTO> getSongs(Long genreId) {

        List<Song> songs = (genreId != null)
                ? songRepository.findByGenre_Id(genreId)
                : songRepository.findAll();

        return songs.stream().map(this::mapToDTO).toList();
    }

    @Override
    public SongResponseDTO getSongById(Long id) {

        Song song = songRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Song not found"));

        return mapToDTO(song);
    }

    @Override
    public List<SongResponseDTO> searchSongs(String keyword) {

        return songRepository.findByTitleContainingIgnoreCase(keyword)
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    @Override
    public ResponseEntity<Resource> streamSong(Long id) {

        Song song = songRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Song not found"));

        File file = new File(song.getFilePath());
        if (!file.exists()) {
            throw new ResourceNotFoundException("Audio file not found");
        }

        Resource resource = new FileSystemResource(file);

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType("audio/mpeg"))
                .contentLength(file.length())
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "inline; filename=\"" + file.getName() + "\"")
                .body(resource);
    }

    @Override
    public void deleteSong(Long id) {

        Song song = songRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Song not found"));

        songRepository.delete(song);
    }

    private SongResponseDTO mapToDTO(Song song) {

        return new SongResponseDTO(
                song.getId(),
                song.getTitle(),
                song.getGenre().getName(),
                song.getArtist() != null ? song.getArtist().getName() : null,
                song.getAlbum() != null ? song.getAlbum().getName() : null
        );
    }
}
