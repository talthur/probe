package com.talthur.project.entrypoint.api.controller.impl;

import com.talthur.project.core.domain.Probe;
import com.talthur.project.core.enums.Direction;
import com.talthur.project.core.usecase.ProbeUseCase;
import com.talthur.project.entrypoint.api.controller.ProbeControllerApi;
import com.talthur.project.entrypoint.api.payload.CreateProbeIn;
import com.talthur.project.entrypoint.api.payload.MoveProbeIn;
import com.talthur.project.entrypoint.api.payload.ProbeOut;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("v1/probes")
public class ProbeController implements ProbeControllerApi {

    private final ProbeUseCase probeUseCase;

    @Override
    @PostMapping("/probes")
    @ResponseStatus(code = HttpStatus.CREATED)
    public ProbeOut createProbe(@RequestParam(name = "Direction") Direction direction,
        @Valid @RequestBody CreateProbeIn createProbeIn) {
        Probe probe = probeUseCase.createProbeOnPlanet(createProbeIn.planetName(), createProbeIn.coordinates(), direction,
            createProbeIn.name());
        return new ProbeOut(probe.getProbeName(), probe.getCoordinates(), probe.getDirection());
    }

    @Override
    @GetMapping("/{planetName}/{probeName}")
    @ResponseStatus(code = HttpStatus.OK)
    public ProbeOut getProbe(@PathVariable("planetName") String planetName, @PathVariable("probeName") String probeName) {
        Probe probe = probeUseCase.getProbe(planetName, probeName);
        return new ProbeOut(probe.getProbeName(), probe.getCoordinates(), probe.getDirection());
    }

    @Override
    @PatchMapping("/probes")
    @ResponseStatus(code = HttpStatus.OK)
    public ProbeOut moveProbe(@Valid @RequestBody MoveProbeIn moveProbeIn) {
        Probe probe = probeUseCase.moveProbe(moveProbeIn.planetName(), moveProbeIn.probeName(), moveProbeIn.command());
        return new ProbeOut(probe.getProbeName(), probe.getCoordinates(), probe.getDirection());
    }

}
