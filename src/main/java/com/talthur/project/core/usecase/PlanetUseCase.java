package com.talthur.project.core.usecase;

import com.talthur.project.core.domain.Planet;
import com.talthur.project.core.domain.Probe;
import com.talthur.project.core.domain.StarShip;
import com.talthur.project.core.enums.OrientationEnum;
import com.talthur.project.core.exception.BusinessException;
import com.talthur.project.core.exception.errors.BusinessError;
import com.talthur.project.dataprovider.PlanetInMemory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PlanetUseCase {

    private final PlanetInMemory planetInMemory;

    public Planet createPlanet(int x, int y) {
        Planet planet = new Planet(x, y);
        planetInMemory.save(planet);
        return planet;
    }

    public List<Planet> getPlanets() {
        return planetInMemory.getAll();
    }

    public Probe placeProbe(String planetId, int x, int y, OrientationEnum orientationEnum, String name) {
        Planet planet = planetInMemory.getPlanet(planetId);
        Probe probe = new Probe(orientationEnum, name, x, y);
        planet.placeStarShip(x, y, probe);
        return probe;
    }

    public Probe moveProbe(String planetId, String probeName, String command) {
        Probe probe = getProbe(planetId, probeName);
    }

    private Probe getProbe(String planetId, String probeName) {
        Planet planet = planetInMemory.getPlanet(planetId);
        StarShip starShip = planet.getStarShip(probeName);
        if (starShip instanceof Probe probe) {
            return probe;
        } else {
            throw new BusinessException(BusinessError.PROBE_NOT_FOUND);
        }
    }
}
