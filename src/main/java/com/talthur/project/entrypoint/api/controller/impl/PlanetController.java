package com.talthur.project.entrypoint.api.controller.impl;

import com.talthur.project.core.domain.Planet;
import com.talthur.project.core.domain.StarShip;
import com.talthur.project.entrypoint.api.controller.PlanetControllerApi;
import com.talthur.project.entrypoint.api.mapper.PlanetMapper;
import com.talthur.project.entrypoint.api.payload.CreateProbeIn;
import com.talthur.project.entrypoint.api.payload.MoveProbeIn;
import com.talthur.project.entrypoint.api.payload.PlanetIn;
import com.talthur.project.entrypoint.api.payload.PlanetOut;
import com.talthur.project.entrypoint.api.payload.ProbeOut;
import com.talthur.project.core.enums.OrientationEnum;
import com.talthur.project.core.usecase.PlanetUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.Objects;

@RestController
@RequiredArgsConstructor
@RequestMapping("v1/planets")
public class PlanetController implements PlanetControllerApi {

    private final PlanetUseCase planetUseCase;
    private final PlanetMapper planetMapper;

    @Override
    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public PlanetOut createPlanet(@RequestBody PlanetIn planet) {
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

    @Override
    @PostMapping("/probes")
    @ResponseStatus(code = HttpStatus.CREATED)
    public ProbeOut createProbe(@RequestParam(name = "orientation") OrientationEnum orientationEnum, @RequestBody CreateProbeIn createProbeIn) {
        if (Objects.isNull(orientationEnum)) {
            orientationEnum = OrientationEnum.NORTH;
        }
        StarShip probe = planetUseCase.placeProbe(createProbeIn.planetId(), createProbeIn.x(), createProbeIn.y(), orientationEnum,
            createProbeIn.name());
        return new ProbeOut(probe.getShipName(), probe.getActualPosition(), probe.getOrientation());
    }

    @Override
    @GetMapping("/{planetId}/{probeName}")
    @ResponseStatus(code = HttpStatus.OK)
    public ProbeOut getProbe(@PathVariable("planetId") String planetId, @PathVariable("probeName") String probeName) {
        StarShip probe = planetUseCase.getProbe(planetId, probeName);
        return new ProbeOut(probe.getShipName(), probe.getActualPosition(), probe.getOrientation());
    }

    @Override
    @PutMapping("/probes")
    @ResponseStatus(code = HttpStatus.OK)
    public ProbeOut moveProbe(@Valid @RequestBody MoveProbeIn moveProbeIn) {
        StarShip starShip = planetUseCase.moveProbe(moveProbeIn.planetId(), moveProbeIn.probeName(), moveProbeIn.command());
        return new ProbeOut(starShip.getShipName(), starShip.getActualPosition(), starShip.getOrientation());
    }

}
