package com.trilogyed.musicstorerecommendations.controller;

import com.trilogyed.musicstorerecommendations.model.TrackRecommendation;
import com.trilogyed.musicstorerecommendations.repository.TrackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("/trackRecommendation")
public class TrackRecommendationController {

    @Autowired
    private TrackRepository trackRepo;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<TrackRecommendation> getAllTrackRecommendations() {
                return trackRepo.findAll();
    }

    @PutMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public TrackRecommendation updateTrackRecommendation(@RequestBody @Valid TrackRecommendation track, @PathVariable Integer id) {
        if (!Objects.equals(track.getId(), id)) {
            throw new IllegalArgumentException("Id numbers are not valid.");
        } else if (track.getId() == null) {
            track.setId(id);
        }
        return trackRepo.save(track);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public TrackRecommendation getTrackById(@PathVariable("id") Integer id) {
        Optional<TrackRecommendation> track = trackRepo.findById(id);
        if (!track.isPresent()) {
            throw new IllegalArgumentException("Track id is invalid!");
        } else {
            return track.get();
        }
    }

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public TrackRecommendation addNewTrackRecommendation(@RequestBody TrackRecommendation track) {
        return trackRepo.save(track);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTrackRecommendationById(@PathVariable("id") Integer trackId) {
        trackRepo.deleteById(trackId);
    }
 }

