package com.example.mediaPlayer.demo.ServiceImpl;


import com.example.mediaPlayer.demo.Entity.Genre;
import com.example.mediaPlayer.demo.Repo.GenreRepository;
import com.example.mediaPlayer.demo.ServiceI.GenreServiceInterface;
import com.example.mediaPlayer.demo.dto.response.GenreResponse;
import com.example.mediaPlayer.demo.exception.BadRequestException;
import com.example.mediaPlayer.demo.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GenreServiceImpl implements GenreServiceInterface {

    @Autowired
    private GenreRepository genreRepository;

    @Override
    public List<GenreResponse> getAllGenres() {

        return genreRepository.findAll()
                .stream()
                .map(g -> new GenreResponse(g.getId(), g.getName()))
                .toList();
    }

    @Override
    public GenreResponse getGenreById(Long id) {

        Genre genre = genreRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Genre not found"));

        return new GenreResponse(genre.getId(), genre.getName());
    }

    @Override
    public GenreResponse createGenre(Genre request) {

        if (genreRepository.existsByName(request.getName())) {
            throw new BadRequestException("Genre already exists");
        }

        Genre genre = new Genre();
        genre.setName(request.getName());

        Genre saved = genreRepository.save(genre);

        return new GenreResponse(saved.getId(), saved.getName());
    }

}
