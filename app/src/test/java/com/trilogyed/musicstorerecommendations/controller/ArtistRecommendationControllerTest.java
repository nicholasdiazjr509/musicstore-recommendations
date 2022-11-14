package com.trilogyed.musicstorerecommendations.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.trilogyed.musicstorerecommendations.model.ArtistRecommendation;
import com.trilogyed.musicstorerecommendations.repository.ArtistRepository;
import org.junit.Before;
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
@WebMvcTest(ArtistRecommendationController.class)
@AutoConfigureMockMvc(addFilters = false)
public class ArtistRecommendationControllerTest {

    @MockBean
    private ArtistRepository artistRepo;
    @Autowired
    private ObjectMapper mapper = new ObjectMapper();
    @Autowired
    MockMvc mockMvc;

    @Test
    public void getAllArtistRecommendationsAndReturn200() throws Exception{
        ArtistRecommendation artist = new ArtistRecommendation(3,3,3, true);
        List<ArtistRecommendation> artistRecommendationList = Arrays.asList(artist);
        String expectedJson = mapper.writeValueAsString(artistRecommendationList);

        doReturn(artistRecommendationList).when(artistRepo).findAll();

        mockMvc.perform(MockMvcRequestBuilders.get("/artistRecommendation"))
                .andDo(print())
                .andExpect(status().isOk())
            .andExpect(content().json(expectedJson));
    }

    @Test
    public void shouldUpdateArtistById() throws Exception {
        ArtistRecommendation artist = new ArtistRecommendation(3,3,3,true);
        String expectedJson = mapper.writeValueAsString(artist);

        mockMvc.perform(put("/artistRecommendation/3")
                        .content(expectedJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void getOneArtistRecommendationById() throws Exception {
        ArtistRecommendation artist = new ArtistRecommendation(4,3,3, true);
        String expectedJson = mapper.writeValueAsString(artist);

        doReturn(Optional.of(artist)).when(artistRepo).findById(4);
        ResultActions result = mockMvc.perform(MockMvcRequestBuilders
                        .get("/artistRecommendation/4"))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedJson));
    }

    @Test
    public void shouldReturnNewArtistRecommendationShouldReturnNewArtist() throws Exception {
        ArtistRecommendation input = new ArtistRecommendation(169,1,1, true);
        ArtistRecommendation output = new ArtistRecommendation(169,1, true);

        String inputJson = mapper.writeValueAsString(input);
        String outputJson = mapper.writeValueAsString(output);

        when(artistRepo.save(input)).thenReturn(output);


        mockMvc.perform(post("/artistRecommendation")
                        .content(inputJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().json(outputJson));
    }

    @Test
    public void deleteArtistRecommendationByIdAndReturn200() throws Exception{
        ArtistRecommendation artist = new ArtistRecommendation(1,1,1, true);
        mockMvc.perform(MockMvcRequestBuilders.delete("/artistRecommendation/1"))
                .andExpect(status().isNoContent());
    }
}