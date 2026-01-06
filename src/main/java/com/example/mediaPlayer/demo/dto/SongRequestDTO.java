package com.example.mediaPlayer.demo.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SongRequestDTO {

    @NotBlank
    private String title;

    @NotBlank
    private String filePath;

    @NotNull
    private Long genreId;

    private Long artistId;
    private Long albumId;
}
