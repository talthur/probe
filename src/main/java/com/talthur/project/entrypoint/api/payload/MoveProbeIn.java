package com.talthur.project.entrypoint.api.payload;

import io.swagger.v3.oas.annotations.media.Schema;

public record MoveProbeIn(@Schema(description = "Probe name")String probeName,
                          @Schema(description = "Planet ID")String planetId,
                          @Schema(description = "Command to move and rotate", example = "LLRRMRM")String command) {

}
