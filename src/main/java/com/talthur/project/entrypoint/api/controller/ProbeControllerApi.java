package com.talthur.project.entrypoint.api.controller;

import com.talthur.project.core.enums.Direction;
import com.talthur.project.core.exception.ExceptionData;
import com.talthur.project.entrypoint.api.payload.CreateProbeIn;
import com.talthur.project.entrypoint.api.payload.MoveProbeIn;
import com.talthur.project.entrypoint.api.payload.ProbeOut;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.MediaType;

@OpenAPIDefinition(info = @Info(title = "Desafio Técnico", version = "1.0.0", description = "Desafio técnico de Java"))
public interface ProbeControllerApi {

    @Operation(summary = "Create probe", tags = "Probe Controller", responses = {
        @ApiResponse(responseCode = "200", description = "Probe Created", content = @Content(
            mediaType = MediaType.APPLICATION_JSON_VALUE,
            schema = @Schema(name = "Response for Probe Creation", implementation = ProbeOut.class)
        )),
        @ApiResponse(responseCode = "422", description = "Placement not allowed", content = @Content(
            mediaType = MediaType.APPLICATION_JSON_VALUE,
            schema = @Schema(name = "Internal server error", implementation = ExceptionData.class)
        ))
    })
    ProbeOut createProbe(Direction direction, CreateProbeIn createProbeIn);

    @Operation(summary = "Get probe", tags = "Probe Controller", responses = {
        @ApiResponse(responseCode = "200", description = "Get probe", content = @Content(
            mediaType = MediaType.APPLICATION_JSON_VALUE,
            schema = @Schema(name = "Response for get Probe", implementation = ProbeOut.class)
        )),
        @ApiResponse(responseCode = "404", description = "Planet or probe does not exist", content = @Content(
            mediaType = MediaType.APPLICATION_JSON_VALUE,
            schema = @Schema(name = "Not found", implementation = ExceptionData.class)
        ))
    })
    ProbeOut getProbe(String planetId, String probeName);

    @Operation(summary = "Move probe", tags = "Probe Controller", responses = {
        @ApiResponse(responseCode = "200", description = "Probe moved", content = @Content(
            mediaType = MediaType.APPLICATION_JSON_VALUE,
            schema = @Schema(name = "Response for Probe movement", implementation = ProbeOut.class)
        )),
        @ApiResponse(responseCode = "422", description = "Placement not allowed", content = @Content(
            mediaType = MediaType.APPLICATION_JSON_VALUE,
            schema = @Schema(name = "Internal server error", implementation = ExceptionData.class)
        )),
        @ApiResponse(responseCode = "404", description = "Planet or probe does not exist", content = @Content(
            mediaType = MediaType.APPLICATION_JSON_VALUE,
            schema = @Schema(name = "Not found", implementation = ExceptionData.class)
        ))
    })
    ProbeOut moveProbe(MoveProbeIn moveProbeIn);

}
