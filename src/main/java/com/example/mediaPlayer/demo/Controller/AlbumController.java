package com.example.mediaPlayer.demo.Controller;

import com.example.mediaPlayer.demo.Entity.Album;
import com.example.mediaPlayer.demo.ServiceI.AlbumServiceInterface;
import com.example.mediaPlayer.demo.dto.AlbumRequestDTO;
import com.example.mediaPlayer.demo.dto.response.AlbumResponseDTO;
import com.example.mediaPlayer.demo.dto.response.ApiSuccessResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/albums")
public class AlbumController {

    @Autowired
    private AlbumServiceInterface albumService;

    // ✅ CREATE (ADMIN)
    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/createAlbum")
    public ResponseEntity<ApiSuccessResponse<AlbumResponseDTO>> createAlbum(
            @RequestBody AlbumRequestDTO dto) {

        return ResponseEntity.ok(
                new ApiSuccessResponse<>(
                        true,
                        "Album created successfully",
                        albumService.createAlbum(dto),
                        LocalDateTime.now()
                )
        );
    }

    // ✅ GET ALL / BY ARTIST
    @GetMapping("/getAlbumsByArtistId")
    public ResponseEntity<ApiSuccessResponse<List<AlbumResponseDTO>>> getAlbums(
            @RequestParam(required = false) Long artistId) {

        List<AlbumResponseDTO> data =
                (artistId != null)
                        ? albumService.getAlbumsByArtist(artistId)
                        : albumService.getListOfAlbum();

        return ResponseEntity.ok(
                new ApiSuccessResponse<>(
                        true,
                        "Albums fetched successfully",
                        data,
                        LocalDateTime.now()
                )
        );
    }

    // ✅ GET BY ID
    @GetMapping("/getAlbumById/{id}")
    public ResponseEntity<ApiSuccessResponse<AlbumResponseDTO>> getAlbumById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                new ApiSuccessResponse<>(
                        true,
                        "Album fetched successfully",
                        albumService.getAlbumById(id),
                        LocalDateTime.now()
                )
        );
    }

    // ✅ DELETE (ADMIN)
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/deleteAlbum/{id}")
    public ResponseEntity<ApiSuccessResponse<String>> deleteAlbum(
            @PathVariable Long id) {

        albumService.deleteAlbum(id);

        return ResponseEntity.ok(
                new ApiSuccessResponse<>(
                        true,
                        "Album deleted successfully",
                        "Album removed",
                        LocalDateTime.now()
                )
        );
    }
}

