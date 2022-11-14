package com.trilogyed.musicstorerecommendations.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.trilogyed.musicstorerecommendations.model.TrackRecommendation;
import com.trilogyed.musicstorerecommendations.repository.TrackRepository;
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

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(TrackRecommendationController.class)
@AutoConfigureMockMvc(addFilters = false)
public class TrackRecommendationControllerTest {
    @MockBean
    private TrackRepository trackRepo;
    @Autowired
    private ObjectMapper mapper;
    @Autowired
    MockMvc mockMvc;

    @Test
    public void getAllTrackRecommendationsAndReturn200() throws Exception{
        TrackRecommendation track = new TrackRecommendation(2,2,1, true);
        List<TrackRecommendation> trackRecommendationList = Arrays.asList(track);
        String expectedJson = mapper.writeValueAsString(trackRecommendationList);

        doReturn(trackRecommendationList).when(trackRepo).findAll();

        mockMvc.perform(MockMvcRequestBuilders.get("/trackRecommendation"))
                .andDo(print())
                .andExpect(status().isOk())
        .andExpect(content().json(expectedJson));
    }

    @Test
    public void shouldUpdateTrackRecommendation() throws Exception {
        TrackRecommendation track = new TrackRecommendation(3,3,3,true);
        String expectedJson = mapper.writeValueAsString(track);

        mockMvc.perform(put("/trackRecommendation/3")
                .content(expectedJson)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void getOneTrackRecommendationById() throws Exception {
        TrackRecommendation track = new TrackRecommendation(4,3,2, true);
        String expectedJson = mapper.writeValueAsString(track);

        doReturn(Optional.of(track)).when(trackRepo).findById(4);
        ResultActions result = mockMvc.perform(MockMvcRequestBuilders
                        .get("/trackRecommendation/4"))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedJson));
    }

    @Test
    public void shouldReturnNewTrackRecommendationShouldReturnNewLabel() throws Exception {
        TrackRecommendation input = new TrackRecommendation(169,1,1, true);
        TrackRecommendation output = new TrackRecommendation(169,1, true);

        String inputJson = mapper.writeValueAsString(input);
        String outputJson = mapper.writeValueAsString(output);

       when(trackRepo.save(input)).thenReturn(output);

        mockMvc.perform(post("/trackRecommendation")
                    .content(inputJson)
                    .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().json(outputJson));
    }

    @Test
    public void deleteTrackRecommendationByIdAndReturn200() throws Exception{
        TrackRecommendation track = new TrackRecommendation(1,1,1, true);
        mockMvc.perform(MockMvcRequestBuilders.delete("/trackRecommendation/1"))
                .andExpect(status().isNoContent());
    }
}