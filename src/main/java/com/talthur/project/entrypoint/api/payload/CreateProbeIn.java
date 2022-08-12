package com.talthur.project.entrypoint.api.payload;

import com.talthur.project.core.domain.Coordinates;
import io.swagger.v3.oas.annotations.media.Schema;

import javax.validation.constraints.Size;


public record CreateProbeIn(@Schema(description = "Planet Name")
                            String planetName,

                            @Schema(description = "Probe name")
                            @Size(min = 1, max = 20)
                            String name,

                            Coordinates coordinates) {

}
