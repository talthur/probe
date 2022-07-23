package com.talthur.project.core.entrypoint;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.talthur.project.core.exception.BusinessException;
import com.talthur.project.core.exception.errors.BusinessError;
import com.talthur.project.core.usecase.PlanetUseCase;
import com.talthur.project.entrypoint.api.controller.impl.PlanetController;
import com.talthur.project.entrypoint.api.handler.CustomGlobalExceptionHandler;
import com.talthur.project.entrypoint.api.mapper.PlanetMapper;
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

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = PlanetController.class)
@AutoConfigureMockMvc
@ContextConfiguration(classes = {PlanetController.class, CustomGlobalExceptionHandler.class})
class PlanetControllerTest {

    final ImmutablePair<Integer, Integer> payloadVálido = new ImmutablePair<Integer, Integer>(4, 4);
    final ImmutablePair<Integer, Integer> payloadInválido = new ImmutablePair<Integer, Integer>(0, 0);

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
    void shouldCreateProbeReturn200() throws Exception {
        mockMvc.perform(post("/v1/planets")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(new PlanetIn(payloadVálido.getLeft(), payloadVálido.getRight()))))
            .andExpect(status().is2xxSuccessful());
    }

    @Test
    @DisplayName("Deve lançar exceção de ")
    void shouldThrowExceptiontReturn422() throws Exception {
        when(planetUseCase.createPlanet(payloadInválido.getLeft(), payloadInválido.getRight()))
            .thenThrow(new BusinessException(BusinessError.COORDINATES_INVALID));
        mockMvc.perform(post("/v1/planets")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(new PlanetIn(payloadInválido.getLeft(), payloadInválido.getRight()))))
            .andExpect(status().isBadRequest());
    }

}
