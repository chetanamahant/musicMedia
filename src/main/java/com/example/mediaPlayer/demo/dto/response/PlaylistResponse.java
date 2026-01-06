package com.example.mediaPlayer.demo.dto.response;

import com.example.mediaPlayer.demo.Entity.Playlist;
import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PlaylistResponse {

    private Long id;
    private String name;
    private String description;
    private List<SongResponseDTO> songs;
}

