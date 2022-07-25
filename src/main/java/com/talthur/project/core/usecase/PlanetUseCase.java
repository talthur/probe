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

    public StarShip moveStarShip(String planetId, String probeName, String command) {
        Planet planet = planetInMemory.getPlanet(planetId);
        StarShip probe = getStarShip(planetId, probeName);
        command.toUpperCase().chars().mapToObj(c -> (char) c).forEach(character -> doCommand(probe, planet, character));
        return probe;
    }

    public StarShip getStarShip(String planetId, String probeName) {
        Planet planet = planetInMemory.getPlanet(planetId);
        return planet.getStarShip(probeName);
    }

    private void doCommand(StarShip starShip, Planet planet, char command) {
        switch (command) {
            case 'L', 'R' -> starShip.rotate(command);
            case 'M' -> {
                planet.moveProbe(starShip);
                planetInMemory.updatePlanet(planet.getId(), planet);
            }
            default -> throw new BusinessException(BusinessError.INVALID_COMMAND);
        }
    }

}
