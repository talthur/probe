package com.talthur.project.core.usecase;

import com.talthur.project.core.domain.Coordinates;
import com.talthur.project.core.domain.Planet;
import com.talthur.project.core.domain.Probe;
import com.talthur.project.core.enums.Direction;
import com.talthur.project.core.exception.BusinessException;
import com.talthur.project.core.exception.errors.BusinessError;
import com.talthur.project.dataprovider.PlanetInMemory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ProbeUseCase {

    private final PlanetInMemory planetInMemory;

    public Probe createProbeOnPlanet(String planetName, Coordinates coordinates, Direction direction, String probeName) {
        Planet planet = planetInMemory.getPlanet(planetName);
        return new Probe(direction, probeName, coordinates, planet);
    }

    public Probe getProbe(String planetName, String probeName) {
        Planet planet = planetInMemory.getPlanet(planetName);
        Probe probe = planet.getProbes().get(probeName);
        if (Objects.isNull(probe)) {
            throw new BusinessException(BusinessError.PROBE_NOT_FOUND);
        }
        return probe;
    }

    public Probe moveProbe(String planetName, String probeName, String commands) {

        Planet planet = planetInMemory.getPlanet(planetName);
        Probe probe = planet.getProbes().get(probeName);
        commands.toUpperCase().chars().mapToObj(c -> (char) c).forEach(command -> probe.commandProbe(command, planet));
        return probe;
    }

}
