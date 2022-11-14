package com.trilogyed.musicstorerecommendations.controller;

import com.trilogyed.musicstorerecommendations.model.ArtistRecommendation;
import com.trilogyed.musicstorerecommendations.repository.ArtistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("/artistRecommendation")
public class ArtistRecommendationController {
    @Autowired
    private ArtistRepository artistRepo;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ArtistRecommendation> getAllArtistRecommendations() {
              return artistRepo.findAll();

    }

    @PutMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ArtistRecommendation updateArtistRecommendation(@RequestBody ArtistRecommendation artist, @PathVariable int id) {
        if (!Objects.equals(artist.getId(), id)) {
            throw new IllegalArgumentException("Id numbers are not valid.");
        } else if (artist.getId() == null) {
            artist.setId(id);
        }
        return artistRepo.save(artist);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ArtistRecommendation getArtistById(@PathVariable("id") Integer id) {
        Optional<ArtistRecommendation> artist = artistRepo.findById(id);
        if (!artist.isPresent()) {
            throw new IllegalArgumentException("Artist id is invalid!");
        } else {
            return artist.get();
        }
    }

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public ArtistRecommendation addNewArtistRecommendation(@RequestBody ArtistRecommendation artist) {
        return artistRepo.save(artist);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteArtistRecommendationById(@PathVariable("id") Integer artistId) {
        artistRepo.deleteById(artistId);
    }
}
