package com.talthur.project.entrypoint.api.payload;

import io.swagger.v3.oas.annotations.media.Schema;


public record CreateProbeIn(@Schema(description = "Planet ID") String planetId,
                            @Schema(description = "X position of the probe") int x,
                            @Schema(description = "Y position of the probe") int y,
                            @Schema(description = "Probe name") String name) {

}
