package com.talthur.project.entrypoint;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.talthur.project.core.domain.Coordinates;
import com.talthur.project.core.domain.Planet;
import com.talthur.project.core.domain.Probe;
import com.talthur.project.core.enums.Direction;
import com.talthur.project.core.exception.BusinessException;
import com.talthur.project.core.exception.errors.BusinessError;
import com.talthur.project.core.usecase.ProbeUseCase;
import com.talthur.project.entrypoint.api.controller.impl.ProbeController;
import com.talthur.project.entrypoint.api.handler.CustomGlobalExceptionHandler;
import com.talthur.project.entrypoint.api.payload.CreateProbeIn;
import com.talthur.project.entrypoint.api.payload.MoveProbeIn;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = ProbeController.class)
@AutoConfigureMockMvc
@ContextConfiguration(classes = {ProbeController.class, CustomGlobalExceptionHandler.class})
class ProbeControllerTest {

    final Coordinates probeCoordinates = new Coordinates(1, 1);
    final Coordinates planetCoordinates = new Coordinates(3, 3);
    final Coordinates expectedCoordinates = new Coordinates(2, 3);
    final String directionParameter = "direction";
    final String planetName = "string";
    final Planet planet = new Planet(planetName, planetCoordinates);
    final List<Planet> planetList = List.of(planet);
    final String probeName = "Dog";
    final Probe probe = new Probe(Direction.NORTH, probeName, probeCoordinates, planet);
    final String validCommand = "LLRM";
    final String invalidCommand = "LLRMBP";


    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ProbeUseCase probeUseCase;

    @Test
    @DisplayName("Deve criar uma probe válida")
    void shouldCreateProbeReturn201() throws Exception {
        when(probeUseCase.createProbeOnPlanet(planetName, probeCoordinates, Direction.NORTH, probeName))
            .thenReturn(probe);
        mockMvc.perform(post("/v1/probes")
                .contentType(MediaType.APPLICATION_JSON)
                .param(directionParameter, Direction.NORTH.name())
                .content(objectMapper.writeValueAsString(new CreateProbeIn(
                    planetName, probeName, probeCoordinates))))
            .andExpect(status().isCreated());
    }

        @Test
        @DisplayName("Deve retornar 404 ao tentar criar uma probe")
        void shouldNotCreateProbeReturn404() throws Exception {
            when(probeUseCase.createProbeOnPlanet(planetName, probeCoordinates, Direction.NORTH, probeName))
                .thenThrow(new BusinessException(BusinessError.PLANET_NOT_EXIST));
            mockMvc.perform(post("/v1/probes")
                    .contentType(MediaType.APPLICATION_JSON)
                    .param(directionParameter, Direction.NORTH.name())
                    .content(objectMapper.writeValueAsString(new CreateProbeIn(
                        planetName, probeName, probeCoordinates))))
                .andExpect(status().isNotFound());
        }

        @Test
        @DisplayName("Deve retornar 422 ao tentar criar uma probe em um espaço que já existe uma outra probe")
        void shouldNotCreateProbeReturn422() throws Exception {
            when(probeUseCase.createProbeOnPlanet(planetName, probeCoordinates, Direction.NORTH, probeName))
                .thenThrow(new BusinessException(BusinessError.PLACEMENT_NOT_ALLOWED_OCUPPIED));
            mockMvc.perform(post("/v1/probes")
                    .contentType(MediaType.APPLICATION_JSON)
                    .param(directionParameter, Direction.NORTH.name())
                    .content(objectMapper.writeValueAsString(new CreateProbeIn(
                        planetName, probeName, probeCoordinates))))
                .andExpect(status().isUnprocessableEntity());
        }

        @Test
        @DisplayName("Deve retornar uma probe")
        void shouldNotGetProbeReturn200() throws Exception {
            when(probeUseCase.getProbe(planetName, probeName))
                .thenReturn(probe);
            mockMvc.perform(MockMvcRequestBuilders
                    .get("/v1/probes/{planetId}/{probeName}", planetName, probeName))
                .andExpect(status().isOk());
        }

        @Test
        @DisplayName("Deve realizar um movimento válido de uma probe")
        void shouldMoveProbeReturn() throws Exception {
            when(probeUseCase.moveProbe(planetName, probeName, validCommand)).thenReturn(probe);
            mockMvc.perform(patch("/v1/probes")
                    .contentType(MediaType.APPLICATION_JSON)
                    .param("orientation", Direction.NORTH.name())
                    .content(objectMapper.writeValueAsString(new MoveProbeIn(probeName, planetName, validCommand))))
                .andExpect(status().isOk());
        }

        @Test
        @DisplayName("Deve realizar um movimento válido de uma probe")
        void shouldNotMoveProbeReturn400() throws Exception {
            when(probeUseCase.moveProbe(planetName, probeName, invalidCommand)).thenReturn(probe);
            mockMvc.perform(patch("/v1/probes")
                    .contentType(MediaType.APPLICATION_JSON)
                    .param("orientation", Direction.NORTH.name())
                    .content(objectMapper.writeValueAsString(new MoveProbeIn(probeName, planetName, invalidCommand))))
                .andExpect(status().isBadRequest());
        }
}


