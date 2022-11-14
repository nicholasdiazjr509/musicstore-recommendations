package com.trilogyed.musicstorerecommendations.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.trilogyed.musicstorerecommendations.model.AlbumRecommendation;
import com.trilogyed.musicstorerecommendations.repository.AlbumRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(AlbumRecommendationController.class)
@AutoConfigureMockMvc(addFilters = false)
public class AlbumRecommendationControllerTest {
    @MockBean
    private AlbumRepository albumRepo;
    @Autowired
    private ObjectMapper mapper = new ObjectMapper();
    @Autowired
    MockMvc mockMvc;

    @Test
    public void getAllTrackAlbumRecommendationsAndReturn200() throws Exception{
        AlbumRecommendation album = new AlbumRecommendation(2,2,1, true);
        List<AlbumRecommendation> albumRecommendationList = Arrays.asList(album);
        String expectedJson = mapper.writeValueAsString(albumRecommendationList);

        doReturn(albumRecommendationList).when(albumRepo).findAll();

        mockMvc.perform(MockMvcRequestBuilders.get("/albumRecommendation"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(expectedJson));
    }

    @Test
    public void shouldUpdateAlbumRecommendation() throws Exception {
        AlbumRecommendation album = new AlbumRecommendation(3,3,3,true);
        String expectedJson = mapper.writeValueAsString(album);

        mockMvc.perform(put("/albumRecommendation/3")
                        .content(expectedJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void getOneAlbumRecommendationById() throws Exception {
        AlbumRecommendation album = new AlbumRecommendation(4,3,2, true);
        String expectedJson = mapper.writeValueAsString(album);

        doReturn(Optional.of(album)).when(albumRepo).findById(4);
        ResultActions result = mockMvc.perform(MockMvcRequestBuilders
                        .get("/albumRecommendation/4"))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedJson));
    }

    @Test
    public void shouldReturnNewAlbumRecommendationShouldReturnNewLabel() throws Exception {
        AlbumRecommendation input = new AlbumRecommendation(169,1,1, true);
        AlbumRecommendation output = new AlbumRecommendation(169,1, true);

        String inputJson = mapper.writeValueAsString(input);
        String outputJson = mapper.writeValueAsString(output);

        when(albumRepo.save(input)).thenReturn(output);

        mockMvc.perform(post("/albumRecommendation")
                        .content(inputJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().json(outputJson));
    }

    @Test
    public void deleteAlbumRecommendationByIdAndReturn200() throws Exception{
        AlbumRecommendation album = new AlbumRecommendation(1,1,1, true);
        mockMvc.perform(MockMvcRequestBuilders.delete("/albumRecommendation/1"))
                .andExpect(status().isNoContent());
    }
}