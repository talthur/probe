package com.talthur.project.dataprovider;

import com.talthur.project.core.domain.Planet;
import com.talthur.project.core.exception.BusinessException;
import com.talthur.project.core.exception.errors.BusinessError;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PlanetInMemory {

    private static List<Planet> planetList = new ArrayList<>();

    public void save(Planet planet) {
        planetList.add(planet);
    }

    public void updatePlanet(String planetId, Planet planet) {
        Planet planetToBeUpdated = getPlanet(planetId);
        int index = planetList.indexOf(planetToBeUpdated);
        planetList.set(index, planet);
    }

    //Caso fosse um repositório real, criar paginação
    public List<Planet> getAll() {
        return planetList;
    }

    public Planet getPlanet(String planetName) {
        return planetList.stream().filter(planet -> planet.getName().equals(planetName)).findAny().orElseThrow(() -> new BusinessException(
            BusinessError.PLANET_NOT_EXIST));
    }

}
