package com.talthur.project.entrypoint;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.talthur.project.core.domain.Coordinates;
import com.talthur.project.core.domain.Planet;
import com.talthur.project.core.exception.BusinessException;
import com.talthur.project.core.exception.errors.BusinessError;
import com.talthur.project.core.usecase.PlanetUseCase;
import com.talthur.project.core.usecase.ProbeUseCase;
import com.talthur.project.entrypoint.api.controller.impl.PlanetController;
import com.talthur.project.entrypoint.api.handler.CustomGlobalExceptionHandler;
import com.talthur.project.entrypoint.api.mapper.PlanetMapper;
import com.talthur.project.entrypoint.api.payload.PlanetIn;
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

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = PlanetController.class)
@AutoConfigureMockMvc
@ContextConfiguration(classes = {PlanetController.class, CustomGlobalExceptionHandler.class})
class PlanetControllerTest {


    final Coordinates planetCoordinates = new Coordinates(3, 3);
    final String planetName = "string";
    final Planet planet = new Planet(planetName, planetCoordinates);
    final List<Planet> planetList = List.of(planet);

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private PlanetUseCase planetUseCase;

    @MockBean
    private ProbeUseCase probeUseCase;

    @MockBean
    private PlanetMapper planetMapper;

    @Test
    @DisplayName("Deve criar um planeta válido")
    void shouldCreatePlanetReturn201() throws Exception {
        mockMvc.perform(post("/v1/planets")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(new PlanetIn(planetName, planetCoordinates))))
            .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("Deve lançar exceção de BadRequest ao criar uma planeta com tamanho inválido")
    void shouldThrowExceptionReturn422CreatePlanet() throws Exception {
        when(planetUseCase.createPlanet(planetName, planetCoordinates))
            .thenThrow(new BusinessException(BusinessError.COORDINATES_INVALID));
        mockMvc.perform(post("/v1/planets")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(new PlanetIn(planetName, planetCoordinates))))
            .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Deve retornar uma lista de planetas")
    void shouldReturnPlanetsReturn200() throws Exception {
        when(planetUseCase.getPlanets())
            .thenReturn(planetList);
        mockMvc.perform(get("/v1/planets")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(new PlanetIn(planetName, planetCoordinates))))
            .andExpect(status().isOk());
    }

}


