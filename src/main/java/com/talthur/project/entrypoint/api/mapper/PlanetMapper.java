package com.talthur.project.entrypoint.api.mapper;

import com.talthur.project.core.domain.Planet;
import com.talthur.project.entrypoint.api.payload.PlanetOut;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class PlanetMapper {

    public List<PlanetOut> mapListOfPlanetsToListOfPlanetsOut(List<Planet> planets) {
        return planets.stream().map(planet -> PlanetOut.builder()
            .id(planet.getId())
            .x(planet.getArea().length)
            .y(Arrays.stream(planet.getArea()).map(squares -> squares.length).findAny().orElse(0))
            .probeNames(new ArrayList<>(planet.getStarShips().keySet())).build()).toList();
    }

    public PlanetOut mapPlanetToPlanetsOut(Planet planet) {

        return PlanetOut.builder()
            .id(planet.getId())
            .y(planet.getArea().length)
            .x(Arrays.stream(planet.getArea()).map(squares -> squares.length).findAny().orElse(0))
            .probeNames(new ArrayList<>(planet.getStarShips().keySet())).build();
    }

}
