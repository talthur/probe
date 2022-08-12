package com.talthur.project.core.usecase;

import com.talthur.project.core.domain.Coordinates;
import com.talthur.project.core.domain.Planet;
import com.talthur.project.dataprovider.PlanetInMemory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PlanetUseCase {

    private final PlanetInMemory planetInMemory;

    public Planet createPlanet(String name, Coordinates coordinates) {
        Planet planet = new Planet(name, coordinates);
        planetInMemory.save(planet);
        return planet;
    }

    public List<Planet> getPlanets() {
        return planetInMemory.getAll();
    }

}
