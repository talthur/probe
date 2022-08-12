package com.talthur.project.entrypoint.api.controller.impl;

import com.talthur.project.core.domain.Planet;
import com.talthur.project.core.usecase.PlanetUseCase;
import com.talthur.project.entrypoint.api.controller.PlanetControllerApi;
import com.talthur.project.entrypoint.api.mapper.PlanetMapper;
import com.talthur.project.entrypoint.api.payload.PlanetIn;
import com.talthur.project.entrypoint.api.payload.PlanetOut;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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
        Planet newPlanet = planetUseCase.createPlanet(planet.name(), planet.coordinates());
        return planetMapper.mapPlanetToPlanetsOut(newPlanet);
    }

    @Override
    @GetMapping
    @ResponseStatus(code = HttpStatus.OK)
    public List<PlanetOut> getPlanets() {
        List<Planet> planets = planetUseCase.getPlanets();
        return planetMapper.mapListOfPlanetsToListOfPlanetsOut(planets);
    }

}
