package com.talthur.project.entrypoint;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.talthur.project.core.domain.Planet;
import com.talthur.project.core.domain.Probe;
import com.talthur.project.core.domain.StarShip;
import com.talthur.project.core.enums.OrientationEnum;
import com.talthur.project.core.exception.BusinessException;
import com.talthur.project.core.exception.errors.BusinessError;
import com.talthur.project.core.usecase.PlanetUseCase;
import com.talthur.project.entrypoint.api.controller.impl.PlanetController;
import com.talthur.project.entrypoint.api.handler.CustomGlobalExceptionHandler;
import com.talthur.project.entrypoint.api.mapper.PlanetMapper;
import com.talthur.project.entrypoint.api.payload.CreateProbeIn;
import com.talthur.project.entrypoint.api.payload.MoveProbeIn;
import com.talthur.project.entrypoint.api.payload.PlanetIn;
import org.apache.commons.lang3.tuple.ImmutablePair;
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
import java.util.UUID;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = PlanetController.class)
@AutoConfigureMockMvc
@ContextConfiguration(classes = {PlanetController.class, CustomGlobalExceptionHandler.class})
class PlanetControllerTest {

    final ImmutablePair<Integer, Integer> validPayload = new ImmutablePair<>(4, 4);
    final ImmutablePair<Integer, Integer> invalidPayload = new ImmutablePair<>(0, 0);
    final List<Planet> planetList = List.of(new Planet(validPayload.getLeft(), validPayload.getRight()));
    final String planetId = UUID.randomUUID().toString();
    final String probeName = "Dog";
    final StarShip probe = new Probe(OrientationEnum.NORTH, probeName, validPayload.getLeft(), validPayload.getRight());
    final String validCommand = "LLRM";
    final String invalidCommand = "LLRMBP";


    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private PlanetUseCase planetUseCase;

    @MockBean
    private PlanetMapper planetMapper;

    @Test
    @DisplayName("Deve criar um planeta válido")
    void shouldCreatePlanetReturn201() throws Exception {
        mockMvc.perform(post("/v1/planets")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(new PlanetIn(validPayload.getLeft(), validPayload.getRight()))))
            .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("Deve lançar exceção de BadRequest ao criar um planeta com tamanho inválido")
    void shouldThrowExceptionReturn422CreatePlanet() throws Exception {
        when(planetUseCase.createPlanet(invalidPayload.getLeft(), invalidPayload.getRight()))
            .thenThrow(new BusinessException(BusinessError.COORDINATES_INVALID));
        mockMvc.perform(post("/v1/planets")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(new PlanetIn(invalidPayload.getLeft(), invalidPayload.getRight()))))
            .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Deve retornar uma lista de planetas")
    void shouldReturnPlanetsReturn200() throws Exception {
        when(planetUseCase.getPlanets())
            .thenReturn(planetList);
        mockMvc.perform(get("/v1/planets")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(new PlanetIn(invalidPayload.getLeft(), invalidPayload.getRight()))))
            .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Deve criar uma probe válida")
    void shouldCreateProbeReturn201() throws Exception {
        when(planetUseCase.placeProbe(planetId, validPayload.getLeft(), validPayload.getRight(), OrientationEnum.NORTH, probeName))
            .thenReturn(probe);
        mockMvc.perform(post("/v1/planets/probes")
                .contentType(MediaType.APPLICATION_JSON)
                .param("orientation", OrientationEnum.NORTH.name())
                .content(objectMapper.writeValueAsString(new CreateProbeIn(planetId, validPayload.getLeft(), validPayload.getRight(), probeName))))
            .andExpect(status().isCreated());
    }


    @Test
    @DisplayName("Deve retornar 404 ao tentar criar uma probe")
    void shouldNotCreateProbeReturn404() throws Exception {
        when(planetUseCase.placeProbe(planetId, validPayload.getLeft(), validPayload.getRight(), OrientationEnum.NORTH, probeName))
            .thenThrow(new BusinessException(BusinessError.PLANET_NOT_EXIST));
        mockMvc.perform(post("/v1/planets/probes")
                .contentType(MediaType.APPLICATION_JSON)
                .param("orientation", OrientationEnum.NORTH.name())
                .content(objectMapper.writeValueAsString(new CreateProbeIn(planetId, validPayload.getLeft(), validPayload.getRight(), probeName))))
            .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Deve retornar 422 ao tentar criar uma probe em um espaço que já existe uma outra probe")
    void shouldNotCreateProbeReturn422() throws Exception {
        when(planetUseCase.placeProbe(planetId, validPayload.getLeft(), validPayload.getRight(), OrientationEnum.NORTH, probeName))
            .thenThrow(new BusinessException(BusinessError.PLACEMENT_NOT_ALLOWED_OCUPPIED));
        mockMvc.perform(post("/v1/planets/probes")
                .contentType(MediaType.APPLICATION_JSON)
                .param("orientation", OrientationEnum.NORTH.name())
                .content(objectMapper.writeValueAsString(new CreateProbeIn(planetId, validPayload.getLeft(), validPayload.getRight(), probeName))))
            .andExpect(status().isUnprocessableEntity());
    }

    @Test
    @DisplayName("Deve retornar uma probe")
    void shouldNotGetProbeReturn200() throws Exception {
        when(planetUseCase.getStarShip(planetId, probeName))
            .thenReturn(probe);
        mockMvc.perform(MockMvcRequestBuilders
            .get("/v1/planets/{planetId}/{probeName}", planetId, probeName))
            .andExpect(status().isOk());

    }


    @Test
    @DisplayName("Deve realizar um movimento válido de uma probe")
    void shouldMoveProbeReturn() throws Exception {
        when(planetUseCase.moveStarShip(planetId, probeName, validCommand)).thenReturn(probe);
        mockMvc.perform(put("/v1/planets/probes")
                .contentType(MediaType.APPLICATION_JSON)
                .param("orientation", OrientationEnum.NORTH.name())
                .content(objectMapper.writeValueAsString(new MoveProbeIn(probeName, planetId, validCommand))))
            .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Deve realizar um movimento válido de uma probe")
    void shouldNotMoveProbeReturn400() throws Exception {
        when(planetUseCase.moveStarShip(planetId, probeName, invalidCommand)).thenReturn(probe);
        mockMvc.perform(put("/v1/planets/probes")
                .contentType(MediaType.APPLICATION_JSON)
                .param("orientation", OrientationEnum.NORTH.name())
                .content(objectMapper.writeValueAsString(new MoveProbeIn(probeName, planetId, invalidCommand))))
            .andExpect(status().isBadRequest());
    }

}
