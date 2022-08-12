package com.talthur.project.entrypoint.api.controller;

import com.talthur.project.entrypoint.api.payload.PlanetIn;
import com.talthur.project.entrypoint.api.payload.PlanetOut;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.MediaType;

import java.util.List;

@OpenAPIDefinition(info = @Info(title = "Desafio Técnico", version = "1.0.0", description = "Desafio técnico de Java"))
public interface PlanetControllerApi {

    @Operation(summary = "Create Planet", tags = "Planet Controller", responses = {
        @ApiResponse(responseCode = "200", description = "Planet Created", content = @Content(
            mediaType = MediaType.APPLICATION_JSON_VALUE,
            schema = @Schema(name = "Response for Planet Creation", implementation = PlanetOut.class)
        )),
    })
    PlanetOut createPlanet(PlanetIn planetIn);

    @Operation(summary = "Get planets", tags = "Planet Controller", responses = {
        @ApiResponse(responseCode = "200", description = "Planets retrieved", content = @Content(
            mediaType = MediaType.APPLICATION_JSON_VALUE,
            schema = @Schema(name = "Response for Planet Creation", implementation = PlanetOut.class)
        )),
    })
    List<PlanetOut> getPlanets();

}
