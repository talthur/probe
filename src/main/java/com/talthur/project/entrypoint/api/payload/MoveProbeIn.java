package com.talthur.project.entrypoint.api.payload;

import io.swagger.v3.oas.annotations.media.Schema;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public record MoveProbeIn(@Schema(description = "Probe name")
                          @NotNull
                          @Max(20)
                          String probeName,

                          @Schema(description = "Planet ID")
                          String planetId,

                          @Schema(description = "Command to move and rotate. 1 to 10 max", example = "LLRRMRM")
                          @Pattern(regexp = "^[LRM]+$")
                          @Size(min = 1, max = 10)
                          String command) {

}
