package com.talthur.project.entrypoint.api.controller.impl;

import com.talthur.project.core.domain.Planet;
import com.talthur.project.core.domain.Position;
import com.talthur.project.core.domain.Probe;
import com.talthur.project.core.domain.StarShip;
import com.talthur.project.core.enums.OrientationEnum;
import com.talthur.project.core.usecase.PlanetUseCase;
import com.talthur.project.entrypoint.api.controller.PlanetControllerApi;
import com.talthur.project.entrypoint.api.mapper.PlanetMapper;
import com.talthur.project.entrypoint.api.payload.CreateProbeIn;
import com.talthur.project.entrypoint.api.payload.MoveProbeIn;
import com.talthur.project.entrypoint.api.payload.PlanetIn;
import com.talthur.project.entrypoint.api.payload.PlanetOut;
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
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("v1/planets")
public class PlanetController implements PlanetControllerApi {

    private final PlanetUseCase planetUseCase;
    private final PlanetMapper planetMapper;

    @Override
    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public PlanetOut createPlanet(@Valid @RequestBody PlanetIn planet) {
        Planet newPlanet = planetUseCase.createPlanet(planet.x(), planet.y());
        return planetMapper.mapPlanetToPlanetsOut(newPlanet);
    }

    @Override
    @GetMapping
    @ResponseStatus(code = HttpStatus.OK)
    public List<PlanetOut> getPlanets() {
        List<Planet> planets = planetUseCase.getPlanets();
        return planetMapper.mapListOfPlanetsToListOfPlanetsOut(planets);
    }

    @PostMapping("/probes")
    @ResponseStatus(code = HttpStatus.CREATED)
    public ProbeOut createProbe(@RequestParam(name = "orientation") OrientationEnum orientationEnum,
        @Valid @RequestBody CreateProbeIn createProbeIn, Position position) {
        Probe probe = planetUseCase.placeProbe(createProbeIn.planetId(), position, orientationEnum,
            createProbeIn.name());
        return new ProbeOut(probe.getShipName(), probe.getActualPosition(), probe.getOrientation());
    }

    @Override
    @GetMapping("/{planetId}/{probeName}")
    @ResponseStatus(code = HttpStatus.OK)
    public ProbeOut getProbe(@PathVariable("planetId") String planetId, @PathVariable("probeName") String probeName) {
        StarShip probe = planetUseCase.getStarShip(planetId, probeName);
        return new ProbeOut(probe.getShipName(), probe.getActualPosition(), probe.getOrientation());
    }

    @Override
    @PatchMapping("/probes")
    @ResponseStatus(code = HttpStatus.OK)
    public ProbeOut moveProbe(@Valid @RequestBody MoveProbeIn moveProbeIn) {
        StarShip starShip = planetUseCase.moveStarShip(moveProbeIn.planetId(), moveProbeIn.probeName(), moveProbeIn.command());
        return new ProbeOut(starShip.getShipName(), starShip.getActualPosition(), starShip.getOrientation());
    }

}
