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

    public StarShip placeProbe(String planetId, int x, int y, OrientationEnum orientationEnum, String name) {
        Planet planet = planetInMemory.getPlanet(planetId);
        Probe probe = new Probe(orientationEnum, name, x, y);
        planet.placeStarShip(x, y, probe);
        return probe;
    }

    public StarShip moveProbe(String planetId, String probeName, String command) {
        Planet planet = planetInMemory.getPlanet(planetId);
        StarShip probe = getProbe(planetId, probeName);
        command.toUpperCase().chars().mapToObj(c->(char) c).forEach(character -> doCommand(probe, planet, character));
        return probe;
    }

    public StarShip getProbe(String planetId, String probeName) {
        Planet planet = planetInMemory.getPlanet(planetId);
        StarShip starShip = planet.getStarShip(probeName);
        if (starShip instanceof Probe probe) {
            return probe;
        } else {
            throw new BusinessException(BusinessError.PROBE_NOT_FOUND);
        }
    }

    private void doCommand(StarShip starShip, Planet planet, char command) {
        switch (command) {
            case 'L', 'R' -> starShip.rotate(command);
            case 'M' -> planet.moveProbe(starShip);
            default -> throw new BusinessException(BusinessError.PLACEMENT_NOT_ALLOWED_OCUPPIED);
        }
    }

}
