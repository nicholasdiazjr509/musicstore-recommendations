package com.trilogyed.musicstorerecommendations.controller;

import com.trilogyed.musicstorerecommendations.model.LabelRecommendation;

import com.trilogyed.musicstorerecommendations.repository.LabelRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("/labelRecommendation")
public class LabelRecommendationController {

    @Autowired
    private LabelRepository labelRepo;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<LabelRecommendation> getAllLabelRecommendations() {
                 return labelRepo.findAll();
            }

    @PutMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public LabelRecommendation updateLabelRecommendation(@RequestBody @Valid LabelRecommendation label, @PathVariable Integer id) {
        if (!Objects.equals(label.getId(), id)) {
            throw new IllegalArgumentException("Id numbers are not valid.");
        } else if (label.getId() == null) {
            label.setId(id);
        }
        return labelRepo.save(label);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public LabelRecommendation getTrackById(@PathVariable("id") Integer id) {
        Optional<LabelRecommendation> label = labelRepo.findById(id);
        if (!label.isPresent()) {
            throw new IllegalArgumentException("Label id is invalid!");
        } else {
            return label.get();
        }
    }

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public LabelRecommendation addNewLabelRecommendation(@RequestBody LabelRecommendation label) {
        return labelRepo.save(label);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteLabelRecommendationById(@PathVariable("id") Integer labelId) {
        labelRepo.deleteById(labelId);
    }
}
