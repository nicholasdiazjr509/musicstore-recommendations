package com.trilogyed.musicstorerecommendations.controller;

import com.trilogyed.musicstorerecommendations.model.AlbumRecommendation;
import com.trilogyed.musicstorerecommendations.repository.AlbumRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("/albumRecommendation")
public class AlbumRecommendationController {
    @Autowired
    private AlbumRepository albumRepo;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<AlbumRecommendation> getAllAlbumRecommendations() {
            return albumRepo.findAll();
    }

    @PutMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public AlbumRecommendation updateAlbumRecommendation(@RequestBody @Valid AlbumRecommendation album, @PathVariable Integer id) {
        if (!Objects.equals(album.getId(), id)) {
            throw new IllegalArgumentException("Id numbers are not valid.");
        } else if (album.getId() == null) {
            album.setId(id);
        }
        return albumRepo.save(album);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public AlbumRecommendation getAlbumById(@PathVariable("id") Integer id) {
        Optional<AlbumRecommendation> album = albumRepo.findById(id);
        if (!album.isPresent()) {
            throw new IllegalArgumentException("Label id is invalid!");
        } else {
            return album.get();
        }
    }

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public AlbumRecommendation addNewAlbumRecommendation(@RequestBody AlbumRecommendation album) {
        return albumRepo.save(album);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAlbumRecommendationById(@PathVariable("id") Integer albumId) {
        albumRepo.deleteById(albumId);
    }
}
