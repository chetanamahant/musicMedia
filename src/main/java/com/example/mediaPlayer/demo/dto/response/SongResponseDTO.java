package com.example.mediaPlayer.demo.dto.response;

import com.example.mediaPlayer.demo.Entity.Album;
import com.example.mediaPlayer.demo.Entity.Artist;
import com.example.mediaPlayer.demo.Entity.Genre;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SongResponseDTO {

    private Long id;
    private String title;
    private String genre;
    private String artist;
    private String album;

    public SongResponseDTO(Long id, String title, String genre, String artist, String album) {
        this.id = id;
        this.title = title;
        this.genre = genre;
        this.artist = artist;
        this.album = album;
    }

    public SongResponseDTO(Long id, String title, String s, String s1) {
    }
}