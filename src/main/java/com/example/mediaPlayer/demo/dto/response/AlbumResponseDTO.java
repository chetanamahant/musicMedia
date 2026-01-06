package com.example.mediaPlayer.demo.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AlbumResponseDTO {
    private Long id;
    private String name;
    private Long artistId;
    private String artistName;
}
