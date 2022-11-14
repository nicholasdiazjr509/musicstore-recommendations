package com.trilogyed.musicstorerecommendations.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.trilogyed.musicstorerecommendations.model.LabelRecommendation;
import com.trilogyed.musicstorerecommendations.model.TrackRecommendation;
import com.trilogyed.musicstorerecommendations.repository.LabelRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
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
@WebMvcTest(LabelRecommendationController.class)
public class LabelRecommendationControllerTest {

    @MockBean
    private LabelRepository labelRepo;

    @Autowired
    MockMvc mockMvc;

    private ObjectMapper mapper = new ObjectMapper();

    @Test
    public void getAllLabelRecommendations() throws Exception {
        LabelRecommendation label = new LabelRecommendation(2,2, true);
        List<LabelRecommendation> labelRecommendationList = Arrays.asList(label);
        String expectedJson = mapper.writeValueAsString(labelRecommendationList);

        doReturn(labelRecommendationList).when(labelRepo).findAll();

        mockMvc.perform(MockMvcRequestBuilders.get("/labelRecommendation"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(expectedJson));
    }

    @Test
    public void shouldUpdateLabelById() throws Exception {
        LabelRecommendation label = new LabelRecommendation(1,1,1,true);
        String expectedJson = mapper.writeValueAsString(label);

        mockMvc.perform(put("/labelRecommendation/1")
                        .content(expectedJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldGetTrackById() throws Exception{
        LabelRecommendation label = new LabelRecommendation(4,3,2, true);
        String expectedJson = mapper.writeValueAsString(label);

        doReturn(Optional.of(label)).when(labelRepo).findById(4);
        ResultActions result = mockMvc.perform(MockMvcRequestBuilders
                        .get("/labelRecommendation/4"))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedJson));
    }

    @Test
    public void shouldAddNewLabelRecommendation() throws Exception {
        LabelRecommendation input = new LabelRecommendation(169,1,1, true);
        LabelRecommendation output = new LabelRecommendation(169,1, true);

        String inputJson = mapper.writeValueAsString(input);
        String outputJson = mapper.writeValueAsString(output);

        when(labelRepo.save(input)).thenReturn(output);

        mockMvc.perform(post("/labelRecommendation")
                        .content(inputJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().json(outputJson));
    }

    @Test
    public void deleteLabelRecommendationById() throws Exception {
        LabelRecommendation label = new LabelRecommendation(1,1,1, true);
        mockMvc.perform(MockMvcRequestBuilders.delete("/labelRecommendation/1"))
                .andExpect(status().isNoContent());
    }
}